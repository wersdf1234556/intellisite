package org.tonzoc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tonzoc.mapper.ImageProgressMapper;
import org.tonzoc.mapper.ImageProgressProjectNameMapper;
import org.tonzoc.mapper.ProjectMapper;
import org.tonzoc.model.ImageProgressModel;
import org.tonzoc.model.ImageProgressProjectNameModel;
import org.tonzoc.model.ImageProgressRateModel;
import org.tonzoc.model.ProjectModel;
import org.tonzoc.model.util.ResultJson;
import org.tonzoc.service.IImageProgressService;
import org.tonzoc.support.param.SqlQueryParam;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service(value = "imageProgressService")
public class ImageProgressService extends BaseService<ImageProgressModel> implements IImageProgressService {
    @Autowired
    private ProjectMapper projectMapper;
    @Autowired
    private ImageProgressMapper imageProgressMapper;
    @Autowired
    private ImageProgressProjectNameMapper imageProgressProjectNameMapper;

    @Override
    public List<ProjectModel> listProject() {
        return projectMapper.selectAll1();
    }

    @Override
    public List<ImageProgressModel> listByProguid(String proGuid) {
        List<ImageProgressModel> list = imageProgressMapper.listByProguid(proGuid);
        return list;
    }

    @Override
    public ResultJson update1(ImageProgressModel imageProgressModel, List<ImageProgressModel> list) {
        ResultJson resultJson = new ResultJson();
        resultJson.setCode("200");
        for (int i = 0; i < list.size(); i++) {
            ImageProgressModel imageProgressModel1 = list.get(i);
            String createTime = imageProgressModel1.getCreateTime();
            int month1 = Integer.parseInt(createTime.substring(5, 7));
            //当前月份
            Calendar cal = Calendar.getInstance();
            int month = cal.get(Calendar.MONTH) + 1;
            String name = imageProgressModel1.getImageProgressProjectName();
            String projectName = imageProgressModel1.getImageProgressProjectName();
            if (month1 == month) {
                resultJson.setCode("405");
                resultJson.setMsg("本月已为" + projectName + "项目添加过" + name + "进度数据");
                resultJson.setObj(name + "," + projectName);
            }
        }
        if (resultJson.getCode().equals("200")) {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String createTime = df.format(System.currentTimeMillis());
            imageProgressModel.setCreateTime(createTime);
            imageProgressModel.setIsdelete("0");
            //计算本月完成比例
            double thisMonthComplete = Double.parseDouble(imageProgressModel.getThisMonthComplete());
            double designQuantity = Double.parseDouble(imageProgressModel.getDesignQuantity());
            double thisMonthRateRound = Math.round((thisMonthComplete / designQuantity)*100);
            String thisMonthRate = String.format("%.1f",thisMonthRateRound);
            imageProgressModel.setThisMonthRate(thisMonthRate);
            imageProgressMapper.update1(imageProgressModel);
        }


        return resultJson;
    }

    @Override
    public List<ProjectModel> firstList(List<SqlQueryParam> sqlQueryParams) {
        SqlQueryParam sqlQueryParam;
        List<ProjectModel> projectModelList = projectMapper.selectAll(sqlQueryParams);
        for (int i = 0; i < projectModelList.size(); i++) {
            ProjectModel projectModel = projectModelList.get(i);
            String proGuid = projectModel.getGuid();
            List<ImageProgressModel> imageProgressList = imageProgressMapper.listByProguid(proGuid);
            projectModel.setChildren(imageProgressList);
        }
        return projectModelList;
    }

    @Override
    public ResultJson save1(ImageProgressModel imageProgressModel, List<ImageProgressModel> list) {
        System.out.println(imageProgressModel);
        System.out.println(list);
        ResultJson resultJson = new ResultJson();
        resultJson.setCode("200");
        for (int i = 0; i < list.size(); i++) {
            ImageProgressModel imageProgressModel1 = list.get(i);
            String createTime = imageProgressModel1.getCreateTime();
            int month1 = Integer.parseInt(createTime.substring(5, 7));
            //当前月份
            Calendar cal = Calendar.getInstance();
            int month = cal.get(Calendar.MONTH) + 1;
            String name = imageProgressModel1.getImageProgressProjectName();
            String projectName = imageProgressModel1.getImageProgressProjectName();
            if (month1 == month) {
                resultJson.setCode("405");
                resultJson.setMsg("本月已为" + projectName + "项目添加过" + name + "进度数据");
                resultJson.setObj(name + "," + projectName);
            }
        }
        if (resultJson.getCode().equals("200")) {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String createTime = df.format(System.currentTimeMillis());
            imageProgressModel.setCreateTime(createTime);
            imageProgressModel.setIsdelete("0");
            //计算本月完成比例
            double thisMonthComplete = Double.parseDouble(imageProgressModel.getThisMonthComplete());
            double designQuantity = Double.parseDouble(imageProgressModel.getDesignQuantity());
            double thisMonthRateRound = Math.round((thisMonthComplete / designQuantity)*100);
            String thisMonthRate = String.format("%.1f",thisMonthRateRound);
            imageProgressModel.setThisMonthRate(thisMonthRate);
            imageProgressMapper.insert(imageProgressModel);
        }
        return resultJson;
    }

    @Override
    public List<ImageProgressModel> listByimageProgressProjectNameGuid(String secondGuid) {
        return imageProgressMapper.listByimageProgressProjectNameGuid(secondGuid);
    }


    @Override
    public List<ImageProgressRateModel> firstCompanyImageProgress() {
       List<ImageProgressRateModel>list=new ArrayList<>();
        //查询一级工程名称
        List<ImageProgressProjectNameModel> firstProjectNameList = imageProgressProjectNameMapper.ListfirstProjectName();
        Double yijigongchengmingchengsheji=0.00;
        Double yijileijiwancheng=0.00;
        for (int i = 0; i < firstProjectNameList.size(); i++) {
            ImageProgressRateModel imageProgressRateModel=new ImageProgressRateModel();
            Double erjigongchengmingchengsheji=0.00;
            Double erjileijiwancheng=0.00;
            ImageProgressProjectNameModel imageProgressProjectNameModel = firstProjectNameList.get(i);
            String pguid = imageProgressProjectNameModel.getGuid();
            String firstName=imageProgressProjectNameModel.getName();
            List<ImageProgressProjectNameModel> childrenList = imageProgressProjectNameMapper.listByPguid(pguid);
            if(childrenList.size()>0){
                for (int j = 0; j < childrenList.size(); j++) {
                    ImageProgressProjectNameModel secondProjectNameModel = childrenList.get(j);
                    String secondGuid = secondProjectNameModel.getGuid();
                    List<ImageProgressModel>imageProgressModelList  = imageProgressMapper.listByimageProgressProjectNameGuid(secondGuid);
                    if(imageProgressModelList.size()>0){
                    for (int k = 0; k <imageProgressModelList.size() ; k++) {
                        ImageProgressModel imageProgressModel=imageProgressModelList.get(k);
                        String sheji = imageProgressModel.getDesignQuantity();
                        Double sheji1=Double.parseDouble(sheji);
                        String leiji=imageProgressModel.getCumulativeComplete();
                        Double leiji1=Double.parseDouble(leiji);
                        erjigongchengmingchengsheji+=sheji1;
                        erjileijiwancheng+=leiji1;
                    }
                    }
                }
            }

            yijigongchengmingchengsheji+=erjigongchengmingchengsheji;
            yijileijiwancheng+=erjileijiwancheng;
            double leijiwanchengbili = yijileijiwancheng / yijigongchengmingchengsheji*100;
            System.out.println("leijiwanchengbili"+leijiwanchengbili);
            String result = String.format("%.2f",leijiwanchengbili);
            System.out.println("result"+result);
            imageProgressRateModel.setGuid(pguid);
            imageProgressRateModel.setName(firstName);
            imageProgressRateModel.setCumulativeCompleteRate(result);
            list.add(imageProgressRateModel);
        }

        return list;
    }

    @Override
    public List<ImageProgressRateModel> firstProjectImageProgress(String projectGuid) {
        List<ImageProgressRateModel>list=new ArrayList<>();
        //查询一级工程名称
        List<ImageProgressProjectNameModel> firstProjectNameList = imageProgressProjectNameMapper.ListfirstProjectName();
        Double yijigongchengmingchengsheji=0.0;
        Double yijileijiwancheng=0.0;
        for (int i = 0; i < firstProjectNameList.size(); i++) {
            ImageProgressRateModel imageProgressRateModel=new ImageProgressRateModel();
            Double erjigongchengmingchengsheji=0.0;
            Double erjileijiwancheng=0.0;
            ImageProgressProjectNameModel imageProgressProjectNameModel = firstProjectNameList.get(i);
            String pguid = imageProgressProjectNameModel.getGuid();
            String firstName=imageProgressProjectNameModel.getName();
            List<ImageProgressProjectNameModel> childrenList = imageProgressProjectNameMapper.listByPguid(pguid);
            System.out.println("childrenList"+childrenList);
            if(childrenList.size()>0){
                for (int j = 0; j < childrenList.size(); j++) {
                    ImageProgressProjectNameModel secondProjectNameModel = childrenList.get(j);
                    String secondGuid = secondProjectNameModel.getGuid();
                    List<ImageProgressModel>imageProgressModelList  = imageProgressMapper.listByimageProgressProjectNameGuidAndProjectGuid(secondGuid,projectGuid);
                    System.out.println("imageProgressModelList"+imageProgressModelList);
                    if(imageProgressModelList.size()>0){
                        for (int k = 0; k <imageProgressModelList.size() ; k++) {
                            ImageProgressModel imageProgressModel=imageProgressModelList.get(k);
                            String sheji = imageProgressModel.getDesignQuantity();
                            Double sheji1=Double.parseDouble(sheji);
                            String leiji=imageProgressModel.getCumulativeComplete();
                            Double leiji1=Double.parseDouble(leiji);
                            erjigongchengmingchengsheji+=sheji1;
                            erjileijiwancheng+=leiji1;
                        }
                    }
                }
            }

            yijigongchengmingchengsheji+=erjigongchengmingchengsheji;
            yijileijiwancheng+=erjileijiwancheng;
            double leijiwanchengbili = yijileijiwancheng / yijigongchengmingchengsheji*100;
            String result = String.format("%.1f",leijiwanchengbili);
            System.out.println("result"+result);
            imageProgressRateModel.setGuid(pguid);
            imageProgressRateModel.setName(firstName);
            imageProgressRateModel.setCumulativeCompleteRate(result);
            list.add(imageProgressRateModel);
        }
        return list;
    }

    @Override
    public List<ImageProgressRateModel> secondImageProgress() {

        //查询二级工程名称list
        List<ImageProgressProjectNameModel> secondNameList = imageProgressMapper.listSecondProjectName();
        List<ImageProgressRateModel> list = new ArrayList<>();

        List<String> secondNameGuidList = new ArrayList<>();

        for (int i = 0; i < secondNameList.size(); i++) {
            ImageProgressRateModel imageProgressRateModel = new ImageProgressRateModel();
            Double monthAll = 0.00;
            Double designQuantityAll = 0.00;
            Double cumulativeCompleteAll=0.00;
            ImageProgressProjectNameModel secondImageProgressProjectNameModel = secondNameList.get(i);
            String secondGuid = secondImageProgressProjectNameModel.getGuid();
            String secondName = secondImageProgressProjectNameModel.getName();
            secondNameGuidList.add(secondGuid);
            List<ImageProgressModel> secondImageProgresslist = imageProgressMapper.listByimageProgressProjectNameGuid(secondGuid);
            if (secondImageProgresslist.size() > 0) {
                for (int j = 0; j < secondImageProgresslist.size(); j++) {
                    ImageProgressModel secondImageProgressModel = secondImageProgresslist.get(j);
                    Double monthComplete = Double.parseDouble(secondImageProgressModel.getThisMonthComplete());
                    Double designQuantity = Double.parseDouble(secondImageProgressModel.getDesignQuantity());
                    Double cumulativeComplete = Double.parseDouble(secondImageProgressModel.getCumulativeComplete());
                    monthAll += monthComplete;
                    designQuantityAll += designQuantity;
                    cumulativeCompleteAll+=cumulativeComplete;
                }
                System.out.println("monthAll"+monthAll);
                System.out.println("designQuantityAll"+designQuantityAll);
                System.out.println("cumulativeCompleteAll"+cumulativeCompleteAll);
                double monthRate = monthAll / designQuantityAll*100 ;
                System.out.println("monthRate"+monthRate);
                double monthRateRound =  Math.round(monthRate);
                String monthRateRound1 = String.format("%.2f", monthRateRound);
                double cumulativeComplete = cumulativeCompleteAll / designQuantityAll*100 ;
                double cumulativeCompleteRound =  Math.round(cumulativeComplete);
                String cumulativeComplete1 = String.format("%.2f", cumulativeCompleteRound);
                imageProgressRateModel.setMonthRate(monthRateRound1);
                imageProgressRateModel.setGuid(secondGuid);
                imageProgressRateModel.setName(secondName);
                imageProgressRateModel.setCumulativeCompleteRate(cumulativeComplete1);
                list.add(imageProgressRateModel);
            }

        }

        System.out.println("list"+list.toString());
        return list;
    }

    @Override
    public void update2(ImageProgressModel imageProgressModel) {
        imageProgressMapper.update1(imageProgressModel);
    }


}
