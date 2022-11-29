package DaoInterfaces;

import domain.Event;
import org.postgresql.util.LruCache;

import java.util.List;

public interface EventDaoInterface {
    Event create(Event event);
    Event getById(int eventId);
    List<Event> getAllEvents();
    Event addAttendeeToEventAttendeeList(int userId, int eventId);
}
