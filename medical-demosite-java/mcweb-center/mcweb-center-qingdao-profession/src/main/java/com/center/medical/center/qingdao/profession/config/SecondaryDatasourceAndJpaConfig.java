package com.center.medical.center.qingdao.profession.config;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = SecondaryDatasourceAndJpaConfig.REPOSITORY_PACKAGE,
        entityManagerFactoryRef = "secondaryEntityManagerFactory",
        transactionManagerRef = "secondaryTransactionManager"
)
public class SecondaryDatasourceAndJpaConfig {

    public static final String REPOSITORY_PACKAGE = "com.center.medical.center.qingdao.profession.orrepository";
    public static final String ENTITY_PACKAGE = "com.center.medical.center.qingdao.profession.entity.oracle";

    //--------------数据源配置-------------------

    /**
     * 扫描spring.datasource.secondary开头的配置信息
     *
     * @return 数据源配置信息
     */
    @Bean(name = "secondaryDataSourceProperties")
    @ConfigurationProperties(prefix = "spring.datasource.secondary")
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    /**
     * 获取次数据源对象
     *
     * @param dataSourceProperties 注入名为secondaryDataSourceProperties的bean
     * @return 数据源对象
     */
    @Bean("secondaryDataSource")
    public DataSource dataSource(@Qualifier("secondaryDataSourceProperties") DataSourceProperties dataSourceProperties) {
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }

    /**
     * 扫描spring.jpa.secondary
     *
     * @return jpa配置信息
     */
    @Bean(name = "secondaryJpaProperties")
    @ConfigurationProperties(prefix = "spring.jpa.secondary")
    public JpaProperties jpaProperties() {
        return new JpaProperties();
    }

    /**
     * 获取次库实体管理工厂对象
     *
     * @param secondaryDataSource 注入名为secondaryDataSource的数据源
     * @param jpaProperties       注入名为secondaryJpaProperties的jpa配置信息
     * @param builder             注入EntityManagerFactoryBuilder
     * @return 实体管理工厂对象
     */
    @Bean(name = "secondaryEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            @Qualifier("secondaryDataSource") DataSource secondaryDataSource,
            @Qualifier("secondaryJpaProperties") JpaProperties jpaProperties,
            EntityManagerFactoryBuilder builder
    ) {
        return builder
                // 设置数据源
                .dataSource(secondaryDataSource)
                // 设置jpa配置
                .properties(jpaProperties.getProperties())
                // 设置实体包名
                .packages(ENTITY_PACKAGE)
                // 设置持久化单元名，用于@PersistenceContext注解获取EntityManager时指定数据源
                .persistenceUnit("secondaryPersistenceUnit").build();
    }

    /**
     * 获取实体管理对象
     *
     * @param factory 注入名为secondaryEntityManagerFactory的bean
     * @return 实体管理对象
     */
    @Bean(name = "secondaryEntityManager")
    public EntityManager entityManager(@Qualifier("secondaryEntityManagerFactory") EntityManagerFactory factory) {
        return factory.createEntityManager();
    }

    /**
     * 获取事务管理对象
     *
     * @param factory 注入名为secondaryEntityManagerFactory的bean
     * @return 事务管理对象
     */
    @Bean(name = "secondaryTransactionManager")
    public JpaTransactionManager transactionManager(@Qualifier("secondaryEntityManagerFactory") EntityManagerFactory factory) {
        return new JpaTransactionManager(factory);
    }
}

