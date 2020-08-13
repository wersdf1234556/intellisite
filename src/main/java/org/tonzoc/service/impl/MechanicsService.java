package org.tonzoc.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.tonzoc.common.HttpHelper;
import org.tonzoc.exception.NotOneResultFoundException;
import org.tonzoc.mapper.*;
import org.tonzoc.model.*;
import org.tonzoc.service.IMechanicsService;
import org.tonzoc.service.IProjectService;
import org.tonzoc.service.IRelevanceTableService;
import org.tonzoc.support.param.SqlQueryParam;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service(value = "mechanicsService")
public class MechanicsService extends BaseService<MechanicsModel> implements IMechanicsService {

    @Autowired
    private IRelevanceTableService relevanceTableService;

    @Override
    public void deleteMechanic(String guid) throws NotOneResultFoundException {
        List<RelevanceTableModel> list = relevanceTableService.listByMainId(guid);
        if (list.size() != 0) {
            throw new NotOneResultFoundException("与该机械有绑定的机械指挥官，请先解除绑定，才可删除该机械");
        }
        remove(guid);
    }

    @Autowired
    private MechanicsMapper mechanicsMapper;

    @Autowired
    private MechanicsRealtimeMapper mechanicsRealtimeMapper;

    @Autowired
    private MechanicsDataRealTimeMapper mechanicsDataRealTimeMapper;

    @Autowired
    private MOConsumptionMapper moConsumptionMapper;

    @Autowired
    private MOConsumptionHourMapper moConsumptionHourMapper;

    @Autowired
    private MechanicsPlusOilMapper mechanicsPlusOilMapper;

    @Autowired
    private MechanicsEntryAndExitMapper mechanicsEntryAndExitMapper;

    @Autowired
    private IProjectService projectService;

    @Value("${app_key}")
    private String app_key;

    @Value("${app_secret}")
    private String app_secret;

    @Value("${tenant_key}")
    private String tenant_key;

    // 机械基本信息
    public List<MechanicsModel> mechanicsInformation(String project_key, Integer count_per_page, Integer page) throws IOException {

        StringBuffer stringBuffer = new StringBuffer("https://open-api.zhgcloud.com/api/v1/machines?");
        Map<String, String> map = new HashMap<>();
        map.put("app_key", app_key);
        map.put("app_secret", app_secret);
        map.put("tenant_key", tenant_key);
        map.put("count_per_page", "0");

        String str = HttpHelper.doGet(stringBuffer.toString(), map, "GET");

        JSONObject returnedModel = JSONObject.parseObject(str);

        List<MechanicsModel> list = JSONArray.parseArray(returnedModel.getString("data_list"), MechanicsModel.class);

        return list;
    }

    //添加机械基本信息和项目的信息
    public String mechanicsInformationAdd(String project_key, Integer count_per_page, Integer page) throws IOException {

        List<MechanicsModel> list = this.mechanicsInformation(project_key, count_per_page, page);

        List<MechanicsModel> list2 = new ArrayList<>();

        Integer maxSortId = mechanicsMapper.maxSortId();
        if (maxSortId == null) {
            maxSortId = 0;
        }

        for (MechanicsModel li : list) {
            li.setSortId(maxSortId + 1);
            list2.add(li);

            if (li.getProject_key() != null) {
                List<SqlQueryParam> sqlQueryParams = new ArrayList<>();
                sqlQueryParams.add(new SqlQueryParam("project_key", li.getProject_key(), "eq"));

                List<ProjectModel> list1 = projectService.list(sqlQueryParams);

                if (list1.size() == 0) {
                    ProjectModel projectModel = new ProjectModel();
                    projectModel.setProject_key(li.getProject_key());
                    projectModel.setName(li.getProject_name());

                    projectService.save(projectModel);
                }
            }
        }

        this.saveMany(list2);
        return "200";
    }

    // 饼图
    public List<MechanicsCountModel> mechanicsChart(String projectGuid) {

        Integer working = 0;
        Integer off = 0;
        Integer idle = 0;
        Integer out_watching = 0;
        List<MechanicsCountModel> list = new ArrayList<>();
        Integer maxSortId = mechanicsRealtimeMapper.maxSortId();  // 实时状态

        MechanicsCountModel mechanicsCountModel = new MechanicsCountModel();
        mechanicsCountModel.setName("设备总数");

        MechanicsCountModel mechanicsCountModel1 = new MechanicsCountModel();
        mechanicsCountModel1.setName("运行");

        MechanicsCountModel mechanicsCountModel2 = new MechanicsCountModel();
        mechanicsCountModel2.setName("静止");

        MechanicsCountModel mechanicsCountModel3 = new MechanicsCountModel();
        mechanicsCountModel3.setName("怠速");

        MechanicsCountModel mechanicsCountModel4 = new MechanicsCountModel();
        mechanicsCountModel4.setName("离线");

        if (projectGuid == null || "".equals(projectGuid)) {

            List<MechanicsDataRealTimeModel> list2 = mechanicsDataRealTimeMapper.realtTimeByState(maxSortId);
            for (MechanicsDataRealTimeModel li : list2) {
                if (li.getState().equals("working")) {
                    working = li.getSortId();
                }
                if (li.getState().equals("off")) {
                    off = li.getSortId();
                }
                if (li.getState().equals("idle")) {
                    idle = li.getSortId();
                }
                if (li.getState().equals("out_watching")) {
                    out_watching = li.getSortId();
                }
            }

            mechanicsCountModel.setCount((working + off + idle + out_watching) + "");
            list.add(mechanicsCountModel);

            mechanicsCountModel1.setCount(working + "");
            list.add(mechanicsCountModel1);

            mechanicsCountModel2.setCount(off + "");
            list.add(mechanicsCountModel2);

            mechanicsCountModel3.setCount(idle + "");
            list.add(mechanicsCountModel3);

            mechanicsCountModel4.setCount(out_watching + "");
            list.add(mechanicsCountModel4);

        } else {
            ProjectModel projectModel = projectService.get(projectGuid);

            List<MechanicsDataRealTimeModel> list3 = mechanicsDataRealTimeMapper.realtTimeByStateAndProject(maxSortId, projectModel.getProject_key());
            for (MechanicsDataRealTimeModel li : list3) {
                if (li.getState().equals("off")) {
                    off = li.getSortId();
                }
                if (li.getState().equals("working")) {
                    working = li.getSortId();
                }
                if (li.getState().equals("idle")) {
                    idle = li.getSortId();
                }
                if (li.getState().equals("out_watching")) {
                    out_watching = li.getSortId();
                }
            }

            mechanicsCountModel.setCount((working + off + idle + out_watching) + "");
            list.add(mechanicsCountModel);

            mechanicsCountModel1.setCount(working + "");
            list.add(mechanicsCountModel1);

            mechanicsCountModel2.setCount(off + "");
            list.add(mechanicsCountModel2);

            mechanicsCountModel3.setCount(idle + "");
            list.add(mechanicsCountModel3);

            mechanicsCountModel4.setCount(out_watching + "");
            list.add(mechanicsCountModel4);

        }
        return list;
    }

    // 机械情况
    public List<MechanicsCountModel> mechanicsState(String projectGuid) {

        List<MechanicsCountModel> list = new ArrayList<>();

        Integer maxSortId = mechanicsEntryAndExitMapper.maxSortId();  // 进退场

        Integer maxSortId1 = mechanicsRealtimeMapper.maxSortId();  // 实时状态

        MechanicsCountModel mechanicsCountModel = new MechanicsCountModel();
        mechanicsCountModel.setName("累计进场");

        MechanicsCountModel mechanicsCountModel1 = new MechanicsCountModel();
        mechanicsCountModel1.setName("目前在场");

        MechanicsCountModel mechanicsCountModel2 = new MechanicsCountModel();
        mechanicsCountModel2.setName("累计离场");

        MechanicsCountModel mechanicsCountModel3 = new MechanicsCountModel();
        mechanicsCountModel3.setName("正在工作");

        MechanicsCountModel mechanicsCountModel4 = new MechanicsCountModel();
        mechanicsCountModel4.setName("停止工作");

        Integer working = 0;
        Integer idle = 0;

        if (projectGuid == null || "".equals(projectGuid)) {
            mechanicsCountModel.setCount(mechanicsEntryAndExitMapper.sumBySortIdAndType(maxSortId, "entry") + "");
            list.add(mechanicsCountModel);

            mechanicsCountModel1.setCount((mechanicsEntryAndExitMapper.sumBySortIdAndType(maxSortId, "entry")
                    - mechanicsEntryAndExitMapper.sumBySortIdAndType(maxSortId, "exit")) + "");
            list.add(mechanicsCountModel1);

            mechanicsCountModel2.setCount(mechanicsEntryAndExitMapper.sumBySortIdAndType(maxSortId, "exit") + "");
            list.add(mechanicsCountModel2);

            List<MechanicsDataRealTimeModel> list1 = mechanicsDataRealTimeMapper.realtTimeByState(maxSortId1);
            for (MechanicsDataRealTimeModel li : list1) {
                if (li.getState().equals("working")) {
                    working = li.getSortId();
                }
                if (li.getState().equals("idle")) {
                    idle = li.getSortId();
                }
            }
            mechanicsCountModel3.setCount((working + idle) + "");
            list.add(mechanicsCountModel3);

            mechanicsCountModel4.setCount((mechanicsEntryAndExitMapper.sumBySortIdAndType(maxSortId, "entry")
                    - mechanicsEntryAndExitMapper.sumBySortIdAndType(maxSortId, "exit")
                    - working - idle) + "");
            list.add(mechanicsCountModel4);

        } else {
            ProjectModel projectModel = projectService.get(projectGuid);

            mechanicsCountModel.setCount(mechanicsEntryAndExitMapper.sumBySortIdAndTypeProject(maxSortId, "entry", projectModel.getProject_key()) + "");
            list.add(mechanicsCountModel);

            mechanicsCountModel1.setCount((mechanicsEntryAndExitMapper.sumBySortIdAndTypeProject(maxSortId, "entry", projectModel.getProject_key())
                    - mechanicsEntryAndExitMapper.sumBySortIdAndTypeProject(maxSortId, "exit", projectModel.getProject_key())) + "");
            list.add(mechanicsCountModel1);

            mechanicsCountModel2.setCount(mechanicsEntryAndExitMapper.sumBySortIdAndTypeProject(maxSortId, "exit", projectModel.getProject_key()) + "");
            list.add(mechanicsCountModel2);

            List<MechanicsDataRealTimeModel> list1 = mechanicsDataRealTimeMapper.realtTimeByStateAndProject(maxSortId1, projectModel.getProject_key());
            for (MechanicsDataRealTimeModel li : list1) {
                if (li.getState().equals("idle")) {
                    idle = li.getSortId();
                }
                if (li.getState().equals("working")) {
                    working = li.getSortId();
                }
            }
            mechanicsCountModel3.setCount((working + idle) + "");
            list.add(mechanicsCountModel3);

            mechanicsCountModel4.setCount((mechanicsEntryAndExitMapper.sumBySortIdAndTypeProject(maxSortId, "entry", projectModel.getProject_key())
                    - mechanicsEntryAndExitMapper.sumBySortIdAndTypeProject(maxSortId, "exit", projectModel.getProject_key())
                    - working - idle) + "");
            list.add(mechanicsCountModel4);
        }

        return list;
    }

    // 进场机械
    public List<MechanicsCountModel> mechanicsApproach(String projectGuid) {

        List<MechanicsCountModel> list = new ArrayList<>();
        Integer maxSortId = mechanicsEntryAndExitMapper.maxSortId();
        Integer maxSortId1 = mechanicsMapper.maxSortId();

        MechanicsCountModel mechanicsCountModel = new MechanicsCountModel();
        mechanicsCountModel.setName("挖掘机");

        MechanicsCountModel mechanicsCountModel1 = new MechanicsCountModel();
        mechanicsCountModel1.setName("起重机");

        MechanicsCountModel mechanicsCountModel2 = new MechanicsCountModel();
        mechanicsCountModel2.setName("吊车");

        MechanicsCountModel mechanicsCountModel3 = new MechanicsCountModel();
        mechanicsCountModel3.setName("压路机");

        MechanicsCountModel mechanicsCountModel4 = new MechanicsCountModel();
        mechanicsCountModel4.setName("摊铺机");

        MechanicsCountModel mechanicsCountModel5 = new MechanicsCountModel();
        mechanicsCountModel5.setName("其他");

        if (projectGuid == null || "".equals(projectGuid)) {

            mechanicsCountModel.setCount(mechanicsEntryAndExitMapper.category_nameByProject("挖掘机", "", maxSortId, maxSortId1) + "");
            list.add(mechanicsCountModel);

            mechanicsCountModel1.setCount(mechanicsEntryAndExitMapper.category_nameByProject("起重机", "", maxSortId, maxSortId1) + "");
            list.add(mechanicsCountModel1);

            mechanicsCountModel2.setCount(mechanicsEntryAndExitMapper.category_nameByProject("吊车", "", maxSortId, maxSortId1) + "");
            list.add(mechanicsCountModel2);

            mechanicsCountModel3.setCount(mechanicsEntryAndExitMapper.category_nameByProject("压路机", "", maxSortId, maxSortId1) + "");
            list.add(mechanicsCountModel3);

            mechanicsCountModel4.setCount(mechanicsEntryAndExitMapper.category_nameByProject("摊铺机", "", maxSortId, maxSortId1) + "");
            list.add(mechanicsCountModel4);

            mechanicsCountModel5.setCount(mechanicsEntryAndExitMapper.count(maxSortId)
                    - mechanicsEntryAndExitMapper.category_nameByProject("挖掘机", "", maxSortId, maxSortId1)
                    - mechanicsEntryAndExitMapper.category_nameByProject("起重机", "", maxSortId, maxSortId1)
                    - mechanicsEntryAndExitMapper.category_nameByProject("吊车", "", maxSortId, maxSortId1)
                    - mechanicsEntryAndExitMapper.category_nameByProject("压路机", "", maxSortId, maxSortId1)
                    - mechanicsEntryAndExitMapper.category_nameByProject("摊铺机", "", maxSortId, maxSortId1) + "");
            list.add(mechanicsCountModel5);
        } else {
            ProjectModel projectModel = projectService.get(projectGuid);

            mechanicsCountModel.setCount(mechanicsEntryAndExitMapper.category_nameByProject("挖掘机", projectModel.getProject_key(), maxSortId, maxSortId1) + "");
            list.add(mechanicsCountModel);

            mechanicsCountModel1.setCount(mechanicsEntryAndExitMapper.category_nameByProject("起重机", projectModel.getProject_key(), maxSortId, maxSortId1) + "");
            list.add(mechanicsCountModel1);

            mechanicsCountModel2.setCount(mechanicsEntryAndExitMapper.category_nameByProject("吊车", projectModel.getProject_key(), maxSortId, maxSortId1) + "");
            list.add(mechanicsCountModel2);

            mechanicsCountModel3.setCount(mechanicsEntryAndExitMapper.category_nameByProject("压路机", projectModel.getProject_key(), maxSortId, maxSortId1) + "");
            list.add(mechanicsCountModel3);

            mechanicsCountModel4.setCount(mechanicsEntryAndExitMapper.category_nameByProject("摊铺机", projectModel.getProject_key(), maxSortId, maxSortId1) + "");
            list.add(mechanicsCountModel4);

            mechanicsCountModel5.setCount(mechanicsEntryAndExitMapper.countByProject(maxSortId, projectModel.getProject_key())
                    - mechanicsEntryAndExitMapper.category_nameByProject("挖掘机", projectModel.getProject_key(), maxSortId, maxSortId1)
                    - mechanicsEntryAndExitMapper.category_nameByProject("起重机", projectModel.getProject_key(), maxSortId, maxSortId1)
                    - mechanicsEntryAndExitMapper.category_nameByProject("吊车", projectModel.getProject_key(), maxSortId, maxSortId1)
                    - mechanicsEntryAndExitMapper.category_nameByProject("压路机", projectModel.getProject_key(), maxSortId, maxSortId1)
                    - mechanicsEntryAndExitMapper.category_nameByProject("摊铺机", projectModel.getProject_key(), maxSortId, maxSortId1) + "");
            list.add(mechanicsCountModel5);
        }

        return list;
    }

    // 油耗
    public List<MechanicsCountModel> oilStatistics(String projectGuid) {

        Integer maxSortId = moConsumptionHourMapper.maxSortId();
        Integer maxSortId1 = mechanicsMapper.maxSortId();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        Date end = c.getTime();
        String dqrq = format.format(end);//当前日期

        List<MechanicsCountModel> list3 = new ArrayList<>();
        MechanicsCountModel mechanicsCountModel = new MechanicsCountModel();
        mechanicsCountModel.setName("本日油耗");

        MechanicsCountModel mechanicsCountModel1 = new MechanicsCountModel();
        mechanicsCountModel1.setName("本月油耗");

        MechanicsCountModel mechanicsCountModel2 = new MechanicsCountModel();
        mechanicsCountModel2.setName("本年油耗");

        MechanicsCountModel mechanicsCountModel3 = new MechanicsCountModel();
        mechanicsCountModel3.setName("累计油耗");

        MechanicsCountModel mechanicsCountModel4 = new MechanicsCountModel();
        mechanicsCountModel4.setName("累计加油");

        if (projectGuid == null || "".equals(projectGuid)) {

            if (format1.format(end).equals(moConsumptionHourMapper.date())) {
                mechanicsCountModel.setCount(moConsumptionHourMapper.sumMOHour(maxSortId) + "");
                list3.add(mechanicsCountModel);
            }else {
                list3.add(mechanicsCountModel);
            }

            mechanicsCountModel1.setCount(moConsumptionMapper.sumMOByMonth(dqrq.substring(0, 4), dqrq.substring(5, 7), maxSortId1) + "");
            list3.add(mechanicsCountModel1);

            mechanicsCountModel2.setCount(moConsumptionMapper.sumMOByYear(dqrq.substring(0, 4), maxSortId1) + "");
            list3.add(mechanicsCountModel2);

            mechanicsCountModel3.setCount(moConsumptionMapper.sumMO() + "");
            list3.add(mechanicsCountModel3);

            mechanicsCountModel4.setCount(mechanicsPlusOilMapper.sumPlusOil() + "");
            list3.add(mechanicsCountModel4);

        } else {
            ProjectModel projectModel = projectService.get(projectGuid);

            if (format1.format(end).equals(moConsumptionHourMapper.date())) {
                mechanicsCountModel.setCount(moConsumptionHourMapper.sumMOHourByProject(projectModel.getProject_key(), maxSortId, maxSortId1) + "");

                list3.add(mechanicsCountModel);
            }else {
                list3.add(mechanicsCountModel);
            }

            mechanicsCountModel1.setCount(moConsumptionMapper.sumMOByProjectMonth(dqrq.substring(0, 4), dqrq.substring(5, 7), projectModel.getProject_key(), maxSortId1) + "");
            list3.add(mechanicsCountModel1);

            mechanicsCountModel2.setCount(moConsumptionMapper.sumMOByProjectYear(dqrq.substring(0, 4), projectModel.getProject_key(), maxSortId1) + "");
            list3.add(mechanicsCountModel2);

            mechanicsCountModel3.setCount(moConsumptionMapper.sumMOByProject(projectModel.getProject_key(), maxSortId1) + "");
            list3.add(mechanicsCountModel3);

            mechanicsCountModel4.setCount(mechanicsPlusOilMapper.sumPlusOilByProject(projectModel.getProject_key(), maxSortId1) + "");
            list3.add(mechanicsCountModel4);

//            System.out.println("日" + moConsumptionHourMapper.sumMOHourByProject(projectModel.getProject_key(), maxSortId));
//            System.out.println("月" + moConsumptionMapper.sumMOByProjectMonth(dqrq.substring(0, 4), dqrq.substring(5, 7), projectModel.getProject_key()));
//            System.out.println("年" + moConsumptionMapper.sumMOByProjectYear(dqrq.substring(0, 4), projectModel.getProject_key()));
//            System.out.println("全部" + moConsumptionMapper.sumMOByProject(projectModel.getProject_key()));
//            System.out.println("加油" + mechanicsPlusOilMapper.sumPlusOilByProject(projectModel.getProject_key()));
        }

        return list3;
    }

    // 项目油耗
    public List<MechanicsCountModel> projectOilStatistics() {

        List<SqlQueryParam> sqlQueryParams = new ArrayList<>();
        List<ProjectModel> list = projectService.list(sqlQueryParams);

        if (list.size() > 0) {
            List<MechanicsCountModel> list5 = new ArrayList<>();

            for (ProjectModel li : list) {
                MechanicsCountModel mechanicsCountModel = new MechanicsCountModel();
                MechanicsProjectCountModel mechanicsProjectCountModel = new MechanicsProjectCountModel();

                if (!"".equals(li.getProject_key()) && li.getProject_key() != null) {
                    mechanicsCountModel.setName(li.getName());

                    List<MechanicsCountModel> list1 = this.mechanicsState(li.getGuid());
                    for (MechanicsCountModel li1 : list1) {
                        if (li1.getName().equals("正在工作")) {
                            mechanicsCountModel.setWorkCount(li1.getCount() + "");
                        }
                        if (li1.getName().equals("停止工作")) {
                            mechanicsCountModel.setStopCount(li1.getCount() + "");
                        }
                    }

                    List<MechanicsCountModel> list2 = this.oilStatistics(li.getGuid());
                    for (MechanicsCountModel li2 : list2) {
                        if (li2.getName().equals("本日油耗")) {
                            mechanicsProjectCountModel.setDayCount(li2.getCount());
                        }
                        if (li2.getName().equals("本月油耗")) {
                            mechanicsProjectCountModel.setMonthCount(li2.getCount());
                        }
                        if (li2.getName().equals("本年油耗")) {
                            mechanicsProjectCountModel.setYearCount(li2.getCount());
                        }
                        if (li2.getName().equals("累计油耗")) {
                            mechanicsProjectCountModel.setAllCount(li2.getCount());
                        }
                        if (li2.getName().equals("累计加油")) {
                            mechanicsProjectCountModel.setPlusOilCount(li2.getCount());
                        }
                    }
                    mechanicsCountModel.setMechanicsProjectCountModel(mechanicsProjectCountModel);
                }
                list5.add(mechanicsCountModel);
            }
            return list5;
        }
        return null;
    }

}

