package org.tonzoc.mapper;

import org.apache.ibatis.annotations.Select;
import org.tonzoc.model.MechanicsModel;


public interface MechanicsMapper extends BaseMapper<MechanicsModel> {

    // 获取排序字段
    @Select("select max(sortId) from mechanics")
    Integer maxSortId();

}
