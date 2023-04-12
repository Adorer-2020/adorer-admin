package space.adorer.admin.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Mybatis 配置类
 *
 * @author adorer
 * @version 1.0
 * @since 2023/4/11 11:33:17
 */
@Configuration
@EnableTransactionManagement
@MapperScan("space.adorer.admin.mapper")
public class MybatisConfig {
}

