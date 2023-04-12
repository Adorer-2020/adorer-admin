package space.adorer.admin.utils;

import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Redis 相关操作工具类
 *
 * @author adorer
 * @version 1.0
 * @since 2023/4/11 12:36:31
 */
@SuppressWarnings("ALL")
@Component
public class RedisUtils {

    private static RedisTemplate redisTemplate;

    public RedisUtils(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 获取 Redis 缓存中的 key
     */
    public static List<String> getKeys(String prefix) {
        Set<String> keys = redisTemplate.keys(prefix.concat("*"));
        return new ArrayList<>(keys);
    }

    /**
     * 向 Redis 中存入基本的对象，Integer、String、实体类等
     */
    public static <T> void setObject(final String key, final T value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 向 Redis 中存入基本的对象，Integer、String、实体类等，并设置有效时长
     *
     * @param timeout  有效时间
     * @param timeUnit 时间颗粒度
     */
    public static <T> void setObject(final String key, final T value, final Integer timeout, final TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    /**
     * 获取 Redis 中缓存的基本对象
     */
    public static <T> T getObject(final String key) {
        ValueOperations<String, T> operation = redisTemplate.opsForValue();
        return operation.get(key);
    }

    /**
     * 批量获取 Redis 中缓存的基本对象
     */
    public static <T> List<T> batchGetObject(final List<String> keys) {
        return redisTemplate.opsForValue().multiGet(keys);
    }

    /**
     * 删除 Redis 中缓存的基本对象
     */
    public static Boolean deleteObject(final String key) {
        return redisTemplate.delete(key);
    }

    /**
     * 批量删除 Redis 中缓存的基本对象
     */
    public static Long batchDeleteObject(final List<String> keys) {
        return redisTemplate.delete(keys);
    }

    /**
     * 向 Redis 中存入 List 集合
     */
    public static <T> long setList(final String key, final List<T> dataList) {
        Long count = redisTemplate.opsForList().rightPushAll(key, dataList);
        return count == null ? 0 : count;
    }

    /**
     * 获取 Redis 中缓存的 List 集合
     */
    public static <T> List<T> getList(final String key) {
        return redisTemplate.opsForList().range(key, 0, -1);
    }

    /**
     * 向 Redis 中存入 Set 集合
     */
    public static <T> BoundSetOperations<String, T> setSet(final String key, final Set<T> dataSet) {
        BoundSetOperations<String, T> setOperation = redisTemplate.boundSetOps(key);
        for (T t : dataSet) {
            setOperation.add(t);
        }
        return setOperation;
    }

    /**
     * 获取 Redis 中缓存的 Set 集合
     */
    public static <T> Set<T> getSet(final String key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 向 Redis 中存入 Map 集合
     */
    public static <T> void setMap(final String key, final Map<String, T> dataMap) {
        if (dataMap != null) {
            redisTemplate.opsForHash().putAll(key, dataMap);
        }
    }

    /**
     * 获取 Redis 中缓存的 Map 集合
     */
    public static <T> Map<String, T> getMap(final String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 删除 Redis 中缓存的 List、Set、Map 集合
     */
    public static Long deleteCollections(final Collection collection) {
        return redisTemplate.delete(collection);
    }

    /**
     * 往 Hash 中存入数据
     */
    public static <T> void setCacheMapValue(final String key, final String hKey, final T value) {
        redisTemplate.opsForHash().put(key, hKey, value);
    }

    /**
     * 获取 Hash 中的数据
     */
    public static <T> T getCacheMapValue(final String key, final String hKey) {
        HashOperations<String, String, T> opsForHash = redisTemplate.opsForHash();
        return opsForHash.get(key, hKey);
    }

    /**
     * 删除Hash中的数据
     */
    public static void delCacheMapValue(final String key, final String hkey) {
        HashOperations hashOperations = redisTemplate.opsForHash();
        hashOperations.delete(key, hkey);
    }

    /**
     * 获取多个Hash中的数据
     *
     * @param key   Redis键
     * @param hKeys Hash键集合
     * @return Hash对象集合
     */
    public static <T> List<T> getMultiCacheMapValue(final String key, final Collection<Object> hKeys) {
        return redisTemplate.opsForHash().multiGet(key, hKeys);
    }

    /**
     * 获得缓存的基本对象列表
     *
     * @param pattern 字符串前缀
     * @return 对象列表
     */
    public static Collection<String> keys(final String pattern) {
        return redisTemplate.keys(pattern);
    }

    /**
     * 设置某个数据的有效时间（秒）
     */
    public static boolean setExpireTime(final String key, final long timeout) {
        return setExpireTime(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * 设置某个数据的有效时间（秒）
     *
     * @param timeout 有效时间
     * @param unit    时间粒度
     */
    public static Boolean setExpireTime(final String key, final long timeout, final TimeUnit unit) {
        return redisTemplate.expire(key, timeout, unit);
    }

    /**
     * key 对应的 value 自增 1
     */
    public static long incrExpire(String key, long seconds) {
        Long count = redisTemplate.opsForValue().increment(key, 1);
        if (count != null && count == 1) {
            redisTemplate.expire(key, seconds, TimeUnit.SECONDS);
        }
        return count;
    }
}
