package per.magnus.dust.components.service.enums.mission;

public enum MissionAuthEnum {

    MISSION_AUTH_ADMIN(0),
    MISSION_LEVEL_PARTICIPANT(1);

    private int level;

    MissionAuthEnum(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }
}
