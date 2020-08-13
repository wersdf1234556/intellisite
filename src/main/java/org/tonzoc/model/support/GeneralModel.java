package org.tonzoc.model.support;

public class GeneralModel {

    private String typeName;
    private String typeCount;
    private String percent;

    public GeneralModel(String typeName, String typeCount, String percent) {
        this.typeName = typeName;
        this.typeCount = typeCount;
        this.percent = percent;
    }

    @Override
    public String toString() {
        return "GeneralModel{" +
                "typeName='" + typeName + '\'' +
                ", typeCount='" + typeCount + '\'' +
                ", percent='" + percent + '\'' +
                '}';
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeCount() {
        return typeCount;
    }

    public void setTypeCount(String typeCount) {
        this.typeCount = typeCount;
    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }
}
