package org.tonzoc.model.support;

public class StockStatModel {
    private String materialName;
    private String stockIn;
    private String stockOut;
    private String surplus;

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getStockIn() {
        return stockIn;
    }

    public void setStockIn(String stockIn) {
        this.stockIn = stockIn;
    }

    public String getStockOut() {
        return stockOut;
    }

    public void setStockOut(String stockOut) {
        this.stockOut = stockOut;
    }

    public String getSurplus() {
        return surplus;
    }

    public void setSurplus(String surplus) {
        this.surplus = surplus;
    }
}
