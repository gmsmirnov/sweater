package ru.gsmirnov.sweater.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String text;

    private String tag;

    @ManyToOne(fetch = FetchType.EAGER) // many messages one user
    @JoinColumn(name = "user_id") // the field-name in db will be "user_id", not "author_id" (by default)
    private User author;

    public Message() {
    }

    public Message(String text, String tag, User author) {
        this.text = text;
        this.tag = tag;
        this.author = author;
    }

    public String getAuthorName() {
        return this.author != null ? this.author.getUsername() : "<none>";
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTag() {
        return this.tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public User getAuthor() {
        return this.author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return this.text.equals(message.text) &&
                this.tag.equals(message.tag) &&
                this.author.equals(message.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.text, this.tag, this.author);
    }
}
