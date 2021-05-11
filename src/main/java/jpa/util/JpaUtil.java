package jpa.util;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
public class JpaUtil {
    private static EntityManagerFactory factory;
    private static final String PERSISTENCE_UNIT_NAME = "SMS";
    public static EntityManagerFactory getEntityManagerFactory(){
        if(factory == null)
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        return factory;
    }

    public static void shutdown(){
        if(factory != null)
            factory.close();
    }
}
