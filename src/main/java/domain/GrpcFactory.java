package domain;

import com.google.type.DateTime;
import event.*;

public class GrpcFactory {
    //Event Mapping
    public static Event fromMessageToEventWithoutOwner(EventMessage eventToMap){
        Event event = new Event(
                eventToMap.getTitle(),
                eventToMap.getDescription(),
                eventToMap.getLocation(),
                fromDateTimeMessageToDateTime(eventToMap.getDateTime())
        );
        return event;
    }

    public static EventMessage fromEventToMessage(Event eventToMap){
        EventMessage event = EventMessage.newBuilder()
                .setId(eventToMap.getId())
                .setUser(UserMessage.newBuilder()
                        .setId(eventToMap.getOwner().getId())
                        .setUsername(eventToMap.getOwner().getUsername())
                        .setPassword(eventToMap.getOwner().getPassword())
                        .setEmail(eventToMap.getOwner().getEmail())
                        .setSecurityLevel(eventToMap.getOwner().getSecurityLevel())
                        .build())
                .setDescription(eventToMap.getDescription())
                .setLocation(eventToMap.getLocation())
                .setDateTime(fromDateTimeToDateTimeMessage(eventToMap.getDateTime()))
                .build();
        return event;
    }

    public static Event fromEventCreationDtoMessageToEvent(EventCreationDtoMessage eventToMap) {
        Event event = new Event(
                eventToMap.getTitle(),
                eventToMap.getDescription(),
                eventToMap.getLocation(),
                fromDateTimeMessageToDateTime(eventToMap.getDateTime())
        );
        return event;

    }

    public static DateTimeMessage fromDateTimeToDateTimeMessage(DateTime dateTimeToMap) {
        return DateTimeMessage.newBuilder()
                .setDay(dateTimeToMap.getDay())
                .setMonth(dateTimeToMap.getMonth())
                .setYear(dateTimeToMap.getYear())
                .setHour(dateTimeToMap.getHours())
                .setMin(dateTimeToMap.getMinutes())
                .build();
    }

    public static DateTime fromDateTimeMessageToDateTime(DateTimeMessage dateTimeToMap)
    {
        return DateTime.newBuilder()
                .setDay(dateTimeToMap.getDay())
                .setMonth(dateTimeToMap.getMonth())
                .setYear(dateTimeToMap.getYear())
                .setHours(dateTimeToMap.getHour())
                .setMinutes(dateTimeToMap.getMin())
                .build();

    }

    //User Mapping ------------------------------------------------------
    public static UserMessage fromUserToMessage(User userToMap){
        UserMessage user = UserMessage.newBuilder()
                .setId(userToMap.getId())
                .setUsername(userToMap.getUsername())
                .setPassword(userToMap.getPassword())
                .setEmail(userToMap.getEmail())
                .setSecurityLevel(userToMap.getSecurityLevel())
                .build();
        return user;
    }

    public static User fromUserCreationDtoMessageToUser(UserCreationDtoMessage userToMap) {
        User user = new User(
                userToMap.getUsername(),
                userToMap.getPassword(),
                userToMap.getEmail(), 2
        );
        return user;

    }


}
