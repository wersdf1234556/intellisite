package org.tonzoc.controller;

import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;
import org.tonzoc.controller.params.DepartmentQueryParams;
import org.tonzoc.controller.params.PageQueryParams;
import org.tonzoc.controller.response.PageResponse;
import org.tonzoc.exception.PageException;
import org.tonzoc.model.DepartmentModel;
import org.tonzoc.service.IDepartmentService;
import org.tonzoc.support.param.SqlQueryParam;

import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "department")
public class DepartmentController extends BaseController {

    @Autowired
    private IDepartmentService departmentService;

    @GetMapping
    public PageResponse list(PageQueryParams pageQueryParams, DepartmentQueryParams departmentQueryParams)
            throws PageException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {

        Page<DepartmentModel> page = parsePage(pageQueryParams);

//        DepartmentQueryParams sqlQueryParamList = new DepartmentQueryParams();
//        if (departmentQueryParams.getName() != null && !departmentQueryParams.getName().equals("")) {
//            sqlQueryParamList.setName(departmentQueryParams.getName());
//        }

        List<SqlQueryParam> sqlQueryParams = parseSqlQueryParams(departmentQueryParams);

        List<DepartmentModel> list = departmentService.list(sqlQueryParams);
//        for (DepartmentModel departmentModel : list) {
//            List<DepartmentModel> departmentModels = departmentService.listByParentId(departmentModel.getGuid());
//
//            departmentModel.setChildren(departmentModels
//                    .stream()
//                    .map(department -> {
//                        DepartmentModel model = new DepartmentModel();
//                        model.setGuid(department.getGuid());
//                        model.setName(department.getName());
//
//                        return model;
//                    })
//                    .collect(Collectors.toList()));
//        }

        return new PageResponse(page.getTotal(), list);
    }

    @GetMapping(value = "{guid}")
    public DepartmentModel get(@PathVariable(value = "guid") String guid) {
        return departmentService.get(guid);
    }

    @PostMapping
    public void add(@RequestBody @Valid DepartmentModel departmentModel) {
        this.departmentService.save(departmentModel);
    }

    @PutMapping(value = "{guid}")
//    @CacheEvict(value = "list_by_departId", allEntries = true)
    public void update(@RequestBody @Valid DepartmentModel departmentModel) {
        this.departmentService.update(departmentModel);
    }

    @DeleteMapping(value = "{guid}")
//    @CacheEvict(value = "list_by_departId", allEntries = true)
    public void remove(@PathVariable(value = "guid") String guid) {
        this.departmentService.remove(guid);
    }


    @GetMapping(value = "listWithLevel")
//    @CacheEvict(value = "list_by_departId", allEntries = true)
    public List<DepartmentModel> listWithLevel(String flowId) throws Exception{
        return departmentService.listWithLevel(flowId);
    }

    @GetMapping(value = "nextDepart")
    public List<DepartmentModel> listNextStep(Integer sign,String leaveId,String currentPersonId) throws Exception{
        return departmentService.listNextDepart(sign,leaveId,currentPersonId);
    }


}
