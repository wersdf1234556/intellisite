package org.tonzoc.service;

import org.tonzoc.model.HelmetFileModel;

import java.text.ParseException;

public interface IHelmetFileService extends IBaseService<HelmetFileModel> {
    void savevideoList() throws ParseException;
}
