package org.tonzoc.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.tonzoc.common.HttpHelper;
import org.tonzoc.mapper.MechanicsRealtimeMapper;
import org.tonzoc.model.*;
import org.tonzoc.service.IMechanicsDataRealTimeService;
import org.tonzoc.service.IMechanicsRealtimeService;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("mechanicsRealtimeService")
public class MechanicsRealtimeService extends BaseService<MechanicsRealtimeModel> implements IMechanicsRealtimeService {

    @Autowired
    private MechanicsRealtimeMapper mechanicsRealtimeMapper;

    @Autowired
    private IMechanicsDataRealTimeService mechanicsDataRealTimeService;

    @Value("${app_key}")
    private String app_key;

    @Value("${app_secret}")
    private String app_secret;

    @Value("${tenant_key}")
    private String tenant_key;

    // 查询机械状态
    public List<MechanicsRealtimeModel> MechanicState() throws IOException, ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, -1);
        Date start = c.getTime();
        String qyt = format.format(start);//前一天日期

        Date date = format.parse(qyt);
        long ts = date.getTime();
        String res = String.valueOf(ts);

        StringBuffer stringBuffer = new StringBuffer("https://open-api.zhgcloud.com/api/v2/tenant/monitor/real-time?");
        Map<String, String> map = new HashMap<>();
        map.put("app_key", app_key);
        map.put("app_secret", app_secret);
        map.put("tenant_key", tenant_key);
        map.put("timestamp", res.substring(0, res.length() - 3));

        String str = HttpHelper.doGet(stringBuffer.toString(), map, "GET");

        JSONObject returnedModel = JSONObject.parseObject(str);
        String data = returnedModel.getString("result");
        JSONObject result = JSONObject.parseObject(data);
        if (result == null) {
            return null;
        }

        List<MechanicsRealtimeModel> list = JSONArray.parseArray(result.getString("data_list"), MechanicsRealtimeModel.class);

        return list;
    }

    //添加机械状态
    public String MechanicStateadd() throws IOException, ParseException {
        List<MechanicsRealtimeModel> list = this.MechanicState();
        Integer maxSortId = mechanicsRealtimeMapper.maxSortId();
        if (maxSortId == null) {
            maxSortId = 0;
        }

        List<MechanicsRealtimeModel> list1 = new ArrayList<>();
        List<MechanicsDataRealTimeModel> list2 = new ArrayList<>();

        for (MechanicsRealtimeModel li : list) {
            UUID uuid = UUID.randomUUID();
            String guid = uuid.toString().toUpperCase();
            MechanicsRealtimeModel mechanicsRealtimeModel = new MechanicsRealtimeModel();
            mechanicsRealtimeModel.setGuid(guid);
            mechanicsRealtimeModel.setMachine_key(li.getMachine_key());
            mechanicsRealtimeModel.setMachine_name(li.getMachine_name());
            mechanicsRealtimeModel.setOnline_state(li.getOnline_state());
            mechanicsRealtimeModel.setSortId(maxSortId + 1);

            list1.add(mechanicsRealtimeModel);

            if (li.getData_real_time() != null) {
                List<MechanicsDataRealTimeModel> dataRealTime = Arrays.asList(li.getData_real_time());

                for (MechanicsDataRealTimeModel dataRealTimeModel : dataRealTime) {
                    MechanicsDataRealTimeModel mechanicsDataRealTimeModel = new MechanicsDataRealTimeModel();
                    UUID uuid1 = UUID.randomUUID();
                    String guid1 = uuid1.toString().toUpperCase();
                    mechanicsDataRealTimeModel.setGuid(guid1);
                    mechanicsDataRealTimeModel.setRealTimeId(guid);
                    mechanicsDataRealTimeModel.setState(dataRealTimeModel.getState());
                    mechanicsDataRealTimeModel.setDevice_state(dataRealTimeModel.getDevice_state());
                    mechanicsDataRealTimeModel.setStanding_time(dataRealTimeModel.getStanding_time());
                    mechanicsDataRealTimeModel.setState_update_at(dataRealTimeModel.getState_update_at());
                    mechanicsDataRealTimeModel.setAddress(dataRealTimeModel.getAddress());
                    mechanicsDataRealTimeModel.setLongitude(dataRealTimeModel.getLongitude());
                    mechanicsDataRealTimeModel.setLatitude(dataRealTimeModel.getLatitude());
                    mechanicsDataRealTimeModel.setLast_location_at(dataRealTimeModel.getLast_location_at());
                    mechanicsDataRealTimeModel.setSortId(maxSortId + 1);

                    list2.add(mechanicsDataRealTimeModel);

                    if (list2.size() % 1000 == 0) {
                        mechanicsDataRealTimeService.saveMany(list2);
                        list2.clear();
                    }
                }
            }
        }
        this.saveMany(list1);
        mechanicsDataRealTimeService.saveMany(list2);
        return "200";
    }

}
