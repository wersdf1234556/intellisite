package org.tonzoc.service;


import org.tonzoc.model.MaterialModel;

import java.util.List;
import java.util.Map;

public interface IMaterialService extends IBaseService<MaterialModel> {
    Map<String,String> countByProjectId(String projectId);
    List<MaterialModel> listByCompanyId();
    Object listByProject(String projectId);
    Object listByProjectAndName(String projectId);
}
