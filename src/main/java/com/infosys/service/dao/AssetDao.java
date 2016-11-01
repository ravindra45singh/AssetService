package com.infosys.service.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.infosys.service.model.Cities;
import com.infosys.service.model.Countries;

@Transactional
public class AssetDao {

    @Autowired
    private HibernateTemplate hibernateTemplate;

    @SuppressWarnings( "unchecked" )
    public Countries getCapital( String country ) {
        String queryString = "from Countries c where c.country = ?";
        List<Countries> count = (ArrayList<Countries>) hibernateTemplate.find( queryString, country );
        return count.size() > 0 ? (Countries) count.get( 0 ) : null;
    }

    @SuppressWarnings( "unchecked" )
    public List<Cities> getCities( String country ) {
        Session session = hibernateTemplate.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria( Cities.class, "cit" );

        List<Cities> cities = criteria.createAlias( "cit.countryId", "count" ).add( Restrictions.eq( "count.country", country ) )
                .list();
        return cities.size() > 0 ? (ArrayList<Cities>) cities : null;

    }

}
