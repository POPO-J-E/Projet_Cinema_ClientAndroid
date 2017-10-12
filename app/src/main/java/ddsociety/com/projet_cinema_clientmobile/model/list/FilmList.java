package ddsociety.com.projet_cinema_clientmobile.model.list;

import java.util.ArrayList;
import java.util.List;

import ddsociety.com.projet_cinema_clientmobile.model.Film;

/**
 * Created by kifkif on 12/10/2017.
 */

public class FilmList {
    private List<Film> films;

    public FilmList() {
        this.films = new ArrayList<>();
    }

    public List<Film> getFilms() {
        return films;
    }

    public void setFilms(List<Film> films) {
        this.films = films;
    }
}
