package com.springboot.blog.service;

import com.springboot.blog.payload.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(long postId, CommentDto commentDto);
   List<CommentDto> getCommentByPostId(Long postId);
   CommentDto getComment_ById( Long postId, Long commentId);
   //CommentDto updateCommentBuId(Long postId, Long commentId);
    CommentDto updateComment(Long postId,Long commentId, CommentDto commentRequest);
 void deleteComment(long postId, Long commentId);
}
