package org.tonzoc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tonzoc.model.CamerasModel;
import org.tonzoc.model.CamerasModuleModel;
import org.tonzoc.service.ICameraService;
import org.tonzoc.service.ICamerasModuleService;
import org.tonzoc.support.param.SqlQueryParam;

import java.util.ArrayList;
import java.util.List;

@Service(value = "cameraService")
public class CameraService extends BaseService<CamerasModel> implements ICameraService {
    @Autowired
    private ICamerasModuleService camerasModuleService;

    @Transactional
    public void removeStack(String guid){
        camerasModuleService.removeByCameraId(guid);
        remove(guid);
    }
}
