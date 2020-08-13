package org.tonzoc.controller;

import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.tonzoc.controller.params.PageQueryParams;
import org.tonzoc.controller.params.PositionTwoQueryParams;
import org.tonzoc.controller.response.PageResponse;
import org.tonzoc.exception.PageException;
import org.tonzoc.model.PositionTwoModel;
import org.tonzoc.service.IPositionTwoService;
import org.tonzoc.support.param.SqlQueryParam;

import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

@RestController
@RequestMapping("positionTwo")
public class PositionTwoController extends BaseController {

    @Autowired
    private IPositionTwoService positionTwoService;

    @GetMapping
    public PageResponse list(PageQueryParams pageQueryParams, PositionTwoQueryParams positionTwoQueryParams)
            throws PageException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {

        Page<PositionTwoModel> page = parsePage(pageQueryParams);

        List<SqlQueryParam> sqlQueryParams = parseSqlQueryParams(positionTwoQueryParams);
        List<PositionTwoModel> list = positionTwoService.list(sqlQueryParams);

        return new PageResponse(page.getTotal(), list);

    }

    @PostMapping
    public void add(@RequestBody @Valid PositionTwoModel positionTwoModel) {
        this.positionTwoService.save(positionTwoModel);
    }

    @PutMapping(value = "{guid}")
    public void update(@RequestBody @Valid PositionTwoModel positionTwoModel) {
        this.positionTwoService.update(positionTwoModel);
    }

    @DeleteMapping(value = "{guid}")
    public void remove(@PathVariable(value = "guid") String guid) {
        this.positionTwoService.remove(guid);
    }


}
