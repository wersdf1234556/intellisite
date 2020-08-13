package org.tonzoc.model;

import org.tonzoc.annotation.Column;
import org.tonzoc.annotation.NotInsertColumn;
import org.tonzoc.annotation.PrimaryKey;
import org.tonzoc.annotation.Table;

// 机械进退场记录
@Table("mechanicsEntryAndExit")
public class MechanicsEntryAndExitModel extends BaseModel {

    @PrimaryKey
    @NotInsertColumn
    @Column(value = "guid")
    private String guid;
    @Column(value = "project_key")
    private String project_key;  // 项目唯一标识
    @Column(value = "project_name")
    private String project_name; // 项目名称
    @Column(value = "machine_key")
    private String machine_key;  // 机械唯一标识
    @Column(value = "machine_name")
    private String machine_name;  // 机械名称
    @Column(value = "type")
    private String type;  // enter or exit 两种类型
    @Column(value = "date_time")
    private String date_time; // type 为 exit 为退场时间， type 为 enter 是进场时间
    @Column(value = "sortId")
    private Integer sortId;

    public MechanicsEntryAndExitModel() {
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getProject_key() {
        return project_key;
    }

    public void setProject_key(String project_key) {
        this.project_key = project_key;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public String getMachine_key() {
        return machine_key;
    }

    public void setMachine_key(String machine_key) {
        this.machine_key = machine_key;
    }

    public String getMachine_name() {
        return machine_name;
    }

    public void setMachine_name(String machine_name) {
        this.machine_name = machine_name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public Integer getSortId() {
        return sortId;
    }

    public void setSortId(Integer sortId) {
        this.sortId = sortId;
    }

    @Override
    public String toString() {
        return "MechanicsEntryAndExitModel{" +
                "guid='" + guid + '\'' +
                ", project_key='" + project_key + '\'' +
                ", project_name='" + project_name + '\'' +
                ", machine_key='" + machine_key + '\'' +
                ", machine_name='" + machine_name + '\'' +
                ", type='" + type + '\'' +
                ", date_time='" + date_time + '\'' +
                ", sortId=" + sortId +
                '}';
    }
}
