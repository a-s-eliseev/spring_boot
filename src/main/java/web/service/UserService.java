package web.service;

import web.model.User;
import java.util.List;

public interface UserService {

    List<User> listUsers();
    User getUserById(Long id);
    User getUserByUsername(String username);
    boolean addUser(User user);
    boolean deleteUser(Long id);
    boolean editUser(User user);
}
