package per.magnus.dust.business.mapper;

import org.apache.ibatis.annotations.*;
import per.magnus.dust.business.domain.Mission;

@Mapper
public interface MissionMapper {

    Mission selectMissionById(Long id);

    void insertMission(Mission mission);

    void deleteMissionById(Long id);

    void updateMissionStatus(@Param("mission") Mission mission);

    Long selectMissionForUpdate(Long id);
}
