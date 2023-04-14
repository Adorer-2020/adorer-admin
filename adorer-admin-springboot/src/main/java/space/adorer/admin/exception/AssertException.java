package space.adorer.admin.exception;

/**
 * 断言失败异常
 *
 * @author adorer
 * @version 1.0
 * @since 2023/4/12 下午4:51
 */
public class AssertException extends RuntimeException {
    public AssertException() {
    }

    public AssertException(String message) {
        super(message);
    }
}
