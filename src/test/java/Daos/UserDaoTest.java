package Daos;

import com.google.type.DateTime;
import shared.model.Event;
import shared.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.*;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;


public class UserDaoTest {

    private static SessionFactory sessionFactory;
    private static EventDao eventDao;

    private static UserDao userDao;

    private static Session session;


    private Event createOneEvent(){
        //Arrange

        User user = createOneUser();
        Event eventToCreate = new Event(
                user,
                "TestTitle",
                "TestDescription",
                "TestLocation",
                DateTime.newBuilder().setDay(1).setMonth(1).setYear(2023).setHours(12).build(),
                "Category",
                "Area",
                new ArrayList<>());

        session.beginTransaction();
        session.save(eventToCreate);
        session.getTransaction().commit();

        return eventToCreate;
    }

    private User createOneUser(){
        //Arrange
        User user = new User(
                "Username1",
                "password1",
                "email1@email.dk",
                "User"
        );
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();

        return user;
    }


    @BeforeAll
    public static void setUpDummyDatabase()
    {
        Configuration configuration = new org.hibernate.cfg.Configuration();
        configuration.configure("hibernate.cfg.xml");

        configuration.addAnnotatedClass(Event.class);
        configuration.addAnnotatedClass(User.class);

        configuration.setProperty("hibernate.default_schema", "test");

        //Session factory - Creates temporary connections to the database (aka sessions)


        sessionFactory = configuration.buildSessionFactory();
        session = sessionFactory.openSession();

        eventDao = new EventDao(sessionFactory);
        userDao = new UserDao(sessionFactory);
    }


    @Test
    public void testCreate()
    {
        //Arrnge
        User user = createOneUser();

        //Act
        User createdUser = userDao.create(user);

        //Assert
        assertSame(user, createdUser);
    }

    @Test
    public void testGetById()
    {
        //Arrange
        User user = createOneUser();

        //Act
        User created = userDao.getById(user.getId());

        //Assert
        assertTrue(created.getId() == user.getId());

    }

    @Test
    public void getByUsername()
    {
        //Arrange
        User user = createOneUser();
        //Act
        User getByUsername = userDao.getByUsername(user.getUsername());

        //Assert
        assertEquals(getByUsername.getUsername(), user.getUsername());
    }

}
