package org.tonzoc.controller;

import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.tonzoc.controller.params.MechanicsEntryAndExitQueryParams;
import org.tonzoc.controller.params.PageQueryParams;
import org.tonzoc.controller.response.PageResponse;
import org.tonzoc.exception.PageException;
import org.tonzoc.model.MechanicsEntryAndExitModel;
import org.tonzoc.service.IMechanicsEntryAndExitService;
import org.tonzoc.support.param.SqlQueryParam;

import javax.validation.Valid;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

@RestController
@RequestMapping("mechanicsEntryAndExit")
public class MechanicsEntryAndExitController extends BaseController {

    @Autowired
    private IMechanicsEntryAndExitService mechanicsEntryAndExitService;

    @GetMapping("/entryAndExit")
    public List<MechanicsEntryAndExitModel> entryAndExit(String project_key, String machine_key, String date) throws IOException {

        return mechanicsEntryAndExitService.entryAndExit(project_key, machine_key, date);
    }

    @PostMapping("/entryAndExitAdd")
    public String entryAndExitAdd(String machine_key, String date) throws IOException {

        return mechanicsEntryAndExitService.entryAndExitAdd(machine_key, date);
    }

    @GetMapping
    public PageResponse list(PageQueryParams pageQueryParams, MechanicsEntryAndExitQueryParams mechanicsEntryAndExitQueryParams)
            throws PageException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {

        Page<MechanicsEntryAndExitModel> page = parsePage(pageQueryParams);

        List<SqlQueryParam> sqlQueryParams = parseSqlQueryParams(mechanicsEntryAndExitQueryParams);

        List list = mechanicsEntryAndExitService.list(sqlQueryParams);

        return new PageResponse(page.getTotal(), list);
    }

    @PostMapping
    public void add(@RequestBody @Valid MechanicsEntryAndExitModel mechanicsEntryAndExitModel) {
        this.mechanicsEntryAndExitService.save(mechanicsEntryAndExitModel);
    }

    @PutMapping(value = "{guid}")
    public void update(@RequestBody @Valid MechanicsEntryAndExitModel mechanicsEntryAndExitModel) {
        this.mechanicsEntryAndExitService.update(mechanicsEntryAndExitModel);
    }

    @DeleteMapping(value = "{guid}")
    public void remove(@PathVariable(value = "guid") String guid) {
        this.mechanicsEntryAndExitService.remove(guid);
    }
}
