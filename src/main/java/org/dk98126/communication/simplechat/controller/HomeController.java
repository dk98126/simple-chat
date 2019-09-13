package org.dk98126.communication.simplechat.controller;

import org.dk98126.communication.simplechat.service.RegistrationChatService;
import org.dk98126.communication.simplechat.user.WebUser;
import org.dk98126.communication.simplechat.repository.WebUserRepo;
import org.dk98126.communication.simplechat.user.WebUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Controller
public class HomeController {

    private RegistrationChatService registrationChatService;

    @Autowired
    public void setRegistrationChatService(RegistrationChatService registrationChatService) {
        this.registrationChatService = registrationChatService;
    }

    private WebUserRepo webUserRepo;

    @Autowired
    public void setWebUserRepo(WebUserRepo webUserRepo) {
        this.webUserRepo = webUserRepo;
    }

    @GetMapping("/registration")
    public String showRegistrationPage() {
        return "registration";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/")
    public String home() {
        return "home";
    }

    @RequestMapping("/user")
    public String userView() {
        return "user";
    }

    @PostMapping("/registration")
    public String registerUser(@RequestParam("firstName") String firstName,
                               @RequestParam("lastName") String lastName,
                               @RequestParam("email") String email,
                               @RequestParam("username") String username,
                               @RequestParam("password") String password,
                               Model model) {
        try {
            registrationChatService.checkIfStringIsBlank(firstName, "Имя");
            registrationChatService.checkIfStringIsBlank(lastName, "Фамилия");
            registrationChatService.checkIfStringIsBlank(email, "Электронный адрес");
            registrationChatService.checkIfStringIsBlank(username, "Логин");
            registrationChatService.checkIfStringIsBlank(password, "Пароль");
            registrationChatService.checkPassword(password);
        } catch (IllegalArgumentException e) {
            model.addAttribute("registrationError", e.getMessage());
            return "registration";
        }

        WebUser user = new WebUser(username, email, password, firstName, lastName);
        user.setActive(true);
        user.setRoles(Set.of(WebUserRole.USER));
        if (webUserRepo.findByUsername(user.getUsername()) != null) {
            model.addAttribute("registrationError",
                    "Пользователь с логином " + user.getUsername() + " уже существует!");
            return "registration";
        } else if (webUserRepo.findByEmail(user.getEmail()) != null) {
            model.addAttribute( "registrationError",
                    "Пользователь с электронной почтой " + user.getEmail() + " уже существует!");
            return "registration";
        } else {
            webUserRepo.save(user);
            return "login";
        }
    }
}
