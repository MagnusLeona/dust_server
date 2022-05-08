package per.magnus.dust.business.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import per.magnus.dust.business.domain.User;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserServiceTest {

    @Autowired
    UserService userService;

    @Test
    void shouldCreateUserCorrectly() {
        User user = new User();
        user.setName("magnus");
        user.setPassword("magnus");
        user.setDescription("this is magnus");
        assertDoesNotThrow(() -> {
            userService.createUser(user);
        });
    }

    @Test
    void givenUserIdShouldReturnUser() {
        User userById = userService.getUserById(1L);
        System.out.println(userById);
        assert userById.getId() == 1;
    }

    @Test
    void givenNoneExsitIdShouldReturnNull() {
        User userById = userService.getUserById(2147483647L);
        assertNull(userById);
    }

    @Test
    void creatingUserShouldInsertIntoTableAndReturnId() {
        User user = new User();
        user.setName("leona");
        user.setPassword("leona");
        user.setDescription("this is leona");
        userService.createUser(user);
        Long id = user.getId();
        User userById = userService.getUserById(id);
        assertNotNull(userById);
        assert Objects.equals(userById.getId(), id);
    }
}