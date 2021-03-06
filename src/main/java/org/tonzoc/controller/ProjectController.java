package org.tonzoc.controller;

import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.tonzoc.controller.params.PageQueryParams;
import org.tonzoc.controller.params.ProjectQueryParams;
import org.tonzoc.controller.response.PageResponse;
import org.tonzoc.exception.PageException;
import org.tonzoc.model.ProjectModel;
import org.tonzoc.model.support.ProjectReturnModel;
import org.tonzoc.service.IProjectService;
import org.tonzoc.support.param.SqlQueryParam;

import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

@RestController
@RequestMapping("project")
public class ProjectController extends BaseController {

    @Autowired
    private IProjectService projectService;

    @GetMapping
    public PageResponse list(PageQueryParams pageQueryParams, ProjectQueryParams projectQueryParams)
            throws PageException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {

        Page<ProjectModel> page = parsePage(pageQueryParams);
        ProjectQueryParams sqlQueryParamList = new ProjectQueryParams();
        if (projectQueryParams.getGuid() != null && !projectQueryParams.getGuid().equals("")) {
            sqlQueryParamList.setGuid(projectQueryParams.getGuid());
        }
        if (projectQueryParams.getProject_key() != null && !projectQueryParams.getProject_key().equals("")) {
            sqlQueryParamList.setProject_key(projectQueryParams.getProject_key());
        }
        if (projectQueryParams.getStatus() != null && !projectQueryParams.getStatus().equals("")) {
            sqlQueryParamList.setStatus(projectQueryParams.getStatus());
        }
        if (projectQueryParams.getType() != null && !projectQueryParams.getType().equals("")) {
            sqlQueryParamList.setType(projectQueryParams.getType());
        }
        if (projectQueryParams.getName() != null && !projectQueryParams.getName().equals("")) {
            sqlQueryParamList.setName(projectQueryParams.getName());
        }

        List<SqlQueryParam> sqlQueryParams = parseSqlQueryParams(sqlQueryParamList);

        List list = projectService.list(sqlQueryParams);

        return new PageResponse(page.getTotal(), list);
    }

    @PostMapping
    public void add(@RequestBody @Valid ProjectModel projectModel) {
        this.projectService.save(projectModel);
    }

    @PutMapping(value = "{guid}")
    public void update(@RequestBody @Valid ProjectModel projectModel) {
        this.projectService.update(projectModel);
    }

    @DeleteMapping(value = "{guid}")
    public void remove(@PathVariable(value = "guid") String guid) {
        this.projectService.remove(guid);
    }

    @GetMapping(value = "groupNum")
    public ProjectReturnModel groupNum() {
        return this.projectService.numByTypeAndStatus();
    }

    @PostMapping("/upFile")
    public void upFile(MultipartFile file, String projectId) {

        projectService.upFile(file,projectId);
    }
}
