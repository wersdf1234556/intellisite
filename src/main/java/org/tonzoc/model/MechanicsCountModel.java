package org.tonzoc.model;

// 返回
public class MechanicsCountModel {

    private String name;
    private String count;
    private String workCount;
    private String stopCount;
    private MechanicsProjectCountModel mechanicsProjectCountModel;

    public MechanicsCountModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getWorkCount() {
        return workCount;
    }

    public void setWorkCount(String workCount) {
        this.workCount = workCount;
    }

    public String getStopCount() {
        return stopCount;
    }

    public void setStopCount(String stopCount) {
        this.stopCount = stopCount;
    }

    public MechanicsProjectCountModel getMechanicsProjectCountModel() {
        return mechanicsProjectCountModel;
    }

    public void setMechanicsProjectCountModel(MechanicsProjectCountModel mechanicsProjectCountModel) {
        this.mechanicsProjectCountModel = mechanicsProjectCountModel;
    }
}
