package org.tonzoc.controller;

import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.tonzoc.controller.params.MechanicsWorkRecordsQueryParams;
import org.tonzoc.controller.params.PageQueryParams;
import org.tonzoc.controller.response.PageResponse;
import org.tonzoc.exception.PageException;
import org.tonzoc.model.MechanicsWorkRecordsModel;
import org.tonzoc.service.impl.MechanicsWorkRecordsService;
import org.tonzoc.support.param.SqlQueryParam;

import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

@RestController
@RequestMapping("mechanicsWorkRecords")
public class MechanicsWorkRecordsController extends BaseController {

    @Autowired
    private MechanicsWorkRecordsService mechanicsWorkRecordsService;

    @GetMapping
    public PageResponse list(PageQueryParams pageQueryParams, MechanicsWorkRecordsQueryParams mechanicsWorkRecordsQueryParams)
            throws PageException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {

        Page<MechanicsWorkRecordsModel> page = parsePage(pageQueryParams);

        List<SqlQueryParam> sqlQueryParams = parseSqlQueryParams(mechanicsWorkRecordsQueryParams);

        List list = mechanicsWorkRecordsService.list(sqlQueryParams);

        return new PageResponse(page.getTotal(), list);
    }

    @PostMapping
    public void add(@RequestBody @Valid MechanicsWorkRecordsModel mechanicsWorkRecordsModel) {
        this.mechanicsWorkRecordsService.save(mechanicsWorkRecordsModel);
    }

    @PutMapping(value = "{guid}")
    public void update(@RequestBody @Valid MechanicsWorkRecordsModel mechanicsWorkRecordsModel) {
        this.mechanicsWorkRecordsService.update(mechanicsWorkRecordsModel);
    }

    @DeleteMapping(value = "{guid}")
    public void remove(@PathVariable(value = "guid") String guid) {
        this.mechanicsWorkRecordsService.remove(guid);
    }
}
