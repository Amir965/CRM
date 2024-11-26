package com.crm.controller;

import com.crm.entity.Post;
import com.crm.repository.CommentsRepository;
import com.crm.repository.PostRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {
    private final PostRepository postRepository;
    private CommentsRepository commentsRepo;

    public PostController(PostRepository postRepository, CommentsRepository commentsRepo) {
        this.postRepository = postRepository;
        this.commentsRepo = commentsRepo;
    }

    @PostMapping
    public String createPost(@RequestBody Post post) {
        postRepository.save(post);
        return "Post created successfully";

    }
    @DeleteMapping
    public void deletePost(){
        postRepository.deleteById(1L);
    } 
}
