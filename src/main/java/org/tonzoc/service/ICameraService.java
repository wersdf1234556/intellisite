package org.tonzoc.service;

import org.tonzoc.model.CamerasModel;

public interface ICameraService extends IBaseService<CamerasModel> {
    void removeStack(String guid);
}
