package org.tonzoc.mapper;

import org.apache.ibatis.annotations.Select;
import org.tonzoc.model.MOConsumptionHourModel;

import java.math.BigDecimal;

public interface MOConsumptionHourMapper extends BaseMapper<MOConsumptionHourModel> {

    // 获取排序字段
    @Select("select max(sortId) from mechanicsOilConsumptionHour")
    Integer maxSortId();

    // 最大日期
    @Select("select max(date) from mechanicsOilConsumptionHour")
    String date();

    // 累计油耗求和
    @Select("select sum(oil) from mechanicsOilConsumptionHour where sortId = #{sortId}")
    BigDecimal sumMOHour(Integer sortId);

    // 按项目油耗求和
    @Select("select sum(mechanicsOilConsumptionHour.oil) from mechanicsOilConsumptionHour " +
            "LEFT JOIN mechanics on mechanicsOilConsumptionHour.machine_key = mechanics.machine_key " +
            "where mechanics.project_key = #{project_key} " +
            "and mechanicsOilConsumptionHour.sortId = #{sortId} and mechanics.sortId = #{sortId1}")
    BigDecimal sumMOHourByProject(String project_key, Integer sortId, Integer sortId1);

}
