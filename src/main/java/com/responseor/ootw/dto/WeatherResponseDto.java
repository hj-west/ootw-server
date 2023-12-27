package com.responseor.ootw.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class WeatherResponseDto {
    @ApiModelProperty(example = "기상조건 코드 : https://open-meteo.com/en/docs")
    private int id;

    @ApiModelProperty(example = "현재 온도. 단위 : 섭씨")
    private float temp;

    @ApiModelProperty(example = "현재 체감 온도. 단위 : 섭씨")
    private float feelsTemp;

    @ApiModelProperty(example = "최저 온도. 단위 : 섭씨")
    private float minTemp;

    @ApiModelProperty(example = "최대 온도. 단위 : 섭씨")
    private float maxTemp;

    @ApiModelProperty(example = "습도, 단위 : %")
    private float humidity;

    @ApiModelProperty(example = "바람의 속도. 단위 : meter/sec,")
    private float windSpeed;

    @ApiModelProperty(example = "흐림, 단위 : %")
    private int cloudsAll;

    @ApiModelProperty(example = "일몰 시간")
    private String sunset;

    @ApiModelProperty(example = "일출 시간")
    private String sunrise;

}
