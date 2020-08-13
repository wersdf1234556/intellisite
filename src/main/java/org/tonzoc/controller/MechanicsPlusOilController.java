package org.tonzoc.controller;

import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.tonzoc.controller.params.MechanicsPlusOilQueryParams;
import org.tonzoc.controller.params.PageQueryParams;
import org.tonzoc.controller.response.PageResponse;
import org.tonzoc.exception.PageException;
import org.tonzoc.model.MechanicsPlusOilModel;
import org.tonzoc.service.IMechanicsPlusOilService;
import org.tonzoc.support.param.SqlQueryParam;

import javax.validation.Valid;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("mechanicsPlusOil")
public class MechanicsPlusOilController extends BaseController {

    @Autowired
    private IMechanicsPlusOilService mechanicsPlusOilService;


    @GetMapping("/plusOil")
    public List<MechanicsPlusOilModel> plusOil(String start_dt, String end_dt, String page, String count_per_page) throws IOException {

        return mechanicsPlusOilService.plusOil(start_dt, end_dt, page, count_per_page);
    }

    @PostMapping("/plusOilAdd")
    public String plusOilAdd(String start_dt, String end_dt, String page, String count_per_page) throws IOException, ParseException {

        return mechanicsPlusOilService.plusOilAdd(start_dt, end_dt, page, count_per_page);
    }

    @GetMapping
    public PageResponse list(PageQueryParams pageQueryParams, MechanicsPlusOilQueryParams mechanicsPlusOilQueryParams)
            throws PageException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {

        Page<MechanicsPlusOilModel> page = parsePage(pageQueryParams);

        List<SqlQueryParam> sqlQueryParams = parseSqlQueryParams(mechanicsPlusOilQueryParams);

        List list = mechanicsPlusOilService.list(sqlQueryParams);

        return new PageResponse(page.getTotal(), list);
    }

    @PostMapping
    public void add(@RequestBody @Valid MechanicsPlusOilModel mechanicsPlusOilModel) {
        this.mechanicsPlusOilService.save(mechanicsPlusOilModel);
    }

    @PutMapping(value = "{guid}")
    public void update(@RequestBody @Valid MechanicsPlusOilModel mechanicsPlusOilModel) {
        this.mechanicsPlusOilService.update(mechanicsPlusOilModel);
    }

    @DeleteMapping(value = "{guid}")
    public void remove(@PathVariable(value = "guid") String guid) {
        this.mechanicsPlusOilService.remove(guid);
    }
}
