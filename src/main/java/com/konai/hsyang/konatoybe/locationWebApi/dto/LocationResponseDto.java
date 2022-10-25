package com.konai.hsyang.konatoybe.locationWebApi.dto;

import com.konai.hsyang.konatoybe.locationWebApi.domain.Location;
import lombok.Getter;

@Getter
public class LocationResponseDto {

    private Long locationID;
    private Double latitude;
    private Double longtitude;

    public LocationResponseDto(Location entity){
        this.locationID = entity.getLocationID();
        this.latitude = entity.getLatitude();
        this.longtitude = entity.getLongtitude();
    }
}