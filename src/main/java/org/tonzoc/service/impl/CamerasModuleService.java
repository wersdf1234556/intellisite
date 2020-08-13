package org.tonzoc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tonzoc.mapper.CamerasModuleMapper;
import org.tonzoc.model.CamerasModel;
import org.tonzoc.model.CamerasModuleModel;
import org.tonzoc.service.ICameraService;
import org.tonzoc.service.ICamerasModuleService;
import org.tonzoc.support.param.SqlQueryParam;

import java.util.ArrayList;
import java.util.List;

@Service("camerasModuelService")
public class CamerasModuleService extends BaseService<CamerasModuleModel> implements ICamerasModuleService {
    @Autowired
    private ICameraService cameraService;
    @Autowired
    private CamerasModuleMapper camerasModuleMapper;

    public List<CamerasModuleModel> listByModuleId(String moduleId) {
        List<SqlQueryParam> sqlQueryParams = new ArrayList<>();
        sqlQueryParams.add(new SqlQueryParam("moduleId", moduleId, "eq"));

        List<CamerasModuleModel> camerasModuleModels = this.list(sqlQueryParams);

        return camerasModuleModels;
    }

    public void addModel(CamerasModuleModel camerasModuleModel){
        CamerasModel camerasModel =cameraService.get(camerasModuleModel.getCamerasId());
        camerasModuleModel.setSortId(camerasModel.getSortId());
        camerasModuleModel.setSecondarySortId(camerasModel.getSecondarySortId());
        save(camerasModuleModel);
    }

    public void updateModel(CamerasModuleModel camerasModuleModel){
        CamerasModel camerasModel =cameraService.get(camerasModuleModel.getCamerasId());
        camerasModuleModel.setSortId(camerasModel.getSortId());
        camerasModuleModel.setSecondarySortId(camerasModel.getSecondarySortId());
        update(camerasModuleModel);
    }

    public void removeByCameraId(String camerasId){
        camerasModuleMapper.removeByCamerasId(camerasId);

    }


}
