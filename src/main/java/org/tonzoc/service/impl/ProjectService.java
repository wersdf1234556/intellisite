package org.tonzoc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.tonzoc.common.FileHelper;
import org.tonzoc.model.AttachmentModel;
import org.tonzoc.model.ProjectModel;
import org.tonzoc.model.support.ProjectReturnModel;
import org.tonzoc.service.IAttachmentService;
import org.tonzoc.service.IProjectService;
import org.tonzoc.support.param.SqlQueryParam;

import java.util.ArrayList;
import java.util.List;

@Service("projectService")
public class ProjectService extends BaseService<ProjectModel> implements IProjectService {
    @Autowired
    private IAttachmentService attachmentService;
    @Autowired
    private FileHelper fileHelper;

    public Integer listByType(Integer type) {
        List<SqlQueryParam> sqlQueryParams = new ArrayList<>();
        sqlQueryParams.add(new SqlQueryParam("type", type.toString(), "eq"));

        Integer num = this.list(sqlQueryParams).size();

        return num;
    }
    public Integer listByStatus(Integer status) {
        List<SqlQueryParam> sqlQueryParams = new ArrayList<>();
        sqlQueryParams.add(new SqlQueryParam("status", status.toString(), "eq"));
        Integer num = this.list(sqlQueryParams).size();

        return num;
    }
    @Override
    public ProjectReturnModel numByTypeAndStatus() {
        ProjectReturnModel returnModel = new ProjectReturnModel();
        List<SqlQueryParam> sqlQueryParams = new ArrayList<>();
        Integer total = this.list(sqlQueryParams).size();
        returnModel.setTotal(total);
        returnModel.setOverseas(listByType(1));
        returnModel.setUnderConstruction(listByStatus(0));
        returnModel.setCompleted(listByStatus(1));
        return returnModel;
    }

    @Override
    public void upFile(MultipartFile file, String projectId) {
        ProjectModel projectModel = get(projectId);
        if (projectModel!=null){
            AttachmentModel attachmentModel = new AttachmentModel();
            String str = fileHelper.fileUpload(file);
            attachmentModel.setName(str.split(",")[1]);
            attachmentModel.setUrl(str.split(",")[0]);
            attachmentService.save(attachmentModel);
            projectModel.setAttachmentId(attachmentModel.getGuid());
            this.update(projectModel);
        }

    }


}
