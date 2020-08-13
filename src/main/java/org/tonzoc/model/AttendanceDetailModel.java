package org.tonzoc.model;

import org.tonzoc.annotation.Column;
import org.tonzoc.annotation.PrimaryKey;
import org.tonzoc.annotation.Table;

@Table(value = "attendanceDetails")
public class AttendanceDetailModel extends BaseModel {
    @PrimaryKey
    @Column(value = "guid")
    private String guid;
    @Column(value = "personName")
    private String personName;
    @Column(value = "inTime")
    private String inTime;
    @Column(value = "outTime")
    private String outTime;
    @Column(value = "workDate")
    private String workDate;
    @Column(value = "inAddress")
    private String inAddress;
    @Column(value = "outAddress")
    private String outAddress;
    @Column(value = "inState")
    private String inState;
    @Column(value = "outState")
    private String outState;
    @Column(value = "idCard")
    private String idCard;
    @Column(value = "leave")
    private Integer leave;
    @Column(value = "departmentId")
    private String departmentId;

    public AttendanceDetailModel() {
    }

    @Override
    public String toString() {
        return "AttendanceDetailModel{" +
                "guid='" + guid + '\'' +
                ", personName='" + personName + '\'' +
                ", inTime='" + inTime + '\'' +
                ", outTime='" + outTime + '\'' +
                ", workDate='" + workDate + '\'' +
                ", inAddress='" + inAddress + '\'' +
                ", outAddress='" + outAddress + '\'' +
                ", inState='" + inState + '\'' +
                ", outState='" + outState + '\'' +
                ", idCard='" + idCard + '\'' +
                ", leave=" + leave +
                ", departmentId=" + departmentId +
                '}';
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getLeave() {
        return leave;
    }

    public void setLeave(Integer leave) {
        this.leave = leave;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getInState() {
        return inState;
    }

    public void setInState(String inState) {
        this.inState = inState;
    }

    public String getOutState() {
        return outState;
    }

    public void setOutState(String outState) {
        this.outState = outState;
    }

    public String getInAddress() {
        return inAddress;
    }

    public void setInAddress(String inAddress) {
        this.inAddress = inAddress;
    }

    public String getOutAddress() {
        return outAddress;
    }

    public void setOutAddress(String outAddress) {
        this.outAddress = outAddress;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }



    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }



//    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
//    public String getInTime() {
//        String res="";
//        if (!inTime.isEmpty()||!inTime.equals("")||inTime!=null||inTime!=" "){
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
//        long lt = new Long(inTime)*1000;
//        Date date = new Date(lt);
//         res = simpleDateFormat.format(date);
//        }
//        else {
//            res=null;
//        }
//        return res;
//    }


    public String getInTime() {
        return inTime;
    }

    public void setInTime(String inTime) {
        this.inTime = inTime;
    }



//    public String getOutTime() {
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
//        String res="";
//        if (outTime.equals(null)||outTime.equals("")){
//            long lt = new Long(outTime)*1000;
//            Date date = new Date(lt);
//             res = simpleDateFormat.format(date);
//        }
//        else {
//            res=null;
//        }
//        return res;
//    }


    public String getOutTime() {
        return outTime;
    }

    public void setOutTime(String outTime) {
        this.outTime = outTime;
    }





//    public String getWorkDate() {
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        long lt = new Long(workDate)*1000;
//        Date date = new Date(lt);
//        String res = simpleDateFormat.format(date);
//        return res;
//    }


    public String getWorkDate() {
        return workDate;
    }

    public void setWorkDate(String workDate) {
        this.workDate = workDate;
    }




}
