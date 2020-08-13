package org.tonzoc.controller;

import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.tonzoc.controller.params.DepartmentQueryParams;
import org.tonzoc.controller.params.FlowQueryParams;
import org.tonzoc.controller.params.PageQueryParams;
import org.tonzoc.controller.response.PageResponse;
import org.tonzoc.exception.PageException;
import org.tonzoc.model.DepartmentModel;
import org.tonzoc.model.FlowModel;
import org.tonzoc.service.IDepartmentService;
import org.tonzoc.service.IFlowService;
import org.tonzoc.support.param.SqlQueryParam;

import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

@RestController
@RequestMapping(value = "flow")
public class FlowController extends BaseController {

    @Autowired
    private IFlowService flowService;

    @GetMapping
    public PageResponse list(PageQueryParams pageQueryParams, FlowQueryParams flowQueryParams)
            throws PageException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {

        Page<FlowModel> page = parsePage(pageQueryParams);

        DepartmentQueryParams sqlQueryParamList = new DepartmentQueryParams();
        if (flowQueryParams.getName() != null && !flowQueryParams.getName().equals("")) {
            sqlQueryParamList.setName(flowQueryParams.getName());
        }

        List<SqlQueryParam> sqlQueryParams = parseSqlQueryParams(sqlQueryParamList);

        List list = flowService.list(sqlQueryParams);

        return new PageResponse(page.getTotal(), list);
    }

    @GetMapping(value = "{guid}")
    public FlowModel get(@PathVariable(value = "guid") String guid) {
        return flowService.get(guid);
    }

    @PostMapping
    public void add(@RequestBody @Valid FlowModel flowModel) {
        this.flowService.save(flowModel);
    }

    @PutMapping(value = "{guid}")
    public void update(@RequestBody @Valid FlowModel flowModel) {
        this.flowService.update(flowModel);
    }

    @DeleteMapping(value = "{guid}")
    public void remove(@PathVariable(value = "guid") String guid) {
        this.flowService.remove(guid);
    }

}
