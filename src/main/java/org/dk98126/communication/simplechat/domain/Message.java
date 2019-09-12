package org.dk98126.communication.simplechat.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Table(name="messages")
@Data
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String text;
    private String tag;

    public Message(String text, String tag) {
        this.text = text;
        this.tag = tag;
    }
}
