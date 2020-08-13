package org.tonzoc.service;

import org.tonzoc.model.MechanicsWorkTimeModel;
import org.tonzoc.model.RequestModel;

import java.io.IOException;
import java.util.List;

public interface IMechanicsWorkTimeService extends IBaseService<MechanicsWorkTimeModel> {

    // 机械时长
    List<MechanicsWorkTimeModel> MechanicsTime(RequestModel requestModel, String timestamp) throws IOException;

    // 添加机械时长
    String MechanicsTimeAdd(RequestModel requestModel, String timestamp) throws IOException;

}
