package org.tonzoc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tonzoc.service.IXmlUtilsService;

@RestController
@RequestMapping("xmlUtils")
public class XmlUtilsControlelr {

  @Autowired
  private IXmlUtilsService iXmlUtilsService;

  //读取压缩包中的坐标点存入roadLine表中
    @GetMapping("/xml")
    public void xml (String pathName) throws Exception {

        iXmlUtilsService.XmlUtils(pathName);
    }
}
