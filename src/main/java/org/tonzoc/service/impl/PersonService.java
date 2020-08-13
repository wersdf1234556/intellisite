package org.tonzoc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.tonzoc.common.FileHelper;
import org.tonzoc.controller.params.PersonQueryParams;
import org.tonzoc.exception.NotOneResultFoundException;
import org.tonzoc.mapper.*;
import org.tonzoc.model.*;
import org.tonzoc.model.support.*;
import org.tonzoc.model.util.ResultJson;
import org.tonzoc.service.*;
import org.tonzoc.support.param.SqlQueryParam;

import java.sql.Date;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@Service(value = "personService")
public class PersonService extends BaseService<PersonModel> implements IPersonService {
    @Autowired
    private PersonMapper personMapper;
    @Autowired
    private IDepartmentService departmentService;
    @Autowired
    private IPersonTypeService personTypeService;
    @Autowired
    private IAttendanceRecordService attendanceRecordService;
    @Autowired
    private AttendanceRecordMapper attendanceRecordMapper;
    @Autowired
    private IEducationService educationService;
    @Autowired
    private IPersonStatusService personStatusService;
    @Autowired
    private IRelevanceTableService relevanceTableService;
    @Autowired
    private IAttachmentService attachmentService;
    @Autowired
    private IProjectService projectService;
    @Autowired
    private ILeaveApplyService leaveApplyService;
    @Autowired
    private IMakeUpAttendanceApplyService makeUpAttendanceApplyService;
    @Autowired
    private PersonTypeMapper personTypeMapper;
    @Autowired
    private ProjectMapper projectMapper;


    @Autowired
    private FileHelper fileHelper;

    public List<PersonModel> listByDepartmentId(String departmentId) {
        List<SqlQueryParam> sqlQueryParams = new ArrayList<>();
        sqlQueryParams.add(new SqlQueryParam("departmentId", departmentId, "eq"));

        List<PersonModel> list = this.list(sqlQueryParams);

        return list;
    }

    public PersonModel findByOpenId(String openId) {
        List<SqlQueryParam> sqlQueryParams = new ArrayList<>();
        sqlQueryParams.add(new SqlQueryParam("openId", openId, "eq"));

        PersonModel personModel = this.list(sqlQueryParams).get(0);

        return personModel;
    }

    //根据departId统计人数
    public Integer count(String projectId) {
        List<SqlQueryParam> sqlQueryParams = new ArrayList<>();
        if (projectId!=null){
            sqlQueryParams.add(new SqlQueryParam("projectId", projectId, "eq"));
        }
        Integer count = this.list(sqlQueryParams).size();

        return count;
    }

    //根据departId统计男人数
    public Integer countMan(String projectId,Integer sex) {
        List<SqlQueryParam> sqlQueryParams = new ArrayList<>();
        if (projectId!=null){
            sqlQueryParams.add(new SqlQueryParam("projectId", projectId, "eq"));
        }
        sqlQueryParams.add(new SqlQueryParam("sex", sex.toString(), "eq"));

        Integer count = this.list(sqlQueryParams).size();

        return count;
    }



    public void deletePerson(String guid) throws NotOneResultFoundException {
        List<RelevanceTableModel> list = relevanceTableService.listByMainId(guid);
        if (list.size() != 0) {
            throw new NotOneResultFoundException("与该人员有绑定的安全帽，请先解除绑定，才可删除该人员");
        }
        remove(guid);
    }


    public Map countSex(String projectId) {
        Integer countMan=0;
        Integer countWoman=0 ;
        Integer count=0;
        Map map = new HashMap();
        countMan = countMan(projectId,1);
        countWoman = countMan(projectId,2);
        count = count(projectId);
        map.put("man", countMan);
        map.put("woman", countWoman);
        map.put("total", count);

        return map;
    }



    @Override
    // 获取人员分类统计结果
    public CountTotalModel listTypeModel(String projectId, String date) throws ParseException {
        if (date == null || date.isEmpty()) {
            Calendar calendar = Calendar.getInstance();
            java.util.Date todayDate = calendar.getTime();
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            //今天
            date = df.format(todayDate);
        }
        Integer governmentTotal=0;
        Integer managerTotal=0;
        Integer contractTotal=0;
        CountTotalModel countTotalModel = new CountTotalModel();
        StatPersonModel statPersonModel= null;
        if (projectId!=null){
            governmentTotal = personMapper.selectByPersonCode(projectId, 1);
            managerTotal = personMapper.selectByPersonCode(projectId, 2);
            contractTotal = personMapper.selectByPersonCode(projectId, 0);
            statPersonModel = attendanceRecordService.countAtt(projectId, date);
        }else {
            governmentTotal = personMapper.listByCodeAndAll(1);
            managerTotal = personMapper.listByCodeAndAll(2);
            contractTotal=personMapper.listByCodeAndAll(0);
            statPersonModel = attendanceRecordService.countAtt(projectId, date);
        }

        Integer totalManage = governmentTotal + managerTotal;

        List<SqlQueryParam> sqlQueryParams = new ArrayList<>();
        List<PersonTypeModel> personTypeModels = personTypeService.list(sqlQueryParams);

        //总人数
        Integer count = count(projectId);
        Integer governmentAttenCount=0;
        Integer managerAttenCount=0;
        for (PersonTypeModel personType : personTypeModels) {
            Integer countByTypeId = attendanceRecordMapper.countAttByTypeId(personType.getGuid(), date);
            if (personType.getCode()==1) {
                governmentAttenCount=countByTypeId;
                countTotalModel.setGovernmentAttenCount(governmentAttenCount.toString());
            } else if (personType.getCode()==2) {
                managerAttenCount=countByTypeId;
                countTotalModel.setManagerAttenCount(managerAttenCount.toString());
            } else if (personType.getCode()==0) {
                countTotalModel.setContractAttenCount(countByTypeId.toString());
                countTotalModel.setContractPercent(String.valueOf(countByTypeId/count));
            }
            Integer gmAttenCount = governmentAttenCount+managerAttenCount;
            countTotalModel.setGmAttenCount(gmAttenCount.toString());
        }

        countTotalModel.setTotal(count.toString());
        countTotalModel.setAttendanceTotal(statPersonModel.getAttendanceNum().toString());
        countTotalModel.setGovernmentTotal(governmentTotal.toString());
        countTotalModel.setManagerTotal(managerTotal.toString());
        countTotalModel.setContractTotal(contractTotal.toString());
        countTotalModel.setAttenPercent(statPersonModel.getAttendancePercent().toString());
        countTotalModel.setSumGoverAndManager(totalManage.toString());

        return countTotalModel;
    }

    @Override
    public List<TypeModel> sumByType(String projectId) {
        List<SqlQueryParam> sqlQueryParams = new ArrayList<>();
        List<PersonTypeModel> list = personTypeService.list(sqlQueryParams);
        List<TypeModel> sumByType = new ArrayList<>();
        Integer totalManage = 0;
        Integer count =0;
        for (PersonTypeModel personTypeModel : list) {
            if (projectId!=null){
                count =personTypeMapper.countByCodeAndProject(personTypeModel.getCode(),projectId);
            }else {
                count = personTypeMapper.countByTypeCode(personTypeModel.getCode());
            }
            if (personTypeModel.getCode()==1 || personTypeModel.getCode()==2) {
                totalManage = totalManage + count;
            } else {
                TypeModel type = new TypeModel();
                type.setTypeName(personTypeModel.getName());
                type.setTypeCount(count.toString());
                type.setTypeId("");
                sumByType.add(type);
            }
        }
        TypeModel type = new TypeModel();
        type.setTypeName("项目管理人员");
        type.setTypeCount(totalManage.toString());
        type.setTypeId("");
        sumByType.add(type);
        return sumByType;
    }

    @Override
    public List<GeneralModel> countEducation(String projectId) {
        List<GeneralModel> list = new ArrayList<>();
        if (projectId==null){
            list=personMapper.stateAllByEducation();
        }else {
            list=personMapper.stateByEducationAndProject(projectId);
        }

        return list;
    }

    @Override
    public List<GeneralModel> countAge(String projectId) throws Exception {
        List<GeneralModel> list = new ArrayList<>();
        if (projectId==null){
            list = personMapper.stateAllByAge();
        }else {
            list=personMapper.stateByAgeAndProject(projectId);
        }
        return list;
    }

    @Override
    public List<DepartCountModel> countStatus(String projectId) {
        List<DepartCountModel> list = new ArrayList<>();
        DepartCountModel departCount = new DepartCountModel();
        departCount.setDepartName("部门");
        departCount.setTotal("总数");
        departCount.setOnGuard("在岗");
        departCount.setLeavePersonal("请假");
        departCount.setOfficial("公出");
        list.add(departCount);
        List<DepartCountModel> models = null;
        if (projectId!=null){
            models = personMapper.countPersonByProject(projectId);
        }else {
            models = personMapper.countByCompany();
        }

        list.addAll(models);

        return list;
    }

    @Override
    public List<String> listTypeId(String projectId) {
        List<String> list = null;
        if (projectId!=null){
            list=personMapper.listTypeId(projectId);
        }else {
            list=personMapper.listAllByTypeId();
        }
        return list;
    }

    public Map<String,String> uploadFile(MultipartFile file, String personId) {
        PersonModel personModel = get(personId);
        Map<String,String> map = new HashMap<>();
        if (personModel!=null){
            AttachmentModel attachmentModel = new AttachmentModel();
            String str = fileHelper.fileUpload(file);
            attachmentModel.setName(str.split(",")[1]);
            attachmentModel.setUrl(str.split(",")[0]);
            attachmentService.save(attachmentModel);
            personModel.setAttachmentId(attachmentModel.getGuid());
            this.update(personModel);
            map.put("personId",personId);
            map.put("url",str.split(",")[0]);
        }
        return map;
    }

    public List<Object> countStatusByProjectId(String projectId) {
        List<Object> list=new ArrayList<>();
        if (projectId==null){
            List<String> listProjectId = new ArrayList<>();
            List<String> allProjectId = new ArrayList<>();
            if (projectId==null){
                List<PStatusModel> models = personMapper.countAllByProject();
                list.addAll(models);
                listProjectId = personMapper.listProjectId();
                allProjectId = projectMapper.allProjectId();
                List<String> diffrentProjects = getDiffrent(allProjectId,listProjectId);
                for (String projectGuid:diffrentProjects){
                    ProjectModel projectModel = projectService.get(projectGuid);
                    PStatusModel pStatusModel = new PStatusModel();
                    pStatusModel.setProjectId(projectGuid);
                    pStatusModel.setProjectName(projectModel.getName());
                    pStatusModel.setOnGuard("0");
                    pStatusModel.setLeavePersonal("0");
                    pStatusModel.setOfficial("0");
                    list.add(pStatusModel);
                }
            }else {
                PStatusModel model = personMapper.countByProject(projectId);
                list.add(model);
            }
        }else {
            List<GeneralModel> modelList = new ArrayList<>();
            PStatusModel model = personMapper.countByProject(projectId);
            GeneralModel generalModel = new GeneralModel("在岗",model.getOnGuard(),"");
            GeneralModel generalModel2 = new GeneralModel("请假",model.getLeavePersonal(),"");
            GeneralModel generalModel3 = new GeneralModel("公出",model.getOfficial(),"");
            modelList.add(generalModel);
            modelList.add(generalModel2);
            modelList.add(generalModel3);
            list.add(modelList);

        }


        return list;
    }



    public List<PersonModel> listByDepartAndFlag(String departmentId,Integer flag ){
        List<SqlQueryParam> sqlQueryParamList = new ArrayList<>();
        sqlQueryParamList.add(new SqlQueryParam("departmentId",departmentId,"eq"));
        sqlQueryParamList.add(new SqlQueryParam("flag",flag.toString(),"neq"));
        List<PersonModel> nextPersons = list(sqlQueryParamList);
        return nextPersons;
    }

    public List<PersonModel> listNextPersons(String leaveId,String currentPersonId,Integer sign) throws ParseException {
        List<PersonModel> list =new ArrayList<>();
        PersonModel submitter;
        DateFormat fmt =new SimpleDateFormat("yyyy-MM-dd");
        //1、判断是请假还是补考勤
        if (sign==0){//请假
            LeaveApplyModel applyModel = leaveApplyService.get(leaveId);
            submitter=get(applyModel.getPersonGuid());
            java.util.Date leaveStartTime=fmt.parse(applyModel.getLeaveStartTime());
            java.util.Date leaveEndTime=fmt.parse(applyModel.getLeaveEndTime());
            //获取相隔天数
            Long days = (leaveEndTime.getTime() - leaveStartTime.getTime()) / (24 * 60 * 60 * 1000);
            days=days+1;
            System.out.println("days="+days);
            list.addAll(judge(submitter,currentPersonId,days));
        }else if (sign==1){//补考勤
            MakeUpAttendanceApplyModel applyModel = makeUpAttendanceApplyService.get(leaveId);
            submitter=get(applyModel.getPersonGuid());

            java.util.Date workDate =fmt.parse(applyModel.getWorkDate());
            java.util.Date d = new java.util.Date();
            java.util.Date today =  fmt.parse(fmt.format(d));

            //获取相隔天数
            Long days = (today.getTime() - workDate.getTime()) / (24 * 60 * 60 * 1000);
            days=days+1;
            System.out.println("days="+days);
            list.addAll(judge(submitter,currentPersonId,days));

        }

        return list;
    }

    private List<PersonModel> judge(PersonModel submitter,String currentPersonId,Long days){
        List<PersonModel> list =new ArrayList<>();
        DepartmentModel currentDepart = departmentService.get(submitter.getDepartmentId());
        PersonModel currentPersonModel = get(currentPersonId);
        Integer submitterTypeCode = personTypeService.get(submitter.getTypeId()).getCode();
        PersonModel finiahPersonModel =new PersonModel();
        finiahPersonModel.setGuid("");
        finiahPersonModel.setName("无");
        //如果提交人是普通机关人员/普通项目管理人员/项目临时人员，下一步为flag！=0的人
        if (currentPersonModel.getFlag()==0){
            list.addAll(listByDepartAndFlag(currentDepart.getGuid(),0));
        }else if (currentPersonModel.getFlag()==1){//如果提交人是 项目经理
            System.out.println("submitterTypeCode="+submitterTypeCode);
            if (submitterTypeCode==3){//临时人员，最后一步是项目经理(走完全程)
                list.add(finiahPersonModel);
            }else if ((submitterTypeCode==1&&submitter.getFlag()==0)||(submitterTypeCode==2&&submitter.getFlag()==0)){//机关普通员工请假,判断申请人请假天数，小于3天只需走到项目经理/部门经理，大于3天走项目经理--》副总--》结束
                if (days>3){
                    DepartmentModel nextDepartment =departmentService.get(currentDepart.getParentId());
                    if (currentDepart.getParentId().equals("0")){
                        list.add(finiahPersonModel);
                    }else {
                        list.addAll(listByDepartAndFlag(nextDepartment.getGuid(),0));
                    }

                }else {
                    list.add(finiahPersonModel);
                }
            }
        }else if (currentPersonModel.getFlag()==2){//如果提交人是 副总
            list.add(finiahPersonModel);
        }
        return list;
    }

    private static List<String> getDiffrent(List<String> list1,
                                            List<String> list2) {
        long st = System.nanoTime();
        Map<String, Integer> map = new HashMap<String, Integer>(list1.size()
                + list2.size());
        List<String> diff = new ArrayList<String>();
        for (String string : list1) {
            map.put(string, 1);
        }
        for (String string : list2) {
            Integer cc = map.get(string);
            if (cc != null)
            {
                map.put(string, ++cc);
                continue;
            }
            map.put(string, 1);
        }
        for (Map.Entry<String, Integer> entry : map.entrySet())
        {
            if (entry.getValue() == 1)
            {
                diff.add(entry.getKey());
            }
        }
        System.out.println("getDiffrent total times "
                + (System.nanoTime() - st));
        return diff;

    }

    @Override
    public ResultJson getTypeByIdcard(String idCard) {
        ResultJson resultJson = new ResultJson();
        List<SqlQueryParam> sqlQueryParams = new ArrayList<>();
        sqlQueryParams.add(new SqlQueryParam("idCard", idCard, "eq"));
        List<PersonModel> personModelList = this.list(sqlQueryParams);
        //数据库中有一条该身份证号记录
        if(personModelList.size()==1){
            System.out.println("一条,机关或者项目管理人员");
            PersonModel personModel=personModelList.get(0);
            String typeId=personModel.getTypeId();
            List<SqlQueryParam> sqlQueryParams1 = new ArrayList<>();
            sqlQueryParams1.add(new SqlQueryParam("guid", typeId, "eq"));
            List<PersonTypeModel> personTypeModelList = personTypeMapper.selectAll(sqlQueryParams1);
            resultJson.setCode("200");
            resultJson.setMsg("数据库中有一条该身份证号记录");
            resultJson.setObj(personTypeModelList);
            System.out.println(resultJson);
            //数据库中没有该身份证号记录
        }else if(personModelList.size()==0){
            //劳务人员和临时工
            List<PersonTypeModel> personTypeModelList = personTypeMapper.selectLaowuAndLinshi();
            resultJson.setCode("201");
            resultJson.setMsg("数据库中没有该身份证号记录");
            //劳务人员和临时工类型
            resultJson.setObj(personTypeModelList);
            System.out.println(resultJson);
        }else if(personModelList.size()>0){
            resultJson.setCode("401");
            resultJson.setMsg("数据库中有多条该身份证号记录,请联系管理员");
        }
        return resultJson;
    }

    @Override
    public PersonModel getByOpenId(String openId) {
        return personMapper.getByOpenId(openId);
    }
    //根据personGuid查询CompanyCode
    @Override
    public PersonModel getCompany(String personGuid) {
        return personMapper.getCompany(personGuid);
    }
}
