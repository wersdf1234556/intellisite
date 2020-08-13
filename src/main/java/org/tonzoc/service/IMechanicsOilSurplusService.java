package org.tonzoc.service;

import org.tonzoc.model.MechanicsOilSurplusModel;

import java.io.IOException;
import java.util.List;

public interface IMechanicsOilSurplusService extends IBaseService<MechanicsOilSurplusModel> {

    //实时油位
    List<MechanicsOilSurplusModel> oilLevel(Integer page, Integer count_per_page) throws IOException;

    //添加实时油位
    String oilLevelAdd(Integer page, Integer count_per_page) throws IOException;


}
