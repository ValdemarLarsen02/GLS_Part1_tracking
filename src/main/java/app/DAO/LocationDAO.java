package app.DAO;

import app.config.HibernateConfig;
import app.entities.Location;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class LocationDAO {
    private final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();

    public void save(Location location) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(location);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public Location findById(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Location.class, id);
        } finally {
            em.close();
        }
    }

    public List<Location> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("FROM Location", Location.class).getResultList();
        } finally {
            em.close();
        }
    }

    public void update(Location location) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(location);
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
            Location loc = em.find(Location.class, id);
            if (loc != null) {
                em.remove(loc);
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
