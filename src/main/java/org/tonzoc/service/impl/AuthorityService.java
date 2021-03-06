package org.tonzoc.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.tonzoc.model.AuthorityModel;
import org.tonzoc.model.RoleAuthorityModel;
import org.tonzoc.model.UserRoleModel;
import org.tonzoc.service.IAuthorityService;
import org.tonzoc.service.IRoleAuthorityService;
import org.tonzoc.service.IUserRoleService;
import org.tonzoc.support.param.SqlQueryParam;

import java.util.*;
import java.util.stream.Collectors;

@Service(value = "authorityService")
public class AuthorityService extends BaseService<AuthorityModel> implements IAuthorityService {

    @Autowired
    private IUserRoleService userRoleService;

    @Autowired
    private IRoleAuthorityService roleAuthorityService;

    @Cacheable(value = "list_by_user", key = "#userGuid")
    public List<AuthorityModel> listByUser(String userGuid) {

        // TODO 完成该功能
        // 获取用户角色
        List<UserRoleModel> userRoleModels = userRoleService.listByUser(userGuid);

        // 获取用户所有的权限
        List<AuthorityModel> authorityModels = new ArrayList<>();

        for (UserRoleModel userRoleModel : userRoleModels) {
            // 获取当前role对应的权限
            List<RoleAuthorityModel> roleAuthorityModels = roleAuthorityService.listByRole(userRoleModel.getRoleGuid());

            for (RoleAuthorityModel roleAuthorityModel : roleAuthorityModels) {
                authorityModels.add(get(roleAuthorityModel.getAuthorityGuid()));
            }
        }

        return authorityModels;
    }

    @Cacheable(value = "list_default")
    public List<AuthorityModel> listDefault() {

        List<SqlQueryParam> sqlQueryParams = new ArrayList<>();

        sqlQueryParams.add(new SqlQueryParam("isDefault", "1", "eq"));

        List<AuthorityModel> list = list(sqlQueryParams);

        return list;
    }

    public List<AuthorityModel> listWithLevel(String userGuid) throws Exception {
        List<AuthorityModel> authorityModels = new ArrayList<>();

        List<AuthorityModel> allAuthorityModels = null;
        if (StringUtils.isEmpty(userGuid)) {
            Page page = PageHelper.startPage(1, 0, "sortId asc");
            page.setOrderByOnly(true);
            List<SqlQueryParam> sqlQueryParams = new ArrayList<>();
            sqlQueryParams.add(new SqlQueryParam("visible", "1", "eq"));
            allAuthorityModels = this.list(sqlQueryParams);
        } else {
            allAuthorityModels = this.listByUser(userGuid);
        }

        HashMap<String, AuthorityModel> authorityModelMap = new HashMap<>();

        while (allAuthorityModels.size() != 0) {
            int count = allAuthorityModels.size();
            Iterator<AuthorityModel> iterator = allAuthorityModels.iterator();

            while (iterator.hasNext()) {
                AuthorityModel authorityModel = iterator.next();

                System.out.println(authorityModel);

                if (authorityModel.getParentId().equals("")) {
                    authorityModelMap.put(authorityModel.getGuid(), authorityModel);
                    iterator.remove();
                } else if (authorityModelMap.containsKey(authorityModel.getParentId())) {
                    AuthorityModel parentAuthorityModel = authorityModelMap.get(authorityModel.getParentId());
                    if (parentAuthorityModel.getChildren() == null) {
                        parentAuthorityModel.setChildren(new ArrayList<>());
                    }
                    parentAuthorityModel.getChildren().add(authorityModel);
                    authorityModelMap.put(authorityModel.getGuid(), authorityModel);
                    iterator.remove();
                }
            }
            if (count == allAuthorityModels.size()) {
                // TODO 需要自定义异常类型
                throw new Exception("进入循环");
            }
        }

        for (AuthorityModel authorityModel : authorityModelMap.values()) {
            if (authorityModel.getParentId().equals("")) {
                authorityModels.add(authorityModel);
            }
        }

        return authorityModels
                .stream()
                .sorted(Comparator.comparing(AuthorityModel::getSortId))
                .collect(Collectors.toList());
    }

}
