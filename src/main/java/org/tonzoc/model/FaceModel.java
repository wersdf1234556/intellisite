package org.tonzoc.model;


import org.tonzoc.annotation.Column;
import org.tonzoc.annotation.NotInsertColumn;
import org.tonzoc.annotation.PrimaryKey;
import org.tonzoc.annotation.Table;

@Table(value = "facePicture")
public class FaceModel extends BaseModel {
    @PrimaryKey
    @NotInsertColumn
    @Column(value = "guid")
    private String guid;
    //图片信息(根据imageType来判断URL路径或base64编码)
    @Column(value = "image")
    private String image;
    //图片类型(URL或base64)
    @Column(value = "imageType")
    private String imageType;
    //百度人脸库中”用户组id”,对应person表中CompanyId
    @Column(value = "groupId")
    private String groupId;
    //百度人脸库中”用户id”,对应person表中guid
    @Column(value = "userId")
    private String userId;
    //百度人脸图片的唯一标识
    @Column(value = "faceToken")
    private String faceToken;


    @Override
    public String toString() {
        return "FaceModel{" +
                "guid='" + guid + '\'' +
                ", image='" + image + '\'' +
                ", imageType='" + imageType + '\'' +
                ", groupId='" + groupId + '\'' +
                ", userId='" + userId + '\'' +
                ", faceToken='" + faceToken + '\'' +
                '}';
    }

    public String getFaceToken() {
        return faceToken;
    }

    public void setFaceToken(String faceToken) {
        this.faceToken = faceToken;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
