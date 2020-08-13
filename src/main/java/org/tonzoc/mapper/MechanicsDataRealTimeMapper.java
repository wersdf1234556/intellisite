package org.tonzoc.mapper;

import org.apache.ibatis.annotations.Select;
import org.springframework.scheduling.annotation.Scheduled;
import org.tonzoc.model.MechanicsDataRealTimeModel;
import org.tonzoc.model.MechanicsRealtimeModel;

import java.util.List;

public interface MechanicsDataRealTimeMapper extends BaseMapper<MechanicsDataRealTimeModel> {

    @Select("select distinct mechanicsRealtime.machine_key from mechanicsDataRealTime left join mechanicsRealtime on mechanicsDataRealTime.realTimeId = mechanicsRealtime.guid where mechanicsDataRealTime.state = #{state}")
    List<String> machine_key(String state);

    @Select("select count(distinct mechanicsRealtime.machine_key) as sortId, mechanicsDataRealTime.state " +
            "from mechanicsDataRealTime left join mechanicsRealtime on mechanicsDataRealTime.realTimeId = mechanicsRealtime.guid " +
            "where mechanicsDataRealTime.sortId = #{sortId} " +
            "GROUP BY mechanicsDataRealTime.state")
    List<MechanicsDataRealTimeModel> realtTimeByState(Integer sortId);

    @Select("select count(distinct mechanicsRealtime.machine_key) as sortId, mechanicsDataRealTime.state " +
            "from mechanicsDataRealTime LEFT JOIN mechanicsRealtime on mechanicsDataRealTime.realTimeId = mechanicsRealtime.guid " +
            "LEFT JOIN mechanics on mechanicsRealtime.machine_key = mechanics.machine_key " +
            "where mechanicsDataRealTime.sortId = #{sortId} and mechanics.project_key = #{project_key} " +
            "GROUP BY mechanicsDataRealTime.state")
    List<MechanicsDataRealTimeModel> realtTimeByStateAndProject(Integer sortId, String project_key);

}
