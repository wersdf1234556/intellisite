package org.tonzoc.service;


import org.tonzoc.model.RelevanceTableModel;

import java.util.List;


public interface IRelevanceTableService extends IBaseService<RelevanceTableModel> {
    List<RelevanceTableModel> listByMainId(String mainId);

    void deleteByMainId(String mainId);
}
