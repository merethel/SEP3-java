package Daos;

import DaoInterfaces.EventDaoInterface;
import domain.Event;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;
import java.util.logging.Logger;

public class EventDao implements EventDaoInterface {
    private final SessionFactory sessionFactory;


    public EventDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Event create(Event event) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        Event eventToReturn = null;

        try {
            transaction = session.beginTransaction();

            // here get object
            int eventId = (int) session.save(event);
            eventToReturn = session.get(Event.class, eventId);
            transaction.commit();

        } catch (HibernateException ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            Logger.getLogger("con").info("Exception: " + ex.getMessage());
            ex.printStackTrace(System.err);
        } finally {
            session.close();
        }

        return eventToReturn;
    }

    @Override
    public Event getById(int eventId) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        Event eventToReturn = null;

        try {
            transaction = session.beginTransaction();
            eventToReturn = session.get(Event.class, eventId);
            transaction.commit();

        } catch (HibernateException ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            Logger.getLogger("con").info("Exception: " + ex.getMessage());
            ex.printStackTrace(System.err);
        } finally {
            session.close();
        }

        return eventToReturn;
    }

    @Override
    public List<Event> getAllEvents() {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        List<Event> eventToReturn = null;

        try {
            transaction = session.beginTransaction();

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Event> criteria = builder.createQuery(Event.class);
            criteria.from(Event.class);
            eventToReturn = session.createQuery(criteria).getResultList();

            transaction.commit();

        } catch (HibernateException ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            Logger.getLogger("con").info("Exception: " + ex.getMessage());
            ex.printStackTrace(System.err);
        } finally {
            session.close();
        }

        return eventToReturn;
    }
}
