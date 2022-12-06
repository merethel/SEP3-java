import com.google.type.DateTime;
import shared.model.Event;
import shared.GrpcFactory;
import shared.model.User;
import event.DateTimeMessage;
import event.EventCreationDtoMessage;
import event.EventMessage;
import event.UserMessage;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

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
                .setCategory("catergory")
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


}
