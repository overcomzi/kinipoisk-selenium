package org.example.models.newTrailers;

public class TrailerItem {
    private long id;
    private long filmId;
    private String title;
    private String genre;
    private String year;
    private String link;

    public TrailerItem() {
    }

    public TrailerItem(long filmId, String title, String genre, String year, String link) {
        this.filmId = filmId;
        this.title = title;
        this.genre = genre;
        this.year = year;
        this.link = link;
    }

    public TrailerItem(long id, long filmId, String title, String genre, String year, String link) {
        this.id = id;
        this.filmId = filmId;
        this.title = title;
        this.genre = genre;
        this.year = year;
        this.link = link;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getFilmId() {
        return filmId;
    }

    public void setFilmId(long filmId) {
        this.filmId = filmId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return "TrailerItem{" +
                "id=" + id +
                ", filmId=" + filmId +
                ", title='" + title + '\'' +
                ", genre='" + genre + '\'' +
                ", year='" + year + '\'' +
                ", link='" + link + '\'' +
                '}';
    }
}
