package org.tonzoc.controller;

import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;
import org.tonzoc.controller.params.MechanicQueryParams;
import org.tonzoc.controller.params.PageQueryParams;
import org.tonzoc.controller.response.PageResponse;
import org.tonzoc.exception.NotOneResultFoundException;
import org.tonzoc.exception.PageException;
import org.tonzoc.model.MechanicsCommandModel;
import org.tonzoc.model.MechanicsCountModel;
import org.tonzoc.model.MechanicsModel;
import org.tonzoc.model.RelevanceTableModel;
import org.tonzoc.service.IMechanicsService;
import org.tonzoc.service.IRelevanceTableService;
import org.tonzoc.support.param.SqlQueryParam;

import javax.validation.Valid;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("mechanics")
public class MechanicsController extends BaseController {

    @Autowired
    private IMechanicsService mechanicsService;

    @Autowired
    private IRelevanceTableService relevanceTableService;

    @GetMapping("/mechanicsInformation")
    public List<MechanicsModel> mechanicsInformation(String project_key, Integer count_per_page, Integer page) throws IOException {

        return mechanicsService.mechanicsInformation(project_key, count_per_page, page);
    }

    @PostMapping("/mechanicsInformationAdd")
    public String mechanicsInformationAdd(String project_key, Integer count_per_page, Integer page) throws IOException {

        return mechanicsService.mechanicsInformationAdd(project_key, count_per_page, page);
    }

    // 饼图
    @GetMapping("/mechanicsChart")
    public List<MechanicsCountModel> mechanicsChart(String projectGuid) {

        return mechanicsService.mechanicsChart(projectGuid);
    }

    // 机械概况
    @GetMapping("/mechanicsState")
    public List<MechanicsCountModel> mechanicsState(String projectGuid) {

        return mechanicsService.mechanicsState(projectGuid);
    }

    // 进场机械
    @GetMapping("/mechanicsApproach")
    public List<MechanicsCountModel> mechanicsApproach(String projectGuid) {

        return mechanicsService.mechanicsApproach(projectGuid);
    }

    // 油耗情况
    @GetMapping("/oilConsumption")
    public List<MechanicsCountModel> oilStatistics(String projectGuid) {

        return mechanicsService.oilStatistics(projectGuid);
    }

    // 项目油耗情况
    @GetMapping("/projectOilStatistics")
    public List<MechanicsCountModel> projectOilStatistics () {

        return mechanicsService.projectOilStatistics();
    }

    @GetMapping
    public PageResponse list(PageQueryParams pageQueryParams, MechanicQueryParams mechanicQueryParams)
            throws PageException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {

        Page<MechanicsModel> page = parsePage(pageQueryParams);
        MechanicQueryParams sqlQueryParamList = new MechanicQueryParams();
        if (mechanicQueryParams.getName() != null && !mechanicQueryParams.getName().equals("")) {
            sqlQueryParamList.setName(mechanicQueryParams.getName());
        }
        List<SqlQueryParam> sqlQueryParams = parseSqlQueryParams(sqlQueryParamList);
        List<MechanicsModel> list = mechanicsService.list(sqlQueryParams);
        for (MechanicsModel mechanicsModel : list) {
            List<MechanicsCommandModel> mechanicsCommandModels = new ArrayList<>();

            List<RelevanceTableModel> relevanceTableModels = relevanceTableService.listByMainId(mechanicsModel.getGuid());

            mechanicsModel.setMechanicsCommandModels(relevanceTableModels
                    .stream()
                    .map(relevanceTableModel -> {
                        MechanicsCommandModel mechanicsCommandModel = new MechanicsCommandModel();
                        mechanicsCommandModel.setGuid(relevanceTableModel.getDependenceId());

                        return mechanicsCommandModel;
                    })
                    .collect(Collectors.toList()));

        }

        return new PageResponse(page.getTotal(), list);
    }

    @PostMapping
    public void add(@RequestBody @Valid MechanicsModel mechanicsModel) {
        this.mechanicsService.save(mechanicsModel);
    }

    @PutMapping(value = "{guid}")
    public void update(@RequestBody @Valid MechanicsModel mechanicsModel) {
        this.mechanicsService.update(mechanicsModel);
    }

    @DeleteMapping(value = "{guid}")
    public void remove(@PathVariable(value = "guid") String projectGuid) throws NotOneResultFoundException {
        this.mechanicsService.deleteMechanic(projectGuid);
    }
}
