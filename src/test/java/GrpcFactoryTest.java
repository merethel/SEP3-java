import com.google.type.DateTime;
import domain.Event;
import domain.GrpcFactory;
import domain.User;
import event.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GrpcFactoryTest
{
    @Test
    public void fromEventToMessage()
    {
        //Arrange
        User user1 = new User("Username1", "password1", "email1@email.dk", "User");
        ArrayList arrayListAttendees = new ArrayList<>();
        arrayListAttendees.add(user1);
        Event testEvent = new Event(user1, "TestTitle", "TestDescription", "TestLocation", DateTime.newBuilder().setDay(1).setMonth(1).setYear(2023).setHours(12).build(),"Category","Area", arrayListAttendees);

        //Act
        EventMessage eventMessage = GrpcFactory.fromEventToMessage(testEvent);


        //Assert
        assertEquals(testEvent.getId(), eventMessage.getId());
        assertEquals(testEvent.getTitle(), eventMessage.getTitle());
        assertEquals(testEvent.getDescription(), eventMessage.getDescription());
        assertEquals(testEvent.getLocation(), eventMessage.getLocation());
        assertEquals(testEvent.getDateTime().getDay(), eventMessage.getDateTime().getDay());
        assertEquals(testEvent.getCategory(), eventMessage.getCategory());
        assertEquals(testEvent.getArea(), eventMessage.getArea());
        assertTrue(testEvent.getAttendees().get(0).getId() == eventMessage.getAttendeesList().get(0).getId());
    }

   @Test
    public void fromEventCreationDtoMessageToEvent()
    {
        //Arrange
        EventCreationDtoMessage dtoMessage = EventCreationDtoMessage.newBuilder()
                .setUsername("username")
                .setTitle("title")
                .setDescription("description")
                .setLocation("location")
                .setDateTime(DateTimeMessage.newBuilder()
                        .setDay(1)
                        .setHour(1)
                        .setMin(1)
                        .setYear(2022)
                        .setMonth(12)
                        .build())
                .setCategory("category")
                .setArea("area")
                .build();

        //Act
        Event eventTest = GrpcFactory.fromEventCreationDtoMessageToEvent(dtoMessage);

        //Assert
        assertEquals(dtoMessage.getUsername(), eventTest.user.getUsername());
        assertEquals(dtoMessage.getTitle(), eventTest.getTitle());
        assertEquals(dtoMessage.getDescription(), eventTest.getDescription());
        assertEquals(dtoMessage.getLocation(), eventTest.getLocation());
        assertEquals(dtoMessage.getDateTime().getHour(), eventTest.getDateTime().getHours());
        assertEquals(dtoMessage.getDateTime().getYear(), eventTest.getDateTime().getYear());
        assertEquals(dtoMessage.getCategory(), eventTest.getCategory());
        assertEquals(dtoMessage.getArea(), eventTest.getArea());
        //nogle vil være null og dem tester vi bare ikke

    }

    @Test
    public void TestFromDateTimeToDateTimeMessage()
    {
        //Arrange
        DateTime dateTime = DateTime.newBuilder()
                .setDay(1)
                .setMonth(1)
                .setYear(2023)
                .setHours(12)
                .setMinutes(12)
                .build();

        //Act
        DateTimeMessage dateTimeMessage = GrpcFactory.fromDateTimeToDateTimeMessage(dateTime);

        //Assert
        assertEquals(dateTime.getDay(), dateTimeMessage.getDay());
        assertEquals(dateTime.getMonth(), dateTimeMessage.getMonth());
        assertEquals(dateTime.getYear(), dateTimeMessage.getYear());
        assertEquals(dateTime.getHours(), dateTimeMessage.getHour());
        assertEquals(dateTime.getMinutes(), dateTimeMessage.getMin());
    }

    @Test
    public void TestFromDateTimeMessageToDateTime()
    {
        //Arrange
        DateTimeMessage dateTimeMessage = DateTimeMessage.newBuilder()
                .setDay(1)
                .setMonth(1)
                .setYear(2023)
                .setHour(12)
                .setMin(12)
                .build();

        //Act
        DateTime dateTime = GrpcFactory.fromDateTimeMessageToDateTime(dateTimeMessage);

        //Assert
        assertEquals(dateTimeMessage.getDay(), dateTime.getDay());
        assertEquals(dateTimeMessage.getMonth(), dateTime.getMonth());
        assertEquals(dateTimeMessage.getYear(), dateTime.getYear());
        assertEquals(dateTimeMessage.getHour(), dateTime.getHours());
        assertEquals(dateTimeMessage.getMin(), dateTime.getMinutes());
    }

    @Test
    public void TestFromUserToMessage()
    {
        //Arrange
        User user = new User("Username1", "password1", "email1@email.dk", "User");

        //Act
        UserMessage userMessage = GrpcFactory.fromUserToMessage(user);

        //Assert
        assertEquals(user.getUsername(), userMessage.getUsername());
        assertEquals(user.getId(), userMessage.getId());
        assertEquals(user.getEmail(), userMessage.getEmail());
        assertEquals(user.getRole(), userMessage.getRole());
        assertEquals(user.getPassword(), userMessage.getPassword());
    }

    @Test
    public void TestFromUserCreationDtoMessageToUser()
    {
        //Arrange
        UserCreationDtoMessage userCreationDtoMessage = UserCreationDtoMessage.newBuilder()
                .setUsername("Testy")
                .setPassword("Testington")
                .setEmail("test@testy.dk")
                .setRole("User")
                .build();

        //Act
        User user = GrpcFactory.fromUserCreationDtoMessageToUser(userCreationDtoMessage);

        //Assert
        assertEquals(userCreationDtoMessage.getUsername(), user.getUsername());
        assertEquals(userCreationDtoMessage.getPassword(), user.getPassword());
        assertEquals(userCreationDtoMessage.getEmail(), user.getEmail());
        assertEquals(userCreationDtoMessage.getRole(), user.getRole());
    }

    @Test
    public void TestFromEventListToEventListMessage()
    {
        //Arrange
        User user1 = new User("Username1", "password1", "email1@email.dk", "User");

        Event testEvent = new Event(user1, "TestTitle", "TestDescription", "TestLocation", DateTime.newBuilder().setDay(1).setMonth(1).setYear(2023).setHours(12).build(),"Category","Area", new ArrayList<>());
        Event testEvent2 = new Event(user1, "TestTitle2", "TestDescription", "TestLocation", DateTime.newBuilder().setDay(2).setMonth(1).setYear(2023).setHours(12).build(),"Category","Area", new ArrayList<>());
        Event testEvent3 = new Event(user1, "TestTitle3", "TestDescription", "TestLocation", DateTime.newBuilder().setDay(3).setMonth(1).setYear(2023).setHours(12).build(),"Category","Area", new ArrayList<>());

        List<Event> events = new ArrayList<>();

        events.add(testEvent);
        events.add(testEvent2);
        events.add(testEvent3);

        //Act

        //Assert

    }
}
