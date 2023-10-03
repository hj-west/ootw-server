package com.responseor.ootw.controller;

import com.responseor.ootw.dto.WeatherResponseDto;
import com.responseor.ootw.entity.ClothesByTemp;
import com.responseor.ootw.service.WeatherService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/weathers")
public class WeatherController {
    private final WeatherService weatherService;

    @GetMapping("")
    private ResponseEntity<WeatherResponseDto> getWeather(@RequestParam("lat") String lat, @RequestParam("lon") String lon) {
        return ResponseEntity.ok().body(weatherService.getWeather(lat, lon));
    }

    @GetMapping("/clothes")
    private ResponseEntity<List<ClothesByTemp>> getClothes(@RequestParam("temp") Integer temp) {
        return ResponseEntity.ok().body(weatherService.getClothes(temp));
    }

}
