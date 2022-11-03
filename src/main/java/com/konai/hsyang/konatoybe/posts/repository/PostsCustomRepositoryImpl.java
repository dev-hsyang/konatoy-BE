package com.konai.hsyang.konatoybe.posts.repository;

import com.konai.hsyang.konatoybe.comments.repository.CommentsRepository;
import com.konai.hsyang.konatoybe.posts.domain.Posts;
import com.konai.hsyang.konatoybe.posts.dto.PageRequestDto;
import com.konai.hsyang.konatoybe.posts.dto.PostsListResponseDto;
import com.konai.hsyang.konatoybe.posts.dto.QPostsListResponseDto;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import static com.konai.hsyang.konatoybe.posts.domain.QPosts.*;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class PostsCustomRepositoryImpl implements PostsCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    private final CommentsRepository commentsRepository;

    @Override
    public List<Posts> findAllDescCurrent() {
        return jpaQueryFactory
                .selectFrom(posts)
                .orderBy(posts.createdate.desc())
                .fetch();
    }

    @Override
    public List<Posts> findAllDescHits() {
        return jpaQueryFactory
                .selectFrom(posts)
                .orderBy(posts.hits.desc())
                .fetch();
    }

    @Override
    public List<Posts> findAllDescLikes() {
        return jpaQueryFactory
                .selectFrom(posts)
                .orderBy(posts.likes.desc())
                .fetch();
    }

    @Override
    public List<Posts> findAllDescByUser(Long userID) {
        return jpaQueryFactory
                .selectFrom(posts)
                .where(posts.user.userID.eq(userID))
                .orderBy(posts.createdate.desc())
                .fetch();
    }

    @Override
    public void updateHits(Long id) {
        jpaQueryFactory
                .update(posts)
                .set(posts.hits, posts.hits.add(1))
                .where(posts.postsID.eq(id))
                .execute();
    }

    @Override
    public void deleteAllByUser(Long id) {
        jpaQueryFactory
                .delete(posts)
                .where(posts.user.userID.eq(id))
                .execute();
    }


    @Override
    public PageImpl<Posts> findAllV1(Pageable pageable) {
        List<Posts> postsList = jpaQueryFactory
                .selectFrom(posts)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(posts.createdate.desc()) // 추후 동적으로 작동하게 수정
                .fetch();

        return new PageImpl<>(postsList, pageable, postsList.size());
    }

    @Override
    public Page<PostsListResponseDto> findAllV2(PageRequestDto requestDto, Pageable pageable){

        List<PostsListResponseDto> content = getPostsList(requestDto, pageable); // offset, limit, sort 설정
        Long count = getCount(requestDto);
        return new PageImpl<>(content, pageable, count);
    }

    private Long getCount(PageRequestDto requestDto){

        return jpaQueryFactory
                .select(posts.count())
                .from(posts)
                .where(
                        nicknameEq(requestDto.getNickname()),
                        containsTitle(requestDto.getTitle())
                )
                .fetchOne();
    }

    private List<PostsListResponseDto> getPostsList(PageRequestDto requestDto, Pageable pageable){

        OrderSpecifier order = this.getSort(pageable);
        return jpaQueryFactory
                .select(new QPostsListResponseDto(posts))
                .from(posts)
                .where(
                        nicknameEq(requestDto.getNickname()), // 마이페이지에서 작성자가 작성한 글 모아오기 + 작성자로 검색하기 기능
                        containsTitle(requestDto.getTitle()) // 제목으로 검색하기 기능
                )
                .offset(pageable.getOffset()) // 페이지 번호 (param)
                .limit(pageable.getPageSize()) // 페이지 당 게시물 수 (param)
                .orderBy(order)
                .fetch();
    }

    private OrderSpecifier getSort(Pageable pageable){

        OrderSpecifier order = new OrderSpecifier(Order.DESC, posts.createdate);
        for(Sort.Order sort : pageable.getSort()) {
            switch (sort.getProperty()) {
                case "hits" : order = new OrderSpecifier(Order.DESC, posts.hits);
                    break;
                case "createdate" : order = new OrderSpecifier(Order.DESC, posts.createdate);
                    break;
            }
        }
        return order;
    }

    private BooleanExpression nicknameEq(String nickname) {

        if (nickname != null)
            return posts.user.nickname.eq(nickname);
        return null;
    }

    private BooleanExpression containsTitle(String title) {

        if(title != null)
            return posts.title.contains(title);
        return null;
    }
}
