package org.tonzoc.controller;

import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.tonzoc.controller.params.PageQueryParams;
import org.tonzoc.controller.params.PositionOneQueryParams;
import org.tonzoc.controller.response.PageResponse;
import org.tonzoc.exception.PageException;
import org.tonzoc.model.PositionOneModel;
import org.tonzoc.service.IPositionOneService;
import org.tonzoc.support.param.SqlQueryParam;

import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

@RestController
@RequestMapping("positionOne")
public class PositionOneController extends BaseController {

    @Autowired
    private IPositionOneService positionOneService;

    @GetMapping
    public PageResponse list(PageQueryParams pageQueryParams, PositionOneQueryParams positionOneQueryParams)
            throws PageException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {

        Page<PositionOneModel> page = parsePage(pageQueryParams);

        List<SqlQueryParam> sqlQueryParams = parseSqlQueryParams(positionOneQueryParams);
        List<PositionOneModel> list = positionOneService.list(sqlQueryParams);

        return new PageResponse(page.getTotal(), list);

    }

    @PostMapping
    public void add(@RequestBody @Valid PositionOneModel positionOneModel) {
        this.positionOneService.save(positionOneModel);
    }

    @PutMapping(value = "{guid}")
    public void update(@RequestBody @Valid PositionOneModel positionOneModel) {
        this.positionOneService.update(positionOneModel);
    }

    @DeleteMapping(value = "{guid}")
    public void remove(@PathVariable(value = "guid") String guid) {
        this.positionOneService.remove(guid);
    }


}
