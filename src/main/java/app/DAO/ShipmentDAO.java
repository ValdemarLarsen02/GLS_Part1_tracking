package app.DAO;

import app.config.HibernateConfig;
import app.entities.Shipment;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class ShipmentDAO {
    private final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();

    public void save(Shipment shipment) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(shipment);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public Shipment findById(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Shipment.class, id);
        } finally {
            em.close();
        }
    }

    public List<Shipment> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("FROM Shipment", Shipment.class).getResultList();
        } finally {
            em.close();
        }
    }

    public void update(Shipment shipment) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(shipment);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public void delete(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Shipment shipment = em.find(Shipment.class, id);
            if (shipment != null) {
                em.remove(shipment);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}
