package ddsociety.com.projet_cinema_clientmobile.service;

/**
 * Created by kifkif on 12/10/2017.
 */

import ddsociety.com.projet_cinema_clientmobile.api.utils.PaginatedResponse;
import ddsociety.com.projet_cinema_clientmobile.model.Film;
import ddsociety.com.projet_cinema_clientmobile.model.list.CategorieList;
import ddsociety.com.projet_cinema_clientmobile.model.list.FilmList;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CinemaService {

//    public static final String ENDPOINT = "http://192.168.1.26:8181";
    public static final String ENDPOINT = "http://192.168.1.26:8181";

    @GET("/films")
    Call<PaginatedResponse<FilmList>> listFilms();

    @GET("/films/{film}?projection=details")
    Call<Film> getFilm(@Query("film") Integer film);

    @GET("/films/{film}")
    Call<Film> getFilm(@Query("film") Integer film, @Query("projection") String projection);

    @GET("/films/search/findByCategorie_CodeCat")
    Call<PaginatedResponse<FilmList>> listFilmsByCategorie(@Query("categorie") String categorie);

    @GET("/categories")
    Call<PaginatedResponse<CategorieList>> listCategories();
}