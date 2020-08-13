package org.tonzoc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tonzoc.mapper.FenceMapper;
import org.tonzoc.model.*;
import org.tonzoc.model.support.ReturnValueModel;
import org.tonzoc.service.*;
import org.tonzoc.support.param.SqlQueryParam;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service(value = "fenceService")
public class FenceService extends BaseService<FenceModel> implements IFenceService {

    @Autowired
    private FenceMapper fenceMapper;

    @Autowired
    private IFenceItemService fenceItemService;

    @Autowired
    private IPersonService personService;

    @Autowired
    private IProjectService projectService;

    @Autowired
    private ISecurityAlertService securityAlertService;

    public List<SecurityAlertModel> alert() {

        List<SqlQueryParam> sqlQueryParams = new ArrayList();
        List<FenceModel> list = this.list(sqlQueryParams);

        List<SecurityAlertModel> list2 = new ArrayList<>();
        for (FenceModel li: list) {
            List<SqlQueryParam> sqlQueryParams1 = new ArrayList<>();
            sqlQueryParams1.add(new SqlQueryParam("fencesId", li.getGuid(), "eq"));
            List<PersonModel> list1 = personService.list(sqlQueryParams1);

            int i = li.getIsOutside();
            ProjectModel projectModel = projectService.get(li.getProjectId());
            SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间
            sdf.applyPattern("yyyy-MM-dd HH:mm:ss");

            SecurityAlertModel securityAlertModel = new SecurityAlertModel();
            securityAlertModel.setProjectId(li.getProjectId());
            securityAlertModel.setProjectName(projectModel.getName());
            securityAlertModel.setDate(sdf.format(new Date()));
            securityAlertModel.setName(li.getName());
            int count = 0;

            for (PersonModel li1: list1) {
                if (i == 0) { // 电子围栏人员在外报警
                    if (li1.getInFence() == 1) {  // 在外报警
                        count = count + 1;
                    }
                }
                if (i == 1) { // 电子围栏人员在内报警
                    if  (li1.getInFence() == 2) {
                        count = count + 1;
                    }
                }
            }

            if (count >= 1) {
                securityAlertModel.setAlert(0); // 报警
            }else {
                securityAlertModel.setAlert(1); // 正常
            }

            list2.add(securityAlertModel);
        }
        securityAlertService.saveMany(list2);

        return list2;
    }
}
