package com.konai.hsyang.konatoybe.commentsWebApi.controller;

import com.konai.hsyang.konatoybe.commentsWebApi.dto.CommentsSaveRequestDto;
import com.konai.hsyang.konatoybe.commentsWebApi.dto.CommentsUpdateRequestDto;
import com.konai.hsyang.konatoybe.commentsWebApi.service.CommentsService;
import com.konai.hsyang.konatoybe.loginWebApi.config.auth.PrincipalDetails;
import com.konai.hsyang.konatoybe.postsWebApi.service.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class CommentsApiController {

    private final CommentsService commentsService;
    private final PostsService postsService;

    @PostMapping("/api/comments/save/{postID}")
    public ResponseEntity<?> saveComment(@PathVariable Long postID, @RequestBody CommentsSaveRequestDto requestDto, @AuthenticationPrincipal PrincipalDetails principalDetails){

        return ResponseEntity.ok(commentsService.saveComment(principalDetails.getUsername(), postID, requestDto));
    }

    @PostMapping("/api/comments/delete/{commentID}")
    public Long deleteComment(@PathVariable Long commentID) {

        return commentsService.delete(commentID);
    }

    @PostMapping("/api/comments/update/{commentID}")
    public Long updateComment(@PathVariable Long commentID, @RequestBody CommentsUpdateRequestDto requestDto) {

        return commentsService.update(commentID, requestDto);
    }

    @GetMapping("/api/comments/click/{commentID}")
    public Long findPostByCommentID(@PathVariable Long commentID) {

        return commentsService.getPostID(commentID);
    }
}
