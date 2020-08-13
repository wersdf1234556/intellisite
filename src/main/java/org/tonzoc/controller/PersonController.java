package org.tonzoc.controller;

import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.tonzoc.controller.params.PageQueryParams;
import org.tonzoc.controller.params.PersonQueryParams;
import org.tonzoc.controller.response.PageResponse;
import org.tonzoc.exception.NotOneResultFoundException;
import org.tonzoc.exception.PageException;
import org.tonzoc.mapper.PersonMapper;
import org.tonzoc.model.PersonModel;
import org.tonzoc.model.RelevanceTableModel;
import org.tonzoc.model.SafetyHelmetModel;
import org.tonzoc.model.support.CountTotalModel;
import org.tonzoc.model.support.DepartCountModel;
import org.tonzoc.model.support.GeneralModel;
import org.tonzoc.model.support.PStatusModel;
import org.tonzoc.service.IPersonService;
import org.tonzoc.service.IRelevanceTableService;
import org.tonzoc.support.param.SqlQueryParam;

import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "person")
public class PersonController extends BaseController {

    @Autowired
    private IPersonService personService;
    @Autowired
    private IRelevanceTableService relevanceTableService;

    @GetMapping
    public PageResponse list(PageQueryParams pageQueryParams, PersonQueryParams personQueryParams)
            throws PageException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {

        Page<PersonModel> page = parsePage(pageQueryParams);
        PersonQueryParams sqlQueryParamList = new PersonQueryParams();
        if (personQueryParams.getTypeId() != null && !personQueryParams.getTypeId().equals("")) {
            sqlQueryParamList.setTypeId(personQueryParams.getTypeId());
        }
        if (personQueryParams.getCompanyId() != null && !personQueryParams.getCompanyId().equals("")) {
            sqlQueryParamList.setCompanyId(personQueryParams.getCompanyId());
        }
        if (personQueryParams.getProjectId() != null && !personQueryParams.getProjectId().equals("")) {
            sqlQueryParamList.setProjectId(personQueryParams.getProjectId());
        }
        if (personQueryParams.getCode() != null && !personQueryParams.getCode().equals("")) {
            sqlQueryParamList.setCode(personQueryParams.getCode());
        }
        if (personQueryParams.getDepartmentId() != null && !personQueryParams.getDepartmentId().equals("")) {
            sqlQueryParamList.setDepartmentId(personQueryParams.getDepartmentId());
        }
        if (personQueryParams.getGuid() != null && !personQueryParams.getGuid().equals("")) {
            sqlQueryParamList.setGuid(personQueryParams.getGuid());
        }
        if (personQueryParams.getInFence() != null && !personQueryParams.getInFence().equals("")) {
            sqlQueryParamList.setInFence(personQueryParams.getInFence());
        }
        if (personQueryParams.getSex() != null && !personQueryParams.getSex().equals("")) {
            sqlQueryParamList.setSex(personQueryParams.getSex());
        }
        if (personQueryParams.getGpsDeviceSerial() != null && !personQueryParams.getGpsDeviceSerial().equals("")) {
            sqlQueryParamList.setGpsDeviceSerial(personQueryParams.getGpsDeviceSerial());
        }
        if (personQueryParams.getName() != null && !personQueryParams.getName().equals("")) {
            sqlQueryParamList.setName(personQueryParams.getName());
        }
        if (personQueryParams.getEducationId() != null && !personQueryParams.getEducationId().equals("")) {
            sqlQueryParamList.setEducationId(personQueryParams.getEducationId());
        }
        if (personQueryParams.getStatusId() != null && !personQueryParams.getStatusId().equals("")) {
            sqlQueryParamList.setStatusId(personQueryParams.getStatusId());
        }

        List<SqlQueryParam> sqlQueryParams = parseSqlQueryParams(sqlQueryParamList);

        List<PersonModel> list = personService.list(sqlQueryParams);
        for (PersonModel personModel : list) {
            List<SafetyHelmetModel> safetyHelmetModels = new ArrayList<>();

            List<RelevanceTableModel> relevanceTableModels = relevanceTableService.listByMainId(personModel.getGuid());

            personModel.setSafetyHelmetModels(relevanceTableModels
                    .stream()
                    .map(relevanceTableModel -> {
                        SafetyHelmetModel safetyHelmetModel = new SafetyHelmetModel();
                        safetyHelmetModel.setGuid(relevanceTableModel.getDependenceId());

                        return safetyHelmetModel;
                    })
                    .collect(Collectors.toList()));

        }


        return new PageResponse(page.getTotal(), list);
    }


    //统计男女人数
    @GetMapping(value = "countBySex")
    public Map countSex(String projectId) {
        return personService.countSex(projectId);
    }

    @PostMapping
    public void add(@RequestBody @Valid PersonModel personModel) {
        this.personService.save(personModel);
    }

    @PutMapping(value = "{guid}")
    public void update(@RequestBody @Valid PersonModel personModel) {
        this.personService.update(personModel);
    }



    @DeleteMapping(value = "{guid}")
    public void remove(@PathVariable(value = "guid") String guid) throws NotOneResultFoundException {
        this.personService.deletePerson(guid);
    }

    //根据companyId分类统计人数
    @GetMapping(value = "countByType")
    public CountTotalModel listTypeModel(String projectId, String date) throws ParseException {
        return personService.listTypeModel(projectId, date);
    }

    //    //将机关和管理人员加到一起
    @GetMapping(value = "sumByType")
    public List sumByType(String projectId) {
        return personService.sumByType(projectId);
    }

    //根据学历查询人数
    @GetMapping(value = "countByEducation")
    public List<GeneralModel> countEducation(String projectId) {
        return personService.countEducation(projectId);
    }

    //根据年龄查询人数
    @GetMapping(value = "countByAge")
    public List<GeneralModel> countAge(String projectId) throws Exception {
        return personService.countAge(projectId);
    }

    //根据子部门（人资部等）、状态查询人数
    @GetMapping(value = "countByStatus")
    public List<DepartCountModel> countStatus(String projectId) throws NotOneResultFoundException {
        return personService.countStatus(projectId);
    }

    @PostMapping("/uploadFile")
    public Map upFile(MultipartFile file, String personId) {

       return personService.uploadFile(file,personId);
    }

    @GetMapping(value = "countStatusByProjectId")
    public List<Object> countStatusByProjectId(String projectId) throws NotOneResultFoundException {
        return personService.countStatusByProjectId(projectId);
    }

    @GetMapping("nextPersons")
    public List<PersonModel> listNextPersons(String leaveId,String currentPersonId,Integer sign) throws ParseException{
        return personService.listNextPersons(leaveId,currentPersonId,sign);
    }

}
