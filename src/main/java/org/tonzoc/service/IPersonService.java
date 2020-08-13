package org.tonzoc.service;

import org.springframework.web.multipart.MultipartFile;
import org.tonzoc.exception.NotOneResultFoundException;
import org.tonzoc.model.PersonModel;
import org.tonzoc.model.support.*;
import org.tonzoc.model.util.ResultJson;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface IPersonService extends IBaseService<PersonModel> {
    PersonModel findByOpenId(String openId);
    void deletePerson(String guid) throws NotOneResultFoundException;
    List<PersonModel> listByDepartmentId(String departmentId);

    Integer count(String projectId);

//    Integer countMan(String departId);
//
//    Integer countWoman(String departId);

    Map countSex(String projectId);

    //    List<TypeModel> listTypeModel(String companyId);
    CountTotalModel listTypeModel(String projectId, String date) throws ParseException;

    List<TypeModel> sumByType(String projectId);

    //统计学历
    List<GeneralModel> countEducation(String projectId);

    //统计每个年龄段人数
    List<GeneralModel> countAge(String projectId) throws Exception;

    //统计部门人员状态
    List<DepartCountModel> countStatus(String projectId) throws NotOneResultFoundException;

    List<String> listTypeId(String projectId);

    Map<String,String> uploadFile(MultipartFile file, String personId);

    //统计部门人员状态
    List<Object> countStatusByProjectId(String projectId) throws NotOneResultFoundException;

    List<PersonModel> listNextPersons(String leaveId, String currentPersonId, Integer sign) throws ParseException;

    /**
     * 根据身份证号返回人员类型
     * @param idCard
     * @return
     */
    ResultJson getTypeByIdcard(String idCard);

    PersonModel getByOpenId(String openId);
    //根据personGuid查询Company
    PersonModel getCompany(String personGuid);


}
