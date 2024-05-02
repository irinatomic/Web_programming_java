package org.example.entities;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class Post {

    private Integer id;
    private String date;

    @NotNull(message = "Author field is required")
    @NotEmpty(message = "Author field is required")
    private String author;

    @NotNull(message = "Title field is required")
    @NotEmpty(message = "Title field is required")
    private String title;

    @NotNull(message = "Content field is required")
    @NotEmpty(message = "Content field is required")
    private String content;

    private List<Comment> comments;

    public Post() {}

    public Post(String author, String title, String content) {
        this();
        this.date = LocalDate.now().toString();
        this.author = author;
        this.title = title;
        this.content = content;
        this.comments = new ArrayList<>();
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }
}
