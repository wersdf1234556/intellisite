package org.tonzoc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.tonzoc.exception.PageException;
import org.tonzoc.model.PersonModel;
import org.tonzoc.model.util.ResultJson;
import org.tonzoc.service.IFaceService;
import org.tonzoc.service.IPersonService;
import org.tonzoc.service.IValidateCodeService;
import org.tonzoc.service.impl.JuheService;
import org.tonzoc.support.param.SqlQueryParam;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * 小程序登录注册验证
 */
@RestController
@RequestMapping(value = "appletValidate")
public class AppletValidateController extends BaseController {
    //人员
    @Autowired
    private IPersonService personService;
    //发送验证码
    @Autowired
    private JuheService juheService;
    //验证码
    @Autowired
    private IValidateCodeService validateCodeService;
    //人脸照片
    @Autowired
    private IFaceService faceService;
    /**
     * 验证微信用户是否注册(openid是否存入)
     *
     * @param openId
     * @return
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    @PostMapping(value = "validateUser")
    public ResultJson validateUser(@RequestParam(value = "openId") String openId) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        System.out.println("验证微信用户是否注册(openid是否存入)");
        System.out.println("openId" + openId);
        ResultJson resultJson = new ResultJson();
        List<SqlQueryParam> sqlQueryParams = new ArrayList<>();
        sqlQueryParams.add(new SqlQueryParam("openId", openId, "eq"));
        try {
            List<PersonModel> personModelList = personService.list(sqlQueryParams);
            if (personModelList.size() == 0) {
                resultJson.setCode("200");
                resultJson.setMsg("未注册过");
                System.out.println(resultJson);
            } else if (personModelList.size() == 1) {
                PersonModel personModel=personModelList.get(0);
                System.out.println("personModel"+personModel);
                Integer count = faceService.countFace(personModel.getGuid());
                System.out.println("count"+count);
                //上传过照片
                if(count==1){
                    resultJson.setCode("400");
                    resultJson.setMsg("已注册过且已上传过照片");
                    resultJson.setObj(personModel);
                    //未上传照片
                }else if(count==0){
                    resultJson.setCode("402");
                    resultJson.setMsg("已注册过但未上传过照片 ");
                    resultJson.setObj(personModel);
                }else {
                    resultJson.setCode("403");
                    resultJson.setMsg("数据库中存在多条照片记录,请联系管理员");
                    resultJson.setObj(personModel);
                }
                System.out.println(resultJson);
            } else {
                resultJson.setCode("401");
                resultJson.setMsg("数据库中存在多条该openid身份数据,请联系管理员");
                System.out.println(resultJson);
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultJson.setCode("444");
            resultJson.setMsg("操作失败 ");
            System.out.println(resultJson);
        }
        return resultJson;
    }

    @PostMapping(value = "sendCode")
    public ResultJson sendMsg(@RequestParam(value = "telNumber") String telNumber) {
        System.out.println("调用发送短信验证码接口");
        ResultJson resultJson = new ResultJson();
        try {
            resultJson = juheService.sendSms(telNumber);
        } catch (Exception e) {
            e.printStackTrace();
            resultJson.setCode("444");
            resultJson.setMsg("操作失败");
        }
        return resultJson;
    }

    /**
     * 注册验证
     *
     * @param telNumber    电话号
     * @param openId
     * @param validateCode 短信验证码
     * @param idCard       身份证号
     * @param typeId       人员类型
     * @return
     * @throws PageException
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    @PostMapping(value = "validateRegister")
    public ResultJson validateRegister(@RequestParam(value = "telNumber") String telNumber,
                                       @RequestParam(value = "openId") String openId,
                                       @RequestParam(value = "validateCode") String validateCode,
                                       @RequestParam(value = "idCard") String idCard,
                                       @RequestParam(value = "typeId") String typeId,
                                       @RequestParam(value = "companyId") String companyId,
                                       @RequestParam(value = "name") String name
    ) throws PageException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        System.out.println("验证人员注册信息并登陆");
        System.out.println("telNumber" + telNumber);
        System.out.println("openId" + openId);
        System.out.println("validateCode" + validateCode);
        System.out.println("idCard" + idCard);
        System.out.println("typeId" + typeId);
        ResultJson resultJson = new ResultJson();
        try {
            resultJson = validateCodeService.validateAndSaveOpenid(telNumber, validateCode, openId, idCard, typeId,companyId,name);
        } catch (Exception e) {
            e.printStackTrace();
            resultJson.setCode("444");
            resultJson.setMsg("操作失败");
        }
        return resultJson;
    }

    /**
     * 根据身份证号验证人员类型
     *
     * @param idCard
     * @return
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    @PostMapping(value = "validatePersonType")
    public ResultJson validatePersonType(@RequestParam(value = "idCard") String idCard) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        System.out.println("根据身份证号验证人员类型");
        ResultJson resultJson = new ResultJson();
        try {
            resultJson = personService.getTypeByIdcard(idCard);
        } catch (Exception e) {
            e.printStackTrace();
            resultJson.setMsg("444");
            resultJson.setMsg("操作失败");
        }
        return resultJson;
    }

}
