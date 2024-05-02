package org.example.service;

import org.example.entities.Comment;
import org.example.entities.Post;
import org.example.repositories.PostRepository;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@Singleton
public class PostService {

    private final PostRepository postRepository;

    @Inject
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post addPost(Post post) {
        return this.postRepository.addPost(post);
    }

    public List<Post> getAllPosts() {
        return this.postRepository.getAllPosts();
    }

    public Post getPostById(Integer id) {
        return this.postRepository.getPostById(id);
    }

    public Comment addComment(Integer postId, Comment comment) {
        return this.postRepository.addComment(postId, comment);
    }
}
