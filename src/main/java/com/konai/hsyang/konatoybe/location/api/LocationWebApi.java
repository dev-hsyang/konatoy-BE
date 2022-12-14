package com.konai.hsyang.konatoybe.location.api;

import com.konai.hsyang.konatoybe.location.domain.Location;
import com.konai.hsyang.konatoybe.location.dto.LocationResponseDto;
import com.konai.hsyang.konatoybe.location.dto.LocationSaveRequestDto;
import com.konai.hsyang.konatoybe.location.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class LocationWebApi {

    private final LocationService locationService;

    @GetMapping("/api/location/dto/{id}")
    public LocationResponseDto dtoFindById(@PathVariable Long id){

        return locationService.responseDtoFindByID(id);
    }

    @GetMapping("/api/location/{id}")
    public Location findById(@PathVariable Long id){

        return locationService.findById(id);
    }

    @PostMapping("/api/v2/location")
    public Long save(@RequestBody LocationSaveRequestDto requestDto){

        return locationService.save(requestDto);
    }


}
