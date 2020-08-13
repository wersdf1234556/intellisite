package org.tonzoc.service;

import org.tonzoc.model.ImageProgressModel;
import org.tonzoc.model.ImageProgressRateModel;
import org.tonzoc.model.ProjectModel;
import org.tonzoc.model.util.ResultJson;
import org.tonzoc.support.param.SqlQueryParam;

import java.util.List;

public interface IImageProgressService   extends IBaseService<ImageProgressModel> {
    List<ProjectModel> listProject();

    List<ImageProgressModel> listByProguid(String proGuid);

    ResultJson update1(ImageProgressModel imageProgressModel, List<ImageProgressModel> list);

    List<ProjectModel> firstList(List<SqlQueryParam> sqlQueryParams);

    ResultJson save1(ImageProgressModel imageProgressModel, List<ImageProgressModel> list);

    List<ImageProgressModel> listByimageProgressProjectNameGuid(String secondGuid);

    /**
     * 查询单个项目一级工程名称进度
     * @return
     * @param
     */
    List<ImageProgressRateModel> firstCompanyImageProgress();

    /**
     * 查询所有项目一级工程名称进度
     * @return
     * @param projectGuid
     */
    List<ImageProgressRateModel> firstProjectImageProgress(String projectGuid);
    /**
     * 项目大屏二级工程名称进度
     * @return
     */
    List<ImageProgressRateModel> secondImageProgress();

    void update2(ImageProgressModel imageProgressModel);
}
