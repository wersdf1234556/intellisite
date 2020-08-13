package org.tonzoc.model;

import org.tonzoc.annotation.*;

@Table(value = "fenceItems")
public class FenceItemModel extends BaseModel{
    @PrimaryKey
    @NotInsertColumn
    @Column(value = "guid")
    private String guid;
    @Column(value = "fenceGuid")
    private String fenceGuid;
    @JoinColumn(value = "name", type = FenceModel.class, leftColumn = "fenceGuid", rightColumn = "guid")
    private String fenceName;
    @Column(value = "lng")
    private String lng;
    @Column(value = "lat")
    private String lat;
    @Column(value = "sortId")
    private Integer sortId;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getFenceGuid() {
        return fenceGuid;
    }

    public void setFenceGuid(String fenceGuid) {
        this.fenceGuid = fenceGuid;
    }

    public String getFenceName() {
        return fenceName;
    }

    public void setFenceName(String fenceName) {
        this.fenceName = fenceName;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public Integer getSortId() {
        return sortId;
    }

    public void setSortId(Integer sortId) {
        this.sortId = sortId;
    }

    @Override
    public String toString() {
        return "FenceItemModel{" +
                "guid='" + guid + '\'' +
                ", fenceGuid='" + fenceGuid + '\'' +
                ", fenceName='" + fenceName + '\'' +
                ", lng='" + lng + '\'' +
                ", lat='" + lat + '\'' +
                ", sortId=" + sortId +
                '}';
    }
}
