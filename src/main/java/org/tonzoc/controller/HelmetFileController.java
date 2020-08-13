package org.tonzoc.controller;

import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tonzoc.controller.BaseController;
import org.tonzoc.controller.params.HelmetFileParams;
import org.tonzoc.controller.params.HelmetParams;
import org.tonzoc.controller.params.PageQueryParams;
import org.tonzoc.controller.response.PageResponse;
import org.tonzoc.exception.PageException;
import org.tonzoc.model.HelmetFileModel;
import org.tonzoc.model.HelmetModel;
import org.tonzoc.service.IHelmetFileService;
import org.tonzoc.support.param.SqlQueryParam;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping(value = "helmetFile")
public class HelmetFileController extends BaseController {
    @Autowired
    private IHelmetFileService helmetFileService;

    @GetMapping(value = "list")
    public PageResponse list(PageQueryParams pageQueryParams , HelmetFileParams helmetFileParams) throws PageException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, ParseException {
        String sipNumber=helmetFileParams.getSipNumber();
        Page<HelmetFileModel> page = parsePage(pageQueryParams);
        List<SqlQueryParam> sqlQueryParams = parseSqlQueryParams(helmetFileParams);
        List<HelmetFileModel> helmetFileModelList = helmetFileService.list(sqlQueryParams);
        return new PageResponse(page.getTotal(), helmetFileModelList);
    }

}
