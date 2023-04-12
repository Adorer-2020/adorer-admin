package space.adorer.admin.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 性别枚举
 *
 * @author adorer
 * @version 1.0
 * @since 2023/4/11 上午11:29
 */
@Getter
@AllArgsConstructor
public enum Gender {
    MALE(0, "男性"), FEMALE(1, "女性");

    /**
     * 枚举值
     */
    @JsonValue
    final int value;

    /**
     * 描述
     */
    final String desc;
}
