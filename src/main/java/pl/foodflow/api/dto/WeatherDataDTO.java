package pl.foodflow.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WeatherDataDTO {

    private String name;
    private String region;
    private String country;
    private String localtime;

    private double temp_c;
    private double temp_f;
    private double wind_mph;
    private double wind_kph;
    private String wind_dir;
    private double feelslike_c;
    private double feelslike_f;
    private double gust_mph;
    private double gust_kph;

    private double precip_mm;
    private int humidity;
    private double vis_km;
    private double uv;
    private String text;
    private String icon;
}




