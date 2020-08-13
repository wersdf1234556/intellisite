package org.tonzoc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tonzoc.mapper.ImageProgressProjectNameMapper;
import org.tonzoc.model.ImageProgressProjectNameModel;
import org.tonzoc.service.IImageProgressProjectNameService;

import java.util.List;

@Service(value = "imageProgressProjectNameService")
public class ImageProgressProjectNameService extends BaseService<ImageProgressProjectNameModel> implements IImageProgressProjectNameService {
    @Autowired
    private ImageProgressProjectNameMapper imageProgressProjectNameMapper;


    @Override
    public List<ImageProgressProjectNameModel> listByPguid(String guid) {
        return imageProgressProjectNameMapper.listByPguid(guid);
    }

    @Override
    public List<ImageProgressProjectNameModel> ListfirstProjectName() {
        return imageProgressProjectNameMapper.ListfirstProjectName();
    }


}
