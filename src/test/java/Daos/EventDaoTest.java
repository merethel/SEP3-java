package Daos;

import com.google.type.DateTime;
import domain.Event;
import domain.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.junit.jupiter.api.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertSame;


public class EventDaoTest {

     private static SessionFactory sessionFactory;
     private static EventDao eventDao;

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

          session.save(new Event(user1, "Title1", "Description1", "Location1", dateTime, new ArrayList<>()));
          session.save(new Event(user2, "Title2", "Description2", "Location2", dateTime, new ArrayList<>()));
          session.save(new Event(user3, "Title3", "Description3", "Location3", dateTime, new ArrayList<>()));
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
          //Arrange happens in before all and before each.
          User user = new User();
          user.setUsername("Username1");
          Event eventToCreate = new Event(user, "TestTitle", "TestDescription", "TestLocation", DateTime.newBuilder().setDay(1).setMonth(1).setYear(2023).setHours(12).build(), new ArrayList<>());

          //Act
          Event created = eventDao.create(eventToCreate);

          //Assert
          assertSame(eventToCreate, created);

     }

     @Test
     public void testGetById()
     {
          //Arrange happens in before all and before each.
          User user = new User();
          user.setUsername("Username1");
          Event eventToCreate = new Event(user, "TestTitle", "TestDescription", "TestLocation", DateTime.newBuilder().setDay(1).setMonth(1).setYear(2023).setHours(12).build(), new ArrayList<>());
          int id = (int) session.save(eventToCreate);

          //Act
          Event event = eventDao.getById(id);

          //Assert
          assertSame(event.getId(), id);
     }



}
