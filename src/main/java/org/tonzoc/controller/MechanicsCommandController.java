package org.tonzoc.controller;

import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.tonzoc.controller.params.MechanicsCommandQueryParams;
import org.tonzoc.controller.params.PageQueryParams;
import org.tonzoc.controller.response.PageResponse;
import org.tonzoc.exception.PageException;
import org.tonzoc.model.MechanicsCommandModel;
import org.tonzoc.service.IMechanicsCommandService;
import org.tonzoc.support.param.SqlQueryParam;

import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

@RestController
@RequestMapping("mechanicalCommand")
public class MechanicsCommandController extends BaseController {

    @Autowired
    private IMechanicsCommandService mechanicalCommandService;

    @GetMapping
    public PageResponse list(PageQueryParams pageQueryParams, MechanicsCommandQueryParams mechanicsCommandQueryParams)
            throws PageException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {

        Page<MechanicsCommandModel> page = parsePage(pageQueryParams);

        List<SqlQueryParam> sqlQueryParams = parseSqlQueryParams(mechanicsCommandQueryParams);
        List<MechanicsCommandModel> list = mechanicalCommandService.list(sqlQueryParams);

        return new PageResponse(page.getTotal(), list);
    }

    @PostMapping
    public void add(@RequestBody @Valid MechanicsCommandModel mechanicsCommandModel) {
        this.mechanicalCommandService.save(mechanicsCommandModel);
    }

    @PutMapping(value = "{guid}")
    public void update(@RequestBody @Valid MechanicsCommandModel mechanicsCommandModel) {
        this.mechanicalCommandService.update(mechanicsCommandModel);
    }

    @DeleteMapping(value = "{guid}")
    public void remove(@PathVariable(value = "guid") String guid) {
        this.mechanicalCommandService.remove(guid);
    }
}
