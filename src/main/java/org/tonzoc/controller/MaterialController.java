package org.tonzoc.controller;

import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.tonzoc.controller.params.FenceQueryParams;
import org.tonzoc.controller.params.MaterialQueryParams;
import org.tonzoc.controller.params.PageQueryParams;
import org.tonzoc.controller.response.PageResponse;
import org.tonzoc.exception.PageException;
import org.tonzoc.model.FenceModel;
import org.tonzoc.model.MaterialModel;
import org.tonzoc.service.IFenceService;
import org.tonzoc.service.IMaterialService;
import org.tonzoc.support.param.SqlQueryParam;

import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("material")
public class MaterialController extends BaseController {

    @Autowired
    private IMaterialService materialService;

    @GetMapping
    public PageResponse list(PageQueryParams pageQueryParams, MaterialQueryParams materialQueryParams)
            throws PageException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {

        Page<MaterialModel> page = parsePage(pageQueryParams);
        MaterialQueryParams sqlQueryParamList = new MaterialQueryParams();
        if (materialQueryParams.getGuid() != null && !materialQueryParams.getGuid().equals("")) {
            sqlQueryParamList.setGuid(materialQueryParams.getGuid());
        }
        if (materialQueryParams.getName() != null && !materialQueryParams.getName().equals("")) {
            sqlQueryParamList.setName(materialQueryParams.getName());
        }
        if (materialQueryParams.getProjectId() != null && !materialQueryParams.getProjectId().equals("")) {
            sqlQueryParamList.setProjectId(materialQueryParams.getProjectId());
        }

        List<SqlQueryParam> sqlQueryParams = parseSqlQueryParams(sqlQueryParamList);
        List<MaterialModel> list = materialService.list(sqlQueryParams);

        return new PageResponse(page.getTotal(), list);

    }

    @PostMapping
    public void add(@RequestBody @Valid MaterialModel materialModel) {
        this.materialService.save(materialModel);
    }

    @PutMapping(value = "{guid}")
    public void update(@RequestBody @Valid MaterialModel materialModel) {
        this.materialService.update(materialModel);
    }

    @DeleteMapping(value = "{guid}")
    public void remove(@PathVariable(value = "guid") String guid) {
        this.materialService.remove(guid);
    }

    //统计
    @GetMapping(value = "stat")
    public Map<String, String> stat(String projectId) {
        return materialService.countByProjectId(projectId);
    }

    //所有项目所有材料统计
    @GetMapping(value = "statByCompany")
    public List<MaterialModel> statByCompanyId(){
        return materialService.listByCompanyId();
    }

    //根据项目查询出入库情况，projectId若不传则查所有项目
    @GetMapping(value = "listByProject")
    public Object listByProject(String projectId){
        return materialService.listByProject(projectId);
    }

    @GetMapping(value = "listByMaterialName")
    public Object listByName(String projectId){
        return materialService.listByProjectAndName(projectId);
    }
}
