package com.responseor.ootw.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class WeatherResponseDto {
    @ApiModelProperty(example = "기상 조건 ID, 기상조건 코드 : https://openweathermap.org/weather-conditions")
    private int id;

    @ApiModelProperty(example = "날씨 매개 변수 그룹 (비, 눈, 극한 등)")
    private String main;

    @ApiModelProperty(example = "날씨 아이콘 ID")
    private String icon;

    @ApiModelProperty(example = "온도. 단위 : 섭씨")
    private float temp;

    @ApiModelProperty(example = "체감 온도. 단위 : 섭씨")
    private float feelsTemp;

    @ApiModelProperty(example = "현재 최저 온도(대규모 대도시 및 도시 지역 내)")
    private float minTemp;

    @ApiModelProperty(example = "현재 최대 온도.(대규모 대도시 및 도시 지역 내)")
    private float maxTemp;

    @ApiModelProperty(example = "습도, %")
    private float humidity;

    @ApiModelProperty(example = "바람의 속도. 단위 : meter/sec,")
    private float windSpeed;

    @ApiModelProperty(example = "흐림, %")
    private int cloudsAll;

    @ApiModelProperty(example = "일몰 시간, 유닉스, UTC")
    private String sunset;

    @ApiModelProperty(example = "일출 시간, 유닉스, UTC")
    private String sunrise;

}
