package per.magnus.dust.business.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import per.magnus.dust.business.domain.Mission;
import per.magnus.dust.business.domain.User;
import per.magnus.dust.business.service.MissionService;
import per.magnus.dust.business.service.UserMissionService;
import per.magnus.dust.components.web.aspect.annotation.LoginCheckRequired;
import per.magnus.dust.components.web.entity.DustResponse;
import per.magnus.dust.components.web.exception.DustException;
import per.magnus.dust.components.web.resolver.annotation.InjectUser;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;

/**
 * 异常处理 ？
 */
@SuppressWarnings("unused")
@RestController
@RequestMapping("/user/mission")
@LoginCheckRequired
public class UserMissionController {

    @Resource
    UserMissionService userMissionService;

    @Resource
    MissionService missionService;

    @RequestMapping("/get")
    public DustResponse queryMissionsForUser(@InjectUser User user) {
        List<Mission> missions = userMissionService.queryMissionForUser(user);
        return DustResponse.okResponse(missions);
    }

    @RequestMapping("/create")
    public DustResponse createMissionForUser(@InjectUser User user, @RequestBody Map<String, Object> missionMap) {
        // 创建任务
        // 优先插入自己的任务-用户关系，设置level为admin
        // 构建mission
        LocalDateTime deadLineDate = LocalDateTime.ofEpochSecond((long) missionMap.get("deadLineDate") / 1000L, 0, ZoneOffset.ofHours(8));
        Mission mission = Mission.createMission((String) missionMap.get("name"), (String) missionMap.get("content"), deadLineDate);
        userMissionService.addMissionToUser(mission, user);
        return DustResponse.okResponse(mission);
    }

    @DeleteMapping("/delete/{missionId}")
    public DustResponse deleteMission(@InjectUser User user, @PathVariable("missionId") long id) throws DustException {
        //
        Mission missionId = Mission.createMission(id);
        userMissionService.deleteMissionForUser(missionId, user);
        return DustResponse.okResponse();
    }

    @PostMapping("/finish/for-all/{id}")
    public DustResponse finishMission(@InjectUser User user, @PathVariable("id") long id) {
        Mission mission = Mission.createMission(id);
        userMissionService.finishMissionForAll(mission, user);
        Mission missionById = missionService.getMissionById(mission.getId());
        return DustResponse.okResponse(missionById);
    }

    @RequestMapping("/status/finish/all")
    public DustResponse finishMissionForAll(@InjectUser User user, Mission mission) {
        // 完成任务
        // 1.判断当前操作的人是否有权限修改这个任务
        // 2.修改
        return DustResponse.okResponse();
    }

    @RequestMapping("/status/finish/self")
    public DustResponse finishMissionForSelf(@InjectUser User user, Mission mission) {
        // 完成自己的部分
        // 1.判断当前操作人是否在这个任务中
        // 2.修改状态为已完成。
        return DustResponse.okResponse();
    }
}
