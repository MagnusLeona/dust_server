package per.magnus.dust.components.service.enums.mission;

public enum MissionStatusEnum {

    MISSION_CREATED(0),
    MISSION_TEMINATED(-1),
    MISSION_FINISHED(10);

    int code;

    MissionStatusEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
