package org.dk98126.communication.simplechat;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {
    @RequestMapping("/")
    public String home() {
        return "home.html";
    }

    @RequestMapping("/registrationResult")
    @ResponseBody
    public String sayHi(@RequestParam("email") String email,
                        @RequestParam("login") String login,
                        @RequestParam("password") String password) {
        return "Вы успешно зарегистрироваись на нашем сайте!";
    }
}
