package org.tonzoc.service;

import org.tonzoc.model.MechanicsEntryAndExitModel;

import java.io.IOException;
import java.util.List;

public interface IMechanicsEntryAndExitService extends IBaseService<MechanicsEntryAndExitModel> {

    // 进退场记录
    List<MechanicsEntryAndExitModel> entryAndExit(String project_key, String machine_key, String date) throws IOException;

    // 添加进退场记录
    String entryAndExitAdd(String machine_key, String date) throws IOException;
}
