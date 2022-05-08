package per.magnus.dust.business.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import per.magnus.dust.business.domain.Mission;
import per.magnus.dust.components.web.entity.DustResponse;

@RestController
@RequestMapping("/mission")
@SuppressWarnings("unused")
public class MissionController {

    @RequestMapping("/create")
    public DustResponse createNewMission(@RequestBody Mission mission) {
        return DustResponse.okResponse();
    }

    @RequestMapping("/query/{id}")
    public DustResponse queryMissionDetails(@PathVariable(name = "id") Long id) {
        return DustResponse.okResponse();
    }

    @RequestMapping("/delete/{id}")
    public DustResponse deleteMission(@PathVariable(name = "id") Long id) {
        return DustResponse.okResponse();
    }
}
