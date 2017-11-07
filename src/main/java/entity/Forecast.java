package entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Forecast {
    @JsonProperty("dt")
    private LocalDate dateTime;
    @JsonProperty("main")
    private Temperatures temperatures;

    public LocalDate getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDate dateTime) {
        this.dateTime = dateTime;
    }

    public Temperatures getTemperatures() {
        return temperatures;
    }

    public void setTemperatures(Temperatures temperatures) {
        this.temperatures = temperatures;
    }
}
