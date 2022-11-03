package com.konai.hsyang.konatoybe.posts.repository;

import com.konai.hsyang.konatoybe.posts.domain.Posts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostsRepository extends JpaRepository<Posts, Long>, PostsCustomRepository {
}
