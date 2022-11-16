package Daos;

import com.google.type.DateTime;
import domain.Event;
import domain.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

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

          session.save(new User("Username1", "password1", "email1@email.dk", 1));
          session.save(new User("Username2", "password2", "email2@email.dk", 1));
          session.save(new User("Username3", "password3", "email3@email.dk", 1));

          session.save(new Event("Title1", "Description1", "Location1", dateTime));
          session.save(new Event("Title2", "Description2", "Location2", dateTime));
          session.save(new Event("Title3", "Description3", "Location3", dateTime));
          session.getTransaction().commit();
     }

     @AfterEach
     public void emptyDummyDatabase()
     {
          session.createQuery("delete from event");
          session.createQuery("delete from usertable");
          session.close();
     }

     @Test
     public void testCreate()
     {
          //Arrange happens in before all and before each.
          User user = new User();
          user.setUsername("Username1");
          Event eventToCreate = new Event(user, "TestTitle", "TestDescription", "TestLocation", DateTime.newBuilder().setDay(1).setMonth(1).setYear(2023).setHours(12).build());


          //Act
          Event created = eventDao.create(eventToCreate);

          //Assert
          assertSame(eventToCreate, created);

     }
}
