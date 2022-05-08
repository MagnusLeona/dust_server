package per.magnus.dust.components.service.enums;

public enum ResponseStatusEnum {

    STATUS_SUCCESSFUL(0),
    STATUS_NOTFOUND(404),
    STATUS_UNKONWN_ERROR(500),
    STATUS_LOGINREQUIRED(700),
    STATUS_LOGININFOERROR(701),
    AUTH_CHECK_DENIED(800);   //权限不足

    int code;

    ResponseStatusEnum(int code) {
        this.code = code;
    }

    public int code() {
        return code;
    }
}
