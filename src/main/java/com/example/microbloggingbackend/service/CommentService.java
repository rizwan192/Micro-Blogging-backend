package com.example.microbloggingbackend.service;
import com.example.microbloggingbackend.dto.CommentsDto;
import com.example.microbloggingbackend.dto.LoginRequest;
import com.example.microbloggingbackend.dto.PostDto;
import com.example.microbloggingbackend.dto.RegisterRequest;
import com.example.microbloggingbackend.exception.MicroBloggingBackendException;
import com.example.microbloggingbackend.exception.PostNotFoundException;
import com.example.microbloggingbackend.mapper.CommentMapper;
import com.example.microbloggingbackend.model.Comment;
import com.example.microbloggingbackend.model.Post;
import com.example.microbloggingbackend.model.User;
import com.example.microbloggingbackend.repository.CommentRepository;
import com.example.microbloggingbackend.repository.PostRepository;
import com.example.microbloggingbackend.repository.UserRepository;
import com.example.microbloggingbackend.security.JwtProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class CommentService {
    private static final String POST_URL = "";
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    //private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;

    public void save(CommentsDto commentsDto) {
        Post post = postRepository.findById(commentsDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException(commentsDto.getPostId().toString()));
        Comment comment = comment.stream().map(commentsDto, post, authService.getCurrentUser());
        commentRepository.save(comment);
    }

    public List<CommentsDto> getAllCommentsForPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId.toString()));
        return commentRepository.findByPost(post)
                .stream()
               // .map(.collect(toList());
    }

    private PostDto mapFromCommentToDto(Post post) {
        CommentsDto commentsDto = new CommentsDto();
        commentsDto.setId(commentsDto.getPostId());
        commentsDto.createdDate(commentsDto.getCreatedDate());
        commentsDto.text(commentsDto.getDescription());
        commentsDto.userName(commentsDto.getUser().getUsername());
        return commentsDto;
    }

    public List<CommentsDto> getAllCommentsForUser(String userName) {
        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new UsernameNotFoundException(userName));
        return commentRepository.findAllByUser(user)
                .stream()
                .map(commentMapper::mapToDto)
                .collect(toList());
    }
}