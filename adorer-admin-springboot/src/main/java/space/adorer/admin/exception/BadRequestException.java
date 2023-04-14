package space.adorer.admin.exception;

/**
 * 请求参数有误异常
 *
 * @author adorer
 * @version 1.0
 * @since 2023/4/12 下午3:53
 */
public class BadRequestException extends RuntimeException {
    public BadRequestException() {
    }

    public BadRequestException(String message) {
        super(message);
    }
}
