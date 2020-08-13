package org.tonzoc.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.tonzoc.service.IAttendanceRecordService;
import org.tonzoc.service.IHelmetFileService;
import org.tonzoc.service.impl.BaiduAuthService;

import java.io.IOException;
import java.text.ParseException;

@Configuration
@EnableScheduling
public class ScheduleTaskConfig {
    @Autowired
    private IHelmetFileService helmetFileService;

    //打卡记录接口
    @Autowired
    private IAttendanceRecordService attendanceRecordService;

    //安全帽视频刷新
    @Scheduled(cron="0 59 * * * ? ")
    public void  helmetVideo() throws ParseException {
        System.out.println("安全帽视频刷新");
        helmetFileService.savevideoList();
    }

    //定时获取权限百度人脸识别token
    @Scheduled(cron="0 0 10 * * ? ")
    public static String getBaiduToken() {
        // 官网获取的 API Key 更新为你注册的
        String clientId = "qNRL8nosR1f3jptjAg7F2o3d";
        // 官网获取的 Secret Key 更新为你注册的
        String clientSecret = "CVZZLwepxF5Z03FhlRgY0jG0V43YP0ES ";
        return BaiduAuthService.getToken(clientId, clientSecret);
    }

    //每天晚上11点 考勤记录向考勤表存储数据
    @Scheduled(cron = "0 0 23 * * ?")
    public  void  saveRecordToDetail()throws ParseException{
        System.out.println("勤记录向考勤表存储数据");
        attendanceRecordService.saveRecordToAttendance();
    }
}
