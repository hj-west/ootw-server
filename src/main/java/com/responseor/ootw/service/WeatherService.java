package com.responseor.ootw.service;

import com.responseor.ootw.dto.WeatherResponseDto;
import com.responseor.ootw.entity.ClothesByTemp;

import java.util.List;

public interface WeatherService {
    WeatherResponseDto getWeather(String lat, String lon);
    List<ClothesByTemp> getClothes(int temp);
}
