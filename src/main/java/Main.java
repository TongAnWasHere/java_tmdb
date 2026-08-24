import model.*;
import service.MovieService;

import java.util.Scanner;

public class Main {
    void main() throws Exception {
        String TMDB_ACCESS_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIyZGQ3ODUyZDIwNDMzODcwNDAyNDA1MmJlNWYzYmJhZSIsIm5iZiI6MTc4NjM4MTcyNS4xNzI5OTk5LCJzdWIiOiI2YTdhMDU5ZDRmNmIzYmMyNTdkY2IxMjYiLCJzY29wZXMiOlsiYXBpX3JlYWQiXSwidmVyc2lvbiI6MX0.biny_ABliXhAlkb_VUexTmM7Sxep-B4NP2jp5KwCMaM";

        Scanner scanner = new Scanner(System.in);
        MovieService movieService = new MovieService();

        while (true) {
            System.out.print("Enter Movie Title: ");
            String movieTitle = scanner.nextLine();

            int currentPage = 1;
            boolean browsing = true;

            while (browsing) {
                MovieSearchResponse searchResponse = movieService.searchMovies(movieTitle, currentPage);
                System.out.println();

                int number = ((currentPage - 1) * 20) + 1;
                for (Movie movie : searchResponse.getResults()) {
                    System.out.println(
                            number + ". "
                                    + movie.getTitle()
                                    + " | "
                                    + movie.getRelease_date()
                                    + " | Rating: "
                                    + movie.getVote_average()
                                    + " | ID: "
                                    + movie.getId()
                    );
                    number++;
                }

                System.out.println("Page " + searchResponse.getPage() + " of " + searchResponse.getTotal_pages());
                System.out.println();

                System.out.println("[N] Next Page");
                System.out.println("[P] Previous Page");
                System.out.println("[G] Go to Page");
                System.out.println("[D] Movie Details");
                System.out.println("[B] Back");
                System.out.println("[E] Exit");
                System.out.print("> ");

                String choice = scanner.nextLine().trim().toUpperCase();
                switch (choice) {
                    case "N" -> {
                        if (currentPage < searchResponse.getTotal_pages()) {
                            currentPage++;
                        }
                    }
                    case "P" -> {
                        if (currentPage > 1) {
                            currentPage--;
                        }
                    }
                    case "G" -> {
                        System.out.print("Enter page number: ");
                        int requestedPage =
                                Integer.parseInt(scanner.nextLine().trim());
                        if (requestedPage < 1) {
                            currentPage = 1;
                        } else if (requestedPage > searchResponse.getTotal_pages()) {
                            currentPage = searchResponse.getTotal_pages();
                        } else {
                            currentPage = requestedPage;
                        }
                    }
                    case "D" -> {
                        System.out.print("Enter movie ID for details: ");
                        Integer movieId = Integer.parseInt(scanner.nextLine().trim());

                        MovieDetails movieDetails = movieService.getMovieDetails(movieId);

                        System.out.println();
                        System.out.println("Movie Details");
                        System.out.println("-------------");
                        System.out.println("Title: " + movieDetails.getTitle());
                        System.out.println("Release Date: " + movieDetails.getRelease_date());
                        System.out.println("Runtime: " + movieDetails.getRuntime() + " minutes");
                        System.out.println("Rating: " + movieDetails.getVote_average());
                        System.out.print("Genres: ");
                        for (int i = 0; i < movieDetails.getGenres().size(); i++) {
                            Genre genre = movieDetails.getGenres().get(i);
                            System.out.print(genre.getName());
                            if (i < movieDetails.getGenres().size() - 1) {
                                System.out.print(", ");
                            }
                        }
                        System.out.println();
                        System.out.println("Overview: " + movieDetails.getOverview());
                        Video trailer = movieService.getTrailer(movieId);
                        if (trailer != null) {
                            String trailerUrl = "https://www.youtube.com/watch?v=" + trailer.getKey();
                            System.out.println("Trailer: " + trailerUrl);
                        } else {
                            System.out.println("N/A");
                        }
                        System.out.println();
                    }
                    case "B" -> browsing = false;
                    case "E" -> {
                        return;
                    }
                    default -> System.out.println("Invalid option.");
                }
            }
        }
    }
}