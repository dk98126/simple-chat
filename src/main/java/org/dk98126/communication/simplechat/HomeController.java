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

    private WebUserRepo webUserRepo;

    @Autowired
    public void setWebUserRepo(WebUserRepo webUserRepo) {
        this.webUserRepo = webUserRepo;
    }

    @RequestMapping("/")
    public String home() {
        return "home.html";
    }

    @RequestMapping("/registrationResult")
    @ResponseBody
    public String sayHi(@RequestParam("firstName") String firstName,
                        @RequestParam("lastName") String lastName,
                        @RequestParam("email") String email,
                        @RequestParam("login") String login,
                        @RequestParam("password") String password) {
        try {
            registrationChatService.checkIfStringIsBlank(firstName, "Имя");
            registrationChatService.checkIfStringIsBlank(lastName, "Фамилия");
            registrationChatService.checkIfStringIsBlank(email, "Электронный адрес");
            registrationChatService.checkIfStringIsBlank(login, "Логин");
            registrationChatService.checkIfStringIsBlank(password, "Пароль");
            registrationChatService.checkPassword(password);
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }

        WebUser user = new WebUser(login, email, password, firstName, lastName);
        if (!webUserRepo.findByLogin(user.getLogin()).isEmpty()) {
            return "Пользователь с логином " + user.getLogin() + " уже существует!";
        } else if (!webUserRepo.findByEmail(user.getEmail()).isEmpty()) {
            return "Пользователь с электронной почтой " + user.getEmail() + " уже существует!";
        } else {
            webUserRepo.save(user);
            return "Вы успешно зарегистрировались на нашем сайте!";
        }
    }
}
