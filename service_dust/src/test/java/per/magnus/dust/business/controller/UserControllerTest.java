package per.magnus.dust.business.controller;

import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import per.magnus.dust.business.domain.User;
import per.magnus.dust.components.web.entity.DustResponse;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {

    @Autowired
    TestRestTemplate testRestTemplate;

    @BeforeEach
    void setup() {
    }

    @Test
    @DisplayName("/user/create/1")
    void shouldCreateNewUserSuccessfully() {
        User user = new User();
        user.setId(1L);
        user.setName("magnus");
        user.setDescription("magnus is magnus");
        DustResponse dustResponse = testRestTemplate.postForObject("/user/create", user, DustResponse.class);
        assert dustResponse.getCode() == 200;
    }

    @Test
    @DisplayName("/user/query/1")
    void shouldReturnUserInQuery() {
        DustResponse dustResponse = testRestTemplate.postForObject("/user/query/1", null, DustResponse.class);
        assert dustResponse.getCode() == 200;
    }

    @Test
    void givenWrongUserOrPasswordShouldNotExecuteAuthRequiredControllers() {
        User magnus = new User(1L, "magnus", null, null, null, null);
        DustResponse dustResponse = testRestTemplate.postForObject("/user/login", magnus, DustResponse.class);
        System.out.println(dustResponse.getMsg());
        System.out.println(dustResponse.getBody());

    }

    @Test
    void givenRightUserAndPasswordShouldLoginSuccessfullyAndReturnRightSession() {
        User magnus = new User(1L, "magnus", "magnus", null,null, null);
        ResponseEntity dustResponse = testRestTemplate.postForEntity("/user/login", magnus, DustResponse.class);
        List<String> strings = dustResponse.getHeaders().get("Set-Cookie");
        System.out.println(JSON.toJSONString(dustResponse));
        System.out.println(strings);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Cookie", strings.get(0));
        HttpEntity<String> httpEntity = new HttpEntity(JSON.toJSONString(magnus), httpHeaders);
        DustResponse dustResponse1 = testRestTemplate.postForObject("/user/check-login-status", httpEntity, DustResponse.class);
        System.out.println(JSON.toJSONString(dustResponse1));
    }
}