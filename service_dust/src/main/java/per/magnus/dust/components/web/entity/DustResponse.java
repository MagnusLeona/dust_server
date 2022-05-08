package per.magnus.dust.components.web.entity;

import lombok.Data;

@Data
public class DustResponse {
    private int code;
    private String msg;
    private Object body;

    private DustResponse() {
    }

    private DustResponse(int code, String msg, Object o) {
        this.code = code;
        this.msg = msg;
        this.body = o;
    }

    public static DustResponse newDustResponse(int code, String msg, Object o) {
        return new DustResponse(code, msg, o);
    }

    public static DustResponse okResponse() {
        return new DustResponse(200, "success", null);
    }

    public static DustResponse okResponse(Object o) {
        return new DustResponse(200, "success", o);
    }

    public static DustResponse notFound() {
        return new DustResponse(404, "not found", null);
    }

    public static DustResponse serverError() {
        return new DustResponse(500, "server error", null);
    }

    public static DustResponse unknownError() {
        return new DustResponse(501, "unknown error", null);
    }

    public static DustResponse definedError(int code, String msg, Object o) {
        return new DustResponse(code, msg, o);
    }
}
