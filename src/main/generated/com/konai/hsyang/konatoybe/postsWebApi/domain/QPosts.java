package com.konai.hsyang.konatoybe.postsWebApi.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPosts is a Querydsl query type for Posts
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPosts extends EntityPathBase<Posts> {

    private static final long serialVersionUID = -1828241477L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPosts posts = new QPosts("posts");

    public final com.konai.hsyang.konatoybe.postsWebApi.etc.QBaseTimeEntity _super = new com.konai.hsyang.konatoybe.postsWebApi.etc.QBaseTimeEntity(this);

    public final ListPath<com.konai.hsyang.konatoybe.commentsWebApi.domain.Comments, com.konai.hsyang.konatoybe.commentsWebApi.domain.QComments> comments = this.<com.konai.hsyang.konatoybe.commentsWebApi.domain.Comments, com.konai.hsyang.konatoybe.commentsWebApi.domain.QComments>createList("comments", com.konai.hsyang.konatoybe.commentsWebApi.domain.Comments.class, com.konai.hsyang.konatoybe.commentsWebApi.domain.QComments.class, PathInits.DIRECT2);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdate = _super.createdate;

    public final ListPath<com.konai.hsyang.konatoybe.fileWebApi.domain.File, com.konai.hsyang.konatoybe.fileWebApi.domain.QFile> file = this.<com.konai.hsyang.konatoybe.fileWebApi.domain.File, com.konai.hsyang.konatoybe.fileWebApi.domain.QFile>createList("file", com.konai.hsyang.konatoybe.fileWebApi.domain.File.class, com.konai.hsyang.konatoybe.fileWebApi.domain.QFile.class, PathInits.DIRECT2);

    public final NumberPath<Long> hits = createNumber("hits", Long.class);

    public final NumberPath<Long> likes = createNumber("likes", Long.class);

    public final com.konai.hsyang.konatoybe.locationWebApi.domain.QLocation location;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifieddate = _super.modifieddate;

    public final NumberPath<Long> postsID = createNumber("postsID", Long.class);

    public final StringPath title = createString("title");

    public final com.konai.hsyang.konatoybe.loginWebApi.domain.QUser user;

    public QPosts(String variable) {
        this(Posts.class, forVariable(variable), INITS);
    }

    public QPosts(Path<? extends Posts> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPosts(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPosts(PathMetadata metadata, PathInits inits) {
        this(Posts.class, metadata, inits);
    }

    public QPosts(Class<? extends Posts> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.location = inits.isInitialized("location") ? new com.konai.hsyang.konatoybe.locationWebApi.domain.QLocation(forProperty("location")) : null;
        this.user = inits.isInitialized("user") ? new com.konai.hsyang.konatoybe.loginWebApi.domain.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

