package org.tonzoc.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.tonzoc.controller.params.HelmetParams;
import org.tonzoc.controller.params.PageQueryParams;
import org.tonzoc.controller.response.PageResponse;
import org.tonzoc.exception.PageException;
import org.tonzoc.model.HelmetModel;
import org.tonzoc.model.ProjectModel;
import org.tonzoc.model.util.HttpUtil;
import org.tonzoc.model.util.ResultJson;
import org.tonzoc.service.IHelmetService;
import org.tonzoc.service.IProjectService;
import org.tonzoc.support.param.SqlQueryParam;

import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "helmet")
public class HelmetController extends BaseController {
    /*
     * **************************************************************************
     * ********************                                  ********************
     * ********************      COPYRIGHT INFORMATION       ********************
     * ********************                                  ********************
     * **************************************************************************
     *                                                                          *
     *                                   _oo8oo_                                *
     *                                  o8888888o                               *
     *                                  88" . "88                               *
     *                                  (| -_- |)                               *
     *                                  0\  =  /0                               *
     *                                ___/'==='\___                             *
     *                              .' \\|     |// '.                           *
     *                             / \\|||  :  |||// \                          *
     *                            / _||||| -:- |||||_ \                         *
     *                           |   | \\\  -  /// |   |                        *
     *                           | \_|  ''\---/''  |_/ |                        *
     *                           \  .-\__  '-'  __/-.  /                        *
     *                         ___'. .'  /--.--\  '. .'___                      *
     *                      ."" '<  '.___\_<|>_/___.'  >' "".                   *
     *                     | | :  `- \`.:`\ _ /`:.`/ -`  : | |                  *
     *                     \  \ `-.   \_ __\ /__ _/   .-` /  /                  *
     *                 =====`-.____`.___ \_____/ ___.`____.-`=====              *
     *                                   `=---=`                                *
     * **************************************************************************
     * ********************                                  ********************
     * ********************      				             ********************
     * ********************         佛祖保佑 永远无BUG        ********************
     * ********************                                  ********************
     * **************************************************************************
     */
    //项目
    @Autowired
    private IProjectService projectService;

    @Autowired
    private IHelmetService helmetService;

    @PostMapping(value = "gps")
    public ResultJson gps(String sipnum){
        ResultJson resultJson=new ResultJson();
        String url="http://60.205.208.38/vdis/api/userapi/gps?action=api+sipnum"+sipnum;
        try {
            String result = HttpUtil.get(url);
            resultJson.setCode("200");
            resultJson.setObj(result);
        } catch (Exception e) {
            e.printStackTrace();
            resultJson.setCode("444");
            resultJson.setMsg("操作失败");
        }
        return resultJson;
    }

//    /**
//     * 获取组织架构
//     * @return
//     */
    @GetMapping(value = "Helmetorganization")
    public ResultJson Helmetorganization(){
        ResultJson resultJson=new ResultJson();
        try {
            List<SqlQueryParam>sqlQueryParams=new ArrayList<>();
            List<SqlQueryParam>sqlQueryParams1=new ArrayList<>();

            List<ProjectModel> projectModelList = projectService.list(sqlQueryParams);
            for (int i = 0; i <projectModelList.size() ; i++) {
                ProjectModel projectModel=projectModelList.get(i);
                String projectGuid=projectModel.getGuid();
                System.out.println("projectGuid"+i+projectGuid);
                sqlQueryParams1.add(new SqlQueryParam("projectId",projectGuid,"eq"));
                List<HelmetModel> helmetModelList = helmetService.list(sqlQueryParams1);
                sqlQueryParams1.clear();
                projectModel.setChildrenHelmet(helmetModelList);
            }
            resultJson.setCode("200");
            resultJson.setObj(projectModelList);
        } catch (Exception e) {
            e.printStackTrace();
            resultJson.setCode("444");
            resultJson.setMsg("操作失败");
        }
        return resultJson;
    }

    /**
     * 获取组织架构(捅咕安全帽方接口)
     * @return
     */
    @GetMapping(value = "organization")
    public ResultJson organization(@RequestParam String sipNumber){
        System.out.println("sipNumber"+sipNumber);
        ResultJson resultJson=new ResultJson();
        String url="http://182.92.108.122:1080/cmt/otherDevice/deplist?action=api&sipNumber="+sipNumber;
        try {
            String result = HttpUtil.get(url);
            System.out.println("result1"+result);
            JSONArray jsonArray= JSONArray.parseArray(result);
            for (int i = 0; i <jsonArray.size() ; i++) {
                JSONObject jsonObject3 = jsonArray.getJSONObject(i);
                jsonObject3.get("deptName");
            }
            System.out.println("jsonArray"+jsonArray);
            resultJson.setCode("200");
            resultJson.setObj(jsonArray);
        } catch (Exception e) {
            e.printStackTrace();
            resultJson.setCode("444");
            resultJson.setMsg("操作失败");
        }
        return resultJson;
    }

    /**
     * 后台管理安全帽查询
     * @param pageQueryParams
     * @param helmetParams
     * @return
     * @throws PageException
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    @GetMapping(value = "list")
    public List<HelmetModel> list(PageQueryParams pageQueryParams , HelmetParams helmetParams) throws PageException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Page<HelmetModel> page = parsePage(pageQueryParams);
        System.out.println("page是啥"+page);
        List<SqlQueryParam> sqlQueryParams = parseSqlQueryParams(helmetParams);
        List<HelmetModel> helmetModelList = helmetService.list(sqlQueryParams);
        return helmetModelList;
    }

    @GetMapping
    public PageResponse list1(PageQueryParams pageQueryParams , HelmetParams helmetParams) throws PageException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Page<HelmetModel> page = parsePage(pageQueryParams);
        System.out.println("page是啥"+page);
        List<SqlQueryParam> sqlQueryParams = parseSqlQueryParams(helmetParams);
        sqlQueryParams.add(new SqlQueryParam("projectId","","neq"));
        List<HelmetModel> helmetModelList = helmetService.list(sqlQueryParams);
        return new PageResponse(page.getTotal(), helmetModelList);
    }


    @PostMapping(value = "add")
    public ResultJson add(@RequestBody @Valid HelmetModel helmetModel) {
        ResultJson resultJson=new ResultJson();
        try {
            //保存sipnumber不重复
            resultJson=helmetService.saveOnly(helmetModel);

        } catch (Exception e) {
            e.printStackTrace();
            resultJson.setCode("444");
            resultJson.setMsg("安全帽添加失败");
        }
        return resultJson;
    }

    @PutMapping(value = "update")
    public ResultJson update(@RequestBody @Valid HelmetModel helmetModel){
        ResultJson resultJson=new ResultJson();
        try {
            //修改sipnumber不重复
            resultJson=helmetService.updateOnly(helmetModel);
        } catch (Exception e) {
            e.printStackTrace();
            resultJson.setCode("444");
            resultJson.setMsg("安全帽修改失败");
        }
        return resultJson;
    }

    @DeleteMapping(value = "{guid}")
    public ResultJson delete(@PathVariable(value = "guid")String guid){
        ResultJson resultJson=new ResultJson();
        try {
            this.helmetService.remove(guid);
            resultJson.setCode("200");
            resultJson.setMsg("安全帽删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            resultJson.setCode("444");
            resultJson.setMsg("安全帽删除失败");
        }
        return resultJson;
    }

    /**
     * 查询数据采集信息列表
     * @return
     */
//    @GetMapping(value = "videoList")
//    public ResultJson videoList(@RequestParam(value = "sipNum")String sipNum,PageQueryParams pageQueryParams,HelmetFileParams helmetFileParams ) throws PageException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
//        ResultJson resultJson=new ResultJson();
//        Page<HelmetFileModel> page = parsePage(pageQueryParams);
//        List<SqlQueryParam> sqlQueryParams = parseSqlQueryParams(helmetFileParams);
//        resultJson=helmetService.savevideoList(sipNum,sqlQueryParams);
//        return resultJson;
//    }





}
