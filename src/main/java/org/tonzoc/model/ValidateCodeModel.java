package org.tonzoc.model;

import org.tonzoc.annotation.Column;
import org.tonzoc.annotation.PrimaryKey;
import org.tonzoc.annotation.Table;

import java.util.Date;

@Table(value = "validateCode")
public class ValidateCodeModel extends BaseModel {
    @PrimaryKey
    @Column(value = "guid")
    private  String guid;
    @Column(value = "code")
    private  Integer code;
    @Column(value = "telNumber")
    private  String telNumber;
    @Column(value = "personId")
    private  String personId;
    @Column(value = "validTime")
    private Date validTime;
    @Column(value = "openId")
    private  String openId;


    @Override
    public String toString() {
        return "validateCodeModel{" +
                "guid='" + guid + '\'' +
                ", code='" + code + '\'' +
                ", telNumber='" + telNumber + '\'' +
                ", personId='" + personId + '\'' +
                ", validTime='" + validTime + '\'' +
                ", openId='" + openId + '\'' +
                '}';
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getTelNumber() {
        return telNumber;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public Date getValidTime() {
        return validTime;
    }

    public void setValidTime(Date validTime) {
        this.validTime = validTime;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}
