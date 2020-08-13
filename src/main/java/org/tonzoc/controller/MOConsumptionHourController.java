package org.tonzoc.controller;

import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.tonzoc.controller.params.MOConsumptionHourQueryParams;
import org.tonzoc.controller.params.PageQueryParams;
import org.tonzoc.controller.response.PageResponse;
import org.tonzoc.exception.PageException;
import org.tonzoc.model.MOConsumptionHourModel;
import org.tonzoc.service.IMOConsumptionHourService;
import org.tonzoc.support.param.SqlQueryParam;

import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

@RestController
@RequestMapping("MOConsumptionHour")
public class MOConsumptionHourController extends BaseController {

    @Autowired
    private IMOConsumptionHourService moConsumptionHourService;

    @GetMapping
    public PageResponse list(PageQueryParams pageQueryParams, MOConsumptionHourQueryParams moConsumptionHourQueryParams)
            throws PageException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {

        Page<MOConsumptionHourModel> page = parsePage(pageQueryParams);

        List<SqlQueryParam> sqlQueryParams = parseSqlQueryParams(moConsumptionHourQueryParams);
        List<MOConsumptionHourModel> list = moConsumptionHourService.list(sqlQueryParams);

        return new PageResponse(page.getTotal(), list);
    }

    @PostMapping
    public void add(@RequestBody @Valid MOConsumptionHourModel moConsumptionHourModel) {
        this.moConsumptionHourService.save(moConsumptionHourModel);
    }

    @PutMapping(value = "{guid}")
    public void update(@RequestBody @Valid MOConsumptionHourModel moConsumptionHourModel) {
        this.moConsumptionHourService.update(moConsumptionHourModel);
    }

    @DeleteMapping(value = "{guid}")
    public void remove(@PathVariable(value = "guid") String guid) {
        this.moConsumptionHourService.remove(guid);
    }
}
