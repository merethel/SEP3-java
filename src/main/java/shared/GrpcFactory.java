package shared;

import com.google.type.DateTime;
import shared.model.Event;
import shared.model.User;
import event.*;

import java.util.ArrayList;
import java.util.List;

public class GrpcFactory {
    //Event Mapping

    public static EventMessage fromEventToMessage(Event eventToMap) {
        List<UserMessage> attendees = new ArrayList<>();

        for (User attendee : eventToMap.getAttendees()) {
            attendees.add(fromUserToMessage(attendee));
        }

        EventMessage event = EventMessage.newBuilder()
                .setId(eventToMap.getId())
                .setTitle(eventToMap.getTitle())
                .setUser(UserMessage.newBuilder()
                        .setId(eventToMap.getOwner().getId())
                        .setUsername(eventToMap.getOwner().getUsername())
                        .setPassword(eventToMap.getOwner().getPassword())
                        .setEmail(eventToMap.getOwner().getEmail())
                        .setRole(eventToMap.getOwner().getRole())
                        .build())
                .setDescription(eventToMap.getDescription())
                .setLocation(eventToMap.getLocation())
                .setDateTime(fromDateTimeToDateTimeMessage(eventToMap.getDateTime()))
                .setCategory(eventToMap.getCategory())
                .setArea(eventToMap.getArea())
                .addAllAttendees(attendees)
                .build();
        return event;
    }

    public static Event fromEventCreationDtoMessageToEvent(EventCreationDtoMessage eventToMap)
    {
        User userWithName = new User();
        userWithName.setUsername(eventToMap.getUsername());
        Event event = new Event(
                userWithName,
                eventToMap.getTitle(),
                eventToMap.getDescription(),
                eventToMap.getLocation(),
                fromDateTimeMessageToDateTime(eventToMap.getDateTime()),
                eventToMap.getCategory(),
                eventToMap.getArea(),
                new ArrayList<>()
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

    public static DateTime fromDateTimeMessageToDateTime(DateTimeMessage dateTimeToMap) {
        return DateTime.newBuilder()
                .setDay(dateTimeToMap.getDay())
                .setMonth(dateTimeToMap.getMonth())
                .setYear(dateTimeToMap.getYear())
                .setHours(dateTimeToMap.getHour())
                .setMinutes(dateTimeToMap.getMin())
                .build();

    }

    //User Mapping ------------------------------------------------------
    public static UserMessage fromUserToMessage(User userToMap) {
        UserMessage user = UserMessage.newBuilder()
                .setId(userToMap.getId())
                .setUsername(userToMap.getUsername())
                .setPassword(userToMap.getPassword())
                .setEmail(userToMap.getEmail())
                .setRole(userToMap.getRole())
                .build();
        return user;
    }

    public static User fromUserCreationDtoMessageToUser(UserCreationDtoMessage userToMap) {
        User user = new User(
                userToMap.getUsername(),
                userToMap.getPassword(),
                userToMap.getEmail(),
                userToMap.getRole()
        );
        return user;

    }


    public static ListEventMessage fromEventListToEventListMessage(List<Event> events) {
        ListEventMessage listToReturn = null;
        ListEventMessage.Builder builder = ListEventMessage.newBuilder();
        for (Event event : events) {
            builder.addEvents(fromEventToMessage(event));
        }
        listToReturn = builder.build();

        return listToReturn;
    }

}