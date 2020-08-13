package org.tonzoc.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.tonzoc.controller.params.HolidayQueryParams;
import org.tonzoc.controller.params.PageQueryParams;
import org.tonzoc.controller.response.PageResponse;
import org.tonzoc.exception.PageException;
import org.tonzoc.model.HolidayModel;
import org.tonzoc.model.TestModel;
import org.tonzoc.service.IHolidayService;
import org.tonzoc.support.param.SqlQueryParam;
import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

@RestController
@RequestMapping(value = "holiday")
public class HolidayController extends BaseController {
    @Autowired
    private IHolidayService holidayService;

    @GetMapping
    public PageResponse list(PageQueryParams pageQueryParams, HolidayQueryParams holidayQueryParams)
            throws PageException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Page<HolidayModel> page = parsePage(pageQueryParams);
        HolidayQueryParams sqlQueryParamList = new HolidayQueryParams();
        if(holidayQueryParams.getDate()!=null&&!holidayQueryParams.getDate().equals("") ){
            sqlQueryParamList.setDate(holidayQueryParams.getDate());
        }
        List<SqlQueryParam> sqlQueryParams = parseSqlQueryParams(sqlQueryParamList);
        List<HolidayModel> list = holidayService.list(sqlQueryParams);
        return new PageResponse(page.getTotal(), list);
    }

    @PostMapping
    public void add(@RequestBody @Valid HolidayModel holidayModel) {
        this.holidayService.save(holidayModel);
    }

    @PutMapping(value = "{guid}")
    public void update(@RequestBody @Valid HolidayModel holidayModel) {
        this.holidayService.update(holidayModel);
    }

    @DeleteMapping(value = "{guid}")
    public void remove(@PathVariable(value = "guid") String guid) {
        this.holidayService.remove(guid);
    }

    @PostMapping(value = "addList")
    public void addList(@RequestBody JSONObject date) {
        JSONArray data2 = date.getJSONArray("date");
        for (int i = 0; i <data2.size() ; i++) {
            HolidayModel holidayModel=new HolidayModel();
            holidayModel.setDate(data2.getString(i));
            this.holidayService.save(holidayModel);
        }
    }
}
