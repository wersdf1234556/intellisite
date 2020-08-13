package org.tonzoc.service;

import org.tonzoc.model.CamerasModuleModel;

import java.util.List;

public interface ICamerasModuleService extends IBaseService<CamerasModuleModel> {
    List<CamerasModuleModel> listByModuleId(String roleGuid);

    void addModel(CamerasModuleModel camerasModuleModel);
    void updateModel(CamerasModuleModel camerasModuleModel);

    void removeByCameraId(String camerasId);

}
