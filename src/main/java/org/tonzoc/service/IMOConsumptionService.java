package org.tonzoc.service;

import org.tonzoc.model.MOConsumptionModel;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface IMOConsumptionService extends IBaseService<MOConsumptionModel> {

    //油耗记录
    List<MOConsumptionModel> oilConsumption(String machine_key, String timestamp) throws IOException;

    //添加前一天油耗记录
    String oilConsumptionAdd(String machine_key) throws IOException, ParseException;

    //按小时添加油耗记录
    String oilConsumptionAddHour(String machine_key) throws IOException, ParseException;

}
