package per.magnus.dust.business.mapper;

import org.apache.ibatis.annotations.*;
import per.magnus.dust.business.domain.Mission;
import per.magnus.dust.business.domain.User;
import per.magnus.dust.business.domain.UserMission;

import java.util.List;

@Mapper
public interface UserMissionMapper {

    List<Mission> getMissionByUserId(Long id);

    void insertMissionUserRelations(@Param("userMission") UserMission userMission);

    void updateUserMission(@Param("mission") Mission mission, @Param("user") User user);

    void updateUserMissionStatus(@Param("missionId") long missionId, @Param("userId") long userId, @Param("status") int status);

    Integer queryUserMissionRole(@Param("missionId") long missionId, @Param("userId") long userId);

    void deleteSingleUserMissionRelation(@Param("missionId") long missionId, @Param("userId") long userId);

    void deleteAllUserMissionRelations(@Param("missionId") long missionId);

}