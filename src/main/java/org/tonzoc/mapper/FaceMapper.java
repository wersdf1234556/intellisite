package org.tonzoc.mapper;

import org.apache.ibatis.annotations.Select;
import org.tonzoc.model.FaceModel;

public interface FaceMapper extends BaseMapper<FaceModel> {
    @Select("SELECT guid, image, groupId, userId, imageType, faceToken FROM facePicture WHERE userId =#{userId}")
    FaceModel selectOneByPersonGuid(String userId);
    //根据personid查询数据库是否有此人脸图片
    @Select("SELECT COUNT (*) FROM facePicture WHERE userId =#{personGuid}")
    Integer countFace(String personGuid);
}
