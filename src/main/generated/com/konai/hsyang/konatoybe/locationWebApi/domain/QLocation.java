package com.konai.hsyang.konatoybe.locationWebApi.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QLocation is a Querydsl query type for Location
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLocation extends EntityPathBase<Location> {

    private static final long serialVersionUID = -2106449911L;

    public static final QLocation location = new QLocation("location");

    public final NumberPath<Double> latitude = createNumber("latitude", Double.class);

    public final NumberPath<Long> locationID = createNumber("locationID", Long.class);

    public final NumberPath<Double> longtitude = createNumber("longtitude", Double.class);

    public QLocation(String variable) {
        super(Location.class, forVariable(variable));
    }

    public QLocation(Path<? extends Location> path) {
        super(path.getType(), path.getMetadata());
    }

    public QLocation(PathMetadata metadata) {
        super(Location.class, metadata);
    }

}

