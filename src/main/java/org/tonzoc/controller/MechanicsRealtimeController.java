package org.tonzoc.controller;

import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.tonzoc.controller.params.MechanicsRealtimeQueryParams;
import org.tonzoc.controller.params.PageQueryParams;
import org.tonzoc.controller.response.PageResponse;
import org.tonzoc.exception.PageException;
import org.tonzoc.model.MechanicsRealtimeModel;
import org.tonzoc.service.IMechanicsRealtimeService;
import org.tonzoc.support.param.SqlQueryParam;

import javax.validation.Valid;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("mechanicsRealtime")
public class MechanicsRealtimeController extends BaseController {

    @Autowired
    private IMechanicsRealtimeService mechanicsRealtimeService;

    @GetMapping("/mechanicState")
    public List<MechanicsRealtimeModel> mechanicState() throws IOException, ParseException {

        return mechanicsRealtimeService.MechanicState();
    }

    @PostMapping("/mechanicStateAdd")
    public String mechanicStateAdd() throws IOException, ParseException {

        return mechanicsRealtimeService.MechanicStateadd();
    }

    @GetMapping
    public PageResponse list(PageQueryParams pageQueryParams, MechanicsRealtimeQueryParams mechanicsRealtimeQueryParams)
            throws PageException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {

        Page<MechanicsRealtimeModel> page = parsePage(pageQueryParams);

        List<SqlQueryParam> sqlQueryParams = parseSqlQueryParams(mechanicsRealtimeQueryParams);

        List list = mechanicsRealtimeService.list(sqlQueryParams);

        return new PageResponse(page.getTotal(), list);
    }

    @PostMapping
    public void add(@RequestBody @Valid MechanicsRealtimeModel mechanicsRealtimeModel) {
        this.mechanicsRealtimeService.save(mechanicsRealtimeModel);
    }

    @PutMapping(value = "{guid}")
    public void update(@RequestBody @Valid MechanicsRealtimeModel mechanicsRealtimeModel) {
        this.mechanicsRealtimeService.update(mechanicsRealtimeModel);
    }

    @DeleteMapping(value = "{guid}")
    public void remove(@PathVariable(value = "guid") String guid) {
        this.mechanicsRealtimeService.remove(guid);
    }
}
