package com.konai.hsyang.konatoybe.location.controller;

import com.konai.hsyang.konatoybe.location.dto.LocationSaveRequestDto;
import com.konai.hsyang.konatoybe.location.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class LocationApiController {

    private final LocationService locationService;

    @PostMapping("/api/location")
    public void location(@RequestBody LocationSaveRequestDto requestDto) {

        locationService.save(requestDto);
    }
}
