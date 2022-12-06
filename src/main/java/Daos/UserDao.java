package Daos;

import DaoInterfaces.UserDaoInterface;
import shared.model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.jboss.logging.Logger;

import java.util.List;

public class UserDao implements UserDaoInterface {
    private final SessionFactory sessionFactory;

    public UserDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public User create(User user) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        User userToReturn = null;

        try {
            transaction = session.beginTransaction();


            // here get object
            int userId = (int) session.save(user);
            userToReturn = session.get(User.class, userId);
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

        return userToReturn;


    }

    @Override
    public User getById(int userId) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        User userToReturn = null;

        try {
            transaction = session.beginTransaction();


            userToReturn = session.get(User.class, userId);

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
        return userToReturn;
    }

    @Override
    public User getByUsername(String usernamestringarg) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        User userToReturn = null;

        try {
            transaction = session.beginTransaction();


            Query<User> query = session.createQuery("from User u where u.username=:usernamestring", User.class).setParameter("usernamestring", usernamestringarg);
            List<User> resultList = query.getResultList();

            if (!resultList.isEmpty())
                userToReturn = resultList.get(0);

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

        return userToReturn;
    }
}
