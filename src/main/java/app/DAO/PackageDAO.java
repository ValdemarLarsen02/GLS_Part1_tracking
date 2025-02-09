package app.DAO;


import app.config.HibernateConfig;
import app.entities.DeliveryStatus;
import app.entities.Package;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import lombok.RequiredArgsConstructor;

import java.util.List;

//Class to perform CRUD operations
@RequiredArgsConstructor
public class PackageDAO {
    private final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();

    // Opret/Gem ny pakke i databasen
    public void save(Package newPackage) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(newPackage);
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


    public Package getPackage(String trackingNumber) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT p FROM Package p WHERE p.trackingNumber = :trackingNumber", Package.class)
                    .setParameter("trackingNumber", trackingNumber)
                    .getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Returner null, hvis pakken ikke findes
        } finally {
            em.close();
        }
    }


    public List<Package> getAllPackages() {
            EntityManager em = emf.createEntityManager();
            try {
                return em.createQuery("SELECT p FROM Package p", Package.class).getResultList();
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                em.close();
            }
    }

    public void updateDeliveryStatus(String trackingNumber, DeliveryStatus newStatus) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            int updatedRows = em.createQuery("UPDATE Package p SET p.deliveryStatus = :newStatus WHERE p.trackingNumber = :trackingNumber")
                    .setParameter("newStatus", newStatus)  // Enum direkte
                    .setParameter("trackingNumber", trackingNumber)
                    .executeUpdate();

            tx.commit();


            if (updatedRows > 0) {
                System.out.println("Pakke med tracking number " + trackingNumber + " opdateret til status: " + newStatus);
            } else {
                System.out.println("Ingen pakke fundet med tracking number: " + trackingNumber);
            }

        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }


    public void removePackage(String trackingNumber) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.remove(getPackage(trackingNumber));
            tx.commit();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            em.close();
        }
    }




}
