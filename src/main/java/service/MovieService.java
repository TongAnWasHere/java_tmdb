package service;

import model.MovieDetails;
import model.MovieSearchResponse;
import model.Video;
import model.VideoResponse;
import tools.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class MovieService {
    private final String token;
    private final HttpClient client;
    private final ObjectMapper mapper;

    public MovieService() {
        token = System.getenv("TMDB_ACCESS_TOKEN");
        client = HttpClient.newHttpClient();
        mapper = new ObjectMapper();
    }

    public MovieSearchResponse searchMovies(String movieTitle, Integer page) throws Exception {
        String encodedTitle =
                URLEncoder.encode(
                        movieTitle,
                        StandardCharsets.UTF_8
                );
        String url = "https://api.themoviedb.org/3/search/movie?query=" + encodedTitle + "&page=" + page;
        String json = sendGetRequest(url);
        return mapper.readValue(
                json,
                MovieSearchResponse.class
        );
    }

    public MovieDetails getMovieDetails(Integer movieId) throws Exception {
        String url = "https://api.themoviedb.org/3/movie/" + movieId;
        String json = sendGetRequest(url);
        return mapper.readValue(
                json,
                MovieDetails.class
        );
    }

    public Video getTrailer(Integer movieId)
            throws Exception {
        String url = "https://api.themoviedb.org/3/movie/" + movieId + "/videos";
        String json = sendGetRequest(url);
        VideoResponse videoResponse =
                mapper.readValue(
                        json,
                        VideoResponse.class
                );
        for (Video video : videoResponse.getResults()) {
            if ("YouTube".equalsIgnoreCase(video.getSite())
                    && "Trailer".equalsIgnoreCase(video.getType())) {
                return video;
            }
        }
        return null;
    }

    private String sendGetRequest(String url) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + token)
                .header("accept", "application/json")
                .GET()
                .build();
        HttpResponse<String> response =
                client.send(
                        request,
                        HttpResponse.BodyHandlers.ofString()
                );
        return response.body();
    }
}
