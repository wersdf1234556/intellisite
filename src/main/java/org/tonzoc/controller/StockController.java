package org.tonzoc.controller;

import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.tonzoc.controller.params.FenceQueryParams;
import org.tonzoc.controller.params.PageQueryParams;
import org.tonzoc.controller.params.StockQueryParams;
import org.tonzoc.controller.response.PageResponse;
import org.tonzoc.exception.PageException;
import org.tonzoc.model.StockModel;
import org.tonzoc.service.IStockService;
import org.tonzoc.support.param.SqlQueryParam;

import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

@RestController
@RequestMapping("stock")
public class StockController extends BaseController {

    @Autowired
    private IStockService stockService;

    @GetMapping
    public PageResponse list(PageQueryParams pageQueryParams, StockQueryParams stockQueryParams)
            throws PageException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {

        Page<StockModel> page = parsePage(pageQueryParams);
        StockQueryParams sqlQueryParamList = new StockQueryParams();
        if (stockQueryParams.getGuid() != null && !stockQueryParams.getGuid().equals("")) {
            sqlQueryParamList.setGuid(stockQueryParams.getGuid());
        }
        if (stockQueryParams.getMaterialId() != null && !stockQueryParams.getMaterialId().equals("")) {
            sqlQueryParamList.setMaterialId(stockQueryParams.getMaterialId());
        }
        if (stockQueryParams.getTime() != null && !stockQueryParams.getTime().equals("")) {
            sqlQueryParamList.setTime(stockQueryParams.getTime());
        }
        if (stockQueryParams.getType() != null && !stockQueryParams.getType().equals("")) {
            sqlQueryParamList.setType(stockQueryParams.getType());
        }

        List<SqlQueryParam> sqlQueryParams = parseSqlQueryParams(sqlQueryParamList);
        List<StockModel> list = stockService.list(sqlQueryParams);

        return new PageResponse(page.getTotal(), list);

    }

    @PostMapping
    public void add(@RequestBody @Valid StockModel stockModel) {
        this.stockService.insertStock(stockModel);
    }

    @PutMapping(value = "{guid}")
    public void update(@RequestBody @Valid StockModel stockModel) {
        this.stockService.updateStock(stockModel);
    }

    @DeleteMapping(value = "{guid}")
    public void remove(@PathVariable(value = "guid") String guid) {
        this.stockService.deleteStock(guid);
    }


}
