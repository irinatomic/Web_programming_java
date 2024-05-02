package org.example.entities;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class Comment {

    private Integer id;

    @NotNull(message = "Author field is required")
    @NotEmpty(message = "Author field is required")
    private String author;

    @NotNull(message = "Content field is required")
    @NotEmpty(message = "Content field is required")
    private String content;

    public Comment() {}

    public Comment(String author, String content) {
        this();
        this.author = author;
        this.content = content;
    }
}
