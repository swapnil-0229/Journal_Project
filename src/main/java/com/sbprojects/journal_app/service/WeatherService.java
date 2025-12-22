package com.sbprojects.journal_app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.sbprojects.journal_app.api.response.WeatherResponse;
import com.sbprojects.journal_app.cache.AppCache;
import com.sbprojects.journal_app.placeholders.Placeholders;

@Service
public class WeatherService {

    @Value("${weather.api.key}")
    private String apiKey; 

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AppCache myCache;

    public WeatherResponse getWeather(String city) {
        String finalAPI = myCache.appCache.get(AppCache.keys.WEATHER_API.toString()).replace(Placeholders.API_KEY, apiKey).replace(Placeholders.CITY, city);
        ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalAPI, HttpMethod.GET, null, WeatherResponse.class);
        WeatherResponse body = response.getBody();
        return body;
    }
    
}

// the process of converting a json response to corrresponding java object is called deserialization

