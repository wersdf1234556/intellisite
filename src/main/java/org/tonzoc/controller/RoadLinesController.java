package org.tonzoc.controller;

import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tonzoc.controller.params.PageQueryParams;
import org.tonzoc.controller.params.RoadLinesQueryParams;
import org.tonzoc.controller.response.PageResponse;
import org.tonzoc.exception.PageException;
import org.tonzoc.model.RoadLinesModel;
import org.tonzoc.service.IRoadLinesService;
import org.tonzoc.support.param.SqlQueryParam;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

@RestController
@RequestMapping("roadLines")
public class RoadLinesController extends BaseController {

    @Autowired
    private IRoadLinesService roadLinesService;


    @GetMapping
    public PageResponse list(PageQueryParams pageQueryParams, RoadLinesQueryParams roadLinesQueryParams)
            throws PageException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {

        Page<RoadLinesModel> page = parsePage(pageQueryParams);
        RoadLinesQueryParams sqlQueryParamList = new RoadLinesQueryParams();
        if (roadLinesQueryParams.getGuid() != null && !roadLinesQueryParams.getGuid().equals("")) {
            sqlQueryParamList.setGuid(roadLinesQueryParams.getGuid());
        }
        if (roadLinesQueryParams.getProjectId() != null && !roadLinesQueryParams.getProjectId().equals("")) {
            sqlQueryParamList.setProjectId(roadLinesQueryParams.getProjectId());
        }

        List<SqlQueryParam> sqlQueryParams = parseSqlQueryParams(sqlQueryParamList);
        List<RoadLinesModel> list = roadLinesService.list(sqlQueryParams);

        return new PageResponse(page.getTotal(), list);
    }


}
