package org.tonzoc.controller;

import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.tonzoc.controller.params.MechanicsDataRealTimeQueryParams;
import org.tonzoc.controller.params.PageQueryParams;
import org.tonzoc.controller.response.PageResponse;
import org.tonzoc.exception.PageException;
import org.tonzoc.model.MechanicsDataRealTimeModel;
import org.tonzoc.service.IMechanicsDataRealTimeService;
import org.tonzoc.support.param.SqlQueryParam;

import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

@RestController
@RequestMapping("mechanicsDataRealTime")
public class MechanicsDataRealTimeController extends BaseController {

    @Autowired
    private IMechanicsDataRealTimeService mechanicsDataRealTimeService;


    @GetMapping
    public PageResponse list(PageQueryParams pageQueryParams, MechanicsDataRealTimeQueryParams mechanicsDataRealTimeQueryParams)
            throws PageException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {

        Page<MechanicsDataRealTimeModel> page = parsePage(pageQueryParams);

        List<SqlQueryParam> sqlQueryParams = parseSqlQueryParams(mechanicsDataRealTimeQueryParams);

        List list = mechanicsDataRealTimeService.list(sqlQueryParams);

        return new PageResponse(page.getTotal(), list);
    }

    @PostMapping
    public void add(@RequestBody @Valid MechanicsDataRealTimeModel mechanicsDataRealTimeModel) {
        this.mechanicsDataRealTimeService.save(mechanicsDataRealTimeModel);
    }

    @PutMapping(value = "{guid}")
    public void update(@RequestBody @Valid MechanicsDataRealTimeModel mechanicsDataRealTimeModel) {
        this.mechanicsDataRealTimeService.update(mechanicsDataRealTimeModel);
    }

    @DeleteMapping(value = "{guid}")
    public void remove(@PathVariable(value = "guid") String guid) {
        this.mechanicsDataRealTimeService.remove(guid);
    }
}
