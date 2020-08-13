package org.tonzoc.service.impl;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tonzoc.mapper.ValidateCodeMapper;
import org.tonzoc.model.AttendanceDetailModel;
import org.tonzoc.model.ValidateCodeModel;
import org.tonzoc.model.util.HttpUtils;
import org.tonzoc.model.util.ResultJson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

@Service(value = "juheService")
public class JuheService {
    @Autowired
    private ValidateCodeMapper validateCodeMapper;


    public ResultJson sendSms(String telNumber) {
        ResultJson resultJson=new ResultJson();
        ValidateCodeModel validateCodeModel = new ValidateCodeModel();
        Date now = new Date();
        System.out.println("now" + now);
        //有效期3分钟之后
        Date validTime = new Date(now.getTime() + 300000);
        System.out.println("validTime" + validTime);
        //6位随机验证码
        int validateCode = (int) ((Math.random() * 9 + 1) * 100000);
        validateCodeModel.setCode(validateCode);
        validateCodeModel.setValidTime(validTime);
        validateCodeModel.setTelNumber(telNumber);
        validateCodeMapper.insert(validateCodeModel);
        String host = "https://dxyzm.market.alicloudapi.com";
        String path = "/chuangxin/dxjk";
        String method = "POST";
        String appcode = "1cad190607b54591aacd7f9067ade187";
        Map<String, String> headers = new HashMap<>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<>();
        //测试可用默认短信模板,测试模板为专用模板不可修改,如需自定义短信内容或改动任意字符,请联系旺旺或QQ726980650进行申请
//        querys.put("content", "【E时代】" + smsModel.getUserName() + "于" + smsModel.getTime() + "登录了" + smsModel.getProjectName() + "系统1。");
        querys.put("content", "【考勤系统】验证码：" + validateCode + "(有效期为3分钟)，请勿泄露给他人，如非本人操作，请忽略此信息。 ");
        querys.put("mobile", telNumber);
        Map<String, String> bodys = new HashMap<>();
        try {
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            System.out.println("发送验证结果"+response);
            String responsebBody = EntityUtils.toString(response.getEntity());
            JSONObject jsonObject= JSONObject.parseObject(responsebBody);
            String returnStatus = (String) jsonObject.get("ReturnStatus");
            System.out.println("returnStatus"+returnStatus);
            if (returnStatus.equals("Success")){
                resultJson.setCode("200");
                resultJson.setMsg("发送成功");
            }else {
                resultJson.setCode("400");
                resultJson.setMsg("发送失败");
            }

        } catch (Exception e) {
            e.printStackTrace();
            resultJson.setCode("444");
            resultJson.setMsg("操作作败");
        }
        return resultJson;
    }

    private String inputStreamToString(InputStream is) throws IOException {
        String s = "";
        String line = "";

        BufferedReader rd = new BufferedReader(new InputStreamReader(is));

        while ((line = rd.readLine()) != null) {
            s += line;
        }

        return s;


    }

    public void sendAttendanceMessagee(String openId, List<AttendanceDetailModel> absentList) {
        for (int i = 0; i <absentList.size(); i++) {
            AttendanceDetailModel attendanceDetailModel = absentList.get(i);
            String date = attendanceDetailModel.getWorkDate();

        }
        List<String> list=new ArrayList<>();
        list.add("oJfYQwAkj2c0TmVsbedOGlI4KSGI");
        String url="http://123.57.220.90:8035/sendTemplate";
        String data="{\n" +
        "           \"template_id\":\"m__TMpcmH0gsZsXxH8mTnMgJf0ZTFiNeaiudEit04nk\",\n" +
        "           \"url\":\"http://weixin.qq.com/download\",  \n" +
        "           \"data\":{\n" +
        "                   \"first\": {\n" +
        "                       \"value\":\"您好，您有一条考勤消息提醒\",\n" +
        "                       \"color\":\"#173177\"\n" +
        "                   },\n" +
        "                   \"keyword1\":{\n" +
        "                       \"value\":\"张亮\",\n" +
        "                       \"color\":\"#173177\"\n" +
        "                   },\n" +
        "                   \"keyword2\": {\n" +
        "                       \"value\":\"2017-7-28 09:06:21\",\n" +
        "                       \"color\":\"#173177\"\n" +
        "                   },\n" +
        "                   \"keyword3\": {\n" +
        "                       \"value\":\"迟到\",\n" +
        "                       \"color\":\"#173177\"\n" +
        "                   },\n" +
        "                   \"remark\":{\n" +
        "                       \"value\":\"点击查看详情\",\n" +
        "                       \"color\":\"#173177\"\n" +
        "                   }\n" +
        "           }\n" +
        " } ";
    }

}
