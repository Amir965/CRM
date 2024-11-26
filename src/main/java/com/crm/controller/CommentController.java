package com.crm.controller;

import com.crm.entity.Comments;
import com.crm.entity.Post;
import com.crm.repository.CommentsRepository;
import com.crm.repository.PostRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/comments")
public class CommentController {
    private final PostRepository postRepository;
    private CommentsRepository commentsRepository;


    public CommentController(PostRepository postRepository, CommentsRepository commentsRepository) {
        this.postRepository = postRepository;
        this.commentsRepository = commentsRepository;
    }
    @PostMapping
    public String createComment(@RequestBody Comments comments, @RequestParam long postId){
        Post post = postRepository.findById(postId).get();
        comments.setPost(post);
        commentsRepository.save(comments);
        return "comment created successfully";
    }
}
