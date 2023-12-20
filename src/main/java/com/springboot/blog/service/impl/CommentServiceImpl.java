package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.BlogApiException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;


    public CommentServiceImpl(CommentRepository commentRepository,PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository= postRepository;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Comment comment = mapToEntity(commentDto);
        Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("post","id", postId));
      comment.setPost(post);
    Comment comment1= commentRepository.save(comment);
    return mapToDTO(comment1);
    }


    @Override
    public List<CommentDto> getCommentByPostId(Long postId) {
        List<Comment> comments= commentRepository.findByPostId(postId);

        return comments.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public CommentDto getComment_ById(Long postId,Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(
                ()->new ResourceNotFoundException("post","id", postId));

        Comment comment= commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("comment","id",commentId));
         if(comment.getPost().getId().equals(post.getId())){
            throw new BlogApiException("comment does not belong on post ", HttpStatus.BAD_REQUEST);
        }
    return mapToDTO(comment);

    }

    @Override
    public CommentDto updateComment(Long postId, Long commentId, CommentDto commentRequest) {
        Post post = postRepository.findById(postId).orElseThrow(
                ()->new ResourceNotFoundException("post","id", postId));
        Comment comment= commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("comment","id",commentId));
       if(!comment.getPost().equals(post.getId())){
           throw  new BlogApiException("comment dose not belong top post", HttpStatus.BAD_REQUEST);
       }
        comment.setName(commentRequest.getName());
       comment.setEmail(commentRequest.getEmail());
       comment.setBody(commentRequest.getBody());
      Comment updatedComment= commentRepository.save(comment);

        return mapToDTO(updatedComment);
    }

    @Override
    public void deleteComment(long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(
                ()->new ResourceNotFoundException("post","id", postId));
        Comment comment= commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("comment","id",commentId));
        if(!comment.getPost().equals(post.getId())){
            throw  new BlogApiException("comment dose not belong top post", HttpStatus.BAD_REQUEST);
        }
        commentRepository.delete(comment);
    }


    private CommentDto mapToDTO(Comment comment){
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setName(comment.getName());
        commentDto.setEmail(comment.getEmail());
        commentDto.setBody(comment.getBody());
        return  commentDto;
    }

    private Comment mapToEntity(CommentDto commentDto){

        Comment comment = new Comment();
        comment.setId(commentDto.getId());
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
        return  comment;
    }
}
