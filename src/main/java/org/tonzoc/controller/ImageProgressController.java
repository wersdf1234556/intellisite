package org.tonzoc.controller;

import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.tonzoc.controller.params.ImageProgressQueryParams;
import org.tonzoc.controller.params.PageQueryParams;
import org.tonzoc.controller.params.ProjectQueryParams;
import org.tonzoc.controller.response.PageResponse;
import org.tonzoc.exception.PageException;
import org.tonzoc.model.ImageProgressModel;
import org.tonzoc.model.ImageProgressRateModel;
import org.tonzoc.model.ProjectModel;
import org.tonzoc.model.util.ResultJson;
import org.tonzoc.service.IImageProgressProjectNameService;
import org.tonzoc.service.IImageProgressService;
import org.tonzoc.support.param.SqlQueryParam;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "imageProgress")
public class ImageProgressController extends BaseController {
    @Autowired
    private IImageProgressService iImageProgressService;
    @Autowired
    private IImageProgressProjectNameService imageProgressProjectNameService;

    //公司大屏形象进度查询 各项目进度情况
    @GetMapping(value = "firstList")
    public List<ProjectModel> firstList(PageQueryParams pageQueryParams, ProjectQueryParams projectQueryParams) throws PageException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        List<SqlQueryParam> sqlQueryParams = parseSqlQueryParams(projectQueryParams);
        Page<ProjectModel> page = parsePage(pageQueryParams);
        List<ProjectModel> projectModelList = iImageProgressService.firstList(sqlQueryParams);
        return projectModelList;
    }

    //项目大屏/后台管理形象进度查询 根据项目guid查询 进度二级页面
    @PostMapping(value = "secondList")
    public PageResponse secondList(PageQueryParams pageQueryParams, ImageProgressQueryParams imageProgressQueryParams) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, PageException {
        System.out.println("imageProgressQueryParams" + imageProgressQueryParams.toString());

        List<SqlQueryParam> sqlQueryParams = parseSqlQueryParams(imageProgressQueryParams);
        sqlQueryParams.add(new SqlQueryParam("isdelete", "0", "eq"));
        Page<ImageProgressModel> page = parsePage(pageQueryParams);
        List<ImageProgressModel> imageProgressList = iImageProgressService.list(sqlQueryParams);
        System.out.println("imageProgressList" + imageProgressList);
        return new PageResponse(page.getTotal(), imageProgressList);
    }


//    //后台管理形象进度添加
//    @PostMapping(value = "add")
//    public ResultJson add(@RequestBody ImageProgressModel imageProgressModel, ImageProgressQueryParams imageProgressQueryParams) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, ParseException {
//        ResultJson resultJson = new ResultJson();
//        resultJson.setCode("100");
//        String projectGuid = imageProgressModel.getProjectGuid();
//
//        List<SqlQueryParam> sqlQueryParams = parseSqlQueryParams(imageProgressQueryParams);
//        sqlQueryParams.add(new SqlQueryParam("projectGuid", projectGuid, "eq"));
//        sqlQueryParams.add(new SqlQueryParam("imageProgressProjectNameGuid", imageProgressModel.getImageProgressProjectNameGuid(), "eq"));
//        List<ImageProgressModel> list = iImageProgressService.list(sqlQueryParams);
//        for (int i = 0; i < list.size(); i++) {
//            ImageProgressModel imageProgressModel1 = list.get(i);
//            String createTime = imageProgressModel1.getCreateTime();
//            int month1 = Integer.parseInt(createTime.substring(5, 7));
//            //当前月份
//            Calendar cal = Calendar.getInstance();
//            int month = cal.get(Calendar.MONTH) + 1;
//            String name = imageProgressModel1.getImageProgressProjectName();
//            if (month1 == month) {
//                resultJson.setCode("405");
//                resultJson.setMsg("本月已添加过" + name + "进度数据");
//                resultJson.setObj(name);
//            }
//        }
//        if (resultJson.getCode().equals("100")) {
//            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            String createTime = df.format(System.currentTimeMillis());
//            imageProgressModel.setCreateTime(createTime);
//            imageProgressModel.setProjectGuid(projectGuid);
//            imageProgressModel.setIsdelete("0");
//            try {
//                this.iImageProgressService.save(imageProgressModel);
//                resultJson.setCode("200");
//                resultJson.setMsg("保存成功");
//            } catch (Exception e) {
//                resultJson.setCode("404");
//                resultJson.setMsg("保存失败");
//                e.printStackTrace();
//            }
//        }
//        return resultJson;
//    }

    //后台管理形象进度添加
    @PostMapping(value = "add")
    public ResultJson add(@RequestBody ImageProgressModel imageProgressModel) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, ParseException {
        System.out.println("添加" + imageProgressModel);
        ResultJson resultJson = new ResultJson();
        System.out.println(imageProgressModel);
        String projectGuid = imageProgressModel.getProjectGuid();
        ImageProgressQueryParams imageProgressQueryParams = new ImageProgressQueryParams();
        List<SqlQueryParam> sqlQueryParams = parseSqlQueryParams(imageProgressQueryParams);
        sqlQueryParams.add(new SqlQueryParam("projectGuid", projectGuid, "eq"));
        sqlQueryParams.add(new SqlQueryParam("imageProgressProjectNameGuid", imageProgressModel.getImageProgressProjectNameGuid(), "eq"));
        sqlQueryParams.add(new SqlQueryParam("isdelete", "0", "eq"));
        List<ImageProgressModel> list = iImageProgressService.list(sqlQueryParams);
        try {
            resultJson = iImageProgressService.save1(imageProgressModel, list);
        } catch (Exception e) {
            resultJson.setCode("404");
            resultJson.setMsg("保存失败");
            e.printStackTrace();
        }
        return resultJson;
    }


    //后台管理形象进度修改
    @PutMapping(value = "update")
    public ResultJson update(@RequestBody ImageProgressModel imageProgressModel) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        System.out.println("修改imageProgressModel" + imageProgressModel);
        imageProgressModel.setIsdelete("0");
        ResultJson resultJson = new ResultJson();
        String projectGuid = imageProgressModel.getProjectGuid();
        ImageProgressQueryParams imageProgressQueryParams = new ImageProgressQueryParams();
        List<SqlQueryParam> sqlQueryParams = parseSqlQueryParams(imageProgressQueryParams);
        sqlQueryParams.add(new SqlQueryParam("projectGuid", projectGuid, "eq"));
        sqlQueryParams.add(new SqlQueryParam("imageProgressProjectNameGuid", imageProgressModel.getImageProgressProjectNameGuid(), "eq"));
        sqlQueryParams.add(new SqlQueryParam("isdelete", "0", "eq"));
        List<ImageProgressModel> list = iImageProgressService.list(sqlQueryParams);
        System.out.println("修改list" + list);
        try {
            resultJson = this.iImageProgressService.update1(imageProgressModel, list);
        } catch (Exception e) {
            e.printStackTrace();
            resultJson.setCode("404");
            resultJson.setMsg("修改失败");
        }
        return resultJson;
    }

    //后台管理形象进度删除
    @DeleteMapping(value = "{guid}")
    public ResultJson remove(@PathVariable(value = "guid") String guid) {
        System.out.println("删除guid" + guid);
        ResultJson resultJson = new ResultJson();
        try {
            ImageProgressModel imageProgressModel = iImageProgressService.get(guid);
            imageProgressModel.setIsdelete("1");
            this.iImageProgressService.update2(imageProgressModel);
            resultJson.setCode("200");
            resultJson.setMsg("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            resultJson.setCode("404");
            resultJson.setMsg("删除失败");
        }
        return resultJson;
    }


    //1.公司大屏进度管理   所有项目一级工程名称完成的百分百//2.项目大屏 进度信息   该项目一级工程名称完成的百分百
    // 现在假设为百分比相加/数量
    //如果参数companyGuid为null或""查询单个项目进度管理(一级工程名称),
    //如果参数projectGuid为null或""查询公司项目进度管理(一级工程名称),
    @GetMapping(value = "companyImageProgress")
    public List<ImageProgressRateModel> companyImageProgress() {
        List<ImageProgressRateModel> list = new ArrayList<>();
        //如果参数项目projectGuid为null或"" 传公司id查询公司项目进度管理(一级工程名称),
        try {
            list = iImageProgressService.firstCompanyImageProgress();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @GetMapping(value = "projectImageProgress")
    public List<ImageProgressRateModel> projectImageProgress(String projectGuid) {
        List<ImageProgressRateModel> list = new ArrayList<>();
        //如果参数公司companyGuid为null或"" 有项目id 查询单个项目进度管理(一级工程名称),
        System.out.println("查询项目一级进度管理");
        list = iImageProgressService.firstProjectImageProgress(projectGuid);
        System.out.println("查询项目一级进度管理" + list);
        return list;
    }

    //总工程进度(公司大屏项目一级)  所有项目二级工程名称本月完成/累计完成/总工程量
    @GetMapping(value = "projectSecondImageProgress")
    public List<ImageProgressRateModel> projectSecondImageProgress() {
        List<ImageProgressRateModel> list = iImageProgressService.secondImageProgress();
        return list;
    }



}
