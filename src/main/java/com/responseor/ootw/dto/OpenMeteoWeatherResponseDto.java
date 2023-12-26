package com.responseor.ootw.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OpenMeteoWeatherResponseDto {
    private Current current;


    @Data
    public static class Current {
        private Float temperature_2m;
        private Integer relative_humidity_2m;
        private Float apparent_temperature;
        private Integer weather_code;
        private Integer cloud_cover;
        private Float wind_speed_10m;
    }

    @Data
    public static class Daily {
        private Float temperature_2m;
        private Integer relative_humidity_2m;
        private Float apparent_temperature;
        private Integer weather_code;
        private Integer cloud_cover;
        private Float wind_speed_10m;
    }

}
