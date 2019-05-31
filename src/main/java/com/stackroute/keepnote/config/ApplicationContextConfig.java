package com.stackroute.keepnote.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
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
public class ApplicationContextConfig {
	
	public ApplicationContextConfig() {
		// default constructor created for PMD
	}
	
	@Autowired
	private Environment env;

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
	@Bean
	public DataSource dataSource() {
		final BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName("com.mysql.cj.jdbc.Driver");

		// commented out localhost configuration of MYSQL for hobbes evaluation
		//ds.setUrl("jdbc:mysql://localhost:3306/keepnoteapp?verifyServerCertificate=false&useSSL=false&requireSSL=false")
		//ds.setUsername("root")
		//ds.setPassword("root")

		// comment out below block for localhost execution
		ds.setUrl("jdbc:mysql://" + System.getenv("MYSQL_HOST") + ":3306/" + System.getenv("MYSQL_DATABASE")
				+ "?verifyServerCertificate=false&useSSL=false&requireSSL=false");
		ds.setUsername(env.getProperty("MYSQL_USER"));
		ds.setPassword(env.getProperty("MYSQL_PASSWORD"));
		return ds;
	}

	/*
	 * Define the bean for SessionFactory. Hibernate SessionFactory is the factory
	 * class through which we get sessions and perform database operations.
	 */
	@Bean
	public LocalSessionFactoryBean sessionFactory(final DataSource dataSource) {
		final LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
		sessionFactoryBean.setDataSource(dataSource);
		final Properties hibernateProps = new Properties();
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
	public HibernateTransactionManager transactionManager(final SessionFactory sessionFactory) {
		return new HibernateTransactionManager(sessionFactory);
	}
}
