import domain.GrpcFactory;
import domain.User;
import event.UserMessage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GrpcFactoryTest
{
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
