package org.tonzoc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tonzoc.mapper.MakeUpAttendanceApplyMapper;
import org.tonzoc.mapper.PersonMapper;
import org.tonzoc.model.MakeUpAttendanceApplyModel;
import org.tonzoc.model.PersonModel;
import org.tonzoc.model.util.ResultJson;
import org.tonzoc.service.IMakeUpAttendanceApplyService;
import org.tonzoc.support.param.SqlQueryParam;

import java.util.ArrayList;
import java.util.List;

@Service(value = "makeUpAttendanceApplyService")
public class MakeUpAttendanceApplyService extends BaseService<MakeUpAttendanceApplyModel> implements IMakeUpAttendanceApplyService {

    //人员
    @Autowired
    private PersonMapper personMapper;

    //人员
    @Autowired
    private MakeUpAttendanceApplyMapper makeUpAttendanceApplyMapper;

    @Override
    public MakeUpAttendanceApplyModel save(MakeUpAttendanceApplyModel makeUpAttendanceApplyModel) {
        makeUpAttendanceApplyModel.setCurrentPersonId(makeUpAttendanceApplyModel.getPersonGuid());
        makeUpAttendanceApplyModel.setStatus("unsubmit");
        String openId = makeUpAttendanceApplyModel.getOpenId();
        List<SqlQueryParam> sqlQueryParams = new ArrayList<>();
        sqlQueryParams.add(new SqlQueryParam("openId", openId, "eq"));
        List<PersonModel> personModelList = personMapper.selectAll(sqlQueryParams);
        PersonModel personModel = personModelList.get(0);
        String personGuid = personModel.getGuid();
        makeUpAttendanceApplyModel.setPersonGuid(personGuid);
        makeUpAttendanceApplyModel.setCurrentPersonId(personGuid);
        System.out.println("personModel" + personModel);
        String departmentId = personModel.getDepartmentId();
        makeUpAttendanceApplyModel.setCurrentDepartId(departmentId);
        return super.save(makeUpAttendanceApplyModel);
    }

    @Override
    public ResultJson queryExamineApply(String openId) {
        ResultJson resultJson = new ResultJson();
        List<SqlQueryParam> sqlQueryParams = new ArrayList<>();
        sqlQueryParams.add(new SqlQueryParam("openId", openId, "eq"));
        List<PersonModel> personModelList = personMapper.selectAll(sqlQueryParams);
        PersonModel personModel = personModelList.get(0);
        String personGuid = personModel.getGuid();
        List<MakeUpAttendanceApplyModel> examineApplyList = makeUpAttendanceApplyMapper.queryExamineApply5Day(personGuid);
        System.out.println("examineApplyList" + examineApplyList);
        System.out.println(examineApplyList.equals(null));
        System.out.println(examineApplyList.isEmpty());
        if (examineApplyList == null || examineApplyList.isEmpty()) {
            resultJson.setCode("400");
            resultJson.setMsg("当前人员没有需要审批的补考勤请求");
        } else {
            resultJson.setCode("200");
            resultJson.setMsg("返回当前人员需要审批的补考勤请求");
            resultJson.setObj(examineApplyList);
        }
        return resultJson;
    }

    //    保存审核通过的补考勤到考勤表
    public void saveMakeUpApplyToAttendance(String guid) {
        MakeUpAttendanceApplyModel makeUpAttendanceApplyModel = makeUpAttendanceApplyMapper.selectOne(guid);
        String fisrtTime = makeUpAttendanceApplyModel.getFirstMakeAttendance();
        String secondTime=makeUpAttendanceApplyModel.getSecondMakeAttendance();

    }
}
