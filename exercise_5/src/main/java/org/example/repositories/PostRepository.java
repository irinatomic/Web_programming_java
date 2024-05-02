package org.example.repositories;

import org.example.entities.Comment;
import org.example.entities.Post;

import java.util.List;

public interface PostRepository {

    Post addPost(Post post);

    List<Post> getAllPosts();

    Post getPostById(Integer id);

    Comment addComment(Integer postId, Comment comment);
}
