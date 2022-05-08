package per.magnus.dust.components.repos.mysql;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.logging.log4j2.Log4j2Impl;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.transaction.SpringManagedTransactionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@SuppressWarnings("unused")
@MapperScan("per.magnus.dust.business.mapper")
@PropertySource("classpath:/properties/datasource.properties")
public class DustDataSourceConfiguration {

    @Value("${datasource.url}")
    public String url;
    @Value("${datasource.username}")
    public String user;
    @Value("${datasource.password}")
    public String password;
    @Value("${druid.max-idle}")
    public String maxIdle;
    @Value("${druid.max-active}")
    public String maxActive;
    @Value("${druid.initial-size}")
    public String initialSize;

    @Bean
    public DataSource dataSource() {
        Properties properties = new Properties();
        properties.put("druid.url", url);
        properties.put("druid.username", user);
        properties.put("druid.password", password);
        properties.put("druid.maxIdle", maxIdle);
        properties.put("druid.maxActive", maxActive);
        properties.put("druid.initialSize", initialSize);
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.configFromPropety(properties);
        return druidDataSource;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setLogImpl(Log4j2Impl.class);
        configuration.setCacheEnabled(true);
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource());
        sqlSessionFactoryBean.setConfiguration(configuration);
        PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver();
        Resource[] resource = pathMatchingResourcePatternResolver.getResources("classpath:/mapper/*.xml");
        sqlSessionFactoryBean.setMapperLocations(resource);
        sqlSessionFactoryBean.setTransactionFactory(transactionFactory());
        return sqlSessionFactoryBean.getObject();
    }

    @Bean
    @Primary
    public SqlSession sqlSession() throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory(), ExecutorType.SIMPLE);
    }

    @Bean
    @Qualifier("batch")
    public SqlSession batchSqlSession() throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory(), ExecutorType.BATCH);
    }

    @Bean
    public SpringManagedTransactionFactory transactionFactory() {
        return new SpringManagedTransactionFactory();
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource());
        dataSourceTransactionManager.setDefaultTimeout(10000);
        return dataSourceTransactionManager;
    }

    @Bean
    public TransactionTemplate transactionTemplate() {
        TransactionTemplate transactionTemplate = new TransactionTemplate();
        transactionTemplate.setTransactionManager(transactionManager());
        return transactionTemplate;
    }
}
