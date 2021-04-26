package com.example.microbloggingbackend.service;

import com.example.microbloggingbackend.dto.PostDto;
import com.example.microbloggingbackend.dto.PostResponse;
import com.example.microbloggingbackend.exception.PostNotFoundException;
import com.example.microbloggingbackend.model.Post;
import com.example.microbloggingbackend.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.time.Instant;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class PostService {

    @Autowired
    private AuthService authService;
    @Autowired
    private PostRepository postRepository;

    @Transactional
    public List<PostResponse> showAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream().map(this::mapFromPostToDto).collect(toList());
    }

    @Transactional
    public void createPost(PostDto postDto) {
        Post post = mapFromDtoToPost(postDto);
        postRepository.save(post);
    }

    @Transactional
    public PostDto readSinglePost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException("For id " + id));
        return mapFromPostToDto(post);
    }

    private PostDto mapFromPostToDto(Post post) {
        PostDto postDto = new PostDto();
        postDto.setId(post.getPostId());
        postDto.setTitle(post.getPostName());
        postDto.setContent(post.getDescription());
        postDto.setUsername(post.getUser().getUsername());
        return postDto;
    }

    private Post mapFromDtoToPost(PostDto postDto) {
        Post post = new Post();
        post.setPostName(postDto.getTitle());
        post.setDescription(postDto.getContent());
        com.example.microbloggingbackend.model.User loggedInUser = authService.getCurrentUser();
        post.setCreatedDate(Instant.now());
        return post;
    }
}