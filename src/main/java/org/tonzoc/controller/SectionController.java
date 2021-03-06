package org.tonzoc.controller;

import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.tonzoc.controller.params.PageQueryParams;
import org.tonzoc.controller.params.SectionQueryParams;
import org.tonzoc.controller.response.PageResponse;
import org.tonzoc.exception.PageException;
import org.tonzoc.model.SectionModel;
import org.tonzoc.service.ISectionService;
import org.tonzoc.support.param.SqlQueryParam;

import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

@RestController
@RequestMapping("section")
public class SectionController extends BaseController {

    @Autowired
    private ISectionService sectionService;

    @GetMapping
    public PageResponse list(PageQueryParams pageQueryParams, SectionQueryParams sectionQueryParams)
            throws PageException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {

        Page<SectionModel> page = parsePage(pageQueryParams);

        List<SqlQueryParam> sqlQueryParams = parseSqlQueryParams(sectionQueryParams);

        List list = sectionService.list(sqlQueryParams);

        return new PageResponse(page.getTotal(), list);
    }

    @PostMapping
    public void add(@RequestBody @Valid SectionModel sectionModel) {
        this.sectionService.save(sectionModel);
    }

    @PutMapping(value = "{guid}")
    public void update(@RequestBody @Valid SectionModel sectionModel) {
        this.sectionService.update(sectionModel);
    }

    @DeleteMapping(value = "{guid}")
    public void remove(@PathVariable(value = "guid") String guid) {
        this.sectionService.remove(guid);
    }
}
