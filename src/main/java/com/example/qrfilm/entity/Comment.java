package com.example.qrfilm.entity;

import jakarta.persistence.*;
import com.example.qrfilm.entity.SaveData;
import com.example.qrfilm.entity.Films;

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private SaveData user;

    @ManyToOne
    @JoinColumn(name = "film_id", nullable = false)
    private Films film;

    private String commentText;

    // Геттеры и сеттеры

    public SaveData getUser() {
        return user;
    }

    public void setUser(SaveData user) {
        this.user = user;
    }

    public Films getFilm() {
        return film;
    }

    public void setFilm(Films film) {
        this.film = film;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public Long getId() {
        return id;
    }
}
