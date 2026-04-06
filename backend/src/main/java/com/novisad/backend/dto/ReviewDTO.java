package com.novisad.backend.dto;

public class ReviewDTO {
    private Long eventId;
    private Integer performance;
    private Integer soundAndLighting;
    private Integer venue;
    private Integer overallImpression;
    private String comment;

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public Integer getPerformance() {
        return performance;
    }

    public void setPerformance(Integer performance) {
        this.performance = performance;
    }

    public Integer getSoundAndLighting() {
        return soundAndLighting;
    }

    public void setSoundAndLighting(Integer soundAndLighting) {
        this.soundAndLighting = soundAndLighting;
    }

    public Integer getVenue() {
        return venue;
    }

    public void setVenue(Integer venue) {
        this.venue = venue;
    }

    public Integer getOverallImpression() {
        return overallImpression;
    }

    public void setOverallImpression(Integer overallImpression) {
        this.overallImpression = overallImpression;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}