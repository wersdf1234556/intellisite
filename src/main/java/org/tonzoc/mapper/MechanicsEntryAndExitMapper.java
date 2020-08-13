package org.tonzoc.mapper;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.tonzoc.model.MechanicsEntryAndExitModel;
import org.tonzoc.provider.MechanicsEntryAndExitProvider;


public interface MechanicsEntryAndExitMapper extends BaseMapper<MechanicsEntryAndExitModel> {

    // 获取排序字段
    @Select("select max(sortId) from mechanicsEntryAndExit")
    Integer maxSortId();

    // 查询进退场数量
    @Select("select count(mechanicsEntryAndExit.machine_key) from mechanicsEntryAndExit where type = #{type} and sortId = #{sortId}")
    Integer sumBySortIdAndType(Integer sortId, String type);

    // 按项目查询进退场数量
    @Select("select count(mechanicsEntryAndExit.machine_key) from mechanicsEntryAndExit where type = #{type} and sortId = #{sortId} and project_key = #{project_key}")
    Integer sumBySortIdAndTypeProject(Integer sortId, String type, String project_key);

    // 按项目的机械类型模糊查询
    @SelectProvider(type = MechanicsEntryAndExitProvider.class, method = "category_name")
    Integer category_nameByProject(String category_name, String project_key, Integer sortId, Integer sortId1);

    // 查询全部进场数量
    @Select("select count(mechanicsEntryAndExit.machine_key) from mechanicsEntryAndExit " +
            "where mechanicsEntryAndExit.type = 'entry' and mechanicsEntryAndExit.sortId = #{sortId}")
    Integer count(Integer sortId);

    // 按项目查询全部进场数量
    @Select("select count(mechanicsEntryAndExit.machine_key) from mechanicsEntryAndExit " +
            "where mechanicsEntryAndExit.type = 'entry' and mechanicsEntryAndExit.sortId = #{sortId} and mechanicsEntryAndExit.project_key = #{project_key}")
    Integer countByProject(Integer sortId, String project_key);
}
