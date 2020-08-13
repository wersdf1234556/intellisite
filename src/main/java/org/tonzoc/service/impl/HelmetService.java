package org.tonzoc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tonzoc.mapper.HelmetFileMapper;
import org.tonzoc.mapper.HelmetMapper;
import org.tonzoc.model.HelmetModel;
import org.tonzoc.model.util.ResultJson;
import org.tonzoc.service.IHelmetService;
import org.tonzoc.support.param.SqlQueryParam;

import java.util.ArrayList;
import java.util.List;


@Service(value = "helmetService")
public class HelmetService extends BaseService<HelmetModel> implements IHelmetService {
    @Autowired
    private HelmetFileMapper helmetFileMapper;
    @Autowired
    private HelmetMapper helmetMapper;

    @Override
    public ResultJson saveOnly(HelmetModel helmetModel) {
        ResultJson resultJson=new ResultJson();
        String sipNumber = helmetModel.getSipNumber();
        List<SqlQueryParam>sqlQueryParams=new ArrayList<>();
        sqlQueryParams.add(new SqlQueryParam("sipNumber",sipNumber,"eq"));
        List<HelmetModel> helmetModelList = helmetMapper.selectAll(sqlQueryParams);
        if(helmetModelList.isEmpty()){
            super.save(helmetModel);
            resultJson.setCode("200");
            resultJson.setMsg("安全帽添加成功");
        }else {
            resultJson.setCode("400");
            resultJson.setMsg("安全帽添加失败,sipNumber:"+sipNumber+"重复");
            resultJson.setObj(sipNumber);
        }
        return resultJson;
    }

    @Override
    public ResultJson updateOnly(HelmetModel helmetModel) {
        ResultJson resultJson=new ResultJson();
        String sipNumber = helmetModel.getSipNumber();
        List<SqlQueryParam>sqlQueryParams=new ArrayList<>();
        sqlQueryParams.add(new SqlQueryParam("sipNumber",sipNumber,"eq"));
        List<HelmetModel> helmetModelList = helmetMapper.selectAll(sqlQueryParams);
        if(helmetModelList.isEmpty()){
            super.update(helmetModel);
            resultJson.setCode("200");
            resultJson.setMsg("安全帽修改成功");
        }else {
            resultJson.setCode("400");
            resultJson.setMsg("安全帽修改失败,sipNumber:"+sipNumber+"重复");
            resultJson.setObj(sipNumber);
        }
        return resultJson;
    }
//    @Override
//    public ResultJson savevideoList(String sipNum, List<SqlQueryParam> sqlQueryParams) {
//        ResultJson resultJson=new ResultJson();
////                根据sipnum获取信息列表(视频和图片dataid的列表);
////                如果dataid列表是video  则根据dataid查询infoid;
////                地址+infoid拼接好存储到表中的url
//        sqlQueryParams.add(new SqlQueryParam("sipnum",sipNum,"eq"));
//        String url="http://182.92.108.122:1080/smallApps/sichuanInfo/selectInfo_New?userName=182000&pageSize=200&sips="+sipNum;
//        String result = HttpUtil.get(url);
//        JSONObject jsonObject= JSONObject.parseObject(result);
//        JSONObject data = (JSONObject) jsonObject.get("data");
//        JSONArray list= (JSONArray) data.get("list");
//        String str = JSONObject.toJSONString(list);
//        List<TestModel> list1 = (List<TestModel>) JSONArray.parseArray(str, TestModel.class);
//        List<SqlQueryParam>sqlQueryParams3=new ArrayList<>();
//        sqlQueryParams3.add(new SqlQueryParam("sipNumber",sipNum,"eq"));
//        List<HelmetModel> helmetModelList = helmetMapper.selectAll(sqlQueryParams3);
//        HelmetModel helmetModel=helmetModelList.get(0);
//        String projectId = helmetModel.getProjectId();
//        for (int i = 0; i <list1.size() ; i++) {
//            TestModel testModel=list1.get(i);
//            String dataId =testModel.getDataId();
//            String url1="http://182.92.108.122:1080/smallApps/sichuanInfo/selectFileByInfoId_Old?infoId="+dataId;
//            String result1 = HttpUtil.get(url1);
//            JSONObject jsonObject1= JSONObject.parseObject(result1);
//            JSONArray data1 = (JSONArray) jsonObject1.get("data");
//            String str1 = JSONObject.toJSONString(data1);
//            List<TestModel> list11 = (List<TestModel>) JSONArray.parseArray(str1, TestModel.class);
//            TestModel testModel1 = list11.get(0);
//            if(testModel1.getFile_type().equals("video")){
//                String infoId = testModel1.getId();
//                String sip=testModel1.getSip();
//                String dateTime=testModel1.getDate_time();
//                String url2="http://182.92.108.122:1080/cmt/user/daq/view/video?fileId="+infoId;
//                HelmetFileModel helmetFileModel1=new HelmetFileModel();
//                helmetFileModel1.setUrl(url2);
//                helmetFileModel1.setSipNumber(sip);
//                helmetFileModel1.setInfoId(infoId);
//                helmetFileModel1.setDateTime(dateTime);
//                helmetFileModel1.setProjectId(projectId);
//                List<SqlQueryParam>sqlQueryParams2=new ArrayList<>();
//                sqlQueryParams2.add(new SqlQueryParam("infoId",infoId,"eq"));
//                List<HelmetFileModel> helmetFileModelList1 = helmetFileMapper.selectAll(sqlQueryParams2);
//                if(helmetFileModelList1.isEmpty()){
//                    helmetFileMapper.insert(helmetFileModel1);
//                }
//            }
////            List<SqlQueryParam>sqlQueryParams1=new ArrayList<>();
//
//            List<HelmetFileModel> helmetFileModelList = helmetFileMapper.selectAll(sqlQueryParams);
//            helmetModel.setHelmetFileChilrdenList(helmetFileModelList);
//        }
//        System.out.println("helmetModel"+helmetModel);
//        resultJson.setCode("200");
//        resultJson.setObj(helmetModel);
//        return resultJson;
//    }



}
