package org.tonzoc.service;

import org.tonzoc.model.DepartmentModel;

import java.util.List;

public interface IDepartmentService extends IBaseService<DepartmentModel> {

    List<DepartmentModel> listByParentId(String parentId);

    List<DepartmentModel> listWithLevel(String flowId) throws Exception;

    List<DepartmentModel> listNextDepart(Integer sign, String leaveId, String currentPersonId) throws Exception;
}
