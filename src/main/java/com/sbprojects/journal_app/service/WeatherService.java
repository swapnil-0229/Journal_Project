package com.sbprojects.journal_app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.sbprojects.journal_app.api.response.WeatherResponse;
import com.sbprojects.journal_app.cache.AppCache;
import com.sbprojects.journal_app.enums.AppCacheKey;
import com.sbprojects.journal_app.placeholders.Placeholders;

import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Service
@Slf4j
public class WeatherService {

    @Value("${weather.api.key}")
    private String apiKey; 

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AppCache appCache;

    @Autowired
    private RedisService redisService;

    public WeatherResponse getWeather(String city) {
        WeatherResponse weatherResponse = redisService.get("weather_of_" + city, WeatherResponse.class);
        if(weatherResponse != null) {
            log.info("Cache hit: returning weather for {} from redis", city);
            return weatherResponse;
        }
        else{
            log.info("Cache miss: returning weather for {} from external api", city);
            String apiUrl = appCache.get(AppCacheKey.WEATHER_API);
            if(apiUrl == null){
                log.error("Weather API URL configuration not found in AppCache");
                return null;
            }
            String finalUrl = apiUrl.replace(Placeholders.API_KEY, apiKey).replace(Placeholders.CITY, city);
    
            try {
                ResponseEntity<WeatherResponse> response = restTemplate.exchange(
                    Objects.requireNonNull(finalUrl), 
                    Objects.requireNonNull(HttpMethod.GET), 
                    null, 
                    WeatherResponse.class
                );

                WeatherResponse body = response.getBody();
                if(body != null) {
                    redisService.set("weather_of_" + city, body, 300L);
                    log.info("Weather data cached for {}", city);
                }
                return body;    
            } catch (HttpClientErrorException e) {
                log.error("Client error fetching weather for {}: {}", city, e.getMessage());
                return null; 
            } catch (Exception e) {
                log.error("Server/Network error fetching weather for {}: {}", city, e.getMessage());
                return null;
            }
        }   
    }
}