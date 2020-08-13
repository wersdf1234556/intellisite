package org.tonzoc.service;

import org.springframework.web.multipart.MultipartFile;
import org.tonzoc.model.ProjectModel;
import org.tonzoc.model.support.ProjectReturnModel;

public interface IProjectService extends IBaseService<ProjectModel> {

    ProjectReturnModel numByTypeAndStatus();
    
    void upFile(MultipartFile file, String projectId);
    
}
