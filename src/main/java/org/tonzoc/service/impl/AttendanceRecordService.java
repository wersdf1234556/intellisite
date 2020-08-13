package org.tonzoc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tonzoc.common.TimeHelper;
import org.tonzoc.mapper.AttendanceDetailMapper;
import org.tonzoc.mapper.AttendanceRecordMapper;
import org.tonzoc.mapper.PersonMapper;
import org.tonzoc.mapper.PersonTypeMapper;
import org.tonzoc.model.*;
import org.tonzoc.model.support.StatPersonModel;
import org.tonzoc.model.support.TypeModel;
import org.tonzoc.model.util.Utils;
import org.tonzoc.service.IAttendanceRecordService;
import org.tonzoc.service.ICompanyService;
import org.tonzoc.service.IPersonService;
import org.tonzoc.support.param.SqlQueryParam;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Service(value = "faceAttendanceRecordService")
public class AttendanceRecordService extends BaseService<AttendanceRecordModel> implements IAttendanceRecordService {
    @Autowired
    private IPersonService personService;
    @Autowired
    private AttendanceRecordMapper attendanceRecordMapper;

    //考勤表注入
    @Autowired
    private AttendanceDetailMapper attendanceDetailMapper;
    //人员注入
    @Autowired
    private PersonMapper personMapper;
    //人员类型注入
    @Autowired
    private PersonTypeMapper personTypeMapper;

    //考勤数据统计
    public StatPersonModel countAtt(String projectId, String date) {
        Integer count = personService.count(projectId);
        StatPersonModel statPersonModel = new StatPersonModel();
        statPersonModel.setName("");
        statPersonModel.setTotal(count.toString());

        Integer countSignIn;
        if (date == null || date.isEmpty()) {
            Calendar calendar = Calendar.getInstance();
            Date todayDate = calendar.getTime();
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            //今天
            date = df.format(todayDate);
        }
        Object rate = null;
        if (projectId == null) {
            countSignIn = attendanceRecordMapper.countSignInAll(date);
            if (countSignIn==null){
                countSignIn=0;
            }
            rate = (float) countSignIn / count * 100;
            DecimalFormat df = new DecimalFormat("0.00");//格式化小数
            rate = df.format(rate);
        } else {
            //打卡人数(去重)
            countSignIn = attendanceRecordMapper.countSignInByProject(projectId,date);
            if (countSignIn==null){
                countSignIn=0;
            }
            rate = (float) countSignIn / count * 100;
            DecimalFormat df = new DecimalFormat("0.00");//格式化小数
            rate = df.format(rate);
        }
        statPersonModel.setAttendanceNum(countSignIn.toString());
        statPersonModel.setAttendancePercent(rate.toString());
        return statPersonModel;
    }

    //存到考勤表中的时候判断有没今天有没有他的考勤  leave是不是1   如果今天有请假的考勤了  那就不是添加考勤  是修改考勤
    @Override
    public void saveRecordToAttendance() throws ParseException {
        List<AttendanceRecordModel> recordModelList = attendanceRecordMapper.listRecord();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //机关人员早上标准时间
        Date zaodian = sdf.parse("08:45:00");
        //中午分界时间
        Date zhongwu = sdf.parse("12:00:00");
        //机关人员晚上标准时间
        Date wandian = sdf.parse("16:15:00");
        for (int i = 0; i < recordModelList.size(); i++) {
            AttendanceDetailModel attendanceDetailModel = new AttendanceDetailModel();
            AttendanceRecordModel attendanceRecordModel = recordModelList.get(i);
            String idCard = attendanceRecordModel.getIdCard();

            if (!idCard.equals(null) && !idCard.equals("")) {
                List<SqlQueryParam> sqlQueryParams2 = new ArrayList<>();
                sqlQueryParams2.add(new SqlQueryParam("idCard", idCard, "eq"));
                List<AttendanceDetailModel> attendanceDetailModelList = attendanceDetailMapper.selectAll(sqlQueryParams2);
//                if (idCard.equals("232101199403013445")) {
                List<SqlQueryParam> sqlQueryParams = new ArrayList<>();
                sqlQueryParams.add(new SqlQueryParam("idCard", idCard, "eq"));
                List<PersonModel> personModelList = personMapper.selectAll(sqlQueryParams);
                if (personModelList.size() == 1) {
                    PersonModel personModel = personModelList.get(0);
                    String attendanceDate = attendanceRecordModel.getAttendanceDate();
                    Integer source = attendanceRecordModel.getSource();
                    String workDate = attendanceRecordModel.getAttendanceDate();
                    String personType = personModel.getTypeId();
                    attendanceDetailModel.setIdCard(idCard);
                    attendanceDetailModel.setWorkDate(workDate);
                    attendanceDetailModel.setPersonName(personModel.getName());
                    attendanceDetailModel.setDepartmentId(personModel.getDepartmentId());
                    //如果人员类型是劳务人员或者人员类型是项目管理人员或者临时人员
                    if (personType.equals("1771AD5E-65D7-4094-911E-9B302A12D9ED") || personType.equals("7AAFECE5-18AC-4577-B063-E96E2BABC502") || personType.equals("5E75ACBC-2914-4D98-871E-9304913114DD")) {
                        String attendanceTime = attendanceRecordModel.getAttendanceTime();
                        String address = attendanceRecordModel.getAddress();
                        String[] timeList = attendanceTime.split(",");
                        String[] addressList =address.split(",");
                        //截取第一次打卡记录存入即可
                        String daka = timeList[0];
                        attendanceDetailModel.setInTime(daka);
                        attendanceDetailModel.setOutTime(daka);
                        attendanceDetailModel.setInState("0");
                        attendanceDetailModel.setOutState("0");
                        attendanceDetailModel.setInAddress(addressList[0]);
                        attendanceDetailModel.setOutAddress(addressList[0]);
                        //如果人员类型是机关人员
                    } else if (personType.equals("1DA027E3-DA59-48E1-9066-CE045A627B75")) {
                        String attendanceTime = attendanceRecordModel.getAttendanceTime();
                        String[] timeList = attendanceTime.split(",");
                        String address = attendanceRecordModel.getAddress();
                        String[] addressList = address.split(",");
                        if (timeList.length == 2) {
                            String time1 = timeList[0];
                            String time2 = timeList[1];
                            String address1 = addressList[0];
                            String address2 = addressList[1];
                            Date daka1 = sdf.parse(sdf.format(new Date(Long.parseLong(time1 + "000"))));
                            Date daka2 = sdf.parse(sdf.format(new Date(Long.parseLong(time2 + "000"))));
                            //打卡1比打卡2早
                            if (daka1.getTime() < daka2.getTime()) {
                                //打卡1早打卡  打卡2晚打卡
                                if (daka1.getTime() < zhongwu.getTime() && daka2.getTime() >= zhongwu.getTime()) {
                                    //如果打卡1<=早上标准时间,InStatus正常,
                                    if (daka1.getTime() <= zaodian.getTime()) {
                                        attendanceDetailModel.setInState("0");
                                        //反之  迟到
                                    } else {
                                        attendanceDetailModel.setInState("1");
                                    }
                                    //如果打卡2>=晚上标准时间,OutStatus正常,
                                    if (daka2.getTime() >= wandian.getTime()) {
                                        attendanceDetailModel.setOutState("0");
//                            反之 早退
                                    } else {
                                        attendanceDetailModel.setOutState("2");
                                    }
                                    attendanceDetailModel.setInAddress(address1);
                                    attendanceDetailModel.setOutAddress(address2);
                                    attendanceDetailModel.setInTime(time1);
                                    attendanceDetailModel.setOutTime(time2);
                                }
                                //打卡1早第一次打卡  打卡2早第二次打卡 晚上未签退
                                else if (daka1.getTime() < zhongwu.getTime() && daka2.getTime() <= zhongwu.getTime()) {
                                    //如果打卡1<=早上标准时间,InStatus正常,
                                    if (daka1.getTime() <= zaodian.getTime()) {
                                        attendanceDetailModel.setInState("0");
                                        //反之  迟到
                                    } else {
                                        attendanceDetailModel.setInState("1");
                                    }
                                    attendanceDetailModel.setOutState("4");
                                    attendanceDetailModel.setInTime(time1);
                                    attendanceDetailModel.setOutTime("null");
                                    attendanceDetailModel.setInAddress(address1);
                                    attendanceDetailModel.setOutAddress("null");
                                }
                                //打卡1晚第一次打卡  打卡2晚第二次打卡 早上未签到
                                else if (daka1.getTime() >= zhongwu.getTime() && daka2.getTime() > zhongwu.getTime()) {
                                    //如果打卡2>=晚上标准时间,OutStatus正常,
                                    if (daka2.getTime() > wandian.getTime()) {
                                        attendanceDetailModel.setOutState("0");
//                            反之 早退
                                    } else {
                                        attendanceDetailModel.setOutState("2");
                                    }
                                    attendanceDetailModel.setInState("3");
                                    attendanceDetailModel.setInTime("null");
                                    attendanceDetailModel.setOutTime(time2);
                                    attendanceDetailModel.setInAddress("null");
                                    attendanceDetailModel.setOutAddress(address2);

                                }
                            }
                            //打卡2比打卡1早
                            else if (daka2.getTime() < daka1.getTime()) {
                                //打卡2早打卡  打卡1晚打卡
                                if (daka2.getTime() < zhongwu.getTime() && daka1.getTime() >= zhongwu.getTime()) {
                                    //如果打卡2<=早上标准时间,InStatus正常,
                                    if (daka2.getTime() <= zaodian.getTime()) {
                                        attendanceDetailModel.setInState("0");
                                        //反之  迟到
                                    } else {
                                        attendanceDetailModel.setInState("1");
                                    }
                                    //如果打卡1>=晚上标准时间,OutStatus正常,
                                    if (daka1.getTime() >= wandian.getTime()) {
                                        attendanceDetailModel.setOutState("0");
//                            反之 早退
                                    } else {
                                        attendanceDetailModel.setOutState("2");
                                    }
                                    attendanceDetailModel.setInTime(time2);
                                    attendanceDetailModel.setOutTime(time1);
                                    attendanceDetailModel.setInAddress(address2);
                                    attendanceDetailModel.setOutAddress(address1);
                                }
                                //打卡2早第一次打卡  打卡1早第二次打卡 晚上未签退
                                else if (daka2.getTime() < zhongwu.getTime() && daka1.getTime() <= zhongwu.getTime()) {
                                    //如果打卡2<=早上标准时间,InStatus正常,
                                    if (daka2.getTime() <= zaodian.getTime()) {
                                        attendanceDetailModel.setInState("0");
                                        //反之  迟到
                                    } else {
                                        attendanceDetailModel.setInState("1");
                                    }
                                    attendanceDetailModel.setInTime(time2);
                                    attendanceDetailModel.setOutTime("null");
                                    attendanceDetailModel.setOutState("4");
                                    attendanceDetailModel.setInAddress(address2);
                                    attendanceDetailModel.setOutAddress("null");
                                }
                                //打卡2晚第一次打卡  打卡1晚第二次打卡 早上未签到
                                else if (daka1.getTime() >= zhongwu.getTime() && daka2.getTime() > zhongwu.getTime()) {
                                    //如果打卡1>=晚上标准时间,OutStatus正常,
                                    if (daka1.getTime() > wandian.getTime()) {
                                        attendanceDetailModel.setOutState("0");
//                            反之 早退
                                    } else {
                                        attendanceDetailModel.setOutState("2");
                                    }
                                    attendanceDetailModel.setInState("3");
                                    attendanceDetailModel.setInTime("null");
                                    attendanceDetailModel.setOutTime(time2);
                                    attendanceDetailModel.setInAddress("null");
                                    attendanceDetailModel.setOutAddress(address2);
                                }
                            }
                            //只有一次刷脸考勤记录
                        } else if (timeList.length == 1) {
                            String time = timeList[0];
                            String address11 = addressList[0];
                            Date daka = sdf.parse(sdf.format(new Date(Long.parseLong(time + "000"))));
                            //如果打卡时间<=12点
                            if (daka.getTime() <= zhongwu.getTime()) {
                                if (daka.getTime() <= zaodian.getTime()) {
                                    attendanceDetailModel.setInState("0");
                                    //反之  迟到
                                } else {
                                    attendanceDetailModel.setInState("1");
                                }
                                attendanceDetailModel.setInTime(time);
                                attendanceDetailModel.setOutTime("null");
                                attendanceDetailModel.setOutState("4");
                                attendanceDetailModel.setInAddress(address11);
                                attendanceDetailModel.setOutAddress("null");
                                //如果打卡时间>=12点
                            } else {
                                if (daka.getTime() >= wandian.getTime()) {
                                    attendanceDetailModel.setOutState("0");
                                    //反之  早退
                                } else {
                                    attendanceDetailModel.setOutState("2");
                                }
                                attendanceDetailModel.setInTime("null");
                                attendanceDetailModel.setOutTime(time);
                                attendanceDetailModel.setInState("3");
                                attendanceDetailModel.setInAddress("null");
                                attendanceDetailModel.setOutAddress(address11);
                            }
                        } else if (timeList.length > 2) {
                            //晚的时间
                            int max = Integer.parseInt(timeList[0]);
                            //早的时间
                            int min = Integer.parseInt(timeList[0]);
                            String outAddress = null;
                            String inAddress = null;
                            for (int j = 0; j < timeList.length; j++) {
                                int time = Integer.parseInt(timeList[j]);
                                if (time >= max) {
                                    max = time;
                                    outAddress = addressList[j];
                                }

                                if (time <= min) {
                                    min = time;
                                    inAddress = addressList[j];
                                }
                            }
                            String maxString = sdf.format(Long.valueOf(max + "000"));
                            String minString = sdf.format(Long.valueOf(min + "000"));
                            Date max1 = sdf.parse(maxString);
                            Date min1 = sdf.parse(minString);
                            //如果早的时间比12点早,晚的时间比12点晚
                            if (min1.getTime() < zhongwu.getTime() && max1.getTime() >= zhongwu.getTime()) {
                                //如果早的时间<=早上标准时间,InStatus正常,
                                if (min1.getTime() <= zaodian.getTime()) {
                                    attendanceDetailModel.setInState("0");
                                    //反之  迟到
                                } else {
                                    attendanceDetailModel.setInState("1");
                                }
                                attendanceDetailModel.setInTime(String.valueOf(min));
                                attendanceDetailModel.setInAddress(inAddress);
                                //如果晚的时间>=晚上标准时间,OutStatus正常,
                                if (max1.getTime() >= wandian.getTime()) {
                                    attendanceDetailModel.setOutState("0");
//                            反之 早退
                                } else {
                                    attendanceDetailModel.setOutState("2");
                                }
                                attendanceDetailModel.setOutTime(String.valueOf(max));
                                attendanceDetailModel.setOutAddress(outAddress);
                            }
                            //早的时间是早第一次打卡  晚的时间是早第二次打卡 晚上未签退
                            else if (min1.getTime() < zhongwu.getTime() && max1.getTime() <= zhongwu.getTime()) {
                                //如果早的时间<=早上标准时间,InStatus正常,
                                if (min1.getTime() <= zaodian.getTime()) {
                                    attendanceDetailModel.setInState("0");
                                    //反之  迟到
                                } else {
                                    attendanceDetailModel.setInState("1");
                                }
                                attendanceDetailModel.setInTime(String.valueOf(min));
                                attendanceDetailModel.setOutTime("null");
                                attendanceDetailModel.setOutState("4");
                                attendanceDetailModel.setInAddress(inAddress);
                                attendanceDetailModel.setOutAddress("null");
                            }
//                            早的打卡时间是晚第一次打卡  晚的打卡时间是晚第二次打卡 早上未签到
                            else if (min1.getTime() >= zhongwu.getTime() && min1.getTime() > zhongwu.getTime()) {
                                //如果打卡2>=晚上标准时间,OutStatus正常,
                                if (min1.getTime() > wandian.getTime()) {
                                    attendanceDetailModel.setOutState("0");
//                            反之 早退
                                } else {
                                    attendanceDetailModel.setOutState("2");
                                }
                                attendanceDetailModel.setInState("3");
                                attendanceDetailModel.setInTime("null");
                                attendanceDetailModel.setOutTime(String.valueOf(max));
                                attendanceDetailModel.setInAddress("null");
                                attendanceDetailModel.setOutAddress(outAddress);
                            }
                        }
                    }
                    if (!attendanceDetailModelList.isEmpty()) {
                        AttendanceDetailModel attendanceDetailModel1 = attendanceDetailModelList.get(0);
                        attendanceDetailModel.setGuid(attendanceDetailModel1.getGuid());
                        attendanceDetailMapper.update(attendanceDetailModel);
                    } else {
                        attendanceDetailModel.setLeave(0);
                        attendanceDetailMapper.insert(attendanceDetailModel);
                    }
                }
            }

        }
//        }
    }

    @Override
    public void saveNoRecordToAttendance() {
        System.out.println("定时自动添加");
        //所有人员IDCARD
        List<String> personIdCardList = new ArrayList<>();
        //今天考勤人员IDCARD
        List<String> attendanceIdCardList = new ArrayList<>();

        //今天时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = simpleDateFormat.format(new Date());
        AttendanceDetailModel attendancedetail = new AttendanceDetailModel();
        System.out.println("currentDate" + currentDate);

        //所有人员IDCARD
        List<SqlQueryParam> sqlQueryParams = new ArrayList<>();
        List<PersonModel> personList = personMapper.selectAll(sqlQueryParams);
        for (int i = 0; i < personList.size(); i++) {
            PersonModel personModel = personList.get(i);
            String idCard = personModel.getIdCard();
            if (!idCard.equals(null) && !idCard.equals("")) {
                personIdCardList.add(idCard);
            }
        }

//        今天考勤人员IDCARD
        List<SqlQueryParam> sqlQueryParams1 = new ArrayList<>();
        sqlQueryParams1.add(new SqlQueryParam("workDate", currentDate, "eq"));
        List<AttendanceDetailModel> todayAttendanceDetailModelsList = attendanceDetailMapper.selectAll(sqlQueryParams1);
        for (int i = 0; i < todayAttendanceDetailModelsList.size(); i++) {
            AttendanceDetailModel attendanceDetailModel = todayAttendanceDetailModelsList.get(i);
            String idCard = attendanceDetailModel.getIdCard();
            attendanceIdCardList.add(idCard);
        }
        //未考勤人员IDCARD
        Utils utils = new Utils();
        List<String> noAttendancecIdCardList = utils.getDifferent(personIdCardList, attendanceIdCardList);
        for (int i = 0; i < noAttendancecIdCardList.size(); i++) {
            String personIdCard = noAttendancecIdCardList.get(i);
            List<SqlQueryParam> sqlQueryParams2 = new ArrayList<>();
            sqlQueryParams2.add(new SqlQueryParam("idCard", personIdCard, "eq"));
            List<PersonModel> personModelList = personMapper.selectAll(sqlQueryParams2);
            if (personModelList.size() == 1) {
                PersonModel person = personModelList.get(0);
                attendancedetail.setPersonName(person.getName());
                attendancedetail.setIdCard(person.getIdCard());
                attendancedetail.setInTime("null");
                attendancedetail.setInState("3");
                attendancedetail.setInAddress("null");
                attendancedetail.setOutAddress("null");
                attendancedetail.setOutTime("null");
                attendancedetail.setOutState("4");
                attendancedetail.setWorkDate(currentDate);
                attendancedetail.setDepartmentId(person.getDepartmentId());
                attendanceDetailMapper.insert(attendancedetail);
            }
        }
    }



}
