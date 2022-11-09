package DaoInterfaces;

import domain.Event;
import org.postgresql.util.LruCache;

public interface EventDaoInterface {
    public Event create(Event event);
    public Event getById(int eventId);

}
