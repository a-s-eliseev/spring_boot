package web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import web.model.User;
import web.service.RoleService;
import web.service.UserService;
import web.service.UtilService;

import java.util.List;

@Controller
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping(value = "/admin")
    public String listUsers(ModelMap modelMap, Model model) {
        List<User> users = userService.listUsers();
        model.addAttribute("user", new User());
        modelMap.put("users", users);
        return "index";
    }

    @GetMapping(value = "/admin/addUser")
    public String getAddUser(Model model) {
        model.addAttribute("user", new User());
        return "addUser";
    }

    @PostMapping(value = "/admin/addUser")
    public String addUser(@ModelAttribute User user) {
        userService.addUser(user);
        return "redirect:/admin";
    }

    @GetMapping(value = "/admin/deleteUser")
    public String deleteUser(@RequestParam(value = "id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    @GetMapping(value = "/admin/editUser")
    public String editUserGet(@RequestParam(value = "id") Long id, ModelMap modelMap) {
        User user = userService.getUserById(id);
//        Set<Role> userRoles = user.getRoles();
//        List<Role> listRoles = roleService.getAllRoles();
//        modelMap.put("userRoles", userRoles);
//        modelMap.put("listRoles", listRoles);
        modelMap.put("user", user);
        return "editUser";
    }

    @RequestMapping(value = "/admin/editUser", method = RequestMethod.POST)
    public String editUserPost(@ModelAttribute User user,
                               @RequestParam(value = "role") String[] rolesArr,
                               @RequestParam(value = "id") Long id) {
        user.setId(id);
        user.setRoles(UtilService.stringArrToSetRoles(rolesArr));
        userService.editUser(user);
        return "redirect:/admin";
    }
}
