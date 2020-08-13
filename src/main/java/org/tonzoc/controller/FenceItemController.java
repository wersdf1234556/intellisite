package org.tonzoc.controller;

import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.tonzoc.controller.params.FenceItemQueryParams;
import org.tonzoc.controller.params.FenceQueryParams;
import org.tonzoc.controller.params.PageQueryParams;
import org.tonzoc.controller.response.FenceItemResponse;
import org.tonzoc.controller.response.PageResponse;
import org.tonzoc.exception.PageException;
import org.tonzoc.model.FenceItemModel;
import org.tonzoc.model.FenceModel;
import org.tonzoc.service.IFenceItemService;
import org.tonzoc.service.IFenceService;
import org.tonzoc.support.param.SqlQueryParam;

import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("fenceItem")
public class FenceItemController extends BaseController {

    @Autowired
    private IFenceItemService fenceItemService;


    //判断电子围栏内外人数
    @GetMapping("/checkCurrentCount")
    public Map checkCurrentCount() {

       return fenceItemService.checkCurrentCount();
    }

    @GetMapping
    public PageResponse list(PageQueryParams pageQueryParams, FenceItemQueryParams fenceItemQueryParams)
            throws PageException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {

        Page<FenceItemModel> page = parsePage(pageQueryParams);

        List<SqlQueryParam> sqlQueryParams = parseSqlQueryParams(fenceItemQueryParams);
        List<FenceItemModel> list = fenceItemService.list(sqlQueryParams);

        return new PageResponse(page.getTotal(), list);

    }

    @PostMapping
    public void add(@RequestBody @Valid FenceItemModel fenceItemModel) {
        this.fenceItemService.save(fenceItemModel);
    }

    //电子围栏坐标点数组接收
    @PostMapping(value = "addMany")
    public void addFenceItem(@RequestBody List<FenceItemModel> fenceItemModels,String projectId,String name,Integer isOutside){
        fenceItemService.addFenceItem(fenceItemModels,projectId,name,isOutside);
    }

    @PutMapping(value = "{guid}")
    public void update(@RequestBody @Valid FenceItemModel fenceItemModel) {
        this.fenceItemService.update(fenceItemModel);
    }

    @DeleteMapping(value = "{guid}")
    public void remove(@PathVariable(value = "guid") String guid) {
        this.fenceItemService.remove(guid);
    }

    @GetMapping(value = "listItemByFenceId")
    public List<FenceItemResponse> listItemByFenceId(String projectId){
        return fenceItemService.listItemByFenceId(projectId);
    }

}
