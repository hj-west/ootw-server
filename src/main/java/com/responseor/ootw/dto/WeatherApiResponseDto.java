package com.responseor.ootw.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Setter
@Getter
@ApiModel(description = "날씨 정보")
public class WeatherApiResponseDto {

    private List<Weather> weather;

    @ApiModelProperty(example = "내부 매개 변수")
    private String base;

    private Main main;

    private Wind wind;

    private Clouds clouds;

    private Sys sys;

    @ApiModelProperty(example = "가시성")
    private int visibility;

    @ApiModelProperty(example = "데이터 계산 시간, unix, UTC")
    private long dt;

    @ApiModelProperty(example = "UTC에서 초 단위로 이동")
    private int timezone;

    @ApiModelProperty(example = "도시 ID, ISO 3166기준")
    private long id;

    @ApiModelProperty(example = "도시 이름, ISO 3166기준")
    private String name;

    @ApiModelProperty(example = "내부 매개 변수")
    private int cod;

    @Data
    public static class Weather {

        @ApiModelProperty(example = "기상 조건 ID, 기상조건 코드 : https://openweathermap.org/weather-conditions")
        private int id;

        @ApiModelProperty(example = "날씨 매개 변수 그룹 (비, 눈, 극한 등)")
        private String main;

        @ApiModelProperty(example = "그룹 내 날씨 조건")
        private String description;

        @ApiModelProperty(example = "날씨 아이콘 ID")
        private String icon;
    }

    @Data
    public static class Main {

        @ApiModelProperty(example = "온도. 단위 : 섭씨")
        private float temp;

        @ApiModelProperty(example = "체감 온도. 단위 : 섭씨")
        private float feels_like;

        @ApiModelProperty(example = "현재 최저 온도(대규모 대도시 및 도시 지역 내)")
        private float temp_min;

        @ApiModelProperty(example = "현재 최대 온도.(대규모 대도시 및 도시 지역 내)")
        private float temp_max;

        @ApiModelProperty(example = "습도, %")
        private float humidity;
    }

    @Data
    public static class Wind {
        @ApiModelProperty(example = "바람의 속도. 단위 : meter/sec,")
        private float speed;
    }

    @Data
    public static class Clouds {
        @ApiModelProperty(example = "흐림, %")
        private int all;
    }

    @Data
    public static class Sys {

        private int type;

        private int id;

        @ApiModelProperty(example = "국가 코드 (GB, JP 등)")
        private String country;

        @ApiModelProperty(example = "일출 시간, 유닉스, UTC")
        private long sunrise;

        public String getSunrise() {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();
            date.setTime(sunrise*1000);

            return format.format(date);
        }

        @ApiModelProperty(example = "일몰 시간, 유닉스, UTC")
        private long sunset;

        public String getSunset() {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();
            date.setTime(sunset*1000);

            return format.format(date);
        }

    }
}
