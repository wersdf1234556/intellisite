package org.tonzoc.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tonzoc.mapper.HelmetFileMapper;
import org.tonzoc.mapper.HelmetMapper;
import org.tonzoc.model.HelmetFileModel;
import org.tonzoc.model.HelmetModel;
import org.tonzoc.model.TestModel;
import org.tonzoc.model.util.HttpUtil;
import org.tonzoc.service.IHelmetFileService;
import org.tonzoc.support.param.SqlQueryParam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service(value = "helmetFileService")
public class HelmetFileService extends BaseService<HelmetFileModel> implements IHelmetFileService {
    @Autowired
    private HelmetMapper helmetMapper;
    @Autowired
    private HelmetFileMapper helmetFileMapper;


//    @Override
//    public void savevideoList(String sipNumber) throws ParseException {
//
////                如果dataid列表是video  则根据dataid查询infoid;
////                地址+infoid拼接好存储到表中的url
//        //        根据sipnum获取信息列表(视频和图片dataid的列表);
//        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        List<SqlQueryParam> sqlQueryParams = new ArrayList<>();
//        sqlQueryParams.add(new SqlQueryParam("sipNumber", sipNumber, "eq"));
//        String url = "http://182.92.108.122:1080/smallApps/sichuanInfo/selectInfo_New?userName=182000&pageSize=200&sips=" + sipNumber;
//        String result = HttpUtil.get(url);
//        JSONObject jsonObject = JSONObject.parseObject(result);
//        JSONObject data = (JSONObject) jsonObject.get("data");
//        JSONArray list = (JSONArray) data.get("list");
//        String str = JSONObject.toJSONString(list);
//        List<TestModel> list1 = (List<TestModel>) JSONArray.parseArray(str, TestModel.class);
//        List<SqlQueryParam> sqlQueryParams3 = new ArrayList<>();
//        sqlQueryParams3.add(new SqlQueryParam("sipNumber", sipNumber, "eq"));
//        List<HelmetModel> helmetModelList = helmetMapper.selectAll(sqlQueryParams3);
//        HelmetModel helmetModel = helmetModelList.get(0);
//        String projectId = helmetModel.getProjectId();
//        for (int i = 0; i < list1.size(); i++) {
//            TestModel testModel = list1.get(i);
//            String dataId = testModel.getDataId();
//            String url1 = "http://182.92.108.122:1080/smallApps/sichuanInfo/selectFileByInfoId_Old?infoId=" + dataId;
//            String result1 = HttpUtil.get(url1);
//            JSONObject jsonObject1 = JSONObject.parseObject(result1);
//            JSONArray data1 = (JSONArray) jsonObject1.get("data");
//            String str1 = JSONObject.toJSONString(data1);
//            List<TestModel> list11 = (List<TestModel>) JSONArray.parseArray(str1, TestModel.class);
//            TestModel testModel1 = list11.get(0);
//            if (testModel1.getFile_type().equals("video")) {
//                String infoId = testModel1.getId();
//                String sip = testModel1.getSip();
//                String dateTime = testModel1.getDate_time();
//                System.out.println("dateTime"+dateTime);
//                Date date = sdf.parse(dateTime);
//                System.out.println("date"+date);
//                String url2 = "http://182.92.108.122:1080/cmt/user/daq/view/video?fileId=" + infoId;
//                HelmetFileModel helmetFileModel1 = new HelmetFileModel();
//                helmetFileModel1.setUrl(url2);
//                helmetFileModel1.setSipNumber(sip);
//                helmetFileModel1.setInfoId(infoId);
//                helmetFileModel1.setDateTime(date);
//                helmetFileModel1.setProjectId(projectId);
//                List<SqlQueryParam> sqlQueryParams2 = new ArrayList<>();
//                sqlQueryParams2.add(new SqlQueryParam("infoId", infoId, "eq"));
//                List<HelmetFileModel> helmetFileModelList1 = helmetFileMapper.selectAll(sqlQueryParams2);
//                if (helmetFileModelList1.isEmpty()) {
//                    helmetFileMapper.insert(helmetFileModel1);
//                }
//            }
//        }
//    }


    @Override
    public void savevideoList() throws ParseException {

//                如果dataid列表是video  则根据dataid查询infoid;
//                地址+infoid拼接好存储到表中的url
        //        根据sipnum获取信息列表(视频和图片dataid的列表);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<SqlQueryParam> sqlQueryParams = new ArrayList<>();
        List<HelmetModel> helmetList = helmetMapper.selectAll(sqlQueryParams);
        for (int i = 0; i < helmetList.size(); i++) {
            HelmetModel helmetModel1 = helmetList.get(i);
            String sipNumber = helmetModel1.getSipNumber();
            String url = "http://182.92.108.122:1080/smallApps/sichuanInfo/selectInfo_New?userName=182000&pageSize=200&sips=" + sipNumber;
            String result = HttpUtil.get(url);
            JSONObject jsonObject = JSONObject.parseObject(result);
            JSONObject data = (JSONObject) jsonObject.get("data");
            JSONArray list = (JSONArray) data.get("list");
            String str = JSONObject.toJSONString(list);
            List<TestModel> list1 = (List<TestModel>) JSONArray.parseArray(str, TestModel.class);
            String projectId = helmetModel1.getProjectId();
            if (!list1.isEmpty()){
                for (int j = 0; j < list1.size(); j++) {
                    TestModel testModel = list1.get(j);
                    String dataId = testModel.getDataId();
                    String url1 = "http://182.92.108.122:1080/smallApps/sichuanInfo/selectFileByInfoId_Old?infoId=" + dataId;
                    String result1 = HttpUtil.get(url1);
                    JSONObject jsonObject1 = JSONObject.parseObject(result1);
                    JSONArray data1 = (JSONArray) jsonObject1.get("data");
                    String str1 = JSONObject.toJSONString(data1);
                    List<TestModel> list11 = (List<TestModel>) JSONArray.parseArray(str1, TestModel.class);
                    TestModel testModel1 = list11.get(0);
                    if (testModel1.getFile_type().equals("video")) {
                        String infoId = testModel1.getId();
                        String sip = testModel1.getSip();
                        String dateTime = testModel1.getDate_time();
                        System.out.println("dateTime" + dateTime);
                        Date date = sdf.parse(dateTime);
                        System.out.println("date" + date);
                        String url2 = "http://182.92.108.122:1080/cmt/user/daq/view/video?fileId=" + infoId;
                        HelmetFileModel helmetFileModel1 = new HelmetFileModel();
                        helmetFileModel1.setUrl(url2);
                        helmetFileModel1.setSipNumber(sip);
                        helmetFileModel1.setInfoId(infoId);
                        helmetFileModel1.setDateTime(date);
                        helmetFileModel1.setProjectId(projectId);
                        List<SqlQueryParam> sqlQueryParams2 = new ArrayList<>();
                        sqlQueryParams2.add(new SqlQueryParam("infoId", infoId, "eq"));
                        List<HelmetFileModel> helmetFileModelList1 = helmetFileMapper.selectAll(sqlQueryParams2);
                        if (helmetFileModelList1.isEmpty()) {
                            helmetFileMapper.insert(helmetFileModel1);
                        }
                    }
                }
            }

        }
    }
}