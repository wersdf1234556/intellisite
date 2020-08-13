package org.tonzoc.model;

import io.swagger.models.auth.In;
import org.tonzoc.annotation.Column;
import org.tonzoc.annotation.NotInsertColumn;
import org.tonzoc.annotation.PrimaryKey;
import org.tonzoc.annotation.Table;

@Table(value = "personType")
public class PersonTypeModel extends BaseModel {

    @Column(value = "guid")
    @NotInsertColumn
    @PrimaryKey
    private String guid;
    @Column(value = "name")
    private String name;
    @Column(value = "code")
    private Integer code;
    @Column(value = "sortId")
    private Integer sortId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public PersonTypeModel() {
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }


    public Integer getSortId() {
        return sortId;
    }

    public void setSortId(Integer sortId) {
        this.sortId = sortId;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
