package org.tonzoc.service;

import org.tonzoc.model.MechanicsPlusOilModel;

import java.io.IOException;

import java.util.List;

public interface IMechanicsPlusOilService extends IBaseService<MechanicsPlusOilModel> {

    //加油记录
    List<MechanicsPlusOilModel> plusOil(String start_dt, String end_dt, String page, String count_per_page) throws IOException;

    //添加加油记录
    String plusOilAdd(String start_dt, String end_dt, String page, String count_per_page) throws IOException;
}
