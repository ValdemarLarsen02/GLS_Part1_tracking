package app.DAO;

import app.config.HibernateConfig;
import app.entities.DeliveryStatus;
import app.entities.Package;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import java.util.List;

public class PackageDAO {
    private final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();

    // Opret/Gem ny pakke i databasen
    public void save(Package newPackage) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(newPackage);  // JPA persist-metode
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    // Hent en pakke via trackingNumber
    public Package getPackage(String trackingNumber) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT p FROM Package p WHERE p.trackingNumber = :trackingNumber", Package.class)
                    .setParameter("trackingNumber", trackingNumber)
                    .getResultStream()
                    .findFirst()
                    .orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Returner null, hvis pakken ikke findes
        } finally {
            em.close();
        }
    }



    // Hent alle pakker
    public List<Package> getAllPackages() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("FROM Package", Package.class).getResultList();
        } finally {
            em.close();
        }
    }

    // Opdater en pakkes leveringsstatus
    public void updateDeliveryStatus(String trackingNumber, DeliveryStatus newStatus) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Package pkg = em.createQuery("SELECT p FROM Package p WHERE p.trackingNumber = :trackingNumber", Package.class)
                    .setParameter("trackingNumber", trackingNumber)
                    .getResultStream()
                    .findFirst()
                    .orElse(null);

            if (pkg != null) {
                pkg.setDeliveryStatus(newStatus);
                em.merge(pkg);
                tx.commit();
                System.out.println("Pakke opdateret: " + pkg);
            } else {
                System.out.println("Ingen pakke fundet med tracking number: " + trackingNumber);
            }
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }


    // Fjern en pakke fra databasen
    public void removePackage(String trackingNumber) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Package pkg = em.find(Package.class, trackingNumber);
            if (pkg != null) {
                em.remove(pkg);  // JPA remove-metode
                tx.commit();
                System.out.println("Pakke med tracking number " + trackingNumber + " er blevet slettet.");
            } else {
                System.out.println("Ingen pakke fundet med tracking number: " + trackingNumber);
            }
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException(e);
        } finally {
            em.close();
        }
    }
}
