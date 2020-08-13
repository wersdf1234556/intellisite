package org.tonzoc.service.impl;

import org.springframework.stereotype.Service;
import org.tonzoc.model.CamerasModuleModel;
import org.tonzoc.model.RelevanceTableModel;
import org.tonzoc.service.ICamerasModuleService;
import org.tonzoc.service.IRelevanceTableService;
import org.tonzoc.support.param.SqlQueryParam;

import java.util.ArrayList;
import java.util.List;

@Service("relevanceTableService")
public class RelevanceTableService extends BaseService<RelevanceTableModel> implements IRelevanceTableService {

    public List<RelevanceTableModel> listByMainId(String mainId) {
        List<SqlQueryParam> sqlQueryParams = new ArrayList<>();
        sqlQueryParams.add(new SqlQueryParam("mainId", mainId, "eq"));

        List<RelevanceTableModel> relevanceTableModels = this.list(sqlQueryParams);

        return relevanceTableModels;
    }

    @Override
    public void deleteByMainId(String mainId) {
        List<RelevanceTableModel> relevanceTableModels = listByMainId(mainId);

        for (RelevanceTableModel relevanceTableModel : relevanceTableModels) {
            remove(relevanceTableModel.getGuid());
        }
    }
}
