package org.tonzoc.controller;

import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.tonzoc.controller.params.CompanyQueryParams;
import org.tonzoc.controller.params.PageQueryParams;
import org.tonzoc.controller.response.PageResponse;
import org.tonzoc.exception.PageException;
import org.tonzoc.model.CompanyModel;
import org.tonzoc.model.ModuleModel;
import org.tonzoc.service.ICompanyService;
import org.tonzoc.support.param.SqlQueryParam;

import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

@RestController
@RequestMapping("company")
public class CompanyController extends BaseController {

    @Autowired
    private ICompanyService companyService;

    @GetMapping
    public PageResponse list(PageQueryParams pageQueryParams, CompanyQueryParams companyQueryParams)
            throws PageException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {

        Page<CompanyModel> page = parsePage(pageQueryParams);

        List<SqlQueryParam> sqlQueryParams = parseSqlQueryParams(companyQueryParams);
        List<CompanyModel> list = companyService.list(sqlQueryParams);

        return new PageResponse(page.getTotal(), list);

    }

    @PostMapping
    public void add(@RequestBody @Valid CompanyModel companyModel) {
        this.companyService.save(companyModel);
    }

    @PutMapping(value = "{guid}")
    public void update(@RequestBody @Valid CompanyModel companyModel) {
        this.companyService.update(companyModel);
    }

    @DeleteMapping(value = "{guid}")
    public void remove(@PathVariable(value = "guid") String guid) {
        this.companyService.remove(guid);
    }


}
