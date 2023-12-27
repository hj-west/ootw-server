package com.responseor.ootw.service;

import com.responseor.ootw.config.exception.CustomException;
import com.responseor.ootw.config.exception.ErrorCode;
import com.responseor.ootw.dto.CustomUserDetails;
import com.responseor.ootw.dto.OpenMeteoWeatherResponseDto;
import com.responseor.ootw.dto.WeatherResponseDto;
import com.responseor.ootw.entity.ClothesByTemp;
import com.responseor.ootw.repository.ClothesByTempRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class WeatherServiceImpl implements WeatherService {

    @Value("${openmeteo.host}")
    private String weatherHost;

    private final ClothesByTempRepository clothesByTempRepository;

    @Override
    public WeatherResponseDto getWeather(String lat, String lon) {
        StringBuilder urlBuilder = new StringBuilder(weatherHost);

        OpenMeteoWeatherResponseDto weatherResponseDto;
        try {
            urlBuilder.append("?" + URLEncoder.encode("latitude", "UTF-8") + "=" + lat);
            urlBuilder.append("&" + URLEncoder.encode("longitude", "UTF-8") + "=" + lon);
            urlBuilder.append("&" + URLEncoder.encode("current", "UTF-8") + "=temperature_2m,relative_humidity_2m,apparent_temperature,precipitation,weather_code,cloud_cover,wind_speed_10m");
            urlBuilder.append("&" + URLEncoder.encode("wind_speed_unit", "UTF-8") + "=ms");
            urlBuilder.append("&" + URLEncoder.encode("daily", "UTF-8") + "=weather_code,temperature_2m_max,temperature_2m_min,apparent_temperature_max,apparent_temperature_min,sunrise,sunset");
            urlBuilder.append("&" + URLEncoder.encode("timezone", "UTF-8") + "=Asia/Seoul");
            urlBuilder.append("&" + URLEncoder.encode("forecast_days", "UTF-8") + "=1");

            RestTemplate restTemplate = new RestTemplate();

            weatherResponseDto = restTemplate.getForObject(urlBuilder.toString(), OpenMeteoWeatherResponseDto.class);

            if (weatherResponseDto == null) {
                log.error("WEATHER_API_ERROR : weatherResponseDto == null");
                throw new CustomException(ErrorCode.WEATHER_API_ERROR);
            }

            return WeatherResponseDto.builder()
                    .id(Objects.requireNonNull(weatherResponseDto).getCurrent().getWeather_code())
                    .temp(weatherResponseDto.getCurrent().getTemperature_2m())
                    .feelsTemp(weatherResponseDto.getCurrent().getApparent_temperature())
                    .minTemp(weatherResponseDto.getDaily().getTemperature_2m_min().get(0))
                    .maxTemp(weatherResponseDto.getDaily().getTemperature_2m_max().get(0))
                    .humidity(weatherResponseDto.getCurrent().getRelative_humidity_2m())
                    .windSpeed(weatherResponseDto.getCurrent().getWind_speed_10m())
                    .cloudsAll(weatherResponseDto.getCurrent().getCloud_cover())
                    .sunrise(weatherResponseDto.getDaily().getSunrise().toString())
                    .sunset(weatherResponseDto.getDaily().getSunset().toString())
                    .build();

        } catch (Exception e) {
            log.error("WEATHER_API_ERROR : {}",e.getMessage(), e);
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

