package per.magnus.dust.business.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import per.magnus.dust.components.service.enums.mission.MissionStatusEnum;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Mission {
    // the unique id
    Long id;
    // the name of mission
    String name;
    // content of the mission
    String content;
    // status of the mission
    Integer status;
    // creating time of the mission
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    LocalDateTime createTime;
    // deadline time of the mission
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    LocalDateTime deadLineTime;
    // paticipators of this mission
    List<User> userList;

    /*
    给任务添加参与者
     */
    public void addUser(User user) {
        this.userList.add(user);
    }

    public List<User> getUsers() {
        return this.userList;
    }

    public static Mission createMission(String name, String content, LocalDateTime deadLineTime) {
        return new Mission(null,
                name,
                content,
                MissionStatusEnum.MISSION_CREATED.getCode(),
                LocalDateTime.ofEpochSecond(LocalDateTime.now().toEpochSecond(ZoneOffset.ofHours(8)), 0, ZoneOffset.ofHours(8)),
                deadLineTime,
                null);
    }

    public static Mission createMission(long id) {
        return new Mission(id, null, null, null, null, null,null);
    }
}
