package DaoInterfaces;

import domain.Event;
import org.postgresql.util.LruCache;

import java.util.List;

public interface EventDaoInterface {
    public Event create(Event event);
    public Event getById(int eventId);
    public List<Event> getAllEvents();

}
