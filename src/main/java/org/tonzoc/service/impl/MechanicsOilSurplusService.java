package org.tonzoc.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.tonzoc.common.HttpHelper;
import org.tonzoc.mapper.MechanicsOilSurplusMapper;
import org.tonzoc.model.MechanicsOilSurplusModel;
import org.tonzoc.service.IMechanicsOilSurplusService;

import java.io.IOException;
import java.util.*;

@Service("oilLevelRealTimeService")
public class MechanicsOilSurplusService extends BaseService<MechanicsOilSurplusModel> implements IMechanicsOilSurplusService {

    @Value("${app_key}")
    private String app_key;

    @Value("${app_secret}")
    private String app_secret;

    @Value("${tenant_key}")
    private String tenant_key;

    @Autowired
    private MechanicsOilSurplusMapper oilLevelRealTimeMapper;

    public List<MechanicsOilSurplusModel> oilLevel(Integer page, Integer count_per_page) throws IOException {

        StringBuffer stringBuffer = new StringBuffer("https://open-api.zhgcloud.com/api/v1/machines/fuel?");
        Map<String, String> map = new HashMap<>();
        map.put("app_key", app_key);
        map.put("app_secret", app_secret);
        map.put("tenant_key", tenant_key);


        String str = HttpHelper.doGet(stringBuffer.toString(), map, "GET");

        JSONObject returnedModel = JSONObject.parseObject(str);

        List<MechanicsOilSurplusModel> list = JSONArray.parseArray(returnedModel.getString("data_list"), MechanicsOilSurplusModel.class);

        return list;
    }

    public String oilLevelAdd(Integer page, Integer count_per_page) throws IOException {
        List<MechanicsOilSurplusModel> list = this.oilLevel(page, count_per_page);

        this.saveMany(list);

        return "200";
    }
}
