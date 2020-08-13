package org.tonzoc.model;

import org.tonzoc.annotation.*;

import java.sql.Date;

@Table("attachments")
public class AttachmentModel extends BaseModel {

    @PrimaryKey
//    @NotInsertColumn
    @Column(value = "guid")
    private String guid;
    @Column(value = "name")
    private String name;
    @Column(value = "url")
    private String url;

    @NotInsertColumn
    @Column(value = "created_at")
    private Date created_at;


    public AttachmentModel() {
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }
}
