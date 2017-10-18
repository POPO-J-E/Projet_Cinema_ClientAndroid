package ddsociety.com.projet_cinema_clientmobile.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ddsociety.com.projet_cinema_clientmobile.R;
import ddsociety.com.projet_cinema_clientmobile.api.utils.PaginatedResponse;
import ddsociety.com.projet_cinema_clientmobile.model.Categorie;
import ddsociety.com.projet_cinema_clientmobile.model.list.CategorieList;
import ddsociety.com.projet_cinema_clientmobile.service.CinemaService;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        // Inflate the layout for this fragment
        loadCategories();

        return view;
    }

    public void loadCategories()
    {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit.Builder builder =
                new Retrofit.Builder()
                        .baseUrl(CinemaService.ENDPOINT)
                        .addConverterFactory(
                                GsonConverterFactory.create()
                        );
        Retrofit retrofit =
                builder
                        .client(
                                httpClient.build()
                        )
                        .build();

        // Create a very simple REST adapter which points the GitHub API endpoint.
        CinemaService cinemaService =  retrofit.create(CinemaService.class);
        Call<PaginatedResponse<CategorieList>> call;
        // Fetch a list of the Github repositories.
        call = cinemaService.listCategories();

        // Execute the call asynchronously. Get a positive or negative callback.
        call.enqueue(new Callback<PaginatedResponse<CategorieList>>() {

            @Override
            public void onResponse(Call<PaginatedResponse<CategorieList>> call, Response<PaginatedResponse<CategorieList>> response) {
                PaginatedResponse<CategorieList> page = response.body();

                if(page != null)
                {
                    CategorieList categories = page.getEmbedded();
                    afficherCategories(categories.getCategories());
                }
            }

            @Override
            public void onFailure(Call<PaginatedResponse<CategorieList>> call, Throwable t) {
                System.out.println("fail");
                t.printStackTrace();
                call.cancel();
            }
        });
    }

    private void afficherCategories(List<Categorie> categories) {
        for (Categorie categorie : categories)
        {
            addFilmList(categorie);
        }
    }

    public void addFilmList(Categorie categorie)
    {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        FilmListFragment fragment = FilmListFragment.newInstance(categorie);
        fragmentTransaction.add(R.id.fragment_list_container, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
