package model;

import java.util.List;

public class MovieSearchResponse {
    private Integer page;
    private List<Movie> results;
    private Integer total_pages;
    private Integer total_results;

    public MovieSearchResponse() {
    }

    public Integer getPage() {
        return page;
    }

    public List<Movie> getResults() {
        return results;
    }

    public Integer getTotal_pages() {
        return total_pages;
    }

    public Integer getTotal_results() {
        return total_results;
    }
}