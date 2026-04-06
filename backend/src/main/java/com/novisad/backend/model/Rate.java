package com.novisad.backend.model;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@Embeddable
public class Rate {
    @Min(value = 1, message = "Ocena ne može biti manja od 1")
    @Max(value = 10, message = "Ocena ne može biti veća od 10")
    private Integer performance;

    @Min(value = 1, message = "Ocena ne može biti manja od 1")
    @Max(value = 10, message = "Ocena ne može biti veća od 10")
    private Integer soundAndLighting;

    @Min(value = 1, message = "Ocena ne može biti manja od 1")
    @Max(value = 10, message = "Ocena ne može biti veća od 10")
    private Integer venue;

    @Min(value = 1, message = "Ocena ne može biti manja od 1")
    @Max(value = 10, message = "Ocena ne može biti veća od 10")
    private Integer overallImpression;

    public Rate() {
    }

    public Integer getPerformance() { return performance; }
    public void setPerformance(Integer performance) { this.performance = performance; }
    public Integer getSoundAndLighting() { return soundAndLighting; }
    public void setSoundAndLighting(Integer soundAndLighting) { this.soundAndLighting = soundAndLighting; }
    public Integer getVenue() { return venue; }
    public void setVenue(Integer venue) { this.venue = venue; }
    public Integer getOverallImpression() { return overallImpression; }
    public void setOverallImpression(Integer overallImpression) { this.overallImpression = overallImpression; }
}