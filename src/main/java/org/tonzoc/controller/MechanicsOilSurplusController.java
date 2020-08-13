package org.tonzoc.controller;

import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.tonzoc.controller.params.MechanicsOilSurplusQueryParams;
import org.tonzoc.controller.params.PageQueryParams;
import org.tonzoc.controller.response.PageResponse;
import org.tonzoc.exception.PageException;
import org.tonzoc.model.MechanicsOilSurplusModel;
import org.tonzoc.service.IMechanicsOilSurplusService;
import org.tonzoc.support.param.SqlQueryParam;

import javax.validation.Valid;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

@RestController
@RequestMapping("mechanicsOilSurplus")
public class MechanicsOilSurplusController extends BaseController {

    @Autowired
    private IMechanicsOilSurplusService oilLevelRealTimeService;

    @GetMapping("/oilLevel")
    public List<MechanicsOilSurplusModel> oilLevel(Integer page, Integer count_per_page) throws IOException {

        return oilLevelRealTimeService.oilLevel(page, count_per_page);
    }

    @GetMapping("/oilLevelAdd")
    public String oilLevelAdd(Integer page, Integer count_per_page) throws IOException {

        return oilLevelRealTimeService.oilLevelAdd(page, count_per_page);
    }

    @GetMapping
    public PageResponse list(PageQueryParams pageQueryParams, MechanicsOilSurplusQueryParams oilLevelRealTimeQueryParams)
            throws PageException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {

        Page<MechanicsOilSurplusModel> page = parsePage(pageQueryParams);

        List<SqlQueryParam> sqlQueryParams = parseSqlQueryParams(oilLevelRealTimeQueryParams);

        List list = oilLevelRealTimeService.list(sqlQueryParams);

        return new PageResponse(page.getTotal(), list);
    }

    @PostMapping
    public void add(@RequestBody @Valid MechanicsOilSurplusModel oilLevelRealTimeModel) {
        this.oilLevelRealTimeService.save(oilLevelRealTimeModel);
    }

    @PutMapping(value = "{guid}")
    public void update(@RequestBody @Valid MechanicsOilSurplusModel oilLevelRealTimeModel) {
        this.oilLevelRealTimeService.update(oilLevelRealTimeModel);
    }

    @DeleteMapping(value = "{guid}")
    public void remove(@PathVariable(value = "guid") String guid) {
        this.oilLevelRealTimeService.remove(guid);
    }
}
