///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package db;
//
//import net.sf.jasperreports.engine.JRException;
//import net.sf.jasperreports.engine.JasperFillManager;
//import net.sf.jasperreports.engine.JasperReport;
//import org.hibernate.Session;
//import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
//import org.hibernate.SessionFactory;
//import org.hibernate.cfg.Configuration;
//import org.hibernate.cfg.DefaultNamingStrategy;
//import org.hibernate.engine.spi.SessionFactoryImplementor;
//import org.hibernate.engine.spi.SessionImplementor;
//import org.hibernate.internal.SessionImpl;
//import org.hibernate.jdbc.Work;
//import org.hibernate.service.ServiceRegistry;
//
//import javax.persistence.EntityManager;
//import javax.persistence.EntityManagerFactory;
//import javax.persistence.Persistence;
//import javax.persistence.PersistenceContext;
//import java.sql.Connection;
//import java.sql.SQLException;
//import java.util.Map;
//
///**
// * Hibernate Utility class with a convenient method to get Session Factory
// * object.
// *
// * @author kepyes_4a_6
// */
//public class HibernateUtil {
//
//
//    private static SessionFactory sessionFactory;
//    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("katataxi_ken_db");
//    private static EntityManager entityManager;
//
//    public static EntityManager getEntityManager() {
//        EntityManagerFactory emf = Persistence.createEntityManagerFactory("katataxi_ken_db");
//        if (entityManager == null) {
//
//            entityManager = emf.createEntityManager();
//        }
//        return entityManager;
//    }
//
//    public static SessionFactory getSessionFactory() {
//        if (sessionFactory == null) {
//            // loads configuration and mappings
////            Configuration configuration = new Configuration().configure("/hibernate.cfg.xml");
//
//            sessionFactory = emf.unwrap(SessionFactory.class);
//        }
//
//        return sessionFactory;
//
//    }
//
//    public static Connection getConnection() throws SQLException {
//        SessionImplementor sessionImplementor;
//        sessionImplementor = (SessionImplementor) sessionFactory.openSession();
//        return sessionImplementor.getJdbcConnectionAccess().obtainConnection();
//    }
//}
//
