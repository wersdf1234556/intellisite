package org.tonzoc.controller;

import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.tonzoc.controller.params.CamerasModuleQueryParams;
import org.tonzoc.controller.params.PageQueryParams;
import org.tonzoc.controller.params.RelevanceTableQueryParams;
import org.tonzoc.controller.response.PageResponse;
import org.tonzoc.exception.PageException;
import org.tonzoc.model.CamerasModuleModel;
import org.tonzoc.model.RelevanceTableModel;
import org.tonzoc.service.ICameraService;
import org.tonzoc.service.ICamerasModuleService;
import org.tonzoc.service.IModuleService;
import org.tonzoc.service.IRelevanceTableService;
import org.tonzoc.support.param.SqlQueryParam;

import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

@RestController
@RequestMapping("relevanceTable")
public class RelevanceTableController extends BaseController {

    @Autowired
    private IRelevanceTableService relevanceTableService;


    @GetMapping
    public PageResponse list(PageQueryParams pageQueryParams, RelevanceTableQueryParams relevanceTableQueryParams)
            throws PageException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {

        Page<RelevanceTableModel> page = parsePage(pageQueryParams);

        List<SqlQueryParam> sqlQueryParams = parseSqlQueryParams(relevanceTableQueryParams);

        List list = relevanceTableService.list(sqlQueryParams);

        return new PageResponse(page.getTotal(), list);
    }

    @PostMapping
    public void add(@RequestBody @Valid RelevanceTableModel relevanceTableModel) {
        this.relevanceTableService.save(relevanceTableModel);
    }

    @PutMapping(value = "{guid}")
    public void update(@RequestBody @Valid RelevanceTableModel relevanceTableModel) {
        this.relevanceTableService.update(relevanceTableModel);
    }

    @DeleteMapping(value = "{guid}")
    public void remove(@PathVariable(value = "guid") String guid) {
        this.relevanceTableService.remove(guid);
    }
}
