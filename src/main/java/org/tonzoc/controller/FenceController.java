package org.tonzoc.controller;

import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.tonzoc.controller.params.FenceQueryParams;
import org.tonzoc.controller.params.PageQueryParams;
import org.tonzoc.controller.response.PageResponse;
import org.tonzoc.exception.PageException;
import org.tonzoc.model.FenceModel;
import org.tonzoc.model.SecurityAlertModel;
import org.tonzoc.model.support.ReturnValueModel;
import org.tonzoc.service.IFenceService;
import org.tonzoc.support.param.SqlQueryParam;

import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

@RestController
@RequestMapping("fence")
public class FenceController extends BaseController {

    @Autowired
    private IFenceService fenceService;

    @GetMapping("/alert")
    public List<SecurityAlertModel> alert () {

        return fenceService.alert();
    }

    @GetMapping
    public PageResponse list(PageQueryParams pageQueryParams, FenceQueryParams fenceQueryParams)
            throws PageException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {

        Page<FenceModel> page = parsePage(pageQueryParams);
        FenceQueryParams sqlQueryParamList = new FenceQueryParams();
        if (fenceQueryParams.getGuid() != null && !fenceQueryParams.getGuid().equals("")) {
            sqlQueryParamList.setGuid(fenceQueryParams.getGuid());
        }
        if (fenceQueryParams.getName() != null && !fenceQueryParams.getName().equals("")) {
            sqlQueryParamList.setName(fenceQueryParams.getName());
        }
        if (fenceQueryParams.getProjectId() != null && !fenceQueryParams.getProjectId().equals("")) {
            sqlQueryParamList.setProjectId(fenceQueryParams.getProjectId());
        }

        List<SqlQueryParam> sqlQueryParams = parseSqlQueryParams(sqlQueryParamList);
        List<FenceModel> list = fenceService.list(sqlQueryParams);

        return new PageResponse(page.getTotal(), list);

    }

    @PostMapping
    public void add(@RequestBody @Valid FenceModel fenceModel) {
        this.fenceService.save(fenceModel);
    }

    @PutMapping(value = "{guid}")
    public void update(@RequestBody @Valid FenceModel fenceModel) {
        this.fenceService.update(fenceModel);
    }

    @DeleteMapping(value = "{guid}")
    public void remove(@PathVariable(value = "guid") String guid) {
        this.fenceService.remove(guid);
    }


}
