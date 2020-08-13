package org.tonzoc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tonzoc.exception.ModelNotFoundException;
import org.tonzoc.exception.NotOneResultFoundException;
import org.tonzoc.mapper.FlowPathMapper;
import org.tonzoc.model.*;
import org.tonzoc.service.*;
import org.tonzoc.support.param.SqlQueryParam;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service(value = "flowPathService")
public class FlowPathService extends BaseService<FlowpathModel> implements IFlowPathService {
    @Autowired
    private IDepartmentService departmentService;
    @Autowired
    private ILeaveApplyService leaveApplyService;
    @Autowired
    private IPersonService personService;
    @Autowired
    private FlowPathMapper flowPathMapper;
    @Autowired
    private IMakeUpAttendanceApplyService makeUpAttendanceApplyService;

    public FlowpathModel currentFlowpath(String leaveId,String personId,Integer isCompleted) throws Exception{
        List<SqlQueryParam> sqlQueryParams = new ArrayList<>();

        sqlQueryParams.add(new SqlQueryParam("leaveId", leaveId, "eq"));
        sqlQueryParams.add(new SqlQueryParam("personId", personId, "eq"));
        sqlQueryParams.add(new SqlQueryParam("isCompleted", isCompleted.toString(), "eq"));

        List<FlowpathModel> flowPathModels = list(sqlQueryParams);
        if (flowPathModels.size() >1) {
            throw new NotOneResultFoundException("符合条件的审批记录有多条");
        }else if (flowPathModels.size() ==0){
            throw new NotOneResultFoundException("没有符合条件的审批记录");
        }
        return flowPathModels.get(0);
    }
    public Integer countByLeaveId(String leaveId){
        List<SqlQueryParam> sqlQueryParams = new ArrayList<>();

        sqlQueryParams.add(new SqlQueryParam("leaveId", leaveId, "eq"));

        List<FlowpathModel> flowPathModels = list(sqlQueryParams);

        return flowPathModels.size();
    }

    //审批流程
    /*
    flowId:哪一种流程
    leaveId：请假表的guid
    nextStepId：传送给哪一步
    nextPersonOpenId：传送给哪一个人
    isBackward：0正常传送，1驳回
    */
    @Transactional
    public void approval(Integer sign,String leaveId,String currentPersonId,String nextPersonId) throws Exception {
        PersonModel currentPerson = personService.get(currentPersonId);
        PersonModel nextPerson=null;
        System.out.println("nextPersonId="+nextPersonId);
        if (nextPersonId!=null&&!nextPersonId.equals("")){
            nextPerson = personService.get(nextPersonId);
        }
        String nextDepartId="";
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        //2、调用get方法获取请假信息
        LeaveApplyModel leaveApplyModel=null;
        MakeUpAttendanceApplyModel makeUpAttendanceApplyModel = null;
        DepartmentModel currentDepartModel=null;
        if (sign==0){           //请假
            leaveApplyModel = leaveApplyService.get(leaveId);
            //3、 获取当前步骤的StepModel
            currentDepartModel = departmentService.get(leaveApplyModel.getCurrentDepartId());
        }else if (sign==1){    //补考勤
            makeUpAttendanceApplyModel= makeUpAttendanceApplyService.get(leaveId);
            System.out.println(makeUpAttendanceApplyModel.toString());
            //3、 获取当前步骤的StepModel
            currentDepartModel = departmentService.get(currentPerson.getDepartmentId());
        }
        if (currentDepartModel == null) {
            throw new ModelNotFoundException("提交人部门未找到");
        }
        if (nextPersonId!=""){
            nextDepartId=departmentService.get(nextPerson.getDepartmentId()).getGuid();
        }

        //如果这条请假/补考勤记录在flowpath表里不存在，则创建两条数据
        if (countByLeaveId(leaveId)==0){
            // 添加第一步已完成的flowpath
            FlowpathModel currentFlowPathModel = new FlowpathModel();
            currentFlowPathModel.setFlowId(sign);
            currentFlowPathModel.setLeaveId(leaveId);
            currentFlowPathModel.setPersonId(currentPerson.getGuid());
            currentFlowPathModel.setIsBackward(0);
            currentFlowPathModel.setIsCompleted(1);
            currentFlowPathModel.setSortId(1);
            currentFlowPathModel.setUpdated_at(currentTime);
            save(currentFlowPathModel);
            if (sign==0){
                // 更新状态
                leaveApplyModel.setCurrentDepartId(nextDepartId);
                leaveApplyModel.setCurrentPersonId(nextPersonId);
                leaveApplyModel.setStatus("submitted");
                leaveApplyService.update(leaveApplyModel);
            }else if (sign==1){
                System.out.println("nextDepartId="+nextDepartId);
                makeUpAttendanceApplyModel.setCurrentDepartId(nextDepartId);
                makeUpAttendanceApplyModel.setCurrentPersonId(nextPersonId);
                makeUpAttendanceApplyModel.setStatus("submitted");
                System.out.println(makeUpAttendanceApplyModel.toString());
                makeUpAttendanceApplyService.update(makeUpAttendanceApplyModel);
            }

        }else {
            FlowpathModel currentFlowPathModel = currentFlowpath(leaveId,currentPerson.getGuid(),0);
            // 更新当前flowpathModel
            currentFlowPathModel.setIsCompleted(1);
            //手动修改update时间

            currentFlowPathModel.setUpdated_at(currentTime);
            update(currentFlowPathModel);
        }

        Integer maxSortId = flowPathMapper.maxSortIdByLeaveId(leaveId);
        // 创建下一步的flowpathModel
        FlowpathModel nextFlowPathModel = new FlowpathModel();
        nextFlowPathModel.setLeaveId(leaveId);
        // 最后结束步骤有可能不传nextUserId，所以需要判断一下
        nextFlowPathModel.setPersonId(nextPersonId);
        nextFlowPathModel.setIsBackward(0);
        nextFlowPathModel.setIsCompleted(0);
        nextFlowPathModel.setSortId(maxSortId+1);
        nextFlowPathModel.setFlowId(sign);
        if (sign==0){
            // leaveRequstModel
            leaveApplyModel.setCurrentDepartId(nextDepartId);
            // 最后结束步骤有可能不传nextUserId，所以需要判断一下
            leaveApplyModel.setCurrentPersonId(nextPersonId);
            // 根据当前步骤类型，且在非退回步骤时，更新documentModel状态
            switch (currentPerson.getFlag()) {
                case 1:
                    leaveApplyModel.setStatus("supervised");
                    leaveApplyModel.setCurrentDepartId(nextDepartId);
                    leaveApplyModel.setCurrentPersonId(nextPersonId);
                    break;
            }
            //副总
//            switch (currentPerson.getFlag()) {
//                case 2:
//                    leaveApplyModel.setStatus("finish");
//                    break;
//            }
            if (nextPersonId==""||nextPersonId==null){
                leaveApplyModel.setStatus("finish");
                leaveApplyModel.setCurrentDepartId("0");
                // 最后结束步骤有可能不传nextUserId，所以需要判断一下
                leaveApplyModel.setCurrentPersonId("0");
                leaveApplyService.saveApplyToAttendance(leaveId);
            }
            leaveApplyService.update(leaveApplyModel);
        }else if (sign==1){

            // 根据当前步骤类型，且在非退回步骤时，更新documentModel状态
            switch (currentPerson.getFlag()) {
                case 1:
                    makeUpAttendanceApplyModel.setStatus("supervised");
                    makeUpAttendanceApplyModel.setCurrentDepartId(nextDepartId);
                    makeUpAttendanceApplyModel.setCurrentPersonId(nextPersonId);
                    break;
            }
            if (nextPersonId==""||nextPersonId==null) {
                makeUpAttendanceApplyModel.setStatus("finish");
                makeUpAttendanceApplyModel.setCurrentDepartId("0");
                // 最后结束步骤有可能不传nextUserId，所以需要判断一下
                makeUpAttendanceApplyModel.setCurrentPersonId("0");
            }
            makeUpAttendanceApplyService.update(makeUpAttendanceApplyModel);
        }
        // 如果下一步是结束步骤
        if (nextPersonId==""||nextPersonId==null) {
            // 首先把nextFlowPath的isCompleted更新，最后一步一定是completed状态
            nextFlowPathModel.setIsCompleted(1);

        }
        nextFlowPathModel.setUpdated_at(currentTime);
        save(nextFlowPathModel);

    }


    @Transactional(rollbackFor = Exception.class)
    //驳回flowId=0请假，flowId=1补考勤
    public void reject(String personId,String leaveId,Integer sign) throws Exception {
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        PersonModel currentPerson = personService.get(personId);
        Integer maxSortId = flowPathMapper.maxSortIdByLeaveId(leaveId);
        PersonModel personModel = personService.get(personId);
        FlowpathModel nextFlowPathModel = new FlowpathModel();
        PersonModel submitter ;
//        DepartmentModel submitterDepart;
        //判断是请假还是补考勤
        if(sign==0){
            //1、修改该条数据的状态
            LeaveApplyModel applyModel = leaveApplyService.get(leaveId);
            submitter = personService.get(applyModel.getPersonGuid());
//            submitterDepart = departmentService.get(submitter.getDepartmentId());
            if (applyModel.getStatus().equals("finish")){
                throw new ModelNotFoundException("该条请求已经审批结束，无法驳回");
            }
            if (applyModel.getPersonGuid()==personId){
                throw new ModelNotFoundException("自己提交的请求无法驳回");
            }
            //副总驳回
            if (currentPerson.getFlag()==2){
                applyModel.setStatus("managerRefus");
            }else if (currentPerson.getFlag()==1){
                applyModel.setStatus("rejected");
            }
            applyModel.setCurrentDepartId(submitter.getDepartmentId());
            applyModel.setCurrentPersonId(applyModel.getPersonGuid());
            leaveApplyService.update(applyModel);
            nextFlowPathModel.setPersonId(applyModel.getPersonGuid());
        }else if (sign==1){
            //1、修改该条数据的状态
            MakeUpAttendanceApplyModel applyModel = makeUpAttendanceApplyService.get(leaveId);
            submitter = personService.get(applyModel.getPersonGuid());
//            submitterDepart = departmentService.get(submitter.getDepartmentId());
            if (applyModel.getStatus().equals("finish")){
                throw new ModelNotFoundException("该条请求已经审批结束，无法驳回");
            }
            //副总驳回
            if (currentPerson.getFlag()==2){
                applyModel.setStatus("managerRefus");
            }else if (currentPerson.getFlag()==1){
                applyModel.setStatus("rejected");
            }
            applyModel.setCurrentDepartId(submitter.getDepartmentId());
            applyModel.setCurrentPersonId(applyModel.getPersonGuid());
            makeUpAttendanceApplyService.update(applyModel);
            nextFlowPathModel.setPersonId(applyModel.getPersonGuid());
        }
        //2、修改flowPath中该条数据最新的一条，并加一条驳回到提交人的数据
        FlowpathModel currentFlowPathModel = currentFlowpath(leaveId,currentPerson.getGuid(),0);
        currentFlowPathModel.setIsCompleted(1);
        currentFlowPathModel.setIsBackward(1);
        update(currentFlowPathModel);

        nextFlowPathModel.setIsBackward(0);
        nextFlowPathModel.setIsCompleted(0);
        nextFlowPathModel.setLeaveId(leaveId);
        nextFlowPathModel.setFlowId(0);
        nextFlowPathModel.setSortId(maxSortId+1);
        nextFlowPathModel.setUpdated_at(currentTime);
        save(nextFlowPathModel);

    }

}
