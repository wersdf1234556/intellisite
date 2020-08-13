package org.tonzoc.controller;

import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.tonzoc.controller.params.MechanicsDeviceParams;
import org.tonzoc.controller.params.PageQueryParams;
import org.tonzoc.controller.response.PageResponse;
import org.tonzoc.exception.PageException;
import org.tonzoc.model.MechanicsDeviceModel;
import org.tonzoc.service.IMechanicsDeviceService;
import org.tonzoc.support.param.SqlQueryParam;

import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

@RestController
@RequestMapping("mechanicsDevice")
public class MechanicsDeviceController extends BaseController {

    @Autowired
    private IMechanicsDeviceService mechanicsDeviceService;

    @GetMapping
    public PageResponse list(PageQueryParams pageQueryParams, MechanicsDeviceParams mechanicsDeviceParams)
            throws PageException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {

        Page<MechanicsDeviceModel> page = parsePage(pageQueryParams);

        List<SqlQueryParam> sqlQueryParams = parseSqlQueryParams(mechanicsDeviceParams);

        List list = mechanicsDeviceService.list(sqlQueryParams);

        return new PageResponse(page.getTotal(), list);
    }

    @PostMapping
    public void add(@RequestBody @Valid MechanicsDeviceModel mechanicsDeviceModel) {
        this.mechanicsDeviceService.save(mechanicsDeviceModel);
    }

    @PutMapping(value = "{guid}")
    public void update(@RequestBody @Valid MechanicsDeviceModel mechanicsDeviceModel) {
        this.mechanicsDeviceService.update(mechanicsDeviceModel);
    }

    @DeleteMapping(value = "{guid}")
    public void remove(@PathVariable(value = "guid") String guid) {
        this.mechanicsDeviceService.remove(guid);
    }
}
