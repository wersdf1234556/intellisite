package org.tonzoc.provider;

import org.apache.ibatis.annotations.Param;

public class MechanicsEntryAndExitProvider {

    //模糊查询
    public String category_name(@Param("category_name") String category_name,
                                @Param("project_key") String project_key,
                                @Param("sortId") Integer sortId,
                                @Param("sortId1") Integer sortId1) {

        StringBuilder stringBuilder = new StringBuilder("select count(mechanicsEntryAndExit.machine_key) from mechanicsEntryAndExit " +
                "LEFT JOIN mechanics on mechanicsEntryAndExit.machine_key = mechanics.machine_key " +
                "where mechanics.category_name like '%" + category_name + "%' " +
                "and mechanicsEntryAndExit.type = 'entry' and mechanicsEntryAndExit.sortId = #{sortId} and mechanics.sortId = #{sortId1}");

        if (!project_key.equals("")) {
            stringBuilder.append(" and mechanicsEntryAndExit.project_key = #{project_key}");
        }

        return stringBuilder.toString();
    }
}
