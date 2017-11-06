package ddsociety.com.projet_cinema_clientmobile.component;

import ddsociety.com.projet_cinema_clientmobile.fragment.HomeFragment;

/**
 * Created by kifkif on 19/10/2017.
 */
//@Singleton
//@Component(dependencies = NetComponent.class, modules = {CinemaModule.class})
public interface CinemaComponent {
    void inject(HomeFragment fragment);
}
