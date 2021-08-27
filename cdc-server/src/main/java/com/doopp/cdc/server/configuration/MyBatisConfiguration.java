package com.doopp.youlin.server.configuration;

import com.doopp.youlin.define.IntArrayTypeHandler;
import com.doopp.youlin.define.LongArrayTypeHandler;
import com.doopp.youlin.define.StringArrayTypeHandler;
import com.github.pagehelper.PageInterceptor;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.logging.log4j.Log4jImpl;
import org.apache.ibatis.logging.log4j2.Log4j2Impl;
import org.apache.ibatis.logging.slf4j.Slf4jImpl;
import org.apache.ibatis.session.Configuration;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Properties;

@EnableTransactionManagement
public class MyBatisConfiguration {

    @Bean
    public HikariDataSource hikariDataSource(@Value("${jdbc.user.driver}") String driver,
                                             @Value("${jdbc.user.url}") String url,
                                             @Value("${jdbc.user.username}") String username,
                                             @Value("${jdbc.user.password}") String password,
                                             @Value("${jdbc.minimumIdle}") int minimumIdle,
                                             @Value("${jdbc.maxLifetime}") Long maxLifetime,
                                             @Value("${jdbc.maximumPoolSize}") int maximumPoolSize,
                                             @Value("${jdbc.connectionTestQuery}") String connectionTestQuery,
                                             @Value("${jdbc.dataSource.cachePrepStmts}") boolean cachePrepStmts,
                                             @Value("${jdbc.dataSource.prepStmtCacheSize}") int prepStmtCacheSize,
                                             @Value("${jdbc.dataSource.prepStmtCacheSqlLimit}") int prepStmtCacheSqlLimit,
                                             @Value("${jdbc.dataSource.useServerPrepStmts}") boolean useServerPrepStmts,
                                             @Value("${jdbc.dataSource.useLocalSessionState}") boolean useLocalSessionState,
                                             @Value("${jdbc.dataSource.rewriteBatchedStatements}") boolean rewriteBatchedStatements,
                                             @Value("${jdbc.dataSource.cacheResultSetMetadata}") boolean cacheResultSetMetadata,
                                             @Value("${jdbc.dataSource.cacheServerConfiguration}") boolean cacheServerConfiguration,
                                             @Value("${jdbc.dataSource.elideSetAutoCommits}") boolean elideSetAutoCommits,
                                             @Value("${jdbc.dataSource.maintainTimeStats}") boolean maintainTimeStats) {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(driver);
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);
        config.setMinimumIdle(minimumIdle);
        config.setMaxLifetime(maxLifetime);
        config.setMaximumPoolSize(maximumPoolSize);
        config.setConnectionTestQuery(connectionTestQuery);
        config.setConnectionInitSql("SET NAMES utf8mb4 COLLATE utf8mb4_unicode_ci");

        config.addDataSourceProperty("cachePrepStmts", cachePrepStmts);
        config.addDataSourceProperty("prepStmtCacheSize", prepStmtCacheSize);
        config.addDataSourceProperty("prepStmtCacheSqlLimit", prepStmtCacheSqlLimit);
        config.addDataSourceProperty("useServerPrepStmts", useServerPrepStmts);

        config.addDataSourceProperty("useLocalSessionState", useLocalSessionState);
        config.addDataSourceProperty("rewriteBatchedStatements", rewriteBatchedStatements);
        config.addDataSourceProperty("cacheResultSetMetadata", cacheResultSetMetadata);
        config.addDataSourceProperty("cacheServerConfiguration", cacheServerConfiguration);
        config.addDataSourceProperty("elideSetAutoCommits", elideSetAutoCommits);
        config.addDataSourceProperty("maintainTimeStats", maintainTimeStats);
        return new HikariDataSource(config);
    }

    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean(HikariDataSource hikariDataSource) throws Exception {
        // properties
        Properties properties =  new Properties();
        properties.setProperty("cacheEnabled", "true");
        // resources
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources("classpath:/mybatis-mapper/*.xml");
        // PageInterceptor
        // PageInterceptor pageInterceptor = new PageInterceptor();
        // pageInterceptor.setProperties(new Properties(){{
        //    setProperty("helperDialect", "mysql");
        // }});
        // plugins
        // PageInterceptor[] pageInterceptors = {pageInterceptor};
        // sqlSessionFactoryBean
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(hikariDataSource);
        sqlSessionFactoryBean.setConfigurationProperties(properties);

        Configuration configuration = new Configuration();
        configuration.setMapUnderscoreToCamelCase(true);
        // 输出日志
        configuration.setLogImpl(Slf4jImpl.class);
        // configuration.setLogImpl(Log4j2Impl.class);
        sqlSessionFactoryBean.setConfiguration(configuration);

        sqlSessionFactoryBean.setMapperLocations(resources);
        // sqlSessionFactoryBean.setPlugins(pageInterceptors);
        sqlSessionFactoryBean.setPlugins(new PageInterceptor());
        sqlSessionFactoryBean.setTypeHandlers(
                new StringArrayTypeHandler(),
                new IntArrayTypeHandler(),
                new LongArrayTypeHandler()
        );
        return sqlSessionFactoryBean;
    }

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage("com.doopp.youlin.dao"); // 多个目录用逗号分隔
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactoryBean");
        return mapperScannerConfigurer;
    }

    /**
     * (事务管理)transaction manager, use JtaTransactionManager for global tx
     */
    @Bean
    public DataSourceTransactionManager transactionManager(HikariDataSource hikariDataSource) {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(hikariDataSource);
        return dataSourceTransactionManager;
    }
}
