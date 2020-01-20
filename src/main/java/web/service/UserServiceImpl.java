package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.UserDao;
import web.model.User;

import java.util.Collections;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleService roleService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    @Override
    public List<User> listUsers() {
        return userDao.listUsers();
    }

    @Transactional
    @Override
    public User getUserById(Long id) {
        return userDao.getUserById(id);
    }

    @Transactional
    @Override
    public User getUserByUsername(String username) {
        return userDao.getUserByUsername(username);
    }

    @Transactional
    @Override
    public boolean addUser(User user) {

        User userFromDB = userDao.getUserByUsername(user.getUsername());

        if (userFromDB != null) {
            return false;
        }

        user.setRoles(Collections.singleton(roleService.getRole(1L)));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userDao.addUser(user);
        return true;
    }

    @Transactional
    @Override
    public void deleteUser(Long id) {
        User user = userDao.getUserById(id);
        user.setRoles(null);
        userDao.deleteUser(user);
    }

    @Transactional
    @Override
    public void editUser(User user) {
//        Set<Role> roles = user.getRoles();
//        user.setRoles(null);
//        for (Role role : roles) {
//            if ("ROLE_ADMIN".equals(role.getName())) {
//                user.setRoles(Collections.singleton(roleService.getRole(2L)));
//            }
//            if ("ROLE_USER".equals(role.getName())) {
//                user.setRoles(Collections.singleton(roleService.getRole(1L)));
//            }
//        }

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userDao.editUser(user);
    }
}
