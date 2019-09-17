package org.dk98126.communication.simplechat.controller;

import org.dk98126.communication.simplechat.domain.Message;
import org.dk98126.communication.simplechat.repository.MessagesRepo;
import org.dk98126.communication.simplechat.user.WebUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MessageController {

    @Autowired
    private MessagesRepo messagesRepo;

    @GetMapping("/messages")
    public String messages(Model model) {
        Iterable<Message> messages = messagesRepo.findAll();
        model.addAttribute("messages", messages);
        return "messages";
    }

    @PostMapping("/messages")
    public String addMessage(@AuthenticationPrincipal WebUser webUser,
                             @RequestParam String text,
                             @RequestParam String tag,
                             Model model) {
        Message message = new Message(text, tag, webUser);
        messagesRepo.save(message);

        Iterable<Message> messages = messagesRepo.findAll();
        model.addAttribute("messages", messages);
        return "messages";
    }

}
