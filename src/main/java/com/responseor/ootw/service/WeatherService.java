package com.responseor.ootw.service;

import com.responseor.ootw.dto.WeatherResponseDto;

public interface WeatherService {
    WeatherResponseDto getWeather(String lat, String lon);
}
