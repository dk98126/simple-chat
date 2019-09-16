package org.dk98126.communication.simplechat.controller;

import org.dk98126.communication.simplechat.repository.WebUserRepo;
import org.dk98126.communication.simplechat.user.WebUser;
import org.dk98126.communication.simplechat.user.WebUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    WebUserRepo webUserRepo;

    @GetMapping
    public String usersList(Model model){
        model.addAttribute("usersList", webUserRepo.findAll());
        return "usersList";
    }

    @GetMapping("{webUser}")
    public String webUsersEdit(@PathVariable WebUser webUser, Model model){
        model.addAttribute("webUser", webUser);
        model.addAttribute("roles", WebUserRole.values());
        return "webUsersEdit";
    }

}
