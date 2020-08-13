package org.tonzoc.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.tonzoc.controller.params.AttendanceDetailQueryParams;
import org.tonzoc.exception.PageException;
import org.tonzoc.model.AttendanceDetailModel;
import org.tonzoc.model.AttendanceRecordModel;
import org.tonzoc.model.PersonModel;
import org.tonzoc.model.util.ResultJson;
import org.tonzoc.service.IAttendanceDetailService;
import org.tonzoc.service.IAttendanceRecordService;
import org.tonzoc.service.IFaceService;
import org.tonzoc.service.IPersonService;
import org.tonzoc.service.impl.JuheService;
import org.tonzoc.support.param.SqlQueryParam;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(value = "faceManager")
public class FaceManagerController extends BaseController {

    //人脸接口
    @Autowired
    private IFaceService faceService;

    //人员service
    @Autowired
    private IPersonService personService;

    //人脸打卡记录接口
    @Autowired
    private IAttendanceRecordService attendanceRecordService;

    @Autowired
    private IAttendanceDetailService attendancedetailService;

    @Autowired
    private JuheService juheService;

    @Autowired
    private IAttendanceDetailService attendanceDetailService;






    //人脸注册
    @PostMapping(value = "faceAdd")
    public ResultJson faceAdd(@RequestParam(value = "openId") String openId, @RequestParam(value = "imageBASE64") String imageBASE64, HttpServletRequest request) {
        System.out.println("人脸识别添加");
        ResultJson resultJson = new ResultJson();
        try {
            List<SqlQueryParam>sqlQueryParams=new ArrayList<>();
            sqlQueryParams.add(new SqlQueryParam("openId",openId,"eq"));
            List<PersonModel> personModelList = personService.list(sqlQueryParams);
            if (personModelList.size()==1){
                PersonModel personModel=personModelList.get(0);
                String personGuid=personModel.getGuid();
                //根据personGuid查询人脸数据库中是否有记录
                Integer count = faceService.countFace(personGuid);
                if (count==0) {
                    String result = faceService.saveFace(personGuid, imageBASE64, request);
                    if (result.contains("SUCCESS")) {
                        resultJson.setCode("200");
                        resultJson.setMsg("人脸注册成功");
                    } else {
                        resultJson.setCode("400");
                        resultJson.setMsg("人脸注册失败");
                        resultJson.setObj(result);
                    }
                } else {
                    resultJson.setCode("401");
                    resultJson.setMsg("人脸注册失败,系统中已经存在此id人员人脸照片,请联系管理员");
                }
            }else if(personModelList.size()==0){
                resultJson.setCode("402");
                resultJson.setMsg("数据库中没有当前微信用户的openId,请联系管理员");
            }else {
                resultJson.setCode("403");
                resultJson.setMsg("数据库中存在多条当前微信用户的openId,请联系管理员");
            }

        } catch (Exception e) {
            e.printStackTrace();
            resultJson.setCode("404");
            resultJson.setMsg("人脸注册程序异常");
        }
        return resultJson;
    }

    //人脸更新
    @PostMapping(value = "faceUpdate")
    public ResultJson faceUpdate(@RequestParam(value = "personGuid") String personGuid, String imageBASE64, HttpServletRequest request) {
        System.out.println("faceUpdate");
        ResultJson resultJson = new ResultJson();
        try {
            String result = faceService.updateFace(personGuid, imageBASE64, request);
            if (result.contains("SUCCESS")) {
                resultJson.setCode("200");
                resultJson.setMsg("人脸更新成功");
            } else {
                resultJson.setCode("400");
                resultJson.setMsg("人脸更新失败");
                resultJson.setObj(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultJson.setCode("404");
            resultJson.setMsg("人脸更新程序异常");
        }
        return resultJson;
    }

    //人脸删除
    @PostMapping("faceDelete")
    public ResultJson faceDelete(String[] personGuids, HttpServletRequest request) {
        ResultJson resultJson = new ResultJson();
        try {
            String result = faceService.remove(personGuids, request);
            if (result.contains("SUCCESS")) {
                resultJson.setCode("200");
                resultJson.setMsg("人脸删除成功");
            } else {
                resultJson.setCode("400");
                resultJson.setMsg("人脸删除失败");
                resultJson.setObj(result);
            }
            return resultJson;
        } catch (Exception e) {
            e.printStackTrace();
            resultJson.setCode("404");
            resultJson.setMsg("人脸删除程序异常");
        }
        return null;
    }

    //人脸搜索+考勤
    @PostMapping(value = "faceSearch")
    public ResultJson faceSearch(@RequestParam(value = "imageBASE64") String imageBASE64, @RequestParam(value = "openId") String openId, @RequestParam(value = "address") String address) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        System.out.println("人脸考勤");
        System.out.println("openId" + openId);
        System.out.println("imageBASE64" + imageBASE64);
        System.out.println("address" + address);
        ResultJson resultJson = new ResultJson();
        List<SqlQueryParam> sqlQueryParams = new ArrayList<>();
        sqlQueryParams.add(new SqlQueryParam("openId", openId, "eq"));
        try {
            //根据openid查询人员
            List<PersonModel> personModelList = personService.list(sqlQueryParams);
            PersonModel personModel = personModelList.get(0);
            String name = personModel.getName();
            String idCard = personModel.getIdCard();
            //人脸识别考勤结果
            String result = faceService.faceSearch(imageBASE64, openId);
            //如果人脸对比成功
            if (result.contains("SUCCESS")) {
                //获取人脸对比分数
                Double score = faceService.resultGetScore(result);
                //80分以上为有效,开始保存记录到考勤记录表
                if (score >= 80) {
                    AttendanceRecordModel attendanceRecordModel = new AttendanceRecordModel();
                    attendanceRecordModel.setAddress(address);
                    attendanceRecordModel.setSource(1);
                    attendanceRecordModel.setIdCard(idCard);
                    //保存到考勤记录表
                    AttendanceRecordModel faceAttendanceRecordModel1 = attendanceRecordService.save(attendanceRecordModel);
                    if (faceAttendanceRecordModel1 != null) {
                        resultJson.setCode("200");
                        resultJson.setMsg(name + "人脸考勤成功");
                        resultJson.setObj(name);
                    }else {
                        resultJson.setObj("401");
                        resultJson.setMsg("人脸识别成功,但保存到考勤记录表失败");
                        resultJson.setObj(name);
                    }
                } else {
                    resultJson.setCode("405");
                    resultJson.setMsg("人脸对比分数过低,人脸考勤失败");
                    resultJson.setObj(result);
                }
            } else {
                resultJson.setCode("400");
                resultJson.setMsg("人脸考勤失败");
                resultJson.setObj(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultJson.setCode("404");
            resultJson.setMsg("人脸考勤程序异常");
        }
        return resultJson;
    }

//    //保存刷脸记录到考勤表
//    @GetMapping(value = "saveFaceAttendance")
//    public void saveFaceAttendance() throws ParseException {
//        attendanceRecordService.saveRecordToAttendance();
//    }



//    //保存未刷脸人到考勤表
//    @GetMapping(value = "saveNoFaceAttendance")
//    public void saveNoFaceAttendance() {
//        attendanceRecordService.saveNoRecordToAttendance();
//    }


//    @GetMapping(value = "tongbu")
//    public void tongbu() throws PageException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
//        AttendanceDetailQueryParams attendanceDetailQueryParams=null;
//        List<SqlQueryParam> sqlQueryParams = parseSqlQueryParams(attendanceDetailQueryParams);
//        List<AttendanceDetailModel> kaoqinlist = attendancedetailService.list(sqlQueryParams);
//        System.out.println("kaoqinlist长度"+kaoqinlist.size());
//        for (int i = 0; i <kaoqinlist.size() ; i++) {
//            AttendanceDetailModel attendanceDetailModel=kaoqinlist.get(i);
//            String code=attendanceDetailModel.getPersonCode();
//            if(code!=null){
//                List<PersonModel> personModellist=personService.selectBycode(code);
//                if(personModellist.size()>0){
//                        String guid=personModellist.get(0).getGuid();
//                        System.out.println("第"+i+"个里第"+0+"个"+attendanceDetailModel);
//                        attendancedetailService.update(attendanceDetailModel);
//                }
//
//            }
//        }
//        attendanceRecordService.saveNoRecordToAttendance();
//    }

//    //微信模板推送迟到提醒
//    @PostMapping("sendAttendanceMessagee")
//    public ResultJson sendAttendanceMessagee(){
//        ResultJson resultJson=new ResultJson();
//        try {
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return resultJson;
//    }


}
