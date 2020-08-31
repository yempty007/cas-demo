package com.example.cas.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

/**
 * @Description
 * @Author yempty
 * @Date 2020/8/31 21:25
 * @Version 1.0
 */
@EnableJpaRepositories(basePackages = "com.example.cas.server.repository")
@Configurable
public class JpaConfig {

    @Autowired
    private DataSource dataSource;

    /**
     * 实体类的管理工厂
     *
     * @return
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        // 实例化jpa适配器，由于使用的是hibernate所以用hibernatejpavendor
        HibernateJpaVendorAdapter japVendor = new HibernateJpaVendorAdapter();
        // 不生成sql
        japVendor.setGenerateDdl(false);
        // 实例化管理工厂
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setDataSource(dataSource);
        entityManagerFactory.setJpaVendorAdapter(japVendor);
        // 设置扫描包名
        entityManagerFactory.setPackagesToScan("com.example.cas.server.entity");
        return entityManagerFactory;
    }


    /**
     * 事务管理
     *
     * @param entityManagerFactory
     * @return
     */
    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        // 实例化
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }
}
