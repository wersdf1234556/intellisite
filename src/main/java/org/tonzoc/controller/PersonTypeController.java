package org.tonzoc.controller;

import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.tonzoc.controller.params.PageQueryParams;
import org.tonzoc.controller.params.PersonTypeQueryParams;
import org.tonzoc.controller.response.PageResponse;
import org.tonzoc.exception.PageException;
import org.tonzoc.model.PersonTypeModel;
import org.tonzoc.service.IPersonTypeService;
import org.tonzoc.support.param.SqlQueryParam;

import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

@RestController
@RequestMapping(value = "personType")
public class PersonTypeController extends BaseController {

    @Autowired
    private IPersonTypeService personTypeService;

    @GetMapping
    public PageResponse list(PageQueryParams pageQueryParams, PersonTypeQueryParams personTypeQueryParams)
            throws PageException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {

        Page<PersonTypeModel> page = parsePage(pageQueryParams);

        List<SqlQueryParam> sqlQueryParams = parseSqlQueryParams(personTypeQueryParams);

        List list = personTypeService.list(sqlQueryParams);

        return new PageResponse(page.getTotal(), list);
    }

    @GetMapping(value = "{guid}")
    public PersonTypeModel get(@PathVariable(value = "guid") String guid) {
        return personTypeService.get(guid);
    }

    @PostMapping
    public void add(@RequestBody @Valid PersonTypeModel personTypeModel) {
        this.personTypeService.save(personTypeModel);
    }

    @PutMapping(value = "{guid}")
    public void update(@RequestBody @Valid PersonTypeModel personTypeModel) {
        this.personTypeService.update(personTypeModel);
    }

    @DeleteMapping(value = "{guid}")
    public void remove(@PathVariable(value = "guid") String guid) {
        this.personTypeService.remove(guid);
    }

}
