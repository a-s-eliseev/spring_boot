package web.dao;

import web.model.User;
import java.util.List;

public interface UserDao {

    List<User> listUsers();
    User getUserById(Long id);
    User getUserByUsername(String username);
    boolean addUser(User user);
    boolean deleteUser(User user);
    boolean editUser(User user);
}
