package org.tonzoc.model;

import org.tonzoc.annotation.Column;
import org.tonzoc.annotation.NotInsertColumn;
import org.tonzoc.annotation.PrimaryKey;
import org.tonzoc.annotation.Table;

// 项目部表
@Table(value = "section")
public class SectionModel extends BaseModel {

    @PrimaryKey
    @NotInsertColumn
    @Column(value = "guid")
    private String guid;
    @Column(value = "name")
    private String name;
    @Column(value = "sortId")
    private Integer sortId;

    public SectionModel() {
    }

    public SectionModel(String guid, String name, Integer sortId) {
        this.guid = guid;
        this.name = name;
        this.sortId = sortId;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSortId() {
        return sortId;
    }

    public void setSortId(Integer sortId) {
        this.sortId = sortId;
    }

    @Override
    public String toString() {
        return "ModuleModel{" +
                "guid='" + guid + '\'' +
                ", name='" + name + '\'' +
                ", sortId=" + sortId +
                '}';
    }
}
