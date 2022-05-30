package per.magnus.dust.business.controller;

import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import per.magnus.dust.business.domain.Mission;
import per.magnus.dust.business.domain.User;
import per.magnus.dust.components.web.entity.DustResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserMissionControllerTest {

    @Autowired
    TestRestTemplate testRestTemplate;

    HttpHeaders httpHeaders;

    @Autowired
    UserMissionController userMissionController;

    User user;
    Mission mission;
    Map<String, Object> missionMap = new HashMap();

    @BeforeEach
    void setUp() {
        User magnus = new User(1L, "magnus", "magnus", null, null, null);
        missionMap.put("createTime", "2022-03-19 10:10:10");
        ResponseEntity dustResponse = testRestTemplate.postForEntity("/user/login", magnus, DustResponse.class);
        List<String> strings = dustResponse.getHeaders().get("Set-Cookie");
        httpHeaders = new HttpHeaders();
        httpHeaders.add("Cookie", strings.get(0));
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
    }

    @Test
    void givenLoginedCookieShouldReturnMissonsBelongToUser() {
        HttpEntity<String> httpEntity = new HttpEntity<>(null, httpHeaders);
        DustResponse dustResponse = testRestTemplate.postForObject("/user/mission/get", httpEntity, DustResponse.class);
        System.out.println(dustResponse);
        assert !Objects.isNull(dustResponse);
        assert dustResponse.getCode() == 200;
    }

    @Test
    void testInsert() {
        DustResponse missionForUser = userMissionController.createMissionForUser(user, missionMap);
    }

    @Test
    void givenMissionShouldDeleteMissionCorrectly() {
        HttpEntity<String> httpEntity = new HttpEntity<>(null, httpHeaders);
    }
}