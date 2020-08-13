package org.tonzoc.service;

import org.tonzoc.exception.NotOneResultFoundException;
import org.tonzoc.model.MechanicsCountModel;
import org.tonzoc.model.MechanicsModel;

import java.io.IOException;
import java.util.List;

public interface IMechanicsService extends IBaseService<MechanicsModel> {

    // 机械基本信息
    List<MechanicsModel> mechanicsInformation(String project_key, Integer count_per_page, Integer page) throws IOException;

    //添加机械基本信息和项目的信息
    String mechanicsInformationAdd(String project_key, Integer count_per_page, Integer page) throws IOException;

    // 饼图
    List<MechanicsCountModel> mechanicsChart(String project_key);

    // 机械情况
    List<MechanicsCountModel> mechanicsState(String project_key);

    // 进场机械
    List<MechanicsCountModel> mechanicsApproach(String projectGuid);

    //油耗情况
    List<MechanicsCountModel> oilStatistics(String projectGuid);

    //项目油耗情况
    List<MechanicsCountModel> projectOilStatistics();

    void deleteMechanic(String guid) throws NotOneResultFoundException;

}
