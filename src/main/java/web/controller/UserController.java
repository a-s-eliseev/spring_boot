package web.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import web.model.User;

@Controller
public class UserController {

    @GetMapping(value = "/hello")
    public String getUserPage(ModelMap modelMap) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        modelMap.put("user", user);
        return "hello";
    }

    @GetMapping(value = "/login")
    public String getLoginPage() {
        return "/login";
    }
}
