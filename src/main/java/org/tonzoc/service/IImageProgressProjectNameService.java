package org.tonzoc.service;

import org.tonzoc.model.ImageProgressProjectNameModel;

import java.util.List;

public interface IImageProgressProjectNameService extends IBaseService<ImageProgressProjectNameModel> {
    List<ImageProgressProjectNameModel> listByPguid(String guid);
    //一级工程名称对象列表
    List<ImageProgressProjectNameModel> ListfirstProjectName();
}
