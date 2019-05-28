package com.stackroute.keepnote.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.stackroute.keepnote.model.Note;

/*This class will contain the application-context for the application. 
 * Define the following annotations:
 * @Configuration - Annotating a class with the @Configuration indicates that the 
 *                  class can be used by the Spring IoC container as a source of 
 *                  bean definitions
 * @EnableTransactionManagement - Enables Spring's annotation-driven transaction management capability.
 *                  
 * */
@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = { "com.stackroute.keepnote" })
public class ApplicationContextConfig {

	private ApplicationContextConfig() {
		// private constructor to restrict implicit creation
	}

	/*
	 * Define the bean for DataSource. In our application, we are using MySQL as the
	 * dataSource. To create the DataSource bean, we need to know: 1. Driver class
	 * name 2. Database URL 3. UserName 4. Password
	 */

	/*
	 * Use this configuration while submitting solution in hobbes.
	 * dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver")
	 * dataSource.setUrl("jdbc:mysql://" + System.getenv("MYSQL_HOST") + ":3306/" +
	 * System.getenv("MYSQL_DATABASE")
	 * +"?verifyServerCertificate=false&useSSL=false&requireSSL=false")
	 * dataSource.setUsername(System.getenv("MYSQL_USER"))
	 * dataSource.setPassword(System.getenv("MYSQL_PASSWORD"))
	 */
	@Bean(value = "datasource")
	public static DataSource ds() {
		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName("driver class");
		ds.setUrl("sql url");
		ds.setUsername("username");
		ds.setPassword("password");
		return ds;
	}

	/*
	 * Define the bean for SessionFactory. Hibernate SessionFactory is the factory
	 * class through which we get sessions and perform database operations.
	 */
	@Bean
	public static LocalSessionFactoryBean sessionFactoryBean(DataSource dataSource) {
		LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
		sessionFactoryBean.setDataSource(dataSource);
		Properties hibernateProps = new Properties();
		hibernateProps.put("hibernate.show_sql", "true");
		hibernateProps.put("hibernate.hmb2ddl.auto", "update");
		hibernateProps.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
		sessionFactoryBean.setAnnotatedClasses(Note.class);
		sessionFactoryBean.setHibernateProperties(hibernateProps);
		return sessionFactoryBean;
	}

	/*
	 * Define the bean for Transaction Manager. HibernateTransactionManager handles
	 * transaction in Spring. The application that uses single hibernate session
	 * factory for database transaction has good choice to use
	 * HibernateTransactionManager. HibernateTransactionManager can work with plain
	 * JDBC too. HibernateTransactionManager allows bulk update and bulk insert and
	 * ensures data integrity.
	 */
	@Bean
	public static HibernateTransactionManager transMgr(SessionFactory sessionFactory) {
		return new HibernateTransactionManager(sessionFactory);
	}
}
