package org.tonzoc.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tonzoc.common.HttpHelper;
import org.tonzoc.model.MechanicsWorkRecordsModel;
import org.tonzoc.model.MechanicsWorkTimeModel;
import org.tonzoc.model.RequestModel;
import org.tonzoc.service.IMechanicsWorkRecordsService;
import org.tonzoc.service.IMechanicsWorkTimeService;

import java.io.IOException;
import java.util.*;

@Service("mechanicService")
public class MechanicsWorkTimeService extends BaseService<MechanicsWorkTimeModel> implements IMechanicsWorkTimeService {

    @Autowired
    private IMechanicsWorkRecordsService mechanicsWorkRecordsService;

    public List<MechanicsWorkTimeModel> MechanicsTime(RequestModel requestModel, String timestamp) throws IOException {

        StringBuffer stringBuffer = new StringBuffer("https://open-api.zhgcloud.com/api/v2/tenant/statistic/work-time?");
        Map<String, String> map = new HashMap<>();
        map.put("app_key", requestModel.getApp_key());
        map.put("app_secret", requestModel.getApp_secret());
        map.put("tenant_key", requestModel.getTenant_key());
        map.put("timestamp", timestamp);

        String str = HttpHelper.doGet(stringBuffer.toString(), map, "GET");

        JSONObject returnedModel = JSONObject.parseObject(str);
        String data = returnedModel.getString("result");
        JSONObject result = JSONObject.parseObject(data);
        if (result == null) {
            return null;
        }
        List<MechanicsWorkTimeModel> list = JSONArray.parseArray(result.getString("data_list"), MechanicsWorkTimeModel.class);

        return list;
    }

    public String MechanicsTimeAdd(RequestModel requestModel, String timestamp) throws IOException {
        List<MechanicsWorkTimeModel> list = this.MechanicsTime(requestModel, timestamp);

        List<MechanicsWorkTimeModel> list1 = new ArrayList<>();
        List<MechanicsWorkRecordsModel> list2 = new ArrayList<>();

        for (MechanicsWorkTimeModel li : list) {
            UUID uuid = UUID.randomUUID();
            String guid = uuid.toString().toUpperCase();
            MechanicsWorkTimeModel mechanicsWorkTimeModel = new MechanicsWorkTimeModel();
            mechanicsWorkTimeModel.setGuid(guid);
            mechanicsWorkTimeModel.setDate(li.getDate());
            mechanicsWorkTimeModel.setIdle_time(li.getIdle_time());
            mechanicsWorkTimeModel.setMachine_key(li.getMachine_key());
            mechanicsWorkTimeModel.setMachine_name(li.getMachine_name());
            mechanicsWorkTimeModel.setOff_time(li.getOff_time());
            mechanicsWorkTimeModel.setTimestamp(li.getTimestamp());
            mechanicsWorkTimeModel.setWork_time(li.getWork_time());

            list1.add(mechanicsWorkTimeModel);

            if (li.getWork_records() != null) {
                List<MechanicsWorkRecordsModel> workRecords = Arrays.asList(li.getWork_records());

                for (MechanicsWorkRecordsModel workRecordsModel : workRecords) {
                    MechanicsWorkRecordsModel mechanicsWorkRecordsModel = new MechanicsWorkRecordsModel();
                    UUID uuid1 = UUID.randomUUID();
                    String guid1 = uuid1.toString().toUpperCase();
                    mechanicsWorkRecordsModel.setGuid(guid1);
                    mechanicsWorkRecordsModel.setWorkTimeId(guid);
                    mechanicsWorkRecordsModel.setStart_ts(workRecordsModel.getStart_ts());
                    mechanicsWorkRecordsModel.setEnd_ts(workRecordsModel.getEnd_ts());
                    mechanicsWorkRecordsModel.setState(workRecordsModel.getState());

                    list2.add(mechanicsWorkRecordsModel);

                    if (list2.size() % 1000 == 0) {
                        mechanicsWorkRecordsService.saveMany(list2);
                        list2.clear();
                    }
                }
            }
        }
        this.saveMany(list1);
        mechanicsWorkRecordsService.saveMany(list2);
        return "200";
    }

}
