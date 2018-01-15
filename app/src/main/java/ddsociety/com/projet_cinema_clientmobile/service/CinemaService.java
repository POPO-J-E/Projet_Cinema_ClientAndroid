package ddsociety.com.projet_cinema_clientmobile.service;

/**
 * Created by kifkif on 12/10/2017.
 */

import ddsociety.com.projet_cinema_clientmobile.api.utils.PaginatedResponse;
import ddsociety.com.projet_cinema_clientmobile.model.Film;
import ddsociety.com.projet_cinema_clientmobile.model.list.CategorieList;
import ddsociety.com.projet_cinema_clientmobile.model.list.FilmList;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CinemaService {

//    public static final String ENDPOINT = "http://192.168.42.21:8181";
//    public static final String ENDPOINT = "http://192.168.43.80:8181";
//    public static final String ENDPOINT = "http://192.168.1.26:8181";
    public static final String ENDPOINT = "http://192.168.1.31:8181";
//    public static final String ENDPOINT = "http://52.215.92.221:8181";

    @GET("/films")
    Call<PaginatedResponse<FilmList>> listFilms();

    @GET("/films/{film}?projection=details")
    Call<Film> getFilm(@Path("film") Integer film);

    @GET("/films/{film}")
    Call<Film> getFilm(@Path("film") Integer film, @Query("projection") String projection);

    @POST("/films")
    Call<Object> addFilm(@Body Film film);

    @PATCH("/films/{film}")
    Call<Object> updateFilm(@Path("film") Integer filmId, @Body Film film);

    @DELETE("/films/{film}")
    Call<Object> deleteFilm(@Path("film") Integer filmId);

    @GET("/films/search/findByCategorie_CodeCat")
    Call<PaginatedResponse<FilmList>> listFilmsByCategorie(@Query("categorie") String categorie);

    @GET("/categories")
    Call<PaginatedResponse<CategorieList>> listCategories();
}