package org.tonzoc.controller;

import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.tonzoc.controller.params.CompanyQueryParams;
import org.tonzoc.controller.params.EducationQueryParams;
import org.tonzoc.controller.params.PageQueryParams;
import org.tonzoc.controller.response.PageResponse;
import org.tonzoc.exception.PageException;
import org.tonzoc.model.CompanyModel;
import org.tonzoc.model.EducationModel;
import org.tonzoc.model.ModuleModel;
import org.tonzoc.service.ICompanyService;
import org.tonzoc.service.IEducationService;
import org.tonzoc.support.param.SqlQueryParam;

import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

@RestController
@RequestMapping("education")
public class EducationController extends BaseController {

    @Autowired
    private IEducationService educationService;

    @GetMapping
    public PageResponse list(PageQueryParams pageQueryParams, EducationQueryParams educationQueryParams)
            throws PageException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {

        Page<EducationModel> page = parsePage(pageQueryParams);
        EducationQueryParams sqlQueryParamList = new EducationQueryParams();
        if (educationQueryParams.getName() != null && !educationQueryParams.getName().equals("")) {
            sqlQueryParamList.setName(educationQueryParams.getName());
        }
        List<SqlQueryParam> sqlQueryParams = parseSqlQueryParams(sqlQueryParamList);
        List<EducationModel> list = educationService.list(sqlQueryParams);

        return new PageResponse(page.getTotal(), list);

    }

    @PostMapping
    public void add(@RequestBody @Valid EducationModel educationModel) {
        if (educationModel.getSortId() == null) {
            educationModel.setSortId(0);
        }
        this.educationService.save(educationModel);
    }

    @PutMapping(value = "{guid}")
    public void update(@RequestBody @Valid EducationModel educationModel) {
        if (educationModel.getSortId() == null) {
            educationModel.setSortId(0);
        }
        this.educationService.update(educationModel);
    }

    @DeleteMapping(value = "{guid}")
    public void remove(@PathVariable(value = "guid") String guid) {
        this.educationService.remove(guid);
    }


}
