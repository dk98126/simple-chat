package org.dk98126.communication.simplechat.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Greeting {
    @Getter
    private String content;

    public Greeting(String content) {
        this.content = content;
    }
}
