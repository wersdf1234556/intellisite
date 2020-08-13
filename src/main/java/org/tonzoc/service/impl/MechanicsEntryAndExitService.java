package org.tonzoc.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.tonzoc.common.HttpHelper;
import org.tonzoc.mapper.MechanicsEntryAndExitMapper;
import org.tonzoc.mapper.ProjectMapper;
import org.tonzoc.model.MechanicsEntryAndExitModel;
import org.tonzoc.service.IMechanicsEntryAndExitService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("mechanicsEntryAndExitService")
public class MechanicsEntryAndExitService extends BaseService<MechanicsEntryAndExitModel> implements IMechanicsEntryAndExitService {

    @Autowired
    private MechanicsEntryAndExitMapper mechanicsEntryAndExitMapper;

    @Autowired
    private ProjectMapper projectMapper;

    @Value("${app_key}")
    private String app_key;

    @Value("${app_secret}")
    private String app_secret;

    @Value("${tenant_key}")
    private String tenant_key;

    public List<MechanicsEntryAndExitModel> entryAndExit(String project_key, String machine_key, String date) throws IOException {

        if (project_key == null) {
            project_key = "";
        }
        if (machine_key == null) {
            machine_key = "";
        }

        StringBuffer stringBuffer = new StringBuffer("https://open-api.zhgcloud.com/api/v1/projects/entry-exit-records?");
        Map<String, String> map = new HashMap<>();
        map.put("app_key", app_key);
        map.put("app_secret", app_secret);
        map.put("project_key", project_key);
        map.put("machine_key", machine_key);
        map.put("count_per_page", "0");

        String str = HttpHelper.doGet(stringBuffer.toString(), map, "GET");

        JSONObject returnedModel = JSONObject.parseObject(str);

        List<MechanicsEntryAndExitModel> list = JSONArray.parseArray(returnedModel.getString("data_list"), MechanicsEntryAndExitModel.class);

        return list;
    }

    // 添加进退场记录
    public String entryAndExitAdd(String machine_key, String date) throws IOException {

        Integer maxSortId = mechanicsEntryAndExitMapper.maxSortId();
        if (maxSortId == null) {
            maxSortId = 0;
        }

        List<MechanicsEntryAndExitModel> list1 = new ArrayList<>();
        List<String> project_key = projectMapper.project_key();
        if (project_key != null) {
            for (String s : project_key) {

                if (!"".equals(s)) {
                    List<MechanicsEntryAndExitModel> list = this.entryAndExit(s, machine_key, date);

                    for (MechanicsEntryAndExitModel li : list) {
                        li.setSortId(maxSortId + 1);

                        list1.add(li);

                    }
                }
            }
        }

        this.saveMany(list1);

        return "200";
    }
}
