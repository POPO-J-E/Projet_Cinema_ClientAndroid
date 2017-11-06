package ddsociety.com.projet_cinema_clientmobile.component;

import android.content.SharedPreferences;

import javax.inject.Singleton;

import dagger.Component;
import ddsociety.com.projet_cinema_clientmobile.fragment.FilmFragment;
import ddsociety.com.projet_cinema_clientmobile.fragment.FilmListFragment;
import ddsociety.com.projet_cinema_clientmobile.fragment.HomeFragment;
import ddsociety.com.projet_cinema_clientmobile.module.AppModule;
import ddsociety.com.projet_cinema_clientmobile.module.NetModule;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * Created by kifkif on 19/10/2017.
 */
@Singleton
@Component(modules={AppModule.class, NetModule.class})
public interface NetComponent {
    // remove injection methods if downstream modules will perform injection
    void inject(HomeFragment fragment);
    void inject(FilmListFragment fragment);
    void inject(FilmFragment fragment);

    // downstream components need these exposed
    // the method name does not matter, only the return type
    Retrofit retrofit();
    OkHttpClient okHttpClient();
    SharedPreferences sharedPreferences();
//    CinemaService cinemaService();
}
