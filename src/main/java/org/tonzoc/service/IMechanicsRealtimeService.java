package org.tonzoc.service;

import org.tonzoc.model.MechanicsRealtimeModel;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface IMechanicsRealtimeService extends IBaseService<MechanicsRealtimeModel> {

    //机械状态
    List<MechanicsRealtimeModel> MechanicState() throws IOException, ParseException;

    //添加机械状态
    String MechanicStateadd() throws IOException, ParseException;
}
