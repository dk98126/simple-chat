package org.dk98126.communication.simplechat.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.dk98126.communication.simplechat.user.WebUser;

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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "web_user_id")
    private WebUser author;

    public String getAuthorName(){
        return author != null ? author.getUsername() : "Don't know";
    }

    public Message(String text, String tag, WebUser webUser) {
        this.author = webUser;
        this.text = text;
        this.tag = tag;
    }
}
