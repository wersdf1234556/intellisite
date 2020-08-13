package org.tonzoc.mapper;

import org.apache.ibatis.annotations.Delete;
import org.springframework.stereotype.Component;
import org.tonzoc.model.CamerasModuleModel;
@Component
public interface CamerasModuleMapper extends BaseMapper<CamerasModuleModel> {
    @Delete("delete from camerasModule where camerasId=#{camerasId}")
    void removeByCamerasId(String camerasId);

}
