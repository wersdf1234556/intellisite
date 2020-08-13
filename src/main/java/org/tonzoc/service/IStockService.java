package org.tonzoc.service;

import org.tonzoc.model.StockModel;

public interface IStockService extends IBaseService<StockModel> {

    void insertStock(StockModel stockModel);
    void updateStock(StockModel stockModel);
    void deleteStock(String guid);

    
}
