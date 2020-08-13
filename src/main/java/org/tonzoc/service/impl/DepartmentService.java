package org.tonzoc.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.tonzoc.model.DepartmentModel;
import org.tonzoc.model.LeaveApplyModel;
import org.tonzoc.model.PersonModel;
import org.tonzoc.service.*;
import org.tonzoc.support.param.SqlQueryParam;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service(value = "departmentService")
public class DepartmentService extends BaseService<DepartmentModel> implements IDepartmentService {
    @Autowired
    private IPersonService personService;
    @Autowired
    private IPersonTypeService personTypeService;
    @Autowired
    private ILeaveApplyService leaveApplyService;


    @Override
    public List<DepartmentModel> listByParentId(String parentId) {
        List<SqlQueryParam> sqlQueryParams = new ArrayList<>();
        sqlQueryParams.add(new SqlQueryParam("parentId", parentId, "eq"));

        List<DepartmentModel> roleAuthorityModels = this.list(sqlQueryParams);

        return roleAuthorityModels;
    }
    public List<DepartmentModel> listByFlowId(String flowId) {
        List<SqlQueryParam> sqlQueryParams = new ArrayList<>();
        sqlQueryParams.add(new SqlQueryParam("flowId", flowId, "eq"));

        List<DepartmentModel> departmentModels = this.list(sqlQueryParams);

        return departmentModels;
    }

    public List<DepartmentModel> listWithLevel(String flowId) throws Exception {
        List<DepartmentModel> list = new ArrayList<>();

        List<DepartmentModel> allDepartmentsByFlowId = null;

        Page page = PageHelper.startPage(1, 0, "parentId asc");
        page.setOrderByOnly(true);

        allDepartmentsByFlowId = this.listByFlowId(flowId);

        HashMap<String, DepartmentModel> departmentModelMap = new HashMap<>();
        while (allDepartmentsByFlowId.size() != 0) {
            int count = allDepartmentsByFlowId.size();
            Iterator<DepartmentModel> iterator = allDepartmentsByFlowId.iterator();

            while (iterator.hasNext()) {
                DepartmentModel departmentModel = iterator.next();

//                System.out.println(departmentModel);

                if (departmentModel.getParentId().equals("0") ) {
                    departmentModelMap.put(departmentModel.getGuid(), departmentModel);
                    iterator.remove();
                } else if (departmentModelMap.containsKey(departmentModel.getParentId())) {
                    DepartmentModel parentDepartmentModel = departmentModelMap.get(departmentModel.getParentId());
                    if (parentDepartmentModel.getChildren() == null) {
//                        System.out.println("dddd="+parentDepartmentModel.getChildren());
                        parentDepartmentModel.setChildren(new ArrayList<>());
//                        System.out.println("aaa="+parentDepartmentModel.getChildren());
                    }
                    parentDepartmentModel.getChildren().add(departmentModel);
                    departmentModelMap.put(departmentModel.getGuid(), departmentModel);
                    iterator.remove();
                }
            }
            if (count == allDepartmentsByFlowId.size()) {
                // TODO 需要自定义异常类型
                throw new Exception("fall into loop");
            }
        }

        for (DepartmentModel model : departmentModelMap.values()) {
            if (model.getParentId().equals("0")) {
                list.add(model);
            }
        }

        return list
                .stream()
                .sorted(Comparator.comparing(DepartmentModel::getSortId))
                .collect(Collectors.toList());
    }

    @Override
    public List<DepartmentModel> listNextDepart(Integer sign, String leaveId,String currentPersonId) throws Exception{
        List<DepartmentModel> nextDeparts = new ArrayList<>();
        //提交人
        PersonModel submitter=new PersonModel();
        DateFormat fmt =new SimpleDateFormat("yyyy-MM-dd");

        //1、判断是请假还是补考勤
        if (sign==0){

            //请假
            LeaveApplyModel applyModel = leaveApplyService.get(leaveId);
            submitter=personService.get(applyModel.getPersonGuid());
            Integer submitterTypeCode = personTypeService.get(submitter.getTypeId()).getCode();
            DepartmentModel currentDepart = get(submitter.getDepartmentId());
            Date leaveStartTime=fmt.parse(applyModel.getLeaveStartTime());
            Date leaveEndTime=fmt.parse(applyModel.getLeaveEndTime());
            //获取相隔天数
            Long days = (leaveEndTime.getTime() - leaveStartTime.getTime()) / (24 * 60 * 60 * 1000);

            //临时人员请假，最后一步是项目经理
            if (submitterTypeCode==3){
                nextDeparts.addAll(judgeNextDepart(submitter,currentPersonId,currentDepart,applyModel));
                return nextDeparts;
            }else if (submitterTypeCode==1&&submitter.getFlag()==0){//机关普通员工请假
                //机关普通人员请假，如果小于3天只需部门经理审批，如果大于3天则需副总审批
                //如果请假天数大于3天，则走全部流程
                if (days>3){
                    System.out.println("请假大于3天");
                    nextDeparts.addAll(judgeNextDepart(submitter,currentPersonId,currentDepart,applyModel));
                    return nextDeparts;
                }
                System.out.println("请假小于3天");
                nextDeparts.addAll(judgeNextDepart(submitter,currentPersonId,currentDepart,applyModel));
                return nextDeparts;
            }else if(submitterTypeCode==2&&submitter.getFlag()==0){//项目管理人员请假（除项目经理外）
                nextDeparts.addAll(judgeNextDepart(submitter,currentPersonId,currentDepart,applyModel));
                return nextDeparts;
            }

        }else {

        }
        return nextDeparts;
    }

    private List<DepartmentModel> judgeNextDepart(PersonModel submitter,String currentPersonId,DepartmentModel currentDepart,LeaveApplyModel applyModel){
        DepartmentModel nextDepartment =get(currentDepart.getGuid());
        PersonModel currentPersonModel = personService.get(currentPersonId);
        DepartmentModel finishModel = new DepartmentModel();
        finishModel.setGuid("0");
        finishModel.setName("结束");
        List<DepartmentModel> nextDeparts = new ArrayList<>();
        //先判断当前人是否是提交人，如果是当前人=提交人，则下一步为相同部门的flag！=0的人员
        if (submitter.getGuid().equals(currentPersonId)){
            nextDeparts.add(currentDepart);
        }else {
            if (currentPersonModel.getFlag()!=0){
                if (!currentDepart.getParentId().equals("0")){
                    nextDepartment =get(currentDepart.getParentId());
                    nextDeparts.add(nextDepartment);
                }else {
                    nextDeparts.add(finishModel);
                    //修改该条leaveId的status=‘finished’
                    applyModel.setStatus("finished");
                    leaveApplyService.update(applyModel);
                }
            }
        }
        return nextDeparts;
    }


}
