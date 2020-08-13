package org.tonzoc.controller;

import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.tonzoc.controller.params.ModuleQueryParams;
import org.tonzoc.controller.params.PageQueryParams;
import org.tonzoc.controller.response.PageResponse;
import org.tonzoc.exception.PageException;
import org.tonzoc.model.ModuleModel;
import org.tonzoc.service.ICameraService;
import org.tonzoc.service.ICamerasModuleService;
import org.tonzoc.service.IModuleService;
import org.tonzoc.support.param.SqlQueryParam;

import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

@RestController
@RequestMapping("module")
public class ModuleController extends BaseController {

    @Autowired
    private IModuleService moduleService;

    @GetMapping
    public PageResponse list(PageQueryParams pageQueryParams, ModuleQueryParams moduleQueryParams)
            throws PageException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {

        Page<ModuleModel> page = parsePage(pageQueryParams);
        ModuleQueryParams sqlQueryParamList = new ModuleQueryParams();
        if (moduleQueryParams.getGuid() != null && !moduleQueryParams.getGuid().equals("")) {
            sqlQueryParamList.setGuid(moduleQueryParams.getGuid());
        }
        if (moduleQueryParams.getName() != null && !moduleQueryParams.getName().equals("")) {
            sqlQueryParamList.setName(moduleQueryParams.getName());
        }

        List<SqlQueryParam> sqlQueryParams = parseSqlQueryParams(sqlQueryParamList);
        List<ModuleModel> list = moduleService.list(sqlQueryParams);

        return new PageResponse(page.getTotal(), list);

    }

    @PostMapping
    public void add(@RequestBody @Valid ModuleModel moduleModel) {
        this.moduleService.save(moduleModel);
    }

    @PutMapping(value = "{guid}")
    public void update(@RequestBody @Valid ModuleModel moduleModel) {
        this.moduleService.update(moduleModel);
    }

    @DeleteMapping(value = "{guid}")
    public void remove(@PathVariable(value = "guid") String guid) {
        this.moduleService.remove(guid);
    }


}
