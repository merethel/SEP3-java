package Daos;

import com.google.type.DateTime;
import event.CriteriaDtoMessage;
import shared.model.Event;
import shared.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class EventDaoTest {

     private static SessionFactory sessionFactory;
     private static EventDao eventDao;

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

          eventDao = new EventDao(sessionFactory);
     }


     public void createManyEvents()
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

          session.save(new Event(user1, "Title1", "Description1", "Location1", dateTime,"Category1","Area1", new ArrayList<>()));
          session.save(new Event(user2, "Title2", "Description2", "Location2", dateTime, "Category2","Area2", new ArrayList<>()));
          session.save(new Event(user3, "Title3", "Description3", "Location3", dateTime, "Category3","Area3", new ArrayList<>()));
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
          Event eventToCreate = new Event(user, "TestTitle", "TestDescription", "TestLocation", DateTime.newBuilder().setDay(1).setMonth(1).setYear(2023).setHours(12).build(),"Category","Area", new ArrayList<>());
          session.beginTransaction();
          session.save(user);
          session.getTransaction().commit();

          //Act
          Event created = eventDao.create(eventToCreate);

          //Assert
          assertSame(eventToCreate, created);

     }


     @Test
     public void testAttendeeAddedToEventList()
     {
          //Arrange
          Event eventToCreate = createOneEvent();
          User user = createOneUser();

          //Act
          Event event = eventDao.addAttendeeToEventAttendeeList(user.getId(), eventToCreate.getId());

          //Assert
          assertTrue(event.getAttendees().get(0).getId() == user.getId());
     }

     @Test
     public void testGetAllEventsWithNoCriteria(){

          //Arrange
          createManyEvents();


          //Act
          List<Event> eventsWithNoCriteria = eventDao.getAllEvents(CriteriaDtoMessage.newBuilder().build());

          //Assert
          assertTrue(!eventsWithNoCriteria.isEmpty());
     }

     @Test
     public void testGetAllEventsWithCategoryCriteria(){

          //Arrange
          createManyEvents();


          //Act
          List<Event> eventsWithCategoryCriteria = eventDao.getAllEvents(CriteriaDtoMessage.newBuilder().setCategory("Category1").build());

          //Assert
          if (eventsWithCategoryCriteria.isEmpty()) { /*Vi tjekker om listen er tom først, for ellers giver
                              giver det ikke mening at assert*/
               assertFalse(true);
          }
          else {
               for (Event e : eventsWithCategoryCriteria) {
                    assertTrue(e.getCategory().equals("Category1"));
               }
          }
     }

     @Test
     public void testGetAllEventsWithWrongCategoryCriteriaIsFalse(){

          //Arrange
          createManyEvents();


          //Act
          List<Event> eventsWithCategoryCriteria = eventDao.getAllEvents(CriteriaDtoMessage.newBuilder().setCategory("Category1").build());

          //Assert
          if (eventsWithCategoryCriteria.isEmpty()) { /*Vi tjekker om listen er tom først, for ellers giver
                              giver det ikke mening at assert*/
               assertFalse(true);
          }
          else {
               for (Event e : eventsWithCategoryCriteria) {
                    assertFalse(e.getCategory().equals("Category2"));
               }
          }
     }

     @Test
     public void testGetAllEventsWithAreaCriteria(){

          //Arrange
          createManyEvents();


          //Act
          List<Event> eventsWithAreaCriteria = eventDao.getAllEvents(CriteriaDtoMessage.newBuilder().setArea("Area1").build());

          //Assert
          if (eventsWithAreaCriteria.isEmpty()) { /*Vi tjekker om listen er tom først, for ellers giver
                               det ikke mening at assert*/
               assertFalse(true);
          }
          else {
               for (Event e : eventsWithAreaCriteria) {
                    assertTrue(e.getArea().equals("Area1"));
               }
          }
     }

     @Test
     public void testGetAllEventsWithWrongAreaCriteriaIsFalse(){

          //Arrange
          createManyEvents();


          //Act
          List<Event> eventsWithAreaCriteria = eventDao.getAllEvents(CriteriaDtoMessage.newBuilder().setArea("Area1").build());

          //Assert
          if (eventsWithAreaCriteria.isEmpty()) { /*Vi tjekker om listen er tom først, for ellers giver
                              giver det ikke mening at assert*/
               assertFalse(true);
          }
          else {

               for (Event e : eventsWithAreaCriteria) {
                    assertFalse(e.getArea().equals("Area2"));
               }
          }
     }

     @Test
     public void testGetAllEventsWithCategoryAndAreaCriteria(){

          //Arrange
          createManyEvents();

          //Act
          List<Event> eventsWithCategoryAndAreaCriteria = eventDao.getAllEvents(
                  CriteriaDtoMessage.newBuilder().setArea("Area1").setCategory("Category1").build());

          //Assert
          if (eventsWithCategoryAndAreaCriteria.isEmpty()) {
               assertFalse(true);
          }
          else{
          for (Event e:eventsWithCategoryAndAreaCriteria) {
               assertTrue(e.getArea().equals("Area1") && e.getCategory().equals("Category1"));
          }}
     }

     //getById
     @Test
     public void testGetEventById(){
          //Arrange
          Event event = createOneEvent();

          //Act
          Event eventToCheck = eventDao.getById(event.getId());

          //Assert
          assertTrue(eventToCheck.getId() == event.getId());
     }

     @Test
     public void testGetEventByNonExistingIdReturnsNull(){
          //Arrange
          Event event = createOneEvent();

          //Act
          Event eventToCheck = eventDao.getById(-1);

          //Assert
          assertNull(eventToCheck);
     }

     //cancel

     @Test
     public void testCancelEvent(){
          //Arrange
          Event event = createOneEvent();

          //Act
          Event eventToCheck = eventDao.cancel(event.getId());

          //Assert
          assertNull(eventDao.getById(eventToCheck.getId()));
     }

     @Test
     public void testCancelEventReturnsTheCorrectCanceledEvent(){
          //Arrange
          Event event = createOneEvent();

          //Act
          Event eventToCheck = eventDao.cancel(event.getId());

          //Assert
          assertTrue(eventToCheck.getId() == event.getId());
     }

     @Test
     public void testCancelEventWithNonExistingIdThrowsException(){
          //Arrange
          boolean thrown = false;

          //Act
          try {
               eventDao.cancel(-1);
          } catch (IllegalArgumentException e) {
               thrown = true;
          }

          //Assert
          assertTrue(thrown);
     }





}
