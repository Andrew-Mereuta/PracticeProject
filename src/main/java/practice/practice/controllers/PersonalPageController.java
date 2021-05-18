package practice.practice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import practice.practice.objects.User;
import practice.practice.services.UserService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/personalPage/")
public class PersonalPageController {

    private final UserService userService;

    @Autowired
    public PersonalPageController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("{id}/allUsers")
    public String getAllUsersOfPlatform(@PathVariable("id") Long id, Model model) {
        if(userService.findUserById(id) == null) {
            return "errorPage";
        }
        List<User> users = userService.getAllUsersOfPlatform();
        model.addAttribute("users", users);
        return "personalPage";
    }

    @GetMapping("{id}")
    public String personalPage(@PathVariable("id") Long id, Model model) {
        User user = userService.findUserById(id);
        if(user != null) {
            model.addAttribute("user", user);
            model.addAttribute("users", new ArrayList<User>());
            return "personalPage";
        }
        return "errorPage";
    }
}
