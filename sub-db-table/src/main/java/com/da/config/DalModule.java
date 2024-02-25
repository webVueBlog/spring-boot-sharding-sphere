package com.da.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.shardingsphere.api.config.sharding.KeyGeneratorConfiguration;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.InlineShardingStrategyConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.StandardShardingStrategyConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @Description: 连接数据库信息
 * Configuration 是Spring的配置类，用于配置数据源、事务管理器等。
 * ComponentScan 用于扫描指定包下的所有类，并将其注册为Spring的Bean。
 * MapperScan 用于扫描指定包下的所有Mapper接口，并将其注册为Spring的Bean。
 */
@Configuration
@ComponentScan(basePackageClasses = DalModule.class)
@MapperScan(basePackages = "com.da.mapper")
public class DalModule {

    /**
     * SqlSessionFactory 实体
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {// 创建 SqlSessionFactory 对象
        // PathMatchingResourcePatternResolver 是Spring的资源匹配器，用于加载Mapper XML文件。
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();// 创建 PathMatchingResourcePatternResolver 对象
        // SqlSessionFactoryBean 是MyBatis的SqlSessionFactoryBean，用于创建SqlSessionFactory。
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean(); // 创建 SqlSessionFactoryBean 对象
        sessionFactory.setDataSource(dataSource());// 设置数据源
        sessionFactory.setFailFast(true);// 设置是否快速失败
        sessionFactory.setMapperLocations(resolver.getResources("classpath:/mapper/*Mapper.xml"));// 设置Mapper XML文件的位置
        return sessionFactory.getObject();// 返回 SqlSessionFactory 对象
    }

    /**
     *  DataSource 实体
     * @return
     * @throws SQLException
     */
    @Bean
    public DataSource dataSource() throws SQLException {// 创建 DataSource 对象
        // ShardingRuleConfiguration 是MyBatis Plus的ShardingRuleConfiguration，用于配置分库分表规则。
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();// 创建 ShardingRuleConfiguration 对象
        // getTableRuleConfigs 是ShardingRuleConfiguration的方法，用于获取分表规则列表。
        shardingRuleConfig.getTableRuleConfigs().add(getOrderTableRuleConfiguration());// 添加分表规则
        // getBindingTableGroups 是ShardingRuleConfiguration的方法，用于获取绑定表规则列表。
        shardingRuleConfig.getBindingTableGroups().add("tab_user");// 添加绑定表规则
        // getBroadcastTables 是ShardingRuleConfiguration的方法，用于获取广播表规则列表。
        shardingRuleConfig.getBroadcastTables().add("t_config");// 添加广播表规则
        //TODO 根据年龄分库 一共分为2个库
        // setDefaultDatabaseShardingStrategyConfig 是ShardingRuleConfiguration的方法，用于设置默认的数据库分片策略配置。
        shardingRuleConfig.setDefaultDatabaseShardingStrategyConfig(new InlineShardingStrategyConfiguration("age", "ds${age % 2}"));
        //TODO 根据ID分表  一共分为2张表
        // setDefaultTableShardingStrategyConfig 是ShardingRuleConfiguration的方法，用于设置默认的表分片策略配置。
        shardingRuleConfig.setDefaultTableShardingStrategyConfig(new StandardShardingStrategyConfiguration("id", new PreciseModuloShardingTableAlgorithm()));
        // createDataSourceMap() 是用于创建数据源映射的方法。
        // shardingRuleConfig 是ShardingRuleConfiguration对象，用于配置分片规则。
        // new Properties() 是用于创建属性对象的构造函数。
        return ShardingDataSourceFactory.createDataSource(createDataSourceMap(), shardingRuleConfig, new Properties());// 创建数据源
    }

    /**
     *  创建数据源映射
     * @return
     */
    private static KeyGeneratorConfiguration getKeyGeneratorConfiguration() {// 创建主键生成器配置
        // new KeyGeneratorConfiguration() 是用于创建KeyGeneratorConfiguration对象的构造函数
        // "SNOWFLAKE" 是用于指定主键生成器类型。
        // "id" 是用于指定主键生成器参数。
        KeyGeneratorConfiguration result = new KeyGeneratorConfiguration("SNOWFLAKE", "id");
        return result;
    }

    /**
     * 创建默认的数据库分片策略配置
     * @return
     */
    TableRuleConfiguration getOrderTableRuleConfiguration() {// 创建表规则配置
        // new TableRuleConfiguration() 是用于创建TableRuleConfiguration对象的构造函数
        // "tab_user" 是用于指定逻辑表名称。
        // "ds${0..1}.tab_user${0..1}" 是用于指定数据源名称和表名的模板
        // getKeyGeneratorConfiguration() 是用于获取主键生成器配置的方法。
        TableRuleConfiguration result = new TableRuleConfiguration("tab_user", "ds${0..1}.tab_user${0..1}");
        result.setKeyGeneratorConfig(getKeyGeneratorConfiguration());
        return result;
    }


    Map<String, DataSource> createDataSourceMap() {// 创建数据源映射
        // new HashMap<>() 是用于创建HashMap<String, DataSource>对象的构造函数
        // DataSourceUtil.createDataSource("ds0") 是用于创建数据源的方法。
        // DataSourceUtil.createDataSource("ds1") 是用于创建数据源的方法。
        // "ds0" 和 "ds1" 是用于指定数据源名称的方法。
        Map<String, DataSource> result = new HashMap<>();
        result.put("ds0", DataSourceUtil.createDataSource("ds0"));
        result.put("ds1", DataSourceUtil.createDataSource("ds1"));
        return result;
    }

}
