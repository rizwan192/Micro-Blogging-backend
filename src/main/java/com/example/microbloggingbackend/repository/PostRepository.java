package com.example.microbloggingbackend.repository;
import com.example.microbloggingbackend.model.Post;
import com.example.microbloggingbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByUser(User user);
}