package org.tonzoc.model;

import org.tonzoc.annotation.Column;
import org.tonzoc.annotation.PrimaryKey;
import org.tonzoc.annotation.Table;

import java.util.List;

//形象进度工程名称表
@Table(value = " imageProgressProjectName")
public class ImageProgressProjectNameModel extends BaseModel {
    @PrimaryKey
    @Column(value = "guid")
    private String guid;
    @Column(value = "name")
    private String name;
    @Column(value = "pguid")
    private String pguid;
    @Column(value = "isdelete")
    private String isdelete;
//    @Column(value = "created_at")
//    private Date created_at;
    private List<ImageProgressProjectNameModel>children;

    @Override
    public String toString() {
        return "ImageProgressProjectNameModel{" +
                "guid='" + guid + '\'' +
                ", name='" + name + '\'' +
                ", pguid='" + pguid + '\'' +
                ", isdelete=" + isdelete +
//                ", created_at='" + created_at + '\'' +
                ", children=" + children +
                '}';
    }

    public String getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(String isdelete) {
        this.isdelete = isdelete;
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

    public String getPguid() {
        return pguid;
    }

    public void setPguid(String pguid) {
        this.pguid = pguid;
    }



//    public Date getCreated_at() {
//        return created_at;
//    }
//
//    public void setCreated_at(Date created_at) {
//        this.created_at = created_at;
//    }

    public List<ImageProgressProjectNameModel> getChildren() {
        return children;
    }

    public void setChildren(List<ImageProgressProjectNameModel> children) {
        this.children = children;
    }
}
