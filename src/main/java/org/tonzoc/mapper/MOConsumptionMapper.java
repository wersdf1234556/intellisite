package org.tonzoc.mapper;

import org.apache.ibatis.annotations.Select;
import org.tonzoc.model.MOConsumptionModel;

import java.math.BigDecimal;
import java.util.List;

public interface MOConsumptionMapper extends BaseMapper<MOConsumptionModel> {

    @Select("select * from mechanicsOilConsumption")
    List<MOConsumptionModel> list();

    // 累计油耗求和
    @Select("select sum(oil) from mechanicsOilConsumption")
    BigDecimal sumMO();

    // 全部油耗求年份和
    @Select("select sum(mechanicsOilConsumption.oil) from mechanicsOilConsumption " +
            "LEFT JOIN mechanics on mechanicsOilConsumption.machine_key = mechanics.machine_key " +
            "where SUBSTRING(mechanicsOilConsumption.[date],1,4) = #{year} and mechanics.sortId = #{sortId}")
    BigDecimal sumMOByYear(String year, Integer sortId);

    // 全部油耗求月份和
    @Select("select sum(mechanicsOilConsumption.oil) from mechanicsOilConsumption " +
            "LEFT JOIN mechanics on mechanicsOilConsumption.machine_key = mechanics.machine_key " +
            "where SUBSTRING(mechanicsOilConsumption.[date],1,4) = #{year} " +
            "and SUBSTRING(mechanicsOilConsumption.[date],6,2) = #{month} and mechanics.sortId = #{sortId}")
    BigDecimal sumMOByMonth(String year, String month, Integer sortId);

    // 按项目累计油耗求和
    @Select("select sum(oil) from mechanicsOilConsumption " +
            "LEFT JOIN mechanics on mechanicsOilConsumption.machine_key = mechanics.machine_key " +
            "where mechanics.project_key = #{project_key} and mechanics.sortId = #{sortId}")
    BigDecimal sumMOByProject(String project_key, Integer sortId);

    // 按项目油耗求年份和
    @Select("select sum(mechanicsOilConsumption.oil) from mechanicsOilConsumption " +
            "LEFT JOIN mechanics on mechanicsOilConsumption.machine_key = mechanics.machine_key " +
            "where SUBSTRING(mechanicsOilConsumption.[date],1,4) = #{year} " +
            "and mechanics.project_key = #{project_key} and mechanics.sortId = #{sortId}")
    BigDecimal sumMOByProjectYear(String year, String project_key, Integer sortId);

    // 按项目油耗求月份和
    @Select("select sum(mechanicsOilConsumption.oil) from mechanicsOilConsumption " +
            "LEFT JOIN mechanics on mechanicsOilConsumption.machine_key = mechanics.machine_key " +
            "where SUBSTRING(mechanicsOilConsumption.[date],1,4) = #{year} " +
            "and SUBSTRING(mechanicsOilConsumption.[date],6,2) = #{month} " +
            "and mechanics.project_key = #{project_key} and mechanics.sortId = #{sortId}")
    BigDecimal sumMOByProjectMonth(String year, String month, String project_key, Integer sortId);
}
