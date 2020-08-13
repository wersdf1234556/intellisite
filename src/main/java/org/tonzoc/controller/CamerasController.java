package org.tonzoc.controller;

import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.tonzoc.controller.params.CamerasQueryParams;
import org.tonzoc.controller.params.PageQueryParams;
import org.tonzoc.controller.response.PageResponse;
import org.tonzoc.exception.PageException;
import org.tonzoc.model.CamerasModel;
import org.tonzoc.service.ICameraService;
import org.tonzoc.support.param.SqlQueryParam;

import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("camera")
public class CamerasController extends BaseController {

    @Autowired
    private ICameraService cameraService;

    @GetMapping
    public PageResponse list(PageQueryParams pageQueryParams, CamerasQueryParams camerasQueryParams)
            throws PageException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        System.out.println("camerasQueryParams=" + camerasQueryParams.toString());
        System.out.println("section=" + camerasQueryParams.getSectionId());
        Page<CamerasModel> page = parsePage(pageQueryParams);
        CamerasQueryParams sqlQueryParamList = new CamerasQueryParams();
        if (camerasQueryParams.getTypeId() != null && !camerasQueryParams.getTypeId().equals("")) {
            sqlQueryParamList.setTypeId(camerasQueryParams.getTypeId());
        }
        if (camerasQueryParams.getPositionOneId() != null && !camerasQueryParams.getPositionOneId().equals("")) {
            sqlQueryParamList.setPositionOneId(camerasQueryParams.getPositionOneId());
        }
        if (camerasQueryParams.getPositionTwoId() != null && !camerasQueryParams.getPositionTwoId().equals("")) {
            sqlQueryParamList.setPositionTwoId(camerasQueryParams.getPositionTwoId());
        }
        if (camerasQueryParams.getSectionId() != null && !camerasQueryParams.getSectionId().equals("")) {
            sqlQueryParamList.setSectionId(camerasQueryParams.getSectionId());
        }
        if (camerasQueryParams.getProjectId() != null && !camerasQueryParams.getProjectId().equals("")) {
            sqlQueryParamList.setProjectId(camerasQueryParams.getProjectId());
        }
        if (camerasQueryParams.getUrl() != null && !camerasQueryParams.getUrl().equals("")) {
            sqlQueryParamList.setUrl(camerasQueryParams.getUrl());
        }
        if (camerasQueryParams.getName() != null && !camerasQueryParams.getName().equals("")) {
            sqlQueryParamList.setName(camerasQueryParams.getName());
        }


        System.out.println("sqlQueryParamList=" + sqlQueryParamList.toString());
        List<SqlQueryParam> sqlQueryParams = parseSqlQueryParams(sqlQueryParamList);


        List list = cameraService.list(sqlQueryParams);

        return new PageResponse(page.getTotal(), list);
    }

    @PostMapping
    public void add(@RequestBody @Valid CamerasModel camerasModel) {
        this.cameraService.save(camerasModel);
    }

    @PutMapping(value = "{guid}")
    public void update(@RequestBody @Valid CamerasModel cameraPositionModel) {
        this.cameraService.update(cameraPositionModel);
    }

    @DeleteMapping(value = "{guid}")
    public void remove(@PathVariable(value = "guid") String guid) {
        this.cameraService.removeStack(guid);
    }

}
