package org.tonzoc.model;


public class TestModel {
    private String id;
    private String file_type;
    private String dataId;
    private String infoId;
    private String sip;
    private String date_time;

    @Override
    public String toString() {
        return "TestModel{" +
                "id='" + id + '\'' +
                ", file_type='" + file_type + '\'' +
                ", dataId='" + dataId + '\'' +
                ", infoId='" + infoId + '\'' +
                ", sip='" + sip + '\'' +
                ", date_time='" + date_time + '\'' +
                '}';
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public String getSip() {
        return sip;
    }

    public void setSip(String sip) {
        this.sip = sip;
    }

    public String getInfoId() {
        return infoId;
    }

    public void setInfoId(String infoId) {
        this.infoId = infoId;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public String getFile_type() {
        return file_type;
    }

    public void setFile_type(String file_type) {
        this.file_type = file_type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
