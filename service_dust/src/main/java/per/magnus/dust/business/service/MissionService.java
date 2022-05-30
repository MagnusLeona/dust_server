package per.magnus.dust.business.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import per.magnus.dust.business.domain.Mission;
import per.magnus.dust.business.mapper.MissionMapper;

@Service
public class MissionService {

    @Autowired
    MissionMapper missionMapper;

    public void createMission(Mission mission) {
        missionMapper.insertMission(mission);
    }

    // 事务机制：如果当前存在事务，则加入事务。如果当前不存在事务，则默认以非事务的方式执行
    @Transactional(propagation = Propagation.SUPPORTS)
    public void deleteMissionById(Long id) {
        missionMapper.deleteMissionById(id);
    }

    public Mission getMissionById(Long id) {
        return missionMapper.selectMissionById(id);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void finishMission(Mission mission) {
        missionMapper.updateMissionStatus(mission);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void updateMissionStatus(Mission mission) {
        missionMapper.updateMissionStatus(mission);
    }
}
