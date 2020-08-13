package org.tonzoc.model;

import org.tonzoc.annotation.Column;
import org.tonzoc.annotation.PrimaryKey;
import org.tonzoc.annotation.Table;

@Table(value = "holiday")
public class HolidayModel extends BaseModel {
    @PrimaryKey
    @Column(value = "guid")
    private String guid;
    @Column(value = "date")
    private String date;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "HolidayModel{" +
                "guid='" + guid + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
