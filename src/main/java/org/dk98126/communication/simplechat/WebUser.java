package org.dk98126.communication.simplechat;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Пользователь чата
 */
@Entity
@Data
@NoArgsConstructor
public class WebUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String login;
    private String email;
    private String password;
    private String firstName;
    private String lastName;

    public WebUser(String login, String email, String password, String firstName, String lastName) {
        this.login = login;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
