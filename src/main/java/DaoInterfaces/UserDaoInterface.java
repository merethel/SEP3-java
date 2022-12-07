package DaoInterfaces;

import domain.Event;
import domain.User;

public interface UserDaoInterface {
    User create(User user);
    User getById(int userId);
    User getByUsername(String username);
    User deleteUser(int userId);
}
