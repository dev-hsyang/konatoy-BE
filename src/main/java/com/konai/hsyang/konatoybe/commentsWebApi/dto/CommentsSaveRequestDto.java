package com.konai.hsyang.konatoybe.commentsWebApi.dto;

import com.konai.hsyang.konatoybe.commentsWebApi.domain.Comments;
import com.konai.hsyang.konatoybe.loginWebApi.domain.User;
import com.konai.hsyang.konatoybe.postsWebApi.domain.Posts;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentsSaveRequestDto {

    private Long commentsID;
    private String content;
    private User user;
    private Posts post;
    private LocalDateTime createdate;
    private LocalDateTime modifieddate;

    public Comments toEntity() {
        return Comments.builder()
                .post(post)
                .user(user)
                .content(content)
                .build();
    }

    public void setCommentInfo(User user, Posts post){
        this.user = user;
        this.post = post;
    }
}
