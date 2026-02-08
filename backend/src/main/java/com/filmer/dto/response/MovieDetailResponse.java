package com.filmer.dto.response;

import java.math.BigDecimal;
import java.util.List;

/**
 * Response DTO for detailed movie information.
 * Contains full movie details including all genres and stars.
 */
public class MovieDetailResponse {

    private String id;
    private String title;
    private Short year;
    private String director;
    private BigDecimal rating;
    private Integer numVotes;
    private List<GenreInfo> genres;
    private List<StarInfo> stars;

    public MovieDetailResponse() {
    }

    // Getters and Setters

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

    public BigDecimal getRating() {
        return rating;
    }

    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }

    public Integer getNumVotes() {
        return numVotes;
    }

    public void setNumVotes(Integer numVotes) {
        this.numVotes = numVotes;
    }

    public List<GenreInfo> getGenres() {
        return genres;
    }

    public void setGenres(List<GenreInfo> genres) {
        this.genres = genres;
    }

    public List<StarInfo> getStars() {
        return stars;
    }

    public void setStars(List<StarInfo> stars) {
        this.stars = stars;
    }

    /**
     * Genre information for movie detail display.
     */
    public static class GenreInfo {
        private Long id;
        private String name;

        public GenreInfo() {
        }

        public GenreInfo(Long id, String name) {
            this.id = id;
            this.name = name;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    /**
     * Star information for movie detail display.
     */
    public static class StarInfo {
        private String id;
        private String name;
        private Short birthYear;

        public StarInfo() {
        }

        public StarInfo(String id, String name, Short birthYear) {
            this.id = id;
            this.name = name;
            this.birthYear = birthYear;
        }

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
    }
}
