package model;

import java.util.List;
public class MovieDetails {
    private Integer id;
    private String title;
    private String overview;
    private Integer runtime;
    private String release_date;
    private Double vote_average;
    private List<Genre> genres;

    public MovieDetails() {
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public Integer getRuntime() {
        return runtime;
    }

    public String getRelease_date() {
        return release_date;
    }

    public Double getVote_average() {
        return vote_average;
    }

    public List<Genre> getGenres() {
        return genres;
    }
}