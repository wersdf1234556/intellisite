package org.tonzoc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tonzoc.mapper.MaterialMapper;
import org.tonzoc.model.MaterialModel;
import org.tonzoc.model.ProjectModel;
import org.tonzoc.model.support.MaterialStatModel;
import org.tonzoc.model.support.StockStatModel;
import org.tonzoc.service.IMaterialService;
import org.tonzoc.service.IProjectService;
import org.tonzoc.support.param.SqlQueryParam;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(value = "materialService")
public class MaterialService extends BaseService<MaterialModel> implements IMaterialService {
    @Autowired
    private MaterialMapper materialMapper;
    @Autowired
    private IProjectService projectService;

    public MaterialModel listByProjectId(String projectId) {
        List<SqlQueryParam> sqlQueryParams = new ArrayList<>();
        sqlQueryParams.add(new SqlQueryParam("projectId", projectId, "eq"));

        return list(sqlQueryParams).get(0);
    }

    public List<MaterialModel> listByName(String name) {
        List<SqlQueryParam> sqlQueryParams = new ArrayList<>();
        sqlQueryParams.add(new SqlQueryParam("name", name, "eq"));

        return list(sqlQueryParams);
    }


    @Override
    public Map<String, String> countByProjectId(String projectId) {
        Map<String,String> map = new HashMap<>();
        if (projectId==null||projectId.equals("")){
            List<SqlQueryParam> sqlQueryParams = new ArrayList<>();
            BigDecimal stockOut = list(sqlQueryParams)
                    .stream()
                    // 将stockModel对象的getNum取出来map为Bigdecimal
                    .map(MaterialModel::getStockOut)
                    // 使用reduce聚合函数,实现累加器
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal stockIn = list(sqlQueryParams)
                    .stream()
                    // 将stockModel对象的getNum取出来map为Bigdecimal
                    .map(MaterialModel::getStockIn)
                    // 使用reduce聚合函数,实现累加器
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            map.put("stockOut",stockOut.toString());
            map.put("stockIn",stockIn.toString());
            BigDecimal total = stockIn.subtract(stockOut);
            map.put("surplus",total.toString());
        }else {
            MaterialModel materialModel = listByProjectId(projectId);
            map.put("stockOut",materialModel.getStockOut().toString());
            map.put("stockIn",materialModel.getStockIn().toString());
            BigDecimal total = materialModel.getStockIn().subtract(materialModel.getStockOut());
            map.put("surplus",total.toString());
        }
        return map;
    }

    public List<MaterialModel> listByCompanyId(){
        List<MaterialModel> list = new ArrayList<>();
        List<String> materialNames = materialMapper.selectAllName();
        for (String name : materialNames){
            //去掉所有空格
//            str.replace(" ", "");
            BigDecimal stockIn=listByName(name)
                    .stream()
                    // 将stockModel对象的getNum取出来map为Bigdecimal
                    .map(MaterialModel::getStockIn)
                    // 使用reduce聚合函数,实现累加器
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal stockOut = listByName(name)
                    .stream()
                    // 将stockModel对象的getNum取出来map为Bigdecimal
                    .map(MaterialModel::getStockOut)
                    // 使用reduce聚合函数,实现累加器
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            MaterialModel materialModel = new MaterialModel();
            materialModel.setName(name);
            materialModel.setStockIn(stockIn);
            materialModel.setStockOut(stockOut);
            materialModel.setSurplus(stockIn.subtract(stockOut));
            list.add(materialModel);
        }

        return list;
    }

    public Object listByProject(String projectId){
        List<SqlQueryParam> sqlQueryParams = new ArrayList<>();
        if (projectId==null){
            List<MaterialStatModel> statModels = new ArrayList<>();
            List<ProjectModel> projectModels = projectService.list(sqlQueryParams);
            for (ProjectModel projectModel : projectModels){
                List<MaterialModel> materialModels = materialMapper.listByProjectId(projectModel.getGuid());
                if (materialModels.size()!=0){
                    statModels.add(new MaterialStatModel(projectModel.getGuid(),projectModel.getName(),materialModels));
                }
            }
            return statModels;
        }else {
            List<MaterialModel> models  = materialMapper.listByProjectId(projectId);
            return models;
        }
    }

    public Object listByProjectAndName(String projectId){
        List<StockStatModel> list = new ArrayList<>();
        List<MaterialModel> models  = materialMapper.listByProjectId(projectId);
        StockStatModel model = new StockStatModel();
        model.setMaterialName("材料名称");
        model.setStockIn("入库");
        model.setStockOut("出库");
        model.setSurplus("库存");
        list.add(model);
        for (MaterialModel materialModel:models){
            StockStatModel model2 = new StockStatModel();
            model2.setMaterialName(materialModel.getName());
            model2.setStockIn(String.valueOf(materialModel.getStockIn()));
            model2.setStockOut(String.valueOf(materialModel.getStockOut()));
            model2.setSurplus(String.valueOf(materialModel.getSurplus()));
            list.add(model2);
        }
        return list;

    }
}
