package org.tonzoc.mapper;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import org.tonzoc.model.FenceItemModel;

import java.util.List;
@Component
public interface FenceItemMapper extends BaseMapper<FenceItemModel> {

    // 排序
    @Select("select * from fenceItems order by sortId")
    List<FenceItemModel> list();

    @Select("select max(lng) from fenceItems\n" +
            "union select min(lng) from fenceItems\n" +
            "union select max(lat) from fenceItems\n" +
            "union select min(lat) from fenceItems")
    List<String> listMaxAndMin();

    @Select("select * from fenceItems where fenceGuid = #{fenceGuid} order by sortId")
    List<FenceItemModel> listItemByFenceId(String fenceGuid);
}
