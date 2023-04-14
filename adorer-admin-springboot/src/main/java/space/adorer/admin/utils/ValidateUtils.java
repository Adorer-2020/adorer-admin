package space.adorer.admin.utils;

/**
 * 格式校验工具类
 *
 * @author adorer
 * @version 1.0
 * @since 2023/4/11 15:35:18
 */
public class ValidateUtils {
    /**
     * 邮箱格式校验
     *
     * @param email 邮箱字符串
     * @return 是否为有效邮箱
     */
    public static boolean isEmail(String email) {
        String regex = "\\w+@\\w+\\.[a-z]+(\\.[a-z]+)?";
        return email.matches(regex);
    }

    /**
     * 手机号格式校验
     *
     * @param mobile 手机号字符串
     * @return 是否为有效手机号
     */
    public static boolean isMobile(String mobile) {
        String regex = "1[3-9]\\d{9}";
        return mobile.matches(regex);
    }

    /**
     * 身份证号格式校验
     *
     * @param idCard 身份证号字符串
     * @return 是否为有效身份证号
     */
    public static boolean isValidIdCard(String idCard) {
        String regex = "\\d{17}[\\d|x]|\\d{15}";
        return idCard.matches(regex);
    }

}
