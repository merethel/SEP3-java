package Daos;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import DaoInterfaces.UserDaoInterface;
import domain.Event;
import domain.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.criteria.JpaPredicate;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

public class UserDao implements UserDaoInterface {
    private final SessionFactory sessionFactory;

    public UserDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public User create(User user) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        User userToReturn = (User) session.save(user);
        session.getTransaction().commit();
        return userToReturn;
    }

    @Override
    public User getById(int userId) {
        Session session = sessionFactory.openSession();
        User userToReturn = session.get(User.class, userId);
        return userToReturn;
    }

    @Override
    public User getByUsername(String usernamestring) {
        Session session = sessionFactory.openSession();
        User userToReturn = (User) session.createQuery("from User user where user.username =: usernamestring");
        return userToReturn;
    }
}
