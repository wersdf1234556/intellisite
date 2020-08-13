package org.tonzoc.service.impl;

import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tonzoc.common.PointSetHelper;
import org.tonzoc.controller.params.PageQueryParams;
import org.tonzoc.controller.response.FenceItemResponse;
import org.tonzoc.controller.response.PageResponse;
import org.tonzoc.mapper.FenceItemMapper;
import org.tonzoc.model.FenceItemModel;
import org.tonzoc.model.FenceModel;
import org.tonzoc.model.PersonModel;
import org.tonzoc.service.IFenceItemService;
import org.tonzoc.service.IFenceService;
import org.tonzoc.service.IPersonService;
import org.tonzoc.support.param.SqlQueryParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(value = "fenceItemService")
public class FenceItemService  extends BaseService<FenceItemModel> implements IFenceItemService {

    @Autowired
    private FenceItemMapper fenceItemMapper;

    @Autowired
    private IPersonService personService;

    @Autowired
    private PointSetHelper pointSetHelper;
    @Autowired
    private IFenceService fenceService;



    //每分钟执行一次
//    @Scheduled(cron = "0 /1 * * ?")
    public Map checkCurrentCount() {

        List<FenceItemModel> list = fenceItemMapper.list();

        List<SqlQueryParam> sqlQueryParams = new ArrayList<>();
        sqlQueryParams.add(new SqlQueryParam("lng", "", "neq"));
        sqlQueryParams.add(new SqlQueryParam("lat", "", "neq"));
        List<PersonModel> list1 = personService.list(sqlQueryParams);
        Integer n = 0;
        Integer w = 0;
        //电子围栏外的人员
        for (PersonModel li :list1) {
            Boolean boo = pointSetHelper.isItInTheFence(li.getLng(), li.getLat(), list);
            if (boo == true) {
                n++;
                //电子围栏外的人修改
                updateGPS(li.getGuid(),li.getLng(), li.getLat(),1);
            }else{
                w++;
                //电子围栏外的人修改
                updateGPS(li.getGuid(),li.getLng(), li.getLat(),2);
            }
        }

        Map<String,String> map = new HashMap<>();
        map.put("围栏内人数：",String.valueOf(n));
        map.put("围栏外人数：",String.valueOf(w));

        return map;
    }

    public void updateGPS(String personGuid,String lng,String lat,Integer inFence){
        PersonModel personModel = personService.get(personGuid);
        personModel.setLng(lng);
        personModel.setLat(lat);
        personModel.setInFence(inFence);
        personService.update(personModel);
    }

    @Transactional
    public void addFenceItem(List<FenceItemModel> fenceItemModels,String projectId,String name,Integer isOutside){
        FenceModel fenceModel = new FenceModel();
        fenceModel.setIsOutside(isOutside);
        fenceModel.setName(name);
        fenceModel.setProjectId(projectId);
        fenceModel.setSortId(0);
        fenceService.save(fenceModel);
        String fenceId = fenceModel.getGuid();
//        System.out.println("fenceId="+fenceId);
        List<FenceItemModel> items = new ArrayList<>();
        for (FenceItemModel fenceItemModel:fenceItemModels){
            fenceItemModel.setFenceGuid(fenceId);
            items.add(fenceItemModel);
        }
        saveMany(items);
    }

    @Override
    public List<FenceItemResponse> listItemByFenceId(String projectId){
        List<FenceItemResponse> fenceItemResponses = new ArrayList<>();
        List<SqlQueryParam> sqlQueryParams = new ArrayList<>();
        sqlQueryParams.add(new SqlQueryParam("projectId", projectId, "eq"));
        List<FenceModel> list = fenceService.list(sqlQueryParams);
        for (FenceModel fenceModel : list){
            List<FenceItemModel> fenceItemModels = fenceItemMapper.listItemByFenceId(fenceModel.getGuid());
            fenceItemResponses.add(new FenceItemResponse(fenceModel.getGuid(),fenceModel.getName(),fenceModel.getIsOutside(),fenceItemModels));
        }
        return fenceItemResponses;
    }
}
