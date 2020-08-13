package org.tonzoc.controller;

import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.tonzoc.controller.params.PageQueryParams;
import org.tonzoc.controller.params.SafetyHelmetQueryParams;
import org.tonzoc.controller.response.PageResponse;
import org.tonzoc.exception.PageException;
import org.tonzoc.model.SafetyHelmetModel;
import org.tonzoc.service.ISafetyHelmetService;
import org.tonzoc.support.param.SqlQueryParam;

import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

@RestController
@RequestMapping("safetyHelmet")
public class SafetyHelmetController extends BaseController {
    @Autowired
    private ISafetyHelmetService safetyHelmetService;

    @GetMapping
    public PageResponse list(PageQueryParams pageQueryParams, SafetyHelmetQueryParams safetyHelmetQueryParams)
            throws PageException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {

        Page<SafetyHelmetModel> page = parsePage(pageQueryParams);

        List<SqlQueryParam> sqlQueryParams = parseSqlQueryParams(safetyHelmetQueryParams);

        List list = safetyHelmetService.list(sqlQueryParams);

        return new PageResponse(page.getTotal(), list);
    }

    @PostMapping
    public void add(@RequestBody @Valid SafetyHelmetModel safetyHelmetModel) {
        this.safetyHelmetService.save(safetyHelmetModel);
    }

    @PutMapping(value = "{guid}")
    public void update(@RequestBody @Valid SafetyHelmetModel safetyHelmetModel) {
        this.safetyHelmetService.update(safetyHelmetModel);
    }

    @DeleteMapping(value = "{guid}")
    public void remove(@PathVariable(value = "guid") String guid) {
        this.safetyHelmetService.remove(guid);
    }

}
