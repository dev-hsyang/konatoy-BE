package com.konai.hsyang.konatoybe.postsWebServer.dto;

import com.konai.hsyang.konatoybe.locationWebServer.domain.Location;
import com.konai.hsyang.konatoybe.loginWebServer.domain.User;
import com.konai.hsyang.konatoybe.postsWebServer.domain.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostsSaveRequestDto {

    private String title;
    private Location location;
    private User author;
    private String content;
    private Long hits;
    private Long likes;
    private Double latitude;
    private Double longtitude;

    @Builder
    public PostsSaveRequestDto(String title, String content, Long hits, Long likes, Double latitude, Double longtitude) {
        this.title = title;
        this.content = content;
        this.hits = hits;
        this.likes = likes;
        this.latitude = latitude;
        this.longtitude = longtitude;
    }

    public Posts toEntity(){
        return Posts.builder()
                .title(title)
                .content(content)
                .location(location)
                .user(author)
                .hits(hits)
                .likes(likes)
                .build();
    }

    public void setAuthor(User user){

        this.author = user;
    }

    public void setLocation(Location location){

        this.location = location;
    }
    public void init() {

        this.likes = 0L;
        this.hits = 0L;
    }
}
