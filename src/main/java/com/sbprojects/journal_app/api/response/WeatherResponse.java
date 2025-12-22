package com.sbprojects.journal_app.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WeatherResponse{

    private Current current;

    @Getter
    @Setter
    public static class Condition{
        @JsonProperty("text")
        private String description;
    }

    @Getter
    @Setter
    public static class Current{

        @JsonProperty("temp_c")
        private double temperature;

        private Condition condition;

        @JsonProperty("feelslike_c")
        private double feelsLike;
    }
}