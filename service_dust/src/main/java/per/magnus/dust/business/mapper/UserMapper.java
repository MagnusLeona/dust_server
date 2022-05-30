package per.magnus.dust.business.mapper;

import org.apache.ibatis.annotations.*;
import per.magnus.dust.business.domain.User;

import java.util.List;

@Mapper
public interface UserMapper {

    void insertUser(User user);

    User selectUserById(Long id);

    void deleteUserById(Long id);

    User selectUserByUserIdAndPassword(@Param("user") User user);

    User selectUserByName(String name);
}
