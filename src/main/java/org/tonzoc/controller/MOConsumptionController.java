package org.tonzoc.controller;

import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.tonzoc.controller.params.MOConsumptionQueryParams;
import org.tonzoc.controller.params.PageQueryParams;
import org.tonzoc.controller.response.PageResponse;
import org.tonzoc.exception.PageException;
import org.tonzoc.model.MOConsumptionModel;
import org.tonzoc.service.IMOConsumptionService;
import org.tonzoc.support.param.SqlQueryParam;

import javax.validation.Valid;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("MOConsumptionController")
public class MOConsumptionController extends BaseController {

    @Autowired
    private IMOConsumptionService moConsumptionService;

    @GetMapping("/oilConsumptionS")
    public List<MOConsumptionModel> oilConsumption(String machine_key, String timestamp) throws IOException {

        return moConsumptionService.oilConsumption(machine_key, timestamp);
    }

    @PostMapping("/oilConsumptionAdd")
    public String oilConsumptionAdd(String machine_key) throws IOException, ParseException {

        return moConsumptionService.oilConsumptionAdd(machine_key);
    }


    @PostMapping("/oilConsumptionAddHour")
    public String oilConsumptionAddHour(String machine_key) throws IOException, ParseException {

        return moConsumptionService.oilConsumptionAddHour(machine_key);
    }

    @GetMapping
    public PageResponse list(PageQueryParams pageQueryParams, MOConsumptionQueryParams moConsumptionQueryParams)
            throws PageException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {

        Page<MOConsumptionModel> page = parsePage(pageQueryParams);

        List<SqlQueryParam> sqlQueryParams = parseSqlQueryParams(moConsumptionQueryParams);
        List<MOConsumptionModel> list = moConsumptionService.list(sqlQueryParams);

        return new PageResponse(page.getTotal(), list);
    }

    @PostMapping
    public void add(@RequestBody @Valid MOConsumptionModel moConsumptionModel) {
        this.moConsumptionService.save(moConsumptionModel);
    }

    @PutMapping(value = "{guid}")
    public void update(@RequestBody @Valid MOConsumptionModel moConsumptionModel) {
        this.moConsumptionService.update(moConsumptionModel);
    }

    @DeleteMapping(value = "{guid}")
    public void remove(@PathVariable(value = "guid") String guid) {
        this.moConsumptionService.remove(guid);
    }

}
