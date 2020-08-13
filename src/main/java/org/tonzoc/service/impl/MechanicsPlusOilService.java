package org.tonzoc.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.tonzoc.common.HttpHelper;
import org.tonzoc.model.MechanicsPlusOilModel;
import org.tonzoc.service.IMechanicsPlusOilService;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;


@Service("mechanicsPlusOilService")
public class MechanicsPlusOilService extends BaseService<MechanicsPlusOilModel> implements IMechanicsPlusOilService {

    @Value("${app_key}")
    private String app_key;

    @Value("${app_secret}")
    private String app_secret;

    @Value("${tenant_key}")
    private String tenant_key;

    public List<MechanicsPlusOilModel> plusOil(String start_dt, String end_dt, String page, String count_per_page) throws IOException {

        StringBuffer stringBuffer = new StringBuffer("https://open-api.zhgcloud.com/api/v1/refuel-records?");
        Map<String, String> map = new HashMap<>();
        map.put("app_key", app_key);
        map.put("app_secret", app_secret);
        map.put("tenant_key", tenant_key);
        map.put("count_per_page", "0");
        if (start_dt != null) {
            map.put("start_dt", start_dt);
        }
        if (end_dt != null) {
            map.put("end_dt", end_dt);
        }
        String str = HttpHelper.doGet(stringBuffer.toString(), map, "GET");

        JSONObject returnedModel = JSONObject.parseObject(str);

        List<MechanicsPlusOilModel> list = JSONArray.parseArray(returnedModel.getString("data_list"), MechanicsPlusOilModel.class);

        return list;
    }

    public String plusOilAdd(String start_dt, String end_dt, String page, String count_per_page) throws IOException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, -1);
        Date start = c.getTime();
        String qyt = format.format(start); //前一天

        c.setTime(new Date());
        Date end = c.getTime();
        String dqrq = format.format(end); //当前日期

        List<MechanicsPlusOilModel> list = this.plusOil(qyt, dqrq, page, count_per_page);
        if (list != null) {
            this.saveMany(list);
        }
        return "200";
    }
}
