package Daos;

import DaoInterfaces.EventDaoInterface;
import domain.Event;
import domain.User;
import event.CriteriaDtoMessage;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.jboss.logging.Logger;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

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
    public List<Event> getAllEvents(CriteriaDtoMessage criteriaDto) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        List<Event> eventToReturn = null;

        try {
            transaction = session.beginTransaction();

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Event> criteria = builder.createQuery(Event.class);
            Root<Event> eventRoot = criteria.from(Event.class);


            if(criteriaDto.getOwnerId() != 0)
                criteria.where(builder.equal(eventRoot.get("user_id"), criteriaDto.getOwnerId()));
            if(!criteriaDto.getArea().equals(""))
                criteria.where(builder.equal(eventRoot.get("area"), criteriaDto.getArea()));
            if(!criteriaDto.getCategory().equals(""))
                criteria.where(builder.equal(eventRoot.get("category"), criteriaDto.getCategory()));


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

    @Override
    public Event addAttendeeToEventAttendeeList(int userId, int eventId) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        Event eventToReturn = null;
        User user = null;

        try {
            transaction = session.beginTransaction();
            user = session.get(User.class, userId);
            eventToReturn = session.get(Event.class, eventId);

            eventToReturn.getAttendees().add(user);
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
    public Event cancel(int eventId) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        Event eventToCancel = null;

        try {
            transaction = session.beginTransaction();
            eventToCancel = session.get(Event.class, eventId);
            session.delete(eventToCancel);
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

        return eventToCancel;


    }
}
