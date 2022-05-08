package per.magnus.dust.business.service;

import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import per.magnus.dust.business.domain.Mission;
import per.magnus.dust.business.domain.User;
import per.magnus.dust.components.web.exception.DustException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
class UserMissionServiceTest {

    @Autowired
    UserMissionService userMissionService;
    @Autowired
    MissionService missionService;

    User user;
    Mission mission;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        mission = new Mission();
        mission.setStatus(0);
        mission.setName("test-mission");
    }

    /**
     * 查询用户对应的所有任务，以及任务的权限
     */
    @Test
    void givenUserIdShouldReturnMissionsForUser() {
        User user = new User();
        user.setId(1L);
        List<Mission> missions = userMissionService.queryMissionForUser(user);
        System.out.println(JSON.toJSONString(missions));
        System.out.println(missions.size());
        assert !missions.isEmpty();
    }

    /**
     * 给用户新增一个任务
     */
    @Test
    void givenMissionAndUserShouldSaveMissionAndUserMissionRelations() {
        User user = new User();
        user.setId(1L);
        Mission mission = new Mission();
        mission.setName("this is a test mission");
        mission.setContent("this is content of a test mission");
        mission.setCreateTime(LocalDateTime.now());
        Assertions.assertDoesNotThrow(() -> {
            userMissionService.addMissionToUser(mission, user);
        });
    }

    @Test
    void givenUpdatedMissionShouldUpdateMissionForValidUser() {
        User user = new User();
        user.setId(1L);
        Mission missionById = missionService.getMissionById(1L);
        missionById.setName("unittest");
        missionById.setContent("unit test content");
        userMissionService.updateMissionForUser(missionById, user);
        Mission missionById1 = missionService.getMissionById(1L);
        assert missionById1.getName().equals("unittest");
    }

    @Test
    void deleteUserAndMissionIfUserAndMissionIsGiven() {

        Mission mission = new Mission();
        mission.setId(1L);

        Assertions.assertDoesNotThrow(() -> {
            userMissionService.deleteMissionForUser(mission, user);
        });
    }

    @Test
    void finishUserAndMissionIfExists() {
            userMissionService.addMissionToUser(mission, user);
            // 修改mission状态
        Mission missionById = missionService.getMissionById(mission.getId());
        assert missionById.getStatus() == 0;
        userMissionService.finishMissionForAll(mission, user);
        Mission missionById1 = missionService.getMissionById(mission.getId());
        assert missionById1.getStatus() == 10;
    }
}