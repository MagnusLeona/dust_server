package per.magnus.dust.business.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import per.magnus.dust.components.service.enums.mission.MissionAuthEnum;
import per.magnus.dust.components.service.enums.mission.MissionLevelEnum;
import per.magnus.dust.components.service.enums.mission.MissionStatusEnum;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserMission {
    // 归属于某个用户的mission对象，需要包含用户操作任务的权限
    User user;
    Mission mission;
    Integer userLevel;
    Integer userStatus;

    public static UserMission definedUser(User user, Mission mission, int level, int status) {
        return new UserMission(user, mission, level, status);
    }

    public static UserMission newUserMission(User user, Mission mission) {
        return new UserMission(user, mission, MissionAuthEnum.MISSION_AUTH_ADMIN.getLevel(), MissionStatusEnum.MISSION_CREATED.getCode());
    }
}

