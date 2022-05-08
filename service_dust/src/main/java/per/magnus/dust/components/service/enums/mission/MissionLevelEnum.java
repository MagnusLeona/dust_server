package per.magnus.dust.components.service.enums.mission;

public enum MissionLevelEnum {

    MISSION_LEVEL_HIGHEST(10),
    MISSION_LEVEL_MIDDLE(5),
    MISSION_LEVEL_LOWEST(0);

    public int level;

    MissionLevelEnum(int level) {
        this.level = level;
    }
}
