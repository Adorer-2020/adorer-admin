package space.adorer.admin.utils;

import org.springframework.util.StringUtils;
import space.adorer.admin.exception.AssertException;

import java.util.Objects;

/**
 * 断言工具类
 *
 * @author adorer
 * @version 1.0
 * @since 2023/4/12 16:49:18
 */
public class AssertUtils {

    /**
     * 表达式为false,则抛异常
     */
    public static void isTrue(boolean expression) {
        if (!expression) {
            throw new AssertException();
        }
    }

    /**
     * 表达式为false,则抛异常
     */
    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new AssertException(message);
        }
    }

    /**
     * 表达式为true,则抛异常
     */
    public static void isFalse(boolean expression) {
        if (expression) {
            throw new AssertException();
        }
    }

    /**
     * 表达式为true,则抛异常
     */
    public static void isFalse(boolean expression, String message) {
        if (expression) {
            throw new AssertException(message);
        }
    }

    /**
     * 对象不为null,则抛异常
     */
    public static void requireNull(Object object) {
        if (Objects.nonNull(object)) {
            throw new AssertException();
        }
    }

    /**
     * 对象不为null,则抛异常
     */
    public static void requireNull(Object object, String message) {
        if (Objects.nonNull(object)) {
            throw new AssertException(message);
        }
    }

    /**
     * 对象为null,则抛异常
     */
    public static void requireNonNull(Object object) {
        if (Objects.isNull(object)) {
            throw new AssertException();
        }
    }

    /**
     * 对象为null,则抛异常
     */
    public static void requireNonNull(Object object, String message) {
        if (Objects.isNull(object)) {
            throw new AssertException(message);
        }
    }

    /**
     * 字符串trim()后为空,则抛异常
     */
    public static void notBlank(String text) {
        if (!StringUtils.hasText(text)) {
            throw new AssertException();
        }
    }

    /**
     * 字符串trim()后为空,则抛异常
     */
    public static void notBlank(String text, String message) {
        if (!StringUtils.hasText(text)) {
            throw new AssertException(message);
        }
    }

    /**
     * 两个对象不相等,则抛异常
     */
    public static void equals(Object one, Object another) {
        if (!Objects.equals(one, another)) {
            throw new AssertException();
        }
    }
}


