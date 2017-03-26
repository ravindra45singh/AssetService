package com.infosys.service.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.infosys.service.model.Cities;
import com.infosys.service.model.Countries;

@Transactional
public class AssetDao {

    @Autowired
    private HibernateTemplate hibernateTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger( AssetDao.class );

    @SuppressWarnings( "unchecked" )
    public Countries getCapital( String country ) {
        String queryString = "from Countries c where c.country like ?";
        List<Countries> count = (ArrayList<Countries>) hibernateTemplate.find( queryString, country );
        return count.size() > 0 ? (Countries) count.get( 0 ) : null;
    }

    @SuppressWarnings( "unchecked" )
    public List<Cities> getCities( String country ) {
        Session session = hibernateTemplate.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria( Cities.class, "cit" );

        List<Cities> cities = criteria.createAlias( "cit.countryId", "count" ).add( Restrictions.ilike( "count.country", country ) )
                .list();
        return cities.size() > 0 ? (ArrayList<Cities>) cities : null;

    }

    @SuppressWarnings( "unchecked" )
    public boolean isCountryExist( String country ) {
        String queryString = "from Countries c where c.country like ?";
        List<Countries> count = (ArrayList<Countries>) hibernateTemplate.find( queryString, country );
        LOGGER.info( "0;count  ", count );
        return count.size() > 0 ? true : false;
    }

    public void saveCountry( Countries country ) {
        Session session = hibernateTemplate.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save( country );
            tx.commit();

        } catch ( HibernateException e ) {
            if ( tx != null )
                tx.rollback();
            LOGGER.error( "0;Exception recieved ", e.getMessage() );
        } finally {
            session.close();
        }
        // session.getTransaction().commit();

    }

    public void saveCities( Cities city ) {
        Session session = hibernateTemplate.getSessionFactory().openSession();
        session.save( city );
        // session.getTransaction().commit();

    }
}
