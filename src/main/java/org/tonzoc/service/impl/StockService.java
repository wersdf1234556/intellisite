package org.tonzoc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tonzoc.model.MaterialModel;
import org.tonzoc.model.StockModel;
import org.tonzoc.service.IMaterialService;
import org.tonzoc.service.IStockService;
import org.tonzoc.support.param.SqlQueryParam;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service(value = "stockService")
public class StockService extends BaseService<StockModel> implements IStockService {
    @Autowired
    private IMaterialService materialService;

    public List<StockModel> listStock(String materialId,Integer type) {
        List<SqlQueryParam> sqlQueryParams = new ArrayList<>();
        sqlQueryParams.add(new SqlQueryParam("materialId", materialId.toString(), "eq"));
        sqlQueryParams.add(new SqlQueryParam("type", type.toString(), "eq"));

        return list(sqlQueryParams);
    }

    public void insertStock(StockModel stockModel){
        save(stockModel);
        updateMaterial(stockModel);

    }

    public void updateStock(StockModel stockModel){
        update(stockModel);
        updateMaterial(stockModel);

    }

    public void deleteStock(String guid){
        StockModel stockModel = get(guid);
        remove(guid);
        if (materialService.get(stockModel.getMaterialId())!=null){
            updateMaterial(stockModel);
        }

    }

    public void updateMaterial(StockModel stockModel){
        MaterialModel materialModel = materialService.get(stockModel.getMaterialId());
        if(materialModel!=null){
            //stockModel.getType()==0出库,stockModel.getType()==1入库
            if (stockModel.getType()==0){
                BigDecimal stockOut = listStock(stockModel.getMaterialId(),0)
                        .stream()
                        // 将stockModel对象的getNum取出来map为Bigdecimal
                        .map(StockModel::getNum)
                        // 使用reduce聚合函数,实现累加器
                        .reduce(BigDecimal.ZERO, BigDecimal::add);;
                materialModel.setStockOut(stockOut);
            }else if (stockModel.getType()==1){
                BigDecimal stockIn = listStock(stockModel.getMaterialId(),1)
                        .stream()
                        // 将stockModel对象的getNum取出来map为Bigdecimal
                        .map(StockModel::getNum)
                        // 使用reduce聚合函数,实现累加器
                        .reduce(BigDecimal.ZERO, BigDecimal::add);;
                materialModel.setStockIn(stockIn);
            }

            materialService.update(materialModel);
        }
    }


}
