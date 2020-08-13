package org.tonzoc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.tonzoc.model.MakeUpAttendanceApplyModel;
import org.tonzoc.model.util.ResultJson;
import org.tonzoc.service.IAttendanceDetailService;
import org.tonzoc.service.IMakeUpAttendanceApplyService;
import org.tonzoc.service.IPersonService;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;


@RestController
@RequestMapping(value = "/makeUpAttendance")
public class MakeUpAttendanceController extends BaseController {
    //考勤表
    @Autowired
    private IAttendanceDetailService attendanceDetailService;
    //人员
    @Autowired
    private IPersonService personService;
//    补考勤申请记录
    @Autowired
    private IMakeUpAttendanceApplyService makeUpAttendanceApplyService;

    /**
     * 根据openid查询该人员近5天异常考勤,只返回未提交过补考勤申请的考勤记录
     *好使8-5
     * @param openId
     * @return
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    @PostMapping(value = "queryAbsent")
    public ResultJson queryAbsent(@RequestParam(value = "openId") String openId) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, ParseException {
        ResultJson resultJson=new ResultJson();
        try {
            resultJson=attendanceDetailService.queryAbsent(openId);
            System.out.println("queryAbsentresultJson"+resultJson);
        } catch (ParseException e) {
            e.printStackTrace();
            resultJson.setCode("404");
            resultJson.setMsg("操作失败");
        }
        return resultJson;
    }

    /**
     * 保存补考勤请求
     * 好使8-5
     * @param makeUpAttendanceApplyModel
     * @return
     */
    @PostMapping(value = "saveMakeUpAttendance")
    public ResultJson saveMakeUpAttendance(@RequestBody MakeUpAttendanceApplyModel makeUpAttendanceApplyModel) {
        System.out.println("保存补考勤请求");
        System.out.println(makeUpAttendanceApplyModel);
        ResultJson resultJson = new ResultJson();
        try {
            MakeUpAttendanceApplyModel makeUpAttendanceApplyModel1 = this.makeUpAttendanceApplyService.save(makeUpAttendanceApplyModel);
            resultJson.setCode("200");
            resultJson.setMsg("保存补考勤请求成功");
            resultJson.setObj(makeUpAttendanceApplyModel1);
        } catch (Exception e) {
            e.printStackTrace();
            resultJson.setCode("444");
            resultJson.setMsg("操作失败");
        }
        return resultJson;
    }

//    /**
//     * 修改补考勤请求
//     * @param makeUpAttendanceApplyModel
//     * @return
//     */
//    @PostMapping(value = "updateMakeUpAttendance")
//    public ResultJson updateMakeUpAttendance(@RequestBody MakeUpAttendanceApplyModel makeUpAttendanceApplyModel) {
//        System.out.println("保存补考勤请求");
//        System.out.println(makeUpAttendanceApplyModel);
//        ResultJson resultJson = new ResultJson();
//        try {
//            this.makeUpAttendanceApplyService.save(makeUpAttendanceApplyModel);
//            resultJson.setCode("200");
//            resultJson.setMsg("保存补考勤请求成功");
//        } catch (Exception e) {
//            e.printStackTrace();
//            resultJson.setCode("444");
//            resultJson.setMsg("操作失败");
//        }
//        return resultJson;
//    }




    /**
     * 查询近五天补考勤申请
     * 好使8-5
     * @param openId
     * @return
     */
    @PostMapping(value = "queryMakeUpAttendanceApply")
    public ResultJson makeUpAttendanceApply(@RequestParam (value = "openId")String openId){
        ResultJson resultJson=new ResultJson();
        try {
            resultJson=attendanceDetailService.queryApply(openId);
        } catch (Exception e) {
            e.printStackTrace();
            resultJson.setCode("444");
            resultJson.setMsg("操作失败");
        }
        System.out.println("resultJson"+resultJson);
        return resultJson;
    }

    /**
     * 审批人查询近五天提交补考勤申请
     * @param openId
     * @return
     */
    @PostMapping("examineMakeUpAttendanceApply")
    public ResultJson examineMakeUpAttendanceApply(@RequestParam (value = "openId")String openId){
        ResultJson resultJson=new ResultJson();
        try {
            resultJson= makeUpAttendanceApplyService.queryExamineApply(openId);
        } catch (Exception e) {
            e.printStackTrace();
            resultJson.setCode("444");
            resultJson.setMsg("操作失败");
        }
        return resultJson;

    }



}
