package per.magnus.dust.business.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import per.magnus.dust.business.domain.Mission;
import per.magnus.dust.business.domain.User;
import per.magnus.dust.business.domain.UserMission;
import per.magnus.dust.business.mapper.UserMissionMapper;
import per.magnus.dust.components.service.enums.mission.MissionAuthEnum;
import per.magnus.dust.components.service.enums.mission.MissionStatusEnum;
import per.magnus.dust.components.web.exception.DustException;
import per.magnus.dust.components.web.exception.ext.AuthCheckException;

import java.util.List;
import java.util.Objects;

@Service
public class UserMissionService {

    @Autowired
    UserMissionMapper userMissionMapper;

    @Autowired
    MissionService missionService;

    @Transactional
    public void addMissionToUser(Mission mission, User user) {
        // 新增一个首先需要新增一个任务
        // 然后新增 任务和用户的对应关系 -- 权限为admin
        missionService.createMission(mission);
        UserMission userMission = UserMission.newUserMission(user, mission);
        userMissionMapper.insertMissionUserRelations(userMission);
    }

    public List<Mission> queryMissionForUser(User user) {
        return userMissionMapper.getMissionByUserId(user.getId());
    }

    public void updateMissionForUser(Mission mission, User user) {
        userMissionMapper.updateUserMission(mission, user);
    }

    @Transactional
    public void finishMissionForAll(Mission mission, User user) {
        // 用户点击完成任务，修改任务状态为已完成
        // 完成总体任务状态。
        missionService.finishMission(mission.getId());
        userMissionMapper.updateUserMissionStatus(mission.getId(), user.getId(), MissionStatusEnum.MISSION_FINISHED.getCode());
    }

    public void terminateMissionForUser(Mission mission, User user) {
        // 终止任务，需要先判断任务是否已经完成，如果完成了就不能终止。    userMissionMapper.updateUserMissionStatus(mission.getId(), user.getId(), MissionStatusEnum.MISSION_TEMINATED.getCode());
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void deleteMissionForUser(Mission mission, User user) throws DustException {
        // 为用户删除任务
        // 删除分为两种情况：
        // 1.当前用户是任务的创建者，删除的时候，需要同步删除任务相关联的所有人和任务本身。
        // 2.当前用户仅仅是任务的参与者，那么就是没有权限删除任务的，只能删除自己和任务的关系。
        // 这里再另当别论
        // 仅作为参与者删除任务
        // 先删除任务-关系表，然后删除任务
        // 先查询当前用户在需求中的角色
        Integer integer = userMissionMapper.queryUserMissionRole(mission.getId(), user.getId());
        if (Objects.isNull(integer)) {
            throw AuthCheckException.accessDeniedForLowAuth();
        }
        if (integer.compareTo(MissionAuthEnum.MISSION_AUTH_ADMIN.getLevel()) == 0) {
            // 是任务的总负责人，删除的话直接删除全部任务
            userMissionMapper.deleteAllUserMissionRelations(mission.getId());
            // 删除任务，以事务方式执行，传播机制为SUPPORTS
            missionService.deleteMissionById(mission.getId());
        } else {
            // 不是的话，仅删除自己和任务的关系
            userMissionMapper.deleteSingleUserMissionRelation(mission.getId(), user.getId());
        }
    }
}
