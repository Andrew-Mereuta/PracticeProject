package practice.practice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import practice.practice.objects.User;
import practice.practice.services.UserService;

@Controller
@RequestMapping("/login")
public class LoginController {

    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String loginPage(Model model) {
        //model.addAttribute("user", new User());
        return "login";
    }

//    @PostMapping
//    public String loginAttempt(@ModelAttribute User user) {
//        String email = user.getEmail();
//        String password = user.getPassword();
//
//        boolean isAuthenticated = userService.authenticate(email, password);
//        if(isAuthenticated) {
//            Long id = userService.findUserIdByEmail(email);
//            return "redirect:personalPage/" + id;
//        }
//        return "errorPage";
//    }
}
