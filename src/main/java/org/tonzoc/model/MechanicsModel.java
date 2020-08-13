package org.tonzoc.model;

import org.tonzoc.annotation.Column;
import org.tonzoc.annotation.NotInsertColumn;
import org.tonzoc.annotation.PrimaryKey;
import org.tonzoc.annotation.Table;

import java.util.List;

// 机械基础表
@Table("mechanics")
public class MechanicsModel extends BaseModel {

    @PrimaryKey
    @NotInsertColumn
    @Column(value = "guid")
    private String guid;
    @Column(value = "machine_key")
    private String machine_key; // 机械编号
    @Column(value = "name")
    private String name; // 机械名称
    @Column(value = "machine_model_name")
    private String machine_model_name; //机械型号名称
    @Column(value = "owner_name")
    private String owner_name; // 车主
    @Column(value = "owner_phone")
    private String owner_phone; // 车主电话
    @Column(value = "driver_name")
    private String driver_name; // 当前司机
    @Column(value = "driver_phone")
    private String driver_phone; // 当前司机电话
    @Column(value = "purchase_date")
    private String purchase_date; // 进场日期
    @Column(value = "departure_date")
    private String departure_date; //离场日期
    @Column(value = "brand_name")
    private String brand_name; // 品牌
    @Column(value = "factory_date")
    private String factory_date; // 生产日期
    @Column(value = "age")
    private String age; // 使用年限
    @Column(value = "category_name")
    private String category_name; // 机械类型
    @Column(value = "project_key")
    private String project_key; // 项目编号
    @Column(value = "project_name")
    private String project_name; // 项目名称
    @Column(value = "sortId")
    private Integer sortId; // 排序

    private List<MechanicsCommandModel> mechanicsCommandModels;

    public MechanicsModel() {
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getMachine_key() {
        return machine_key;
    }

    public void setMachine_key(String machine_key) {
        this.machine_key = machine_key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMachine_model_name() {
        return machine_model_name;
    }

    public void setMachine_model_name(String machine_model_name) {
        this.machine_model_name = machine_model_name;
    }

    public String getOwner_name() {
        return owner_name;
    }

    public void setOwner_name(String owner_name) {
        this.owner_name = owner_name;
    }

    public String getOwner_phone() {
        return owner_phone;
    }

    public void setOwner_phone(String owner_phone) {
        this.owner_phone = owner_phone;
    }

    public String getDriver_name() {
        return driver_name;
    }

    public void setDriver_name(String driver_name) {
        this.driver_name = driver_name;
    }

    public String getDriver_phone() {
        return driver_phone;
    }

    public void setDriver_phone(String driver_phone) {
        this.driver_phone = driver_phone;
    }

    public String getPurchase_date() {
        return purchase_date;
    }

    public void setPurchase_date(String purchase_date) {
        this.purchase_date = purchase_date;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    public String getFactory_date() {
        return factory_date;
    }

    public void setFactory_date(String factory_date) {
        this.factory_date = factory_date;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
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

    public String getDeparture_date() {
        return departure_date;
    }

    public void setDeparture_date(String departure_date) {
        this.departure_date = departure_date;
    }

    public Integer getSortId() {
        return sortId;
    }

    public void setSortId(Integer sortId) {
        this.sortId = sortId;
    }

    public List<MechanicsCommandModel> getMechanicsCommandModels() {
        return mechanicsCommandModels;
    }

    public void setMechanicsCommandModels(List<MechanicsCommandModel> mechanicsCommandModels) {
        this.mechanicsCommandModels = mechanicsCommandModels;
    }


    @Override
    public String toString() {
        return "MechanicsModel{" +
                "guid='" + guid + '\'' +
                ", machine_key='" + machine_key + '\'' +
                ", name='" + name + '\'' +
                ", machine_model_name='" + machine_model_name + '\'' +
                ", owner_name='" + owner_name + '\'' +
                ", owner_phone='" + owner_phone + '\'' +
                ", driver_name='" + driver_name + '\'' +
                ", driver_phone='" + driver_phone + '\'' +
                ", purchase_date='" + purchase_date + '\'' +
                ", departure_date='" + departure_date + '\'' +
                ", brand_name='" + brand_name + '\'' +
                ", factory_date='" + factory_date + '\'' +
                ", age='" + age + '\'' +
                ", category_name='" + category_name + '\'' +
                ", project_key='" + project_key + '\'' +
                ", project_name='" + project_name + '\'' +
                ", sortId=" + sortId +
                ", mechanicsCommandModels=" + mechanicsCommandModels +
                '}';
    }
}
