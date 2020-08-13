package org.tonzoc.controller;

import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.tonzoc.controller.params.PageQueryParams;
import org.tonzoc.controller.params.SecurityAlertQueryParams;
import org.tonzoc.controller.response.PageResponse;
import org.tonzoc.exception.PageException;
import org.tonzoc.model.SectionProjectModel;
import org.tonzoc.model.SecurityAlertModel;
import org.tonzoc.service.ISecurityAlertService;
import org.tonzoc.support.param.SqlQueryParam;

import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

@RestController
@RequestMapping("securityAlert")
public class SecurityAlertController extends BaseController {

    @Autowired
    private ISecurityAlertService securityAlertService;

    @GetMapping
    public PageResponse list(PageQueryParams pageQueryParams, SecurityAlertQueryParams sectionProjectQueryParams)
            throws PageException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {

        Page<SectionProjectModel> page = parsePage(pageQueryParams);

        List<SqlQueryParam> sqlQueryParams = parseSqlQueryParams(sectionProjectQueryParams);

        List list = securityAlertService.list(sqlQueryParams);

        return new PageResponse(page.getTotal(), list);
    }

    @PostMapping
    public void add(SecurityAlertModel securityAlertModel) {
        this.securityAlertService.save(securityAlertModel);
    }

    @PutMapping(value = "{guid}")
    public void update(SecurityAlertModel securityAlertModel) {
        this.securityAlertService.update(securityAlertModel);
    }

    @DeleteMapping(value = "{guid}")
    public void remove(@PathVariable(value = "guid") String guid) {
        this.securityAlertService.remove(guid);
    }
}
