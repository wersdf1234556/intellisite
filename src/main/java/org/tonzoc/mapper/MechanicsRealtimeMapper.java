package org.tonzoc.mapper;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.tonzoc.model.MechanicsRealtimeModel;

import java.util.List;

public interface MechanicsRealtimeMapper extends BaseMapper<MechanicsRealtimeModel> {

    // 获取排序字段
    @Select("select max(sortId) from mechanicsRealtime")
    Integer maxSortId();
}
