package practice.practice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import practice.practice.services.UserService;

@Controller
@RequestMapping("/api/link/")
public class LinkController {

    private final UserService userService;

    @Autowired
    public LinkController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("{id}")
    public String enableAccount(@PathVariable("id") Long id, Model model) {
        boolean isEnabled = userService.enableUserAccountById(id);
        model.addAttribute("isEnabled", isEnabled);
        return  "linkPage";
    }
}
