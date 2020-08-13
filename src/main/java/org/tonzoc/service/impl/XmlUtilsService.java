package org.tonzoc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tonzoc.common.XmlUtils;
import org.tonzoc.service.IXmlUtilsService;

@Service("XmlUtilsService")
public class XmlUtilsService implements IXmlUtilsService {

    @Autowired
     private XmlUtils xmlUtils;

    @Override
    public void XmlUtils(String pathName) throws Exception {

        xmlUtils.parseKml(pathName);
    }
}
