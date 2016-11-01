package com.infosys.service.config;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.infosys.service.dao.AssetDao;
import com.infosys.service.model.Cities;
import com.infosys.service.model.Countries;

@Configuration
@EnableTransactionManagement
public class SpringHiberConfig {

    @Value( "${db.driver}" )
    private String dbDriver;

    @Value( "${db.url}" )
    private String dbUrl;

    @Value( "${db.username}" )
    private String dbUsername;

    @Value( "${db.password}" )
    private String dbPassword;

    @Value( "${hb.sqlDialect}" )
    private String sqlDialect;

    @Bean
    public AssetDao assetDao() {
        return new AssetDao();
    }

    @Bean
    public HibernateTemplate hibernateTemplate() {
        return new HibernateTemplate( (SessionFactory) sessionFactory() );
    }

    @Bean
    public SessionFactory sessionFactory() {
        return new LocalSessionFactoryBuilder( getDataSource() ).addAnnotatedClasses( Cities.class, Countries.class )
                .addPackages( "com.infosys.service.model" ).buildSessionFactory();
    }

    @Bean
    public DataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName( dbDriver );
        dataSource.setUrl( dbUrl );
        dataSource.setUsername( dbUsername );
        dataSource.setPassword( dbPassword );
        return dataSource;
    }

}
