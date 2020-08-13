package org.tonzoc.controller;

import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.tonzoc.controller.params.EducationQueryParams;
import org.tonzoc.controller.params.PageQueryParams;
import org.tonzoc.controller.params.PersonStatusQueryParams;
import org.tonzoc.controller.response.PageResponse;
import org.tonzoc.exception.PageException;
import org.tonzoc.model.EducationModel;
import org.tonzoc.model.PersonStatusModel;
import org.tonzoc.service.IEducationService;
import org.tonzoc.service.IPersonStatusService;
import org.tonzoc.support.param.SqlQueryParam;

import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

@RestController
@RequestMapping("personStatus")
public class PersonStatusController extends BaseController {

    @Autowired
    private IPersonStatusService personStatusService;

    @GetMapping
    public PageResponse list(PageQueryParams pageQueryParams, PersonStatusQueryParams personStatusQueryParams)
            throws PageException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {

        Page<PersonStatusModel> page = parsePage(pageQueryParams);

        List<SqlQueryParam> sqlQueryParams = parseSqlQueryParams(personStatusQueryParams);
        List<PersonStatusModel> list = personStatusService.list(sqlQueryParams);

        return new PageResponse(page.getTotal(), list);

    }

    @PostMapping
    public void add(@RequestBody @Valid PersonStatusModel personStatusModel) {
        if (personStatusModel.getSortId() == null) {
            personStatusModel.setSortId(0);
        }
        this.personStatusService.save(personStatusModel);
    }

    @PutMapping(value = "{guid}")
    public void update(@RequestBody @Valid PersonStatusModel personStatusModel) {
        if (personStatusModel.getSortId() == null) {
            personStatusModel.setSortId(0);
        }
        this.personStatusService.update(personStatusModel);
    }

    @DeleteMapping(value = "{guid}")
    public void remove(@PathVariable(value = "guid") String guid) {
        this.personStatusService.remove(guid);
    }


}
