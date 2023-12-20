package com.springboot.blog.controller;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.service.impl.CommentServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class CommentController {

    private CommentServiceImpl  commentServiceImpl;

    public CommentController(CommentServiceImpl commentServiceImpl) {
        this.commentServiceImpl = commentServiceImpl;
    }

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable(value = "postId") long postId,
                                                    @RequestBody CommentDto commentDto) {
        // Assuming commentService.createComment returns the created CommentDto
        CommentDto createdComment = commentServiceImpl.createComment(postId, commentDto);

        // Return the created comment with HTTP status 201 (CREATED)
        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }

    @GetMapping("/posts/{postId}/comments")
    public List<CommentDto> getCommentsByPostId(@PathVariable (value = "postId") Long postId){
        return  commentServiceImpl.getCommentByPostId(postId);
    }

    @GetMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> getComment_ById(@PathVariable (value = "postId") Long postId, @PathVariable (value = "id") Long commentId){
         CommentDto commentDto = commentServiceImpl.getComment_ById(postId,commentId);
         return  new ResponseEntity<>(commentDto,HttpStatus.OK);


    }
    @PutMapping("/posts/{postId}/comments/{id}")
    public  ResponseEntity<CommentDto> updatedComment(@PathVariable (value = "postId") Long postId,@PathVariable (value = "id") Long commentId,@PathVariable (value = "") CommentDto commentDto ){
       CommentDto updateComment= commentServiceImpl.updateComment(postId,commentId,commentDto);
        return new ResponseEntity<>(updateComment,HttpStatus.OK);

    }
@DeleteMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable (value = "postId") Long postId,@PathVariable (value = "id") Long commentId){
commentServiceImpl.deleteComment(postId,commentId);
return new ResponseEntity<>("Comment deleted successfully",HttpStatus.OK);

    }

}
