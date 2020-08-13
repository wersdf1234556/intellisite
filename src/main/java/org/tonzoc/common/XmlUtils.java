package org.tonzoc.common;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.tonzoc.model.RoadLinesModel;
import org.tonzoc.service.IRoadLinesService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

@Configuration
public class XmlUtils {

    private int i = 0;
    private int sortId = 0;
    private List<String> list = new ArrayList<>();
    List<RoadLinesModel> list2 = new ArrayList<>();

    @Autowired
    private IRoadLinesService roadLinesService;

    public void parseKml(String pathName) throws Exception
    {
        File file = new File("D:/tool/renlian/hazhao.zip");//pathName为KML文件的路径
        try {
            ZipFile zipFile = new ZipFile(file);
            ZipInputStream zipInputStream = null;
            InputStream inputStream = null;
            ZipEntry entry = null;
            zipInputStream = new ZipInputStream(new FileInputStream(file));
            while ((entry = zipInputStream.getNextEntry()) != null) {
                String zipEntryName = entry.getName();

                if (zipEntryName.endsWith("kml") || zipEntryName.endsWith("kmz")) {
                    inputStream = zipFile.getInputStream(entry);
                    parseXmlWithDom4j(inputStream);
                }
            }
            zipInputStream.close();
            inputStream.close();
        } catch (ZipException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void parseXmlWithDom4j(InputStream input) throws Exception {
        SAXReader reader = new SAXReader();
        Document document = null;
        try {
            document = reader.read(input);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        Element root = document.getRootElement();//获取doc.kml文件的根结点
        listNodes(root);
    }

    //遍历当前节点下的全部节点
    public void listNodes(Element node){
        String str = "tfao_";


        if (node.getName().equals("name") && node.getTextTrim().equals(str + i)) {
            System.out.println("名称1：" + node.getName() + ", 值1：" + node.getTextTrim());
            list.add(node.getTextTrim());
            i++;
        }

        if (node.getName().equals("coordinates")){
            if (list.size() > 0 && list.size() < 46) {
               String [] str1 = node.getTextTrim().split(" ");
//                for (String s: str1) {
////                    String [] str2 = s.split(",");
////                    System.out.println("str" + str2[0] + "    " + str2[1]);
////                    System.out.println(sortId++);
////                }

                for (String s: str1) {
                    String [] str2 = s.split(",");
                    RoadLinesModel roadLinesModel = new RoadLinesModel();
                    roadLinesModel.setSection(list.get(i - 1));
                    roadLinesModel.setIsParsed(0);
                    roadLinesModel.setBaiduLat("");
                    roadLinesModel.setBaiduLng("");
                    roadLinesModel.setOriginLat(str2[1]);
                    roadLinesModel.setOriginLng(str2[0]);
                    roadLinesModel.setSortId(sortId);
                    sortId++;

                    list2.add(roadLinesModel);

                    if (list2.size() % 1000 == 0) {
                        roadLinesService.saveMany(list2);
                        list2.clear();
                    }
                }
                if (list.size() == 45) {
                    list.clear();
                }
            }
        }
       // roadLinesService.saveMany(list2);

//        if (node.getName().equals("coordinates")) {
//            System.out.println("名称：" + node.getName() + ", 值：" + node.getTextTrim());
//        }
        //同一时候迭代当前节点以下的全部子节点
        //使用递归
        Iterator<Element> iterator = node.elementIterator();
        while(iterator.hasNext()){
            Element e = iterator.next();

            listNodes(e);
        }
    }

    // 添加数据
    public void listNodesAdd(Element node){
//        if (node.getName().equals("name")) {
//            System.out.println("名称1：" + node.getName() + ", 值1：" + node.getTextTrim());
//        }

        Iterator<Element> iterator = node.elementIterator();

        while(iterator.hasNext()){
            Element e = iterator.next();

            listNodesAdd(e);
        }
    }

}
