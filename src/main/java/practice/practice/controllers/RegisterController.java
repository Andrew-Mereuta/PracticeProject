package practice.practice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import practice.practice.customExceptions.UserAlreadyExistsException;
import practice.practice.objects.User;
import practice.practice.services.UserService;

import javax.validation.Valid;

@Controller
@RequestMapping("/register")
public class RegisterController {

    private final JavaMailSender javaMailSender;

    private final UserService userService;

    @Autowired
    public RegisterController(JavaMailSender javaMailSender, UserService userService) {
        this.javaMailSender = javaMailSender;
        this.userService = userService;
    }

    @GetMapping
    public String indexScene(Model model) {
        //model.addAttribute("user", new User());
        return "registerPage";
    }

//    @PostMapping
//    public String formSubmission(@ModelAttribute("user") @Valid User user) throws UserAlreadyExistsException {
//        user = userService.registerNewUser(user);
//
//        SimpleMailMessage msg = new SimpleMailMessage();
//        msg.setFrom("andrewapple03@gmail.com");
//        msg.setTo(user.getEmail());
//        msg.setSubject("Spring Message");
//        msg.setText("http://localhost:8080/api/link/" + user.getId());
//
//        javaMailSender.send(msg);
//
//        return "redirect:personalPage/" + user.getId();
//    }
    @PostMapping
    public String formSubmission(@RequestParam String username,
                                 @RequestParam String password,
                                 @RequestParam Integer age,
                                 @RequestParam String email) throws UserAlreadyExistsException {
        User user = new User();
        user.setUsername(username);
        user.setAge(age);
        user.setPassword(password);
        user.setEmail(email);
        user = userService.registerNewUser(user);

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom("andrewapple03@gmail.com");
        msg.setTo(user.getEmail());
        msg.setSubject("Spring Message");
        msg.setText("http://localhost:8080/api/link/" + user.getId());

        javaMailSender.send(msg);

        return "redirect:personalPage/" + user.getId();
    }
}
