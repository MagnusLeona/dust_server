package per.magnus.dust.business.service;

import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.support.TransactionTemplate;
import per.magnus.dust.business.domain.Mission;
import per.magnus.dust.business.domain.User;
import per.magnus.dust.business.domain.UserMission;
import per.magnus.dust.business.mapper.MissionMapper;
import per.magnus.dust.business.mapper.UserMissionMapper;
import per.magnus.dust.components.service.enums.mission.MissionAuthEnum;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MissionServiceTest {

    @Autowired
    MissionService missionService;

    @Autowired
    UserMissionService userMissionService;

    @Autowired
    @Qualifier("batch")
    SqlSession batchSqlSession;

    @Autowired
    TransactionTemplate transactionTemplate;

    @BeforeEach
    void setUp() {

    }

    @Test
    void shouldCreateMissionCorrectly() {
        Mission mission = new Mission();
        mission.setName("dust_mission");
        mission.setContent("dust_mission_content");
        mission.setCreateTime(LocalDateTime.now());
        mission.setDeadLineTime(LocalDateTime.now().plusDays(1));
        assertDoesNotThrow(() -> missionService.createMission(mission));
    }

    @Test
    void shouldReturnMissionIdIfInsertSuccessfully() {
        Mission mission = new Mission();
        mission.setName("dust_mission");
        mission.setContent("dust_mission_content");
        mission.setCreateTime(LocalDateTime.now());
        mission.setDeadLineTime(LocalDateTime.now().plusDays(1));
        // insert mission will return the right id
        missionService.createMission(mission);
        assertNotNull(mission.getId());
    }

    @Test
    void shouldReturnExistingMissionInDatabase() {
        Mission missionById = missionService.getMissionById(1L);
        assertNotNull(missionById);
        assert missionById.getId().equals(1L);
    }

    @Test
    void shouldDeleteMissionSuccessfully() {
        assertDoesNotThrow(() -> missionService.deleteMissionById(1L));
    }


    @Test
    @Disabled
    void testGreatNumberOfRows() {
        transactionTemplate.executeWithoutResult((state) -> {
            MissionMapper mapper = batchSqlSession.getMapper(MissionMapper.class);
            for (int i = 0; i < 100000; i++) {
                Mission mission = new Mission();
                mission.setName("mission" + i);
                mapper.insertMission(mission);
            }
        });

    }

    @Test
    @Disabled
    void testGreatUserMissionRelations() {
        transactionTemplate.executeWithoutResult((status) -> {
            UserMissionMapper mapper = batchSqlSession.getMapper(UserMissionMapper.class);
            for (int i = 0; i < 100; i++) {
                User user = new User();
                user.setId(1L);
                Mission mission = new Mission();
                mission.setId((long) i + 1);
                UserMission userMission = UserMission.newUserMission(user, mission);
                mapper.insertMissionUserRelations(userMission);
            }
        });
    }
}