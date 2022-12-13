package Daos;
import shared.model.Event;
import shared.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;


public class UserDaoTest {

    private static SessionFactory sessionFactory;

    private static UserDao userDao;

    private static Session session;



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
    public void testGetIdWithNonExistingValueThrowsException() {
        //Arrange
        boolean thrown = false;

        //Act
        try {
            User user = userDao.getById(-1);
            user.getId();
        } catch (NullPointerException e) {
            thrown = true;
        }

        //Assert
        assertTrue(thrown);
    }


    @Test
    public void testGetByUsername()
    {
        //Arrange
        User user = createOneUser();
        //Act
        User getByUsername = userDao.getByUsername(user.getUsername());

        //Assert
        assertEquals(getByUsername.getUsername(), user.getUsername());
    }


    @Test
    public void testGetByUsernameThrowsExceptionAtNonExistingUsername()
    {
        //Arrange
        boolean thrown = false;

        //Act
        try {
            User getByUsername = userDao.getByUsername("tester");
            getByUsername.getUsername();
        } catch (NullPointerException e) {
            thrown = true;
        }
        //Assert
        assertTrue(thrown);
    }

    @Test
    public void testDeleteUser() {
        //Arrange
        User userToDelete = createOneUser();
        boolean thrown = false;

        //Act
        try {
            userDao.deleteUser(userToDelete.getId());
            User user = userDao.getById(userToDelete.getId());
            user.getId();
        }

        catch (NullPointerException e){
            thrown = true;
        }

        //Assert
        assertTrue(thrown);
    }
}
