package com.filmer.dto.response;

import java.util.List;

/**
 * Response DTO for detailed star information.
 * Contains full star details including all movies.
 */
public class StarDetailResponse {

    private String id;
    private String name;
    private Short birthYear;
    private List<MovieInfo> movies;

    public StarDetailResponse() {
    }

    // Getters and Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Short getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Short birthYear) {
        this.birthYear = birthYear;
    }

    public List<MovieInfo> getMovies() {
        return movies;
    }

    public void setMovies(List<MovieInfo> movies) {
        this.movies = movies;
    }

    /**
     * Movie information for star detail display.
     */
    public static class MovieInfo {
        private String id;
        private String title;
        private Short year;
        private String director;

        public MovieInfo() {
        }

        public MovieInfo(String id, String title, Short year, String director) {
            this.id = id;
            this.title = title;
            this.year = year;
            this.director = director;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Short getYear() {
            return year;
        }

        public void setYear(Short year) {
            this.year = year;
        }

        public String getDirector() {
            return director;
        }

        public void setDirector(String director) {
            this.director = director;
        }
    }
}
