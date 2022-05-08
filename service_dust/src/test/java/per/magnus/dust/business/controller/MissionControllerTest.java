package per.magnus.dust.business.controller;

import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import per.magnus.dust.DustMainApplication;
import per.magnus.dust.business.domain.Mission;
import per.magnus.dust.components.web.entity.DustResponse;

import java.time.LocalDateTime;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = DustMainApplication.class)
class MissionControllerTest {

    @Autowired
    TestRestTemplate testRestTemplate;

    @BeforeEach
    void setUp() {

    }

    @Test
    @DisplayName("/mission/create/1")
    void shouldCreateMissionSuccessfully() {
        Mission mission = new Mission();
        mission.setId(1L);
        mission.setName("test mission");
        mission.setContent("the mission for test");
        mission.setCreateTime(LocalDateTime.now());
        mission.setDeadLineTime(LocalDateTime.now().plusDays(1));
        DustResponse dustResponse = testRestTemplate.postForObject("/mission/create", mission, DustResponse.class);
        System.out.println(JSON.toJSONString(dustResponse));
        assert dustResponse.getCode() == 200;
    }

    @Test
    @DisplayName("/mission/query/1")
    void shouldQueryMissionSuccessfully() {
        DustResponse dustResponse = testRestTemplate.postForObject("/mission/query/1", null, DustResponse.class);
        assert dustResponse.getCode() == 200;
    }

    @Test
    @DisplayName("/mission/delete/1")
    void shouldDeleteMissionSuccessfully() {
        DustResponse dustResponse = testRestTemplate.postForObject("/mission/delete/1", null, DustResponse.class);
        assert dustResponse.getCode() == 200;
    }

    @Test
    void shouldReturnNullObjectForNoneExistData() {
        DustResponse dustResponse = testRestTemplate.postForObject("/mission/query/1", null, DustResponse.class);
        assert dustResponse.getBody() == null;
    }
}