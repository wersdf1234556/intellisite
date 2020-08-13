package org.tonzoc.controller;

import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.tonzoc.controller.params.ImageProgressProjectNameQueryParams;
import org.tonzoc.controller.params.PageQueryParams;
import org.tonzoc.controller.response.PageResponse;
import org.tonzoc.exception.PageException;
import org.tonzoc.model.ImageProgressProjectNameModel;
import org.tonzoc.model.util.ResultJson;
import org.tonzoc.service.IImageProgressProjectNameService;
import org.tonzoc.support.param.SqlQueryParam;

import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "imageProgressProjectName")
public class ImageProgressProjectNameController extends BaseController {

    @Autowired
    private IImageProgressProjectNameService imageProgressProjectNameService;


    //后台维护工程名称查询
    @GetMapping(value = "list")
    public PageResponse list(PageQueryParams pageQueryParams, ImageProgressProjectNameQueryParams imageProgressProjectNameQueryParams)
            throws PageException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        System.out.println("工程名称查询");
        System.out.println("imageProgressProjectNameQueryParams" + imageProgressProjectNameQueryParams);
        PageResponse pageResponse = new PageResponse();
        if (imageProgressProjectNameQueryParams.getName() == null || imageProgressProjectNameQueryParams.getName().equals("")) {
            System.out.println("name是空");
            Page<ImageProgressProjectNameModel> page = parsePage(pageQueryParams);
            List<SqlQueryParam> sqlQueryParams = parseSqlQueryParams(imageProgressProjectNameQueryParams);
            sqlQueryParams.add(new SqlQueryParam("pguid", "0", "eq"));
            sqlQueryParams.add(new SqlQueryParam("isdelete", "0", "eq"));
            List<ImageProgressProjectNameModel> list = imageProgressProjectNameService.list(sqlQueryParams);
            for (int i = 0; i < list.size(); i++) {
                ImageProgressProjectNameModel imageProgressProjectNameModel = list.get(i);
                String guid = imageProgressProjectNameModel.getGuid();
                List<ImageProgressProjectNameModel> list1 = imageProgressProjectNameService.listByPguid(guid);
                imageProgressProjectNameModel.setChildren(list1);
            }
            pageResponse = new PageResponse(page.getTotal(), list);
        } else {
            System.out.println("name有内容");
            Page<ImageProgressProjectNameModel> page = parsePage(pageQueryParams);
            List<SqlQueryParam> sqlQueryParams = parseSqlQueryParams(imageProgressProjectNameQueryParams);
//            sqlQueryParams.add(new SqlQueryParam("pguid","0","eq"));
            sqlQueryParams.add(new SqlQueryParam("isdelete", "0", "eq"));
            List<ImageProgressProjectNameModel> list = imageProgressProjectNameService.list(sqlQueryParams);
            ImageProgressProjectNameModel imageProgressProjectNameModel = list.get(0);
            String pguid = imageProgressProjectNameModel.getPguid();
            String guid = imageProgressProjectNameModel.getGuid();
            System.out.println("pguid" + pguid);
            System.out.println("guid" + guid);
            if (pguid.equals("0")) {
                System.out.println("传的一级");
                List<ImageProgressProjectNameModel> list1 = imageProgressProjectNameService.listByPguid(guid);
                imageProgressProjectNameModel.setChildren(list1);
                pageResponse = new PageResponse(page.getTotal(), list);
            } else {
                System.out.println("传的二级");
                List<SqlQueryParam> sqlQueryParams1 = new ArrayList<>();
                sqlQueryParams1.add(new SqlQueryParam("guid", pguid, "eq"));
                List<ImageProgressProjectNameModel> list1 = imageProgressProjectNameService.list(sqlQueryParams1);
                System.out.println("list1"+list1);
                list1.get(0).setChildren(list);
                pageResponse = new PageResponse(page.getTotal(), list1);
            }
        }
        return pageResponse;
    }

    //后台维护工程名称添加
    @PostMapping(value = "add")
    public ResultJson add(@RequestBody @Valid ImageProgressProjectNameModel imageProgressProjectNameModel) {
        ResultJson resultJson = new ResultJson();
        if (imageProgressProjectNameModel.getPguid() == "") {
            imageProgressProjectNameModel.setPguid("0");
            imageProgressProjectNameModel.setIsdelete("0");
        }
        try {
            imageProgressProjectNameModel.setIsdelete("0");
            this.imageProgressProjectNameService.save(imageProgressProjectNameModel);
            resultJson.setCode("200");
            resultJson.setMsg("保存成功");
        } catch (Exception e) {
            e.printStackTrace();
            resultJson.setCode("404");
            resultJson.setMsg("操作失败");
        }
        System.out.println(imageProgressProjectNameModel);
        return resultJson;
    }

    //后台维护工程名称修改
    @PutMapping(value = "update")
    public ResultJson update(@RequestBody @Valid ImageProgressProjectNameModel imageProgressProjectNameModel) {
        System.out.println("修改" + imageProgressProjectNameModel);
        ResultJson resultJson = new ResultJson();
        try {
            this.imageProgressProjectNameService.update(imageProgressProjectNameModel);
            resultJson.setCode("200");
            resultJson.setMsg("修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            resultJson.setCode("404");
            resultJson.setMsg("操作失败");
        }
        return resultJson;
    }

    //后台维护工程名称删除
    @DeleteMapping(value = "{guid}")
    public ResultJson remove(@PathVariable(value = "guid") String guid) {
        System.out.println("删除guid" + guid);
        ResultJson resultJson = new ResultJson();
        ImageProgressProjectNameModel imageProgressProjectNameModel = imageProgressProjectNameService.get(guid);
        String pguid = imageProgressProjectNameModel.getPguid();
        //是父类
        if (pguid.equals("0")) {
            resultJson.setCode("400");
            resultJson.setMsg("删除失败,该工程名称下已存在子集");
        } else {
            try {
                imageProgressProjectNameModel.setIsdelete("1");
                this.imageProgressProjectNameService.update(imageProgressProjectNameModel);
                resultJson.setCode("200");
                resultJson.setMsg("删除成功");
            } catch (Exception e) {
                e.printStackTrace();
                resultJson.setCode("404");
                resultJson.setMsg("操作失败");
            }
        }
        return resultJson;
    }
}
