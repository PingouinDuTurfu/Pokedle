package fr.project.pokedle;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtils {

//    public static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("persistence");
//    public static EntityManager entityManager = entityManagerFactory.createEntityManager();

    public static StandardServiceRegistry standardRegistry;
    public static Metadata metadata;
    public static SessionFactory sessionFactory;

    static {
        try {
//            Configuration configuration = new Configuration();
//            configuration.configure();
//            ServiceRegistry registry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
//            SessionFactory sessionFactory = configuration.buildSessionFactory(registry);

            standardRegistry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
            metadata = new MetadataSources(standardRegistry).getMetadataBuilder().build();
            sessionFactory = metadata.getSessionFactoryBuilder().build();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

//    public static EntityManager getEntityManager() {
//        try {
//            return entityManager;
//        }
//        catch (Exception e ){
//            e.printStackTrace();
//        }
//        return null;
//    }
}