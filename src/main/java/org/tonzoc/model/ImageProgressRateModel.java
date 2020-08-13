package org.tonzoc.model;

public class ImageProgressRateModel {
    private String guid;
    private String name;
    private String monthRate;
    private String cumulativeCompleteRate;

    @Override
    public String toString() {
        return "ImageProgressRateModel{" +
                "guid='" + guid + '\'' +
                ", name='" + name + '\'' +
                ", monthRate='" + monthRate + '\'' +
                ", cumulativeCompleteRate='" + cumulativeCompleteRate + '\'' +
                '}';
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

    public String getMonthRate() {
        return monthRate;
    }

    public void setMonthRate(String monthRate) {
        this.monthRate = monthRate;
    }

    public String getCumulativeCompleteRate() {
        return cumulativeCompleteRate;
    }

    public void setCumulativeCompleteRate(String cumulativeCompleteRate) {
        this.cumulativeCompleteRate = cumulativeCompleteRate;
    }
}
