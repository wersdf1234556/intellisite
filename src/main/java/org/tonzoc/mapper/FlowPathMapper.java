package org.tonzoc.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import org.tonzoc.model.FlowpathModel;
@Component
public interface FlowPathMapper extends BaseMapper<FlowpathModel> {

    @Select("SELECT MAX(sortId) from flowpath where leaveId=#{leaveId}")
    Integer maxSortIdByLeaveId(@Param(value = "leaveId") String leaveId);
}
