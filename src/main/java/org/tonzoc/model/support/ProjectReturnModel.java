package org.tonzoc.model.support;

public class ProjectReturnModel {
    private Integer total;  //总共数量
    private Integer overseas; //海外数量
    private Integer underConstruction; //在建数量
    private Integer completed; //已完成工程数量

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getOverseas() {
        return overseas;
    }

    public void setOverseas(Integer overseas) {
        this.overseas = overseas;
    }

    public Integer getUnderConstruction() {
        return underConstruction;
    }

    public void setUnderConstruction(Integer underConstruction) {
        this.underConstruction = underConstruction;
    }

    public Integer getCompleted() {
        return completed;
    }

    public void setCompleted(Integer completed) {
        this.completed = completed;
    }
}
