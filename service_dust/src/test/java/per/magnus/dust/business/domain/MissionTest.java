package per.magnus.dust.business.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class MissionTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void givenMissionBuilderShouldReturnMission() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime next = now.plusHours(12);
        Mission mission = new Mission(1L, "the mission","start of dust",0, now, next, null);
        assert mission.getContent().equals("start of dust");
        assert mission.getName().equals("the mission");
        assert mission.getCreateTime().equals(now);
        assert mission.getDeadLineTime().equals(next);
    }
}