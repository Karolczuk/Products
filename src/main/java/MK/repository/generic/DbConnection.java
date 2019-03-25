package MK.repository.generic;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class DbConnection {

    private static DbConnection ourInstance = new DbConnection();
    public static DbConnection getInstance() {
        return ourInstance;
    }


    EntityManagerFactory emf = Persistence.createEntityManagerFactory("MANAGEMENT_PRODUCTS");


    private DbConnection() {
    }

    public EntityManagerFactory getSessionFactory() {
        return emf;
    }

    public void close() {
        if (emf != null) {
            //sessionFactory.close();
            emf.close();
        }
    }
}
