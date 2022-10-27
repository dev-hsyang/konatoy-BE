package com.konai.hsyang.konatoybe.postsWebApi.repository;

import com.konai.hsyang.konatoybe.postsWebApi.domain.Posts;
import com.konai.hsyang.konatoybe.postsWebApi.dto.PageRequestDto;
import com.konai.hsyang.konatoybe.postsWebApi.dto.PostsListResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface PostsCustomRepository {

    List<Posts> findAllDescCurrent();
    List<Posts> findAllDescHits();
    List<Posts> findAllDescLikes();
    List<Posts> findAllDescByUser(@Param("userID") Long userID);
    void updateHits(@Param("id") Long id);
    void deleteAllByUser(@Param("userID") Long id);
    PageImpl<Posts> findAllV1(Pageable pageable);
    Page<PostsListResponseDto> findAllV2(PageRequestDto requestDto, Pageable pageable);
}
