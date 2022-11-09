package DaoInterfaces;

import domain.Event;
import domain.User;

public interface UserDaoInterface {
    public User create(User user);
    public User getById(int userId);
    public User getByUsername(String username);
}
