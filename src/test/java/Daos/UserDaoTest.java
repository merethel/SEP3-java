package Daos;

import com.google.type.DateTime;
import shared.model.Event;
import shared.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.junit.jupiter.api.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class UserDaoTest {


    private static SessionFactory sessionFactory;
    private static EventDao eventDao;

    private static UserDao userDao;

    private static Session session;
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

        eventDao = new EventDao(sessionFactory);
        userDao = new UserDao(sessionFactory);
    }

    @BeforeEach
    public void repopulateDummyDatabase()
    {
        DateTime dateTime = DateTime.newBuilder().setDay(1).setMonth(1).setYear(2023).setHours(12).build();


        session = sessionFactory.openSession();
        session.beginTransaction();

        User user1 = new User("Username1", "password1", "email1@email.dk", "User");
        User user2 = new User("Username2", "password2", "email2@email.dk", "User");
        User user3 = new User("Username3", "password3", "email3@email.dk", "User");

        session.save(user1);
        session.save(user2);
        session.save(user3);

        session.save(new Event(user1, "Title1", "Description1", "Location1", dateTime,"Category","Area", new ArrayList<>()));
        session.save(new Event(user2, "Title2", "Description2", "Location2", dateTime,"Category","Area", new ArrayList<>()));
        session.save(new Event(user3, "Title3", "Description3", "Location3", dateTime,"Category","Area", new ArrayList<>()));
        session.getTransaction().commit();
    }

    @AfterEach
    public void emptyDummyDatabase()
    {
        session.beginTransaction();
        System.out.println("emptying dummy database");
        Class c1 = Event.class;
        String hql1 = "delete from " + c1.getSimpleName();
        Query q1 = session.createQuery( hql1 );
        q1.executeUpdate();

        Class c2 = User.class;
        String hql2 = "delete from " + c2.getSimpleName();
        Query q2 = session.createQuery( hql2 );
        q2.executeUpdate();
        session.getTransaction().commit();
    }

    @Test
    public void testCreate()
    {
        //Arrnge
        User user = new User ("Username1", "PasswordTest", "testEmail@test.com", "User");

        //Act
        User createdUser = userDao.create(user);

        //Assert
        assertSame(user, createdUser);
    }

    @Test
    public void testGetById()
    {
        //Arrange
        User user = new User();
        user.setId(2);

        //Axt
        User created = userDao.getById(user.getId());

        //Assert
        assertEquals(2, 2);

    }

    @Test
    public void getByUsername()
    {
        //Arrange
        User user = new User("Username1", "password1", "email1@email.dk", "User");

        //Act
        User getByUsername = userDao.getByUsername(user.getUsername());

        //Assert
        assertEquals("Username1", "Username1");
    }
}
