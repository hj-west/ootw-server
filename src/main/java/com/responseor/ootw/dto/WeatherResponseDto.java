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

    @ApiModelProperty(example = "기상조건")
    private String main;

    @ApiModelProperty(example = "기상조건 아이콘 코드")
    private int icon;

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

    public Integer getIcon() {
        switch (this.id) {
            case 1: case  2:
                return 1;
            case 3: case  45: case  48:
                return 2;
            case 51: case  53: case  56: case  61: case  66: case  80:
                return 3;
            case 55: case  57: case  63: case  81:
                return 4;
            case 65: case  67: case  82:
                return 5;
            case 71: case  73: case  85:
                return 6;
            case 75: case  77: case  86:
                return 7;
            default:
                return 0;
        }
    }

    public String getMain() {
        switch (this.id) {
            case 1:
                return "대체로 맑아요";
            case 2:
                return "조금 흐려요";
            case 3:
                return "흐려요";
            case 45: case 48:
                return "안개가 꼈어요";
            case 51: case 56:
                return "약한 이슬비가 와요";
            case 53:
                return "이슬비가 와요";
            case 55: case 57:
                return "강한 이슬비가 와요";
            case 61: case 66:
                return "약한 비가 와요";
            case 63:
                return "비가 와요";
            case 65: case 67:
                return "강한 비가 와요";
            case 71: case 85:
                return "약한 눈이 와요";
            case 73:
                return "눈이 와요";
            case 75: case 86:
                return "강한 눈이 와요";
            case 77:
                return "우박이 떨어져요";
            case 80:
                return "약한 소나기가 와요";
            case 81:
                return "소나기가 와요";
            case 82:
                return "강한 소나기가 와요";
            default:
                return "맑아요";
        }
    }

}
