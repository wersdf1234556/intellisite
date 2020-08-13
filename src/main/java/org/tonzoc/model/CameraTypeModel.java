package org.tonzoc.model;

import org.tonzoc.annotation.Column;
import org.tonzoc.annotation.PrimaryKey;
import org.tonzoc.annotation.Table;

import javax.validation.constraints.NotEmpty;

// 摄像头类型
@Table(value = "cameraTypes")
public class CameraTypeModel extends BaseModel {
    @PrimaryKey
    @Column(value = "guid")
    private String guid;
    @NotEmpty
    @Column(value = "name")
    private String name;
    @Column(value = "projectId")
    private String projectId;
    @Column(value = "attachmentId")
    private String attachmentId;
    @Column(value = "sortId")
    private Integer sortId;

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

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(String attachmentId) {
        this.attachmentId = attachmentId;
    }

    public Integer getSortId() {
        return sortId;
    }

    public void setSortId(Integer sortId) {
        this.sortId = sortId;
    }
}
