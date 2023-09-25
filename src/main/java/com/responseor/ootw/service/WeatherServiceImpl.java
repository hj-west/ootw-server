package com.responseor.ootw.service;

import com.responseor.ootw.dto.WeatherResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WeatherServiceImpl implements WeatherService {

    @Value("${openweather.host}")
    private String weatherHost;

    @Value("${openweather.apikey}")
    private String apiKey;

    @Override
    public WeatherResponseDto getWeather(String lat, String lon) {
        StringBuilder urlBuilder = new StringBuilder(weatherHost);

        try {
            urlBuilder.append("?" + URLEncoder.encode("lat", "UTF-8") + "=" + lat);
            urlBuilder.append("&" + URLEncoder.encode("lon", "UTF-8") + "=" + lon);
            urlBuilder.append("&" + URLEncoder.encode("appid", "UTF-8") + "=" + apiKey);
            urlBuilder.append("&" + URLEncoder.encode("lang", "UTF-8") + "=kr");
            urlBuilder.append("&" + URLEncoder.encode("units", "UTF-8") + "=metric");

            RestTemplate restTemplate = new RestTemplate();

            return restTemplate.getForObject(urlBuilder.toString(), WeatherResponseDto.class);


        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}

