package com.cisco.acisizer.util; 

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

public class GenericIdGenerator implements IdentifierGenerator {

   

    public Serializable generate(SessionImplementor session, Object object)
            throws HibernateException {

        
        Connection connection = session.connection();
        try {

            PreparedStatement ps = connection
                    .prepareStatement("SELECT nextval ('aci_project_id_seq') as nextval");

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id = 100+rs.getInt("nextval");
               // String code = 100+ StringUtils.leftPad("" + id,3, '0');
                //log.debug("Generated Stock Code: " + code);
                return id;
            }

        } catch (SQLException e) {
           // log.error(e);
            throw new HibernateException(
                    "Unable to generate Stock Code Sequence");
        }
        return null;
    }
}