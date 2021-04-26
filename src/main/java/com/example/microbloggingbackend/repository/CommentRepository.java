package com.example.microbloggingbackend.repository;

import com.example.microbloggingbackend.model.Comment;
import com.example.microbloggingbackend.model.Post;
import com.example.microbloggingbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);

    List<Comment> findAllByUser(User user);
}