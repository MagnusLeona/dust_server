package per.magnus.dust.business.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    Long id;
    String name;
    String password;
    String description;
    List<Mission> missionList;

    /**
     * 给用户添加任务
     * @param mission
     */
    public void addMission(Mission mission) {
        this.missionList.add(mission);
    }
}
