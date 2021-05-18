package practice.practice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import practice.practice.objects.User;
import practice.practice.services.UserService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/personalPage/{id}")
public class PersonalPageController {

    private final UserService userService;

    @Autowired
    public PersonalPageController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("allUsers")
    public String getAllUsersOfPlatform(@PathVariable("id") Long id, @RequestParam(required = false) String param, Model model) {
        User user = userService.findUserById(id);
        if(user == null) {
            return "redirect:error";
        }
        List<User> users = userService.getAllUsersOfPlatform();
        model.addAttribute("users", users);
        model.addAttribute("user", user);
        return "personalPage";
    }

    @GetMapping
    public String personalPage(@PathVariable("id") Long id, Model model) {
        User user = userService.findUserById(id);
        if(user != null) {
            model.addAttribute("user", user);
            model.addAttribute("users", new ArrayList<User>());
            return "personalPage";
        }
        return "redirect:error";
    }
}
