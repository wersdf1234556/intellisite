package org.tonzoc.service;

import org.tonzoc.exception.NotOneResultFoundException;
import org.tonzoc.model.FaceModel;
import org.tonzoc.model.PersonModel;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;

public interface IFaceService extends IBaseService<FaceModel> {
    String saveFace(String personGuid, String imageBASE64, HttpServletRequest request) throws Exception;

    String updateFace(String personGuid, String imageBASE64, HttpServletRequest request) throws Exception;


    String remove(String[] personGuids, HttpServletRequest request) throws Exception;

    String faceSearch(String imageBASE64, String openId) throws Exception;

//    List<AttendanceDetailModel> getattendanceDetail(String personCode);

//    void saveFaceAttendanceInexistence(AttendanceDetailModel attendancedetail);

    //根据personId查询person
    PersonModel getPerson(String personGuid) throws NotOneResultFoundException;

    //根据personid查询数据库是否有此人脸图片
    Integer countFace(String personGuid);

    //保存考勤
    Integer saveFaceAttendance(String personGuid, String address) throws ParseException;

    //从sresult中取score
    Double resultGetScore(String result);
    //从sresult中取personGuId
//    String resultGetpersonGuId(String result);
}
