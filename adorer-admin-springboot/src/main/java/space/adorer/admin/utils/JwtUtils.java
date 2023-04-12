package space.adorer.admin.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

/**
 * Jwt 相关操作工具类
 *
 * @author adorer
 * @version 1.0
 * @since 2023/4/11 13:54:21
 */
public class JwtUtils {

    /**
     * 过期时长
     */
    private static final Long EXPIRE_TIME = 7 * 24 * 60 * 60L;

    /**
     * 密钥
     */
    private static final String SECURITY_KEY = "adorer";

    /**
     * 获取一个随机字符串
     */
    private static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 生成加密后的秘钥 secretKey
     */
    private static SecretKey generalKey() {
        byte[] encodedKey = Base64.getDecoder().decode(SECURITY_KEY);
        return new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
    }

    /**
     * 创建 jwt
     *
     * @param subject   加密内容
     * @param ttlMillis 过期时长
     * @param uuid      jwt 唯一标识
     */
    private static JwtBuilder getJwtBuilder(String subject, Long ttlMillis, String uuid) {
        SecretKey secretKey = generalKey();
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        if (ttlMillis == null) {
            ttlMillis = EXPIRE_TIME;
        }
        long expMillis = nowMillis + ttlMillis * 1000;
        Date expDate = new Date(expMillis);
        return Jwts.builder()
                .setId(uuid)            // 唯一标识
                .setSubject(subject)    // 主题  可以是JSON数据
                .setIssuer("adore")     // 签发者
                .setIssuedAt(now)       // 签发时间
                .signWith(SignatureAlgorithm.HS256, secretKey)    // 使用HS256对称加密算法签名, 第二个参数为秘钥
                .setExpiration(expDate);    // 过期时间
    }

    /**
     * 生成 JWT
     */
    public static String generator(String subject) {
        JwtBuilder builder = getJwtBuilder(subject, null, getUUID());// 设置过期时间
        return builder.compact();
    }

    /**
     * 生成 JWT
     */
    public static String generator(String subject, Long ttlMillis) {
        JwtBuilder builder = getJwtBuilder(subject, ttlMillis, getUUID());// 设置过期时间
        return builder.compact();
    }

    /**
     * 生成 JWT
     */
    public static String generator(String subject, Long ttlMillis, String id) {
        JwtBuilder builder = getJwtBuilder(subject, ttlMillis, id);// 设置过期时间
        return builder.compact();
    }

    /**
     * 解析 JWT
     */
    public static Claims parse(String jwt) {
        SecretKey secretKey = generalKey();
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwt)
                .getBody();
    }
}
