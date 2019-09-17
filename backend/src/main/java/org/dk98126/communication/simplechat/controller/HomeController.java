package org.dk98126.communication.simplechat.controller;

import org.dk98126.communication.simplechat.repository.WebUserRepo;
import org.dk98126.communication.simplechat.service.RegistrationChatService;
import org.dk98126.communication.simplechat.user.WebUser;
import org.dk98126.communication.simplechat.user.WebUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (!(auth instanceof AnonymousAuthenticationToken))
            return "redirect:/user";
        else
            return "registration";
    }

    @GetMapping("/login")
    public String login(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (!(auth instanceof AnonymousAuthenticationToken)) {
            return "redirect:/user";
        } else
            return "login";
    }

    @RequestMapping("/")
    public String home() {
        return "home";
    }

    @RequestMapping("/user")
    public String userView(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            //FIXME не обращаться каждый раз к базе при переходе на эту страницу, а брать данные из сессии
            Object principal = auth.getPrincipal();
            if (principal instanceof UserDetails) {
                WebUser webUser = webUserRepo.findByUsername(((UserDetails) principal).getUsername());
                model.addAttribute("webUser", webUser);
            }
            return "user";
        } else {
            return "redirect:/registration";
        }
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
            model.addAttribute("registrationError",
                    "Пользователь с электронной почтой " + user.getEmail() + " уже существует!");
            return "registration";
        } else {
            webUserRepo.save(user);
            return "login";
        }
    }
}
