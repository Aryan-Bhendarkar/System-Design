package moviebooking.service;

import moviebooking.models.Movie;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Manages all movies in the system.
 * Single Responsibility: Only handles movie-related operations.
 */
public class MovieService {

    private List<Movie> movies;

    public MovieService() {
        this.movies = new ArrayList<>();
    }

    public void addMovie(Movie movie) {
        movies.add(movie);
        System.out.println("[MovieService] Added movie: " + movie.getTitle());
    }

    public List<Movie> getAllMovies() {
        return movies;
    }

    public Optional<Movie> getMovieById(String movieId) {
        return movies.stream()
                     .filter(m -> m.getId().equals(movieId))
                     .findFirst();
    }

    public List<Movie> searchByTitle(String title) {
        List<Movie> result = new ArrayList<>();
        for (Movie movie : movies) {
            if (movie.getTitle().toLowerCase().contains(title.toLowerCase())) {
                result.add(movie);
            }
        }
        return result;
    }
}
