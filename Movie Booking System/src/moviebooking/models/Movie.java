package moviebooking.models;

import moviebooking.enums.Genre;

public class Movie {
    private String id;
    private String title;
    private Genre genre;
    private int durationMins;
    private String language;

    public Movie(String id, String title, Genre genre, int durationMins, String language) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.durationMins = durationMins;
        this.language = language;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public Genre getGenre() { return genre; }
    public int getDurationMins() { return durationMins; }
    public String getLanguage() { return language; }

    @Override
    public String toString() {
        return "Movie{id='" + id + "', title='" + title + "', genre=" + genre +
               ", duration=" + durationMins + "min, language='" + language + "'}";
    }
}
