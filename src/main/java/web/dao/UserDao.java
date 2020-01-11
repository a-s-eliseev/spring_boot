package web.dao;

import web.model.User;
import java.util.List;

public interface UserDao {

    List<User> listUsers();
    User getUserById(Long id);
    User getUserByUsername(String username);
    void addUser(User user);
    void deleteUser(User user);
    void editUser(User user);
}
