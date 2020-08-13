package org.tonzoc.controller;

import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.tonzoc.controller.params.PageQueryParams;
import org.tonzoc.controller.params.SectionProjectQueryParams;
import org.tonzoc.controller.response.PageResponse;
import org.tonzoc.exception.PageException;
import org.tonzoc.model.SectionProjectModel;
import org.tonzoc.service.ISectionProjectService;
import org.tonzoc.support.param.SqlQueryParam;

import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

@RestController
@RequestMapping("sectionProject")
public class SectionProjectController extends BaseController {

    @Autowired
    private ISectionProjectService sectionProjectService;

    @GetMapping
    public PageResponse list(PageQueryParams pageQueryParams, SectionProjectQueryParams sectionProjectQueryParams)
            throws PageException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {

        Page<SectionProjectModel> page = parsePage(pageQueryParams);

        List<SqlQueryParam> sqlQueryParams = parseSqlQueryParams(sectionProjectQueryParams);

        List list = sectionProjectService.list(sqlQueryParams);

        return new PageResponse(page.getTotal(), list);
    }

    @PostMapping
    public void add(@RequestBody @Valid SectionProjectModel sectionProjectModel) {
        this.sectionProjectService.save(sectionProjectModel);
    }

    @PutMapping(value = "{guid}")
    public void update(@RequestBody @Valid SectionProjectModel sectionProjectModel) {
        this.sectionProjectService.update(sectionProjectModel);
    }

    @DeleteMapping(value = "{guid}")
    public void remove(@PathVariable(value = "guid") String guid) {
        this.sectionProjectService.remove(guid);
    }
}
