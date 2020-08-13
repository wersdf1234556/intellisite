package org.tonzoc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.tonzoc.controller.params.LeaveTypeQueryParams;
import org.tonzoc.model.LeaveApplyModel;
import org.tonzoc.model.LeaveTypeModel;
import org.tonzoc.model.PersonModel;
import org.tonzoc.model.util.ResultJson;
import org.tonzoc.service.ILeaveApplyService;
import org.tonzoc.service.ILeaveTypeService;
import org.tonzoc.service.IPersonService;
import org.tonzoc.support.param.SqlQueryParam;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "leaveApply")
public class LeaveApplyController extends BaseController {
    @Autowired
    private ILeaveApplyService leaveApplyService;

    @Autowired
    private ILeaveTypeService leaveTypeService;

    @Autowired
    private ILeaveApplyService applyService;
    @Autowired
    private IPersonService personService;

    /**
     * 查看请假类型
     *已布
     * @return
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    @GetMapping(value = "ListLeaveType")
    public List<LeaveTypeModel> ListLeaveType() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        LeaveTypeQueryParams leaveTypeQueryParams = null;
        List<SqlQueryParam> sqlQueryParams = parseSqlQueryParams(leaveTypeQueryParams);
        List<LeaveTypeModel> leaveTypeModelList = leaveTypeService.list(sqlQueryParams);
        return leaveTypeModelList;
    }

    /**
     * 保存请假请求
     *已布
     * @param leaveApplyModel
     * @return
     */
    @PostMapping(value = "saveLeaveApply")
    public ResultJson saveLeaveApply(@RequestBody LeaveApplyModel leaveApplyModel) throws ParseException {
        System.out.println(leaveApplyModel);
        ResultJson resultJson = new ResultJson();
        try {
            LeaveApplyModel leaveApplyModel1 = this.leaveApplyService.save(leaveApplyModel);
            resultJson.setCode("200");
            resultJson.setMsg("保存请假请求成功");
            resultJson.setObj(leaveApplyModel1);
        } catch (Exception e) {
            e.printStackTrace();
            resultJson.setCode("444");
            resultJson.setMsg("操作失败");
        }
        return resultJson;
    }


    /**
     * 审批人查看近五天请假请求列表
     *8-4还没调用
     * @param openId
     * @return
     */
    @PostMapping(value = "examineLeaveApply")
    public ResultJson examineLeaveApply(@RequestParam(value = "openId") String openId) {
        ResultJson resultJson = new ResultJson();
        try {
            resultJson = leaveApplyService.queryLeaveExamineApply(openId);
        } catch (Exception e) {
            e.printStackTrace();
            resultJson.setCode("444");
            resultJson.setMsg("操作失败");
        }
        return resultJson;
    }





    /**
     * 用户查看请假记录(从今天往后的)
     *已布
     * @param openId
     * @return
     */
    @PostMapping(value = "leaveApplyList")
    public ResultJson leaveApplyList(@RequestParam(value = "openId") String openId) {
        ResultJson resultJson = new ResultJson();
        try {
            List<LeaveApplyModel> leaveApplyModelList = leaveApplyService.leaveApplyList(openId);
            resultJson.setCode("200");
            resultJson.setMsg("用户查看请假记录(从今天往后的)");
            resultJson.setObj(leaveApplyModelList);
        } catch (Exception e) {
            e.printStackTrace();
            resultJson.setCode("444");
            resultJson.setMsg("操作失败");
        }
        return resultJson;
    }

    /**
     *  查看副总列表
     */

    @GetMapping(value = "queryMananger")
    public ResultJson queryMananger() {
        ResultJson resultJson = new ResultJson();
        try {
            List<SqlQueryParam>sqlQueryParams=new ArrayList<>();
            sqlQueryParams.add(new SqlQueryParam("flag","2","eq"));
            List<PersonModel> personModelList=personService.list(sqlQueryParams);
            resultJson.setCode("200");
            resultJson.setMsg("返回副总列表");
            resultJson.setObj(personModelList);
        } catch (Exception e) {
            e.printStackTrace();
            resultJson.setCode("444");
            resultJson.setMsg("操作失败");
        }
        return resultJson;
    }
}
