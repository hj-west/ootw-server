package com.responseor.ootw.service;

import com.responseor.ootw.dto.CustomUserDetails;
import com.responseor.ootw.dto.WeatherApiResponseDto;
import com.responseor.ootw.dto.WeatherResponseDto;
import com.responseor.ootw.entity.ClothesByTemp;
import com.responseor.ootw.exception.CustomException;
import com.responseor.ootw.exception.ErrorCode;
import com.responseor.ootw.repository.ClothesByTempRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WeatherServiceImpl implements WeatherService {

    @Value("${openweather.host}")
    private String weatherHost;

    @Value("${openweather.apikey}")
    private String apiKey;

    private final ClothesByTempRepository clothesByTempRepository;

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

            WeatherApiResponseDto apiResponse =restTemplate.getForObject(urlBuilder.toString(), WeatherApiResponseDto.class);

            return WeatherResponseDto.builder()
                    .id(Objects.requireNonNull(apiResponse).getWeather().get(0).getId())
                    .main(Objects.requireNonNull(apiResponse).getWeather().get(0).getMain())
                    .icon(Objects.requireNonNull(apiResponse).getWeather().get(0).getIcon())
                    .temp(apiResponse.getMain().getTemp())
                    .feelsTemp(apiResponse.getMain().getFeels_like())
                    .minTemp(apiResponse.getMain().getTemp_min())
                    .maxTemp(apiResponse.getMain().getTemp_max())
                    .humidity(apiResponse.getMain().getHumidity())
                    .windSpeed(apiResponse.getWind().getSpeed())
                    .cloudsAll(apiResponse.getClouds().getAll())
                    .sunrise(apiResponse.getSys().getSunrise())
                    .sunset(apiResponse.getSys().getSunset())
                    .build();

        } catch (Exception e) {
            throw new CustomException(ErrorCode.WEATHER_API_ERROR);
        }
    }

    @Override
    public List<ClothesByTemp> getClothes(int temp) {

        Long uuid = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUuid();

        List<ClothesByTemp> clothesByTempList = clothesByTempRepository.findByUuidAndStartTempLessThanEqualOrEndTempGreaterThanEqual(uuid, temp);

        if (clothesByTempList.isEmpty()) {
            clothesByTempList = clothesByTempRepository.findByUuidIsNullAndStartTempLessThanEqualOrEndTempGreaterThanEqual(temp);
        }

        return clothesByTempList;
    }
}

