package space.adorer.admin.pojo;

import lombok.NonNull;
import space.adorer.admin.enums.HttpFailure;

import java.io.Serial;
import java.util.HashMap;
import java.util.Map;

/**
 * 通用返回消息类
 *
 * @author adorer
 * @version 1.0
 * @since 2023/4/11 13:10:21
 */
public class R extends HashMap<String, Object> {
    @Serial
    private static final long serialVersionUID = 1L;

    private R() {
        this.put("code", 200);
        this.put("msg", "success");
    }

    public static R ok() {
        return new R();
    }

    public static R ok(String msg) {
        return new R().put("msg", msg);
    }

    public static R ok(Map<String, Object> map) {
        R r = new R();
        r.putAll(map);
        return r;
    }

    public static R err() {
        return err(HttpFailure.SERVER_ERROR);
    }

    public static R err(String msg) {
        return err(500, msg);
    }

    public static R err(HttpFailure failure) {
        return err(failure.getCode(), failure.getMsg());
    }

    public static R err(Integer code, String msg) {
        return new R().put("code", code).put("msg", msg);
    }

    @NonNull
    @Override
    public R put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}