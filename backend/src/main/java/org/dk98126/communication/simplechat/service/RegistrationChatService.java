package org.dk98126.communication.simplechat.service;

import org.springframework.stereotype.Component;

/**
 * Реализация интерфейса {@link ChatService} для регистрационной страницы
 */
@Component
public class RegistrationChatService implements ChatService {

    private static final int PASSWORD_LENGTH = 8;

    /**
     * Проверка строки на пустоту
     * @param string строка (может быть паролем, email и т.д.)
     * @param purpose назначение строки, чтобы правильно сформировать exception
     * @throws IllegalArgumentException выбрасывается, если строка пустая (сообщение содержит назначение строки)
     */
    public void checkIfStringIsBlank(String string, String purpose) throws IllegalArgumentException {
        if (string.isBlank()) {
            throw new IllegalArgumentException(purpose + "не может быть пустым!");
        }
    }

    /**
     * Проврка пароля на некоторые условия
     * @param password строка, представляющая пароль
     * @throws IllegalArgumentException выбрасывается, если пароль не удовлетворил одному из условий
     */
    public void checkPassword(String password) throws IllegalArgumentException {
        if (password.chars()
                .filter(Character::isLetter)
                .map(Character::toUpperCase)
                .anyMatch(ch -> ch < 'A' || ch > 'Z')) {
            throw new IllegalArgumentException("Буквы в пароле могут быть только латинские");
        }

        if (password.chars().filter(ch -> !Character.isLetterOrDigit(ch)).count() != 0) {
            throw new IllegalArgumentException("Пароль может состоять только из цифр и букв латинского алфавита");
        }

        if (password.length() < PASSWORD_LENGTH) {
            throw new IllegalArgumentException("Пароль должен быть длиннее " + PASSWORD_LENGTH + " символов");
        }

        if (password.chars().noneMatch(Character::isUpperCase)) {
            throw new IllegalArgumentException("В пароле должна быть хотя бы одна заглавная буква");
        }

        if (password.chars().noneMatch(Character::isLowerCase)) {
            throw new IllegalArgumentException("В пароле должна быть хотя бы одна маленькая буква");
        }
    }
}
