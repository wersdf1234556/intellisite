package org.tonzoc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tonzoc.mapper.PersonMapper;
import org.tonzoc.mapper.PersonTypeMapper;
import org.tonzoc.mapper.ValidateCodeMapper;
import org.tonzoc.model.PersonModel;
import org.tonzoc.model.PersonTypeModel;
import org.tonzoc.model.ValidateCodeModel;
import org.tonzoc.model.util.ResultJson;
import org.tonzoc.service.IValidateCodeService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service(value = "validateCodeService")
public class ValidateCodeService extends BaseService<ValidateCodeModel> implements IValidateCodeService {
    //验证码
    @Autowired
    private ValidateCodeMapper validateCodeMapper;
    //人员
    @Autowired
    private PersonMapper personMapper;
    //人员类型
    @Autowired
    private PersonTypeMapper personTypeMapper;

    @Override
    public ResultJson validateAndSaveOpenid(String telNumber, String validateCode, String openId, String idCard, String typeId, String companyId, String name) {
        ResultJson resultJson = new ResultJson();
        //当前时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        Date now = new Date();
        //查询数据库该手机号最近日期验证码
        ValidateCodeModel validateCodeModel = validateCodeMapper.select(telNumber, validateCode);
        if (validateCodeModel == null) {
            resultJson.setCode("403");
            resultJson.setMsg("验证码错误");
        } else {
            Date validTime = validateCodeModel.getValidTime();
            //验证码正确且有效
            if (validTime.getTime() > now.getTime()) {
                List<PersonTypeModel> personTypeModelList = personTypeMapper.selectLaowuAndLinshi();
                List<String> laoWutypeIdList = new ArrayList<>();
                for (int i = 0; i < personTypeModelList.size(); i++) {
                    PersonTypeModel personTypeModel = personTypeModelList.get(i);
                    String laoWutypeId = personTypeModel.getGuid();
                    laoWutypeIdList.add(laoWutypeId);
                }
                System.out.println(laoWutypeIdList);
                //是劳务人员/临时工
                if (laoWutypeIdList.contains(typeId)) {
                    PersonModel personModel = new PersonModel();
                    personModel.setTelNumber(telNumber);
                    personModel.setOpenId(openId);
                    personModel.setTypeId(typeId);
                    personModel.setIdCard(idCard);
                    personModel.setCompanyId(companyId);
                    personModel.setName(name);
                    System.out.println("劳务人员/临时工");
                    System.out.println(personModel);
                    try {
                        personMapper.insert(personModel);
                        resultJson.setCode("200");
                        resultJson.setMsg("注册成功");
                    } catch (Exception e) {
                        resultJson.setCode("401");
                        resultJson.setMsg("注册失败");
                        e.printStackTrace();
                    }
                    //是机关人员/项目管理人员
                } else {
                    System.out.println("机关人员/项目管理人员");
                    PersonModel personModel = null;
                    personModel = personMapper.selectByIdCard(idCard);
                    personModel.setTelNumber(telNumber);
                    personModel.setOpenId(openId);
                    System.out.println(personModel);
                    try {
                        personMapper.update(personModel);
                        resultJson.setCode("200");
                        resultJson.setMsg("注册成功");
                    } catch (Exception e) {
                        e.printStackTrace();
                        resultJson.setCode("401");
                        resultJson.setMsg("注册失败");
                    }
                }
            } else {
                resultJson.setCode("406");
                resultJson.setMsg("验证码超时无效");
            }
        }
        return resultJson;
    }
}
