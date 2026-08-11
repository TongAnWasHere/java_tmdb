package model;

public class Movie {
    private Integer id;
    private String title;
    private String release_date;
    private Double vote_average;

    public Movie() {
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getRelease_date() {
        return release_date;
    }

    public Double getVote_average() {
        return vote_average;
    }
}