package org.tonzoc.controller;

import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.tonzoc.controller.params.FlowpathQueryParams;
import org.tonzoc.controller.params.PageQueryParams;
import org.tonzoc.controller.response.PageResponse;
import org.tonzoc.exception.PageException;
import org.tonzoc.model.FlowpathModel;
import org.tonzoc.service.IFlowPathService;
import org.tonzoc.support.param.SqlQueryParam;

import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

@RestController
@RequestMapping("flowpath")
public class FlowpathController extends BaseController{

    @Autowired
    private IFlowPathService flowPathService;
    @GetMapping
    public PageResponse list(PageQueryParams pageQueryParams, FlowpathQueryParams flowpathQueryParams)
            throws PageException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {

        Page<FlowpathModel> page = parsePage(pageQueryParams);
        FlowpathQueryParams sqlQueryParamList = new FlowpathQueryParams();
        if (flowpathQueryParams.getLeaveId() != null && !flowpathQueryParams.getLeaveId().equals("")) {
            sqlQueryParamList.setLeaveId(flowpathQueryParams.getLeaveId());
        }
        if (flowpathQueryParams.getPersonId() != null && !flowpathQueryParams.getPersonId().equals("")) {
            sqlQueryParamList.setPersonId(flowpathQueryParams.getPersonId());
        }
        if (flowpathQueryParams.getIsBackward() != null && !flowpathQueryParams.getIsBackward().equals("")) {
            sqlQueryParamList.setIsBackward(flowpathQueryParams.getIsBackward());
        }

        List<SqlQueryParam> sqlQueryParams = parseSqlQueryParams(sqlQueryParamList);
        List<FlowpathModel> list = flowPathService.list(sqlQueryParams);

        return new PageResponse(page.getTotal(), list);

    }

    @PostMapping
    public void add(@RequestBody @Valid FlowpathModel flowpathModel) {
        this.flowPathService.save(flowpathModel);
    }

    @PutMapping(value = "{guid}")
    public void update(@RequestBody @Valid FlowpathModel flowpathModel) {
        this.flowPathService.update(flowpathModel);
    }

    @DeleteMapping(value = "{guid}")
    public void remove(@PathVariable(value = "guid") String guid) {
        this.flowPathService.remove(guid);
    }

    //上报、审批
    @PostMapping(value = "approval")
    public void approval(Integer sign, String leaveId, String currentPersonId, String nextPersonId) throws Exception {
        flowPathService.approval(sign,leaveId,currentPersonId,nextPersonId);
    }

    @PostMapping(value = "reject")
    public void reject(String personId,String leaveId,Integer sign) throws Exception {
        flowPathService.reject(personId,leaveId,sign);
    }
}
