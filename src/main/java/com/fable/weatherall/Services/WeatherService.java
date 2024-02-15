package com.fable.weatherall.Services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {
	private static final String API_KEY = "f43a7bfb47684151b6962305240602";
    private static final String API_URL = "http://api.weatherapi.com/v1/forecast.json?key=f43a7bfb47684151b6962305240602&q=London&days=5&aqi=no&alerts=no";

    public String get5DayForecast(String city) {
        String url = String.format("%s?q=%s&appid=%s", API_URL, city, API_KEY);
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, String.class);
    }

}
