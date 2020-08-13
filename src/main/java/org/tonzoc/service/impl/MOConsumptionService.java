package org.tonzoc.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.tonzoc.common.HttpHelper;
import org.tonzoc.mapper.MOConsumptionHourMapper;
import org.tonzoc.model.*;
import org.tonzoc.service.IMOConsumptionHourService;
import org.tonzoc.service.IMOConsumptionService;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@Service(value = "MOConsumptionService")
public class MOConsumptionService extends BaseService<MOConsumptionModel> implements IMOConsumptionService {

    @Autowired
    private MOConsumptionHourMapper moConsumptionHourMapper;

    @Autowired
    private IMOConsumptionHourService moConsumptionHourService;

    @Value("${app_key}")
    private String app_key;

    @Value("${app_secret}")
    private String app_secret;

    @Value("${tenant_key}")
    private String tenant_key;


    public List<MOConsumptionModel> oilConsumption(String machine_key, String timestamp) throws IOException {
        StringBuffer stringBuffer = new StringBuffer("https://open-api.zhgcloud.com/api/v2/machine/fuel-consumptions/timestamp?");
        Map<String, String> map = new HashMap<>();
        map.put("app_key", app_key);
        map.put("app_secret", app_secret);
        map.put("tenant_key", tenant_key);
        map.put("timestamp", timestamp);

        String str = HttpHelper.doGet(stringBuffer.toString(), map, "GET");

        JSONObject returnedModel = JSONObject.parseObject(str);
        String data = returnedModel.getString("result");
        JSONObject result = JSONObject.parseObject(data);

        List<MOConsumptionModel> list = JSONArray.parseArray(result.getString("data_list"), MOConsumptionModel.class);

        return list;
    }

    //存前一天的油耗量
    public String oilConsumptionAdd(String machine_key) throws IOException, ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, -1);
        Date start = c.getTime();
        String qyt = format.format(start);//前一天日期

        Date date = format.parse(qyt);
        long ts = date.getTime();
        String res = String.valueOf(ts);

        List<MOConsumptionModel> list = this.oilConsumption(machine_key, res.substring(0, res.length() - 3));
        List<MOConsumptionModel> list1 = new ArrayList<>();

        for (MOConsumptionModel li : list) {
            if (qyt.substring(8, 10).equals(li.getDate().substring(8, 10))) {
                if (li.getOil() == null) {
                    li.setOil(BigDecimal.ZERO);
                }
                list1.add(li);
            }
        }

        this.saveMany(list1);

        return "200";
    }

    //按小时添加油耗记录
    public String oilConsumptionAddHour(String machine_key) throws IOException, ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, -1);
        Date start = c.getTime();
        String qyt = format.format(start); //前一天日期

        Date date = format.parse(qyt);
        long ts = date.getTime();
        String res = String.valueOf(ts);

        c.setTime(new Date());
        Date end = c.getTime();
        String dqrq = format.format(end);//当前日期

        Integer maxSortId = moConsumptionHourMapper.maxSortId();
        if (maxSortId == null) {
            maxSortId = 0;
        }
        List<MOConsumptionModel> list = this.oilConsumption(machine_key, res.substring(0, res.length() - 3));
        List<MOConsumptionHourModel> list1 = new ArrayList<>();

        if (list.size() > 0) {
            for (MOConsumptionModel li : list) {
                if (dqrq.substring(8, 10).equals(li.getDate().substring(8, 10))) {

                    MOConsumptionHourModel moConsumptionHourModel = new MOConsumptionHourModel();
                    moConsumptionHourModel.setDate(li.getDate());
                    if (li.getOil() == null) {
                        moConsumptionHourModel.setOil(BigDecimal.ZERO);
                    } else {
                        moConsumptionHourModel.setOil(li.getOil());
                    }
                    moConsumptionHourModel.setMachine_key(li.getMachine_key());
                    moConsumptionHourModel.setMachine_name(li.getMachine_name());
                    moConsumptionHourModel.setSortId(maxSortId + 1);
                    System.out.println("moConsumptionHourModel" + moConsumptionHourModel);
                    list1.add(moConsumptionHourModel);
                }
            }
            moConsumptionHourService.saveMany(list1);
        }

        return "200";
    }

}
