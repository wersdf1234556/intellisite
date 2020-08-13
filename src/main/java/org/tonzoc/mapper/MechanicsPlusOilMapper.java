package org.tonzoc.mapper;

import org.apache.ibatis.annotations.Select;
import org.tonzoc.model.MechanicsPlusOilModel;

import java.math.BigDecimal;
import java.util.List;

public interface MechanicsPlusOilMapper extends BaseMapper<MechanicsPlusOilModel> {

    @Select("select * from mechanicsPlusOil")
    List<MechanicsPlusOilModel> list();

    // 累计加油求和
    @Select("select sum(volume) from mechanicsPlusOil")
    BigDecimal sumPlusOil();

    // 按项目加油求和
    @Select("select sum(mechanicsPlusOil.volume) from mechanicsPlusOil " +
            "LEFT JOIN mechanics on mechanicsPlusOil.machine_key = mechanics.machine_key " +
            "where mechanics.project_key = #{project_key} and mechanics.sortId = #{sortId}")
    BigDecimal sumPlusOilByProject(String project_key, Integer sortId);

}
