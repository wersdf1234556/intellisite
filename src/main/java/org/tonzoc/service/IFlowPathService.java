package org.tonzoc.service;

import org.tonzoc.model.FlowpathModel;

public interface IFlowPathService extends IBaseService<FlowpathModel> {
    void approval(Integer sign, String leaveId, String currentPersonId, String nextPersonId) throws Exception ;

    void reject(String personId, String leaveId, Integer sign) throws Exception;
}
