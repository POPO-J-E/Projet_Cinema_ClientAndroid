package ddsociety.com.projet_cinema_clientmobile;

import android.app.Application;

import ddsociety.com.projet_cinema_clientmobile.component.CinemaComponent;
import ddsociety.com.projet_cinema_clientmobile.component.DaggerNetComponent;
import ddsociety.com.projet_cinema_clientmobile.component.NetComponent;
import ddsociety.com.projet_cinema_clientmobile.module.AppModule;
import ddsociety.com.projet_cinema_clientmobile.module.NetModule;
import ddsociety.com.projet_cinema_clientmobile.service.CinemaService;

/**
 * Created by kifkif on 19/10/2017.
 */

public class App extends Application {

    private NetComponent mNetComponent;
    private CinemaComponent cinemaComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        // Dagger%COMPONENT_NAME%
        mNetComponent = DaggerNetComponent.builder()
                // list of modules that are part of this component need to be created here too
                .appModule(new AppModule(this)) // This also corresponds to the name of your module: %component_name%Module
                .netModule(new NetModule(CinemaService.ENDPOINT))
                .build();

//        cinemaComponent = DaggerCinemaComponent.builder()
//                .netComponent(mNetComponent)
//                .cinemaModule(new CinemaModule())
//                .build();
        // If a Dagger 2 component does not have any constructor arguments for any of its modules,
        // then we can use .create() as a shortcut instead:
        //  mNetComponent = com.codepath.dagger.components.DaggerNetComponent.create();
    }

    public NetComponent getNetComponent() {
        return mNetComponent;
    }

    public CinemaComponent getCinemaComponent() {
        return cinemaComponent;
    }
}