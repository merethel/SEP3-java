package DaoInterfaces;

import shared.model.Event;
import event.CriteriaDtoMessage;

import java.util.List;

public interface EventDaoInterface {
    Event create(Event event);
    Event getById(int eventId);
    List<Event> getAllEvents(CriteriaDtoMessage criteriaDtoMessage);
    Event addAttendeeToEventAttendeeList(int userId, int eventId);
    Event cancel(int eventId);
}
