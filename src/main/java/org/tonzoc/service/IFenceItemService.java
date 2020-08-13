package org.tonzoc.service;

import org.tonzoc.controller.params.PageQueryParams;
import org.tonzoc.controller.response.FenceItemResponse;
import org.tonzoc.controller.response.PageResponse;
import org.tonzoc.model.FenceItemModel;
import org.tonzoc.model.FenceModel;

import java.util.List;
import java.util.Map;

public interface IFenceItemService extends IBaseService<FenceItemModel> {

    // 检查电子围栏内外人数
    Map checkCurrentCount();
    void addFenceItem(List<FenceItemModel> fenceItemModels, String projectId, String name, Integer isOutside);
    List<FenceItemResponse> listItemByFenceId(String projectId);

}
