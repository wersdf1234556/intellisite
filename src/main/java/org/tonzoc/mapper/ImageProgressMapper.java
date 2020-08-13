package org.tonzoc.mapper;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;
import org.tonzoc.model.ImageProgressModel;
import org.tonzoc.model.ImageProgressProjectNameModel;

import java.util.List;
@Component
public interface ImageProgressMapper extends BaseMapper<ImageProgressModel> {
    @Select("SELECT ip.guid,ip.thisMonthRate,ip.createTime, ippn.name as imageProgressProjectName, imageProgressProjectNameGuid, designQuantity, thisYearPlan, thisMonthComplete, thisYearComplete, yearCompleteRate, cumulativeComplete, cumulativeCompleteRate, projectGuid, p.name projectName FROM imageProgress ip LEFT JOIN imageProgressProjectName ippn ON ip.imageProgressProjectNameGuid = ippn.guid LEFT JOIN project p ON p.guid = ip.projectGuid WHERE ip.projectGuid = #{proGuid} and ip.isdelete='0'")
    List<ImageProgressModel> listByProguid(String proGuid);
    @Update("UPDATE imageProgress SET imageProgressProjectNameGuid = #{ imageProgressProjectNameGuid }, designQuantity = #{ designQuantity }, thisYearPlan = #{ thisYearPlan }, thisMonthComplete = #{ thisMonthComplete }, thisYearComplete = #{ thisYearComplete }, yearCompleteRate = #{ yearCompleteRate }, cumulativeComplete = #{ cumulativeComplete }, cumulativeCompleteRate = #{ cumulativeCompleteRate }, projectGuid = #{ projectGuid },thisMonthRate=#{thisMonthRate},isdelete = #{ isdelete } WHERE (guid = #{ guid })")
    void update1(ImageProgressModel imageProgressModel);
    @Select("SELECT guid,thisMonthRate, name, pguid FROM imageProgressProjectName WHERE pguid='0'")
    List<ImageProgressModel> listFirstProjectName();
    @Select("SELECT guid,thisMonthRate, imageProgressProjectNameGuid, designQuantity, thisYearPlan, thisMonthComplete, thisYearComplete, yearCompleteRate, cumulativeComplete, cumulativeCompleteRate, projectGuid, isdelete FROM imageProgress WHERE isdelete = '0' AND imageProgressProjectNameGuid = #{secondGuid}")
    List<ImageProgressModel> listByimageProgressProjectNameGuid(String secondGuid);
    @Select("SELECT guid, name, pguid FROM imageProgressProjectName WHERE pguid!='0'")
    List<ImageProgressProjectNameModel> listSecondProjectName();
    @Select("SELECT guid,thisMonthRate, imageProgressProjectNameGuid, designQuantity, thisYearPlan, thisMonthComplete, thisYearComplete, yearCompleteRate, cumulativeComplete, cumulativeCompleteRate, projectGuid, isdelete FROM imageProgress WHERE isdelete = '0' AND imageProgressProjectNameGuid = #{secondGuid} and projectGuid=#{projectGuid}")
    List<ImageProgressModel> listByimageProgressProjectNameGuidAndProjectGuid(String secondGuid, String projectGuid);
}
