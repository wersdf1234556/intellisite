package org.tonzoc.model;

import org.tonzoc.annotation.*;

// 机械指挥官
@Table("mechanicsCommand")
public class MechanicsCommandModel extends BaseModel {

    @PrimaryKey
    @NotInsertColumn
    @Column(value = "guid")
    private String guid;
    @Column(value = "equipmentNo")
    private String equipmentNo;
//    @Column(value = "mechanicsId")
//    private String mechanicsId;
//
//    @JoinColumn(value = "machineKey", type = MechanicsModel.class, leftColumn = "mechanicsId", rightColumn = "guid")
//    private String machineKey;
//    @JoinColumn(value = "machineName", type = MechanicsModel.class, leftColumn = "mechanicsId", rightColumn = "guid")
//    private String machineName;
//    @JoinColumn(value = "projectKey", type = MechanicsModel.class, leftColumn = "mechanicsId", rightColumn = "guid")
//    private String projectKey;
//    @JoinColumn(value = "projectName", type = MechanicsModel.class, leftColumn = "mechanicsId", rightColumn = "guid")
//    private String projectName;

    public MechanicsCommandModel() {
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getEquipmentNo() {
        return equipmentNo;
    }

    public void setEquipmentNo(String equipmentNo) {
        this.equipmentNo = equipmentNo;
    }


}
