package org.tonzoc.controller;

import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.tonzoc.controller.params.MechanicsWorkTimeQueryParams;
import org.tonzoc.controller.params.PageQueryParams;
import org.tonzoc.controller.response.PageResponse;
import org.tonzoc.exception.PageException;
import org.tonzoc.model.MechanicsWorkTimeModel;
import org.tonzoc.model.RequestModel;
import org.tonzoc.service.IMechanicsWorkTimeService;
import org.tonzoc.support.param.SqlQueryParam;

import javax.validation.Valid;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

@RestController
@RequestMapping("mechanicsWorkTime")
public class MechanicsWorkTimeController extends BaseController {

    @Autowired
    private IMechanicsWorkTimeService mechanicsWorkTimeService;

    @GetMapping("/mechanicsTime")
    public List<MechanicsWorkTimeModel> mechanicsTime(RequestModel requestModel, String timestamp) throws IOException {

        return mechanicsWorkTimeService.MechanicsTime(requestModel, timestamp);
    }

    @PostMapping("/mechanicsTimeAdd")
    public String mechanicsTimeAdd(RequestModel requestModel, String timestamp) throws IOException {

        return mechanicsWorkTimeService.MechanicsTimeAdd(requestModel, timestamp);
    }

    @GetMapping
    public PageResponse list(PageQueryParams pageQueryParams, MechanicsWorkTimeQueryParams mechanicsWorkTimeQueryParams)
            throws PageException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {

        Page<MechanicsWorkTimeModel> page = parsePage(pageQueryParams);

        List<SqlQueryParam> sqlQueryParams = parseSqlQueryParams(mechanicsWorkTimeQueryParams);

        List list = mechanicsWorkTimeService.list(sqlQueryParams);

        return new PageResponse(page.getTotal(), list);
    }

    @PostMapping
    public void add(@RequestBody @Valid MechanicsWorkTimeModel mechanicsWorkTimeModel) {
        this.mechanicsWorkTimeService.save(mechanicsWorkTimeModel);
    }

    @PutMapping(value = "{guid}")
    public void update(@RequestBody @Valid MechanicsWorkTimeModel mechanicsWorkTimeModel) {
        this.mechanicsWorkTimeService.update(mechanicsWorkTimeModel);
    }

    @DeleteMapping(value = "{guid}")
    public void remove(@PathVariable(value = "guid") String guid) {
        this.mechanicsWorkTimeService.remove(guid);
    }
}
