package Daos;

import DaoInterfaces.EventDaoInterface;
import domain.Event;
import domain.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.jboss.logging.Logger;

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


            //QUICKFIX
            //Her laver jeg egentlig UserDaos arbejde men jeg ved ikke hvordan jeg ellers lige gør det :/
            Query<User> query = session.createQuery("from User u where u.username=:usernamestring", User.class).setParameter("usernamestring", event.getOwner().getUsername());
            event.setOwner(query.getResultList().get(0));


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


}
