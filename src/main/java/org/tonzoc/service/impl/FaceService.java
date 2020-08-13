package org.tonzoc.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;
import org.tonzoc.configuration.ScheduleTaskConfig;
import org.tonzoc.exception.NotOneResultFoundException;
import org.tonzoc.mapper.AttendanceDetailMapper;
import org.tonzoc.mapper.AttendanceRecordMapper;
import org.tonzoc.mapper.FaceMapper;
import org.tonzoc.model.AttendanceRecordModel;
import org.tonzoc.model.FaceModel;
import org.tonzoc.model.PersonModel;
import org.tonzoc.model.util.Base64MultipartFile;
import org.tonzoc.model.util.FileUtils;
import org.tonzoc.model.util.GsonUtils;
import org.tonzoc.model.util.HttpUtil;
import org.tonzoc.service.IAttendanceDetailService;
import org.tonzoc.service.IFaceService;
import org.tonzoc.service.IPersonService;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Transactional(rollbackFor = Exception.class)
@Service(value = "faceService")
public class FaceService extends BaseService<FaceModel> implements IFaceService {
    @Autowired
    private FaceMapper faceMapper;
    //考勤service
    @Autowired
    private IAttendanceDetailService attendanceService;

    //考勤mapper
    @Autowired
    private AttendanceDetailMapper attendanceMapper;

    //人员service
    @Autowired
    private IPersonService personService;

//    @Autowired
//    private static BaiduTokenMapper baiduTokenMapper;

    @Autowired
    private AttendanceRecordMapper attendanceRecordMapper;



    //获取百度API访问token
    @Autowired
    private static BaiduAuthService authService;
    static String accessToken = ScheduleTaskConfig.getBaiduToken();

    //百度人脸搜索接口
    public String faceSearch(String imageBASE64, String openId) throws Exception {
        //获取最近一条
        System.out.println("百度人脸搜索接口token"+accessToken);
        System.out.println("openId"+openId);
        PersonModel personModel=personService.getByOpenId(openId);
        String typeId=personModel.getTypeId();
        String personId=personModel.getGuid();
        String[] baseStrs = imageBASE64.split(",");
        String image = baseStrs[1];
        Map<String, Object> map = new HashMap<>();
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/search";
        map.put("image", image);
        map.put("image_type", "BASE64");
        map.put("group_id_list", typeId.replace("-", "_"));
        map.put("quality_control", "HIGH");
        map.put("liveness_control", "HIGH");
        map.put("user_id", personId.replace("-", "_"));
        String param = GsonUtils.toJson(map);
        String result = HttpUtil.post(url, accessToken, "application/json", param);
        System.out.println(result);
        return result;
    }

//    //保存当天没考勤人员的考勤记录(缺勤)
//    public void saveFaceAttendanceInexistence(AttendanceDetailModel attendancedetail) {
//        attendancedetail.setSeqid("0");
//        attendancedetail.setIfSignIn("1");
//        attendancedetail.setIfSignOut("1");
//        attendancedetail.setSource(1);
//        attendancedetail.setWorkTime("08:30:00-16:30:00");
//        attendancedetail.setInTime("null");
//        attendancedetail.setInStatus("3");
//        attendancedetail.setOutTime("null");
//        attendancedetail.setOutStatus("4");
//        //获取当天时间戳
//        ZoneId z = ZoneId.systemDefault();//获取时区
//        ZonedDateTime dt = ZonedDateTime.now(z);//获取当前时间
//        long timestamp = dt.toLocalDate().atStartOfDay(z).toEpochSecond() * 1000;//获取当天的起始时间戳
//        attendancedetail.setWorkDate(String.valueOf(timestamp));
//        attendanceService.save(attendancedetail);
//    }


    //根据personId查询person
    @Override
    public PersonModel getPerson(String personGuid) throws NotOneResultFoundException {
        return personService.getCompany(personGuid);
    }

    //根据personid查询数据库是否有此人脸图片
    @Override
    public Integer countFace(String personGuid) {
        return faceMapper.countFace(personGuid);
    }

    //保存人脸考勤
    @Override
    public Integer saveFaceAttendance(String personGuid,String address) throws ParseException {

        AttendanceRecordModel faceAttendanceRecordModel=new AttendanceRecordModel();
//        faceAttendanceRecordModel.setPersonGuid(personGuid);
        faceAttendanceRecordModel.setAttendanceTime(String.valueOf(System.currentTimeMillis()));
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = simpleDateFormat.format(new Date());
        faceAttendanceRecordModel.setAttendanceDate(currentDate);
        Integer tiao = attendanceRecordMapper.insert(faceAttendanceRecordModel);
        return tiao;
    }

//    //根据personCode查询当天考勤记录
//    @Override
//    public List<AttendanceDetailModel> getattendanceDetail(String personCode) {
//        ZoneId z = ZoneId.systemDefault();//获取时区
//        ZonedDateTime dt = ZonedDateTime.now(z);//获取当前时间
//        long timestamp = dt.toLocalDate().atStartOfDay(z).toEpochSecond() * 1000;//获取当天的起始时间戳
//        String workDate = String.valueOf(timestamp);
//        return attendanceMapper.listAttendance(personCode, workDate);
//    }

    //从result中取score
    @Override
    public Double resultGetScore(String result) {
        JSONObject jsonObject = JSONObject.parseObject(result);
        String dataJson = jsonObject.getString("result");
        JSONObject jsonObject1 = JSONObject.parseObject(dataJson);
        JSONArray user_list = jsonObject1.getJSONArray("user_list");
        String score = user_list.getJSONObject(0).getString("score");
        Double scoreDou = Double.parseDouble(score);
        return scoreDou;
    }
    //从result中取userId(personGuid)
//    @Override
//    public String resultGetpersonGuId(String result) {
//        JSONObject jsonObject = JSONObject.parseObject(result);
//        String dataJson = jsonObject.getString("result");
//        JSONObject jsonObject1 = JSONObject.parseObject(dataJson);
//        JSONArray user_list = jsonObject1.getJSONArray("user_list");
//        JSONObject user = (JSONObject) user_list.get(0);
//        String userId = (String) user.get("user_id").toString();
//        return userId;
//    }


    //人脸注册
    @Override
    public String saveFace(String personGuid, String imageBASE64, HttpServletRequest request) throws Exception {
        String result = null;
        PersonModel person = getPerson(personGuid);
        //保存图片
        String[] baseStrs = imageBASE64.split(",");
        byte[] b = new byte[0];
        b = Base64.decodeBase64(baseStrs[1]);
        for (int i = 0; i < b.length; ++i) {
            if (b[i] < 0) {
                b[i] += 256;
            }
        }
        MultipartFile file = new Base64MultipartFile(b, baseStrs[0]);
        FaceModel faceModel = new FaceModel();
        faceModel.setGroupId(person.getTypeId());
        faceModel.setUserId(personGuid);
        String imageUrl = "";
        if (!file.isEmpty()) {
            imageUrl = FileUtils.upload(file, request);
            faceModel.setImage(imageUrl);
            faceModel.setImageType("URL");
        }
        faceMapper.insert(faceModel);

        Map<String, Object> map = new HashMap<>();
        map.put("image",baseStrs[1]);
        map.put("image_type", "BASE64");
//        map.put("image", "https://t8.baidu.com/it/u=1484500186,1503043093&fm=79&app=86&size=h300&n=0&g=4n&f=jpeg?sec=1589088508&t=4a9ea15431e5aef3efed1e26992be143");
//        map.put("image_type", "URL");
        map.put("group_id", person.getTypeId().replace("-", "_"));
        map.put("user_id", personGuid.replace("-", "_"));
        map.put("quality_control", "HIGH");
        map.put("liveness_control", "NORMAL");
        map.put("action_type", "APPEND");
        String param = GsonUtils.toJson(map);
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/faceset/user/add";
        result = HttpUtil.post(url, accessToken, "application/json", param);
        System.out.println(result);
        //如果调百度后台接口报错,删除保存照片,数据回滚
        if (!result.contains("SUCCESS")) {
            boolean bj = FileUtils.delete(faceModel.getImage(), request);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

        }
        return result;
    }

    //人脸更新
    @Override
    public String updateFace(String personGuid, String imageBASE64, HttpServletRequest request) throws Exception {
        
        String[] baseStrs = imageBASE64.split(",");
        byte[] b = new byte[0];
        b = Base64.decodeBase64(baseStrs[1]);
        for (int i = 0; i < b.length; ++i) {
            if (b[i] < 0) {
                b[i] += 256;
            }
        }
        Base64MultipartFile file = new Base64MultipartFile(b, baseStrs[0]);
        FaceModel faceModel = faceMapper.selectOneByPersonGuid(personGuid);
        //删除原文件
        if (!file.isEmpty()) {
            //FaceModel old = faceMapper.selectOne(faceModel.getGuid());
            String  oldImage=faceModel.getImage();
            if (!oldImage.isEmpty()) {
                boolean bj = FileUtils.delete(oldImage, request);
                System.out.println("bj" + bj);
            }
        }
        //上传新文件
        String fileUrl = FileUtils.upload(file, request);
        faceModel.setImage(fileUrl);
        faceMapper.update(faceModel);
        //调用百度人脸更新接口
        PersonModel person = getPerson(personGuid);
        Map<String, Object> map = new HashMap<>();
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/faceset/user/update";
        //map.put("image",fileUrl);
        map.put("image", "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=2218352923,3558525448&fm=26&gp=0.jpg");
        map.put("image_type", "URL");
        map.put("group_id", person.getProjectId().replace("-", "_"));
        map.put("user_id", personGuid.replace("-", "_"));
        map.put("quality_control", "HIGH");
        map.put("liveness_control", "HIGH");
        map.put("action_type", "UPDATE");
        String param = GsonUtils.toJson(map);
        String result = HttpUtil.post(url, accessToken, "application/json", param);
        System.out.println(result);
        //如果调百度后台接口报错,数据回滚
        if (!result.contains("SUCCESS")) {
            boolean bj = FileUtils.delete(faceModel.getImage(), request);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }


        return result;
    }

    //人脸删除
    @Override
    public String remove(String[] personGuids, HttpServletRequest request) throws Exception {
        String result = null;
        try {
            result = "";
            List<FaceModel> faceModelList = new ArrayList<>();
            for (int i = 0; i < personGuids.length; i++) {
                String personGuid = personGuids[i];
                FaceModel faceModel = faceMapper.selectOneByPersonGuid(personGuid);
                faceModelList.add(faceModel);
                if (!"".equals(faceModel)) {
                    FileUtils.delete(faceModel.getImage(), request);
                }
                faceMapper.delete(faceModel.getGuid());
            }
            //调用百度人脸删除接口
            Map<String, Object> map = new HashMap<>();
            String url = "https://aip.baidubce.com/rest/2.0/face/v3/faceset/user/delete";
            for (int i = 0; i < faceModelList.size(); i++) {
            FaceModel faceModel = faceModelList.get(i);
            map.put("group_id", faceModel.getGroupId().replace("-", "_"));
            map.put("user_id", faceModel.getUserId().replace("-", "_"));
            String param = GsonUtils.toJson(map);
            result = HttpUtil.post(url, accessToken, "application/json", param);
            System.out.println(result);
            //如果调百度后台接口报错,数据回滚
            if (!result.contains("SUCCESS")) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }
        }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
