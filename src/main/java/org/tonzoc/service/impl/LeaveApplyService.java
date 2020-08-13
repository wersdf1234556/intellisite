package org.tonzoc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tonzoc.mapper.AttendanceDetailMapper;
import org.tonzoc.mapper.LeaveApplyMapper;
import org.tonzoc.mapper.LeaveTypeMapper;
import org.tonzoc.mapper.PersonMapper;
import org.tonzoc.model.AttendanceDetailModel;
import org.tonzoc.model.LeaveApplyModel;
import org.tonzoc.model.PersonModel;
import org.tonzoc.model.PersonStatusModel;
import org.tonzoc.model.util.ResultJson;
import org.tonzoc.model.util.Utils;
import org.tonzoc.service.ILeaveApplyService;
import org.tonzoc.service.IPersonStatusService;
import org.tonzoc.support.param.SqlQueryParam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service(value = "leaveApplyService")
public class LeaveApplyService extends BaseService<LeaveApplyModel> implements ILeaveApplyService {
    //人员mapper
    @Autowired
    private PersonMapper personMapper;

    //请假mapper
    @Autowired
    private LeaveApplyMapper leaveApplyMapper;

    //请假累心mapper
    @Autowired
    private LeaveTypeMapper leaveTypeMapper;

    //考勤明细mapper
    @Autowired
    private AttendanceDetailMapper attendanceDetailMapper;

    @Autowired
    private IPersonStatusService personStatusService;


    @Override
    public LeaveApplyModel save(LeaveApplyModel leaveApplyModel) {

        leaveApplyModel.setLeaveStartTime(leaveApplyModel.getLeaveStartTime().substring(0,10));
        leaveApplyModel.setLeaveEndTime(leaveApplyModel.getLeaveEndTime().substring(0,10));
        String openId = leaveApplyModel.getOpenId();
        List<SqlQueryParam> sqlQueryParams = new ArrayList<>();
        sqlQueryParams.add(new SqlQueryParam("openId", openId, "eq"));
        List<PersonModel> personModelList = personMapper.selectAll(sqlQueryParams);
        if(personModelList.size()==1){
            PersonModel personModel = personModelList.get(0);
            leaveApplyModel.setPersonGuid(personModel.getGuid());
            leaveApplyModel.setCurrentDepartId(personModel.getDepartmentId());
            leaveApplyModel.setStatus("unsubmit");
            leaveApplyModel.setCurrentPersonId(personModel.getGuid());
        }
        return super.save(leaveApplyModel);
    }

    @Override
    public ResultJson queryLeaveExamineApply(String openId) {
        ResultJson resultJson = new ResultJson();
        PersonModel personModel = queryPerson(openId);
        String personGuid = personModel.getGuid();
        List<SqlQueryParam> sqlQueryParams = new ArrayList<>();
        sqlQueryParams.add(new SqlQueryParam("personGuid", personGuid, "eq"));
        List<LeaveApplyModel> examineApplyList = leaveApplyMapper.queryExamineApply5Day(personGuid);
        if (examineApplyList == null || examineApplyList.isEmpty()) {
            resultJson.setCode("400");
            resultJson.setMsg("当前人员没有需要审批的请假请求");
        } else {
            resultJson.setCode("200");
            resultJson.setMsg("返回当前人员需要审批的补考勤请求");
            resultJson.setObj(examineApplyList);
        }
        return resultJson;
    }

    /**
     * 保存人员请假到考勤表
     * @param guid
     * @throws ParseException
     */
    @Override
    @Transactional
    public void saveApplyToAttendance(String guid) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //        请假人员信息
        LeaveApplyModel leaveApplyModel = leaveApplyMapper.selectOne(guid);
        String personGuid = leaveApplyModel.getPersonGuid();
        PersonModel personModel = personMapper.selectOne(personGuid);
        //        获取开始时间和结束时间中间的天数
        Date startTime = sdf.parse(leaveApplyModel.getLeaveStartTime());
        Date endTime = sdf.parse(leaveApplyModel.getLeaveEndTime());
        Utils utils=new Utils();
        List<Date> dateList = utils.getBetweenDates(startTime, endTime);
        //        将该人员 这几天考勤存为请假
        for (int i = 0; i < dateList.size(); i++) {
            AttendanceDetailModel attendanceDetailModel = new AttendanceDetailModel();
            Date date = dateList.get(i);
            attendanceDetailModel.setPersonName(personModel.getName());
            attendanceDetailModel.setIdCard(personModel.getIdCard());
            attendanceDetailModel.setInState("5");
            attendanceDetailModel.setOutState("5");
            attendanceDetailModel.setInTime("null");
            attendanceDetailModel.setOutTime("null");
            attendanceDetailModel.setInAddress("null");
            attendanceDetailModel.setOutAddress("null");
            attendanceDetailModel.setWorkDate(sdf.format(date));
            attendanceDetailModel.setLeave(1);
            attendanceDetailMapper.insert(attendanceDetailModel);
        }
        //        判断请假状态是公出还是请假 将人员状态改为请假或公出
        String leaveTypeId = leaveApplyModel.getLeaveTypeId();
        String leaveTypeCode=leaveTypeMapper.selectOne(leaveTypeId).getCode();
        List<SqlQueryParam>sqlQueryParams=new ArrayList<>();
        if(leaveTypeCode.equals(0)){
            sqlQueryParams.add(new SqlQueryParam("code","3","eq"));
        }else {
            sqlQueryParams.add(new SqlQueryParam("code","2","eq"));
        }
        List<PersonStatusModel> personStatusModelList = personStatusService.list(sqlQueryParams);
        personModel.setStatusId(personStatusModelList.get(0).getGuid());
        personMapper.update(personModel);
    }

    //用户查看请假记录(从今天往后的)
    @Override
    public List<LeaveApplyModel> leaveApplyList(String openId) {
        ResultJson resultJson = new ResultJson();
        List<LeaveApplyModel> leaveApplyModelList = leaveApplyMapper.leaveApplyList(openId);
        return leaveApplyModelList;
    }

    public PersonModel queryPerson(String openId) {
        List<SqlQueryParam> sqlQueryParams = new ArrayList<>();
        sqlQueryParams.add(new SqlQueryParam("openId", openId, "eq"));
        List<PersonModel> personModelList = personMapper.selectAll(sqlQueryParams);
        PersonModel personModel=null;
        if(personModelList.size()==1){
             personModel = personModelList.get(0);
        }
        return personModel;
    }

}
