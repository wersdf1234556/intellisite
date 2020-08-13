package org.tonzoc.controller;

import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.tonzoc.controller.params.CamerasModuleQueryParams;
import org.tonzoc.controller.params.PageQueryParams;
import org.tonzoc.controller.response.PageResponse;
import org.tonzoc.exception.PageException;
import org.tonzoc.model.CamerasModuleModel;
import org.tonzoc.service.ICameraService;
import org.tonzoc.service.ICamerasModuleService;
import org.tonzoc.service.IModuleService;
import org.tonzoc.support.param.SqlQueryParam;

import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

@RestController
@RequestMapping("camerasModule")
public class CamerasModuleController extends BaseController {

    @Autowired
    private ICamerasModuleService camerasModuleService;
    @Autowired
    private IModuleService moduleService;
    @Autowired
    private ICameraService cameraService;

    @GetMapping
    public PageResponse list(PageQueryParams pageQueryParams, CamerasModuleQueryParams camerasModuleQueryParams)
            throws PageException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {

        Page<CamerasModuleModel> page = parsePage(pageQueryParams);
        CamerasModuleQueryParams sqlQueryParamList = new CamerasModuleQueryParams();
        if (camerasModuleQueryParams.getCamerasId() != null && !camerasModuleQueryParams.getCamerasId().equals("")) {
            sqlQueryParamList.setCamerasId(camerasModuleQueryParams.getCamerasId());
        }
        if (camerasModuleQueryParams.getModuleId() != null && !camerasModuleQueryParams.getModuleId().equals("")) {
            sqlQueryParamList.setModuleId(camerasModuleQueryParams.getModuleId());
        }

        List<SqlQueryParam> sqlQueryParams = parseSqlQueryParams(sqlQueryParamList);

        List list = camerasModuleService.list(sqlQueryParams);

        return new PageResponse(page.getTotal(), list);
    }

    @PostMapping
    public void add(@RequestBody @Valid CamerasModuleModel camerasModuleModel) {
        this.camerasModuleService.addModel(camerasModuleModel);
    }

    @PutMapping(value = "{guid}")
    public void update(@RequestBody @Valid CamerasModuleModel camerasModuleModel) {
        this.camerasModuleService.updateModel(camerasModuleModel);
    }

    @DeleteMapping(value = "{guid}")
    public void remove(@PathVariable(value = "guid") String guid) {
        this.camerasModuleService.remove(guid);
    }
}
