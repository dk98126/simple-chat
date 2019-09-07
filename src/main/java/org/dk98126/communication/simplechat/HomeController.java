package org.dk98126.communication.simplechat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

    private RegistrationChatService registrationChatService;

    @Autowired
    public void setRegistrationChatService(RegistrationChatService registrationChatService) {
        this.registrationChatService = registrationChatService;
    }

    @RequestMapping("/")
    public String home() {
        return "home.html";
    }

    @RequestMapping("/registrationResult")
    @ResponseBody
    public String sayHi(@RequestParam("email") String email,
                        @RequestParam("login") String login,
                        @RequestParam("password") String password) {
        try {
            registrationChatService.checkIfStringIsBlank(email, "Электронный адрес");
            registrationChatService.checkIfStringIsBlank(login, "Логин");
            registrationChatService.checkIfStringIsBlank(password, "Пароль");
            registrationChatService.checkPassword(password);
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }

        return "Вы успешно зарегистрироваись на нашем сайте!";
    }
}
