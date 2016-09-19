package com.zz.core.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration
//@EnableCaching // <!-- 启用缓存注解 --> <cache:annotation-driven cache-manager="cacheManager" />
@EnableJpaRepositories(basePackages = "com.zz.*.repository")
@ImportResource("classpath:transaction-config.xml")
public class SpringConfiguration {

	@Resource
	private DataSource dataSource;
	
	@Value("${databasePlatform}")
	private String databasePlatform;

	@Value("${hibernate.show_sql}")
	private String hibernateShowSql;

	@Value("${hibernate.format_sql}")
	private String hibernateFormatSql;

	@Value("${hibernate.hbm2ddl.auto}")
	private String hibernateHbm2ddlAuto;
	
	@Bean(name = "hibernateJpaVendorAdapter")
	public HibernateJpaVendorAdapter hibernateJpaVendorAdapter() {
		HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
		hibernateJpaVendorAdapter.setDatabasePlatform(databasePlatform);
		return hibernateJpaVendorAdapter;
	}

	@Bean(name = "entityManagerFactory")
	public LocalContainerEntityManagerFactoryBean localEntityManagerFactory() {
		LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactory.setDataSource(dataSource);
		entityManagerFactory.setJpaVendorAdapter(hibernateJpaVendorAdapter());
		String[] packagesToScan = new String[] { "com.zz.*.entity" };
		entityManagerFactory.setPackagesToScan(packagesToScan);
		Properties jpaProperties = new Properties();
		jpaProperties.setProperty("hibernate.ejb.naming_strategy", "org.hibernate.cfg.ImprovedNamingStrategy");
		jpaProperties.setProperty("hibernate.show_sql", hibernateShowSql);
		jpaProperties.setProperty("hibernate.format_sql", hibernateFormatSql);
		jpaProperties.setProperty("hibernate.hbm2ddl.auto", hibernateHbm2ddlAuto);
		entityManagerFactory.setJpaProperties(jpaProperties);
		return entityManagerFactory;
	}
	
	@Bean(name = "transactionManager")
	public JpaTransactionManager transactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		Map<String, Object> jpaProperties = new HashMap<String, Object>();
		jpaProperties.put("entityManagerFactory", localEntityManagerFactory());
		transactionManager.setJpaPropertyMap(jpaProperties);
//		transactionManager.setEntityManagerFactory();
		return transactionManager;
	}
}
