package org.dk98126.communication.simplechat.controller;

import org.dk98126.communication.simplechat.repository.WebUserRepo;
import org.dk98126.communication.simplechat.service.RegistrationChatService;
import org.dk98126.communication.simplechat.user.WebUser;
import org.dk98126.communication.simplechat.user.WebUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashSet;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private WebUserRepo webUserRepo;

    @Autowired
    public void setWebUserRepo(WebUserRepo webUserRepo) {
        this.webUserRepo = webUserRepo;
    }

    private RegistrationChatService registrationChatService;

    @Autowired
    public void setRegistrationChatService(RegistrationChatService registrationChatService) {
        this.registrationChatService = registrationChatService;
    }

    @GetMapping
    public String usersList(Model model){
        model.addAttribute("usersList", webUserRepo.findAll());
        return "usersList";
    }

    @GetMapping("{webUser}")
    public String webUsersEdit(@PathVariable WebUser webUser, Model model){
        model.addAttribute("webUser", webUser);
        model.addAttribute("roles", Arrays.asList(WebUserRole.values()));
        return "usersEdit";
    }

    @PostMapping("{webUser}")
    public String changeUserInfo(@PathVariable WebUser webUser,
                                 @RequestParam(name = "username") String username,
                                 @RequestParam(name = "roles") HashSet<WebUserRole> roles,
                                 Model model)
    {
        try {
            registrationChatService.checkIfStringIsBlank(username, "Имя пользователя");
        } catch (IllegalArgumentException e) {
            model.addAttribute("registrationError", e.getMessage());
            return webUsersEdit(webUser, model);
        }
        if (!username.equals(webUser.getUsername()) && webUserRepo.findByUsername(username) != null) {
            model.addAttribute("registrationError", "Пользователь с таким логином " +
                    "уже сущестует");
            return webUsersEdit(webUser, model);
        }
        webUser.setUsername(username);
        webUser.setRoles(roles);
        webUserRepo.save(webUser);
        return "redirect:/admin";
    }
}
