package ddsociety.com.projet_cinema_clientmobile.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ddsociety.com.projet_cinema_clientmobile.R;
import ddsociety.com.projet_cinema_clientmobile.api.utils.PaginatedResponse;
import ddsociety.com.projet_cinema_clientmobile.model.Film;
import ddsociety.com.projet_cinema_clientmobile.model.list.FilmList;
import ddsociety.com.projet_cinema_clientmobile.service.CinemaService;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class FilmListFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String ARG_FILMS = "films";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private List<Film> films;
    private OnListFragmentInteractionListener mListener;
    private FilmListRecyclerViewAdapter recyclerViewAdapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FilmListFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static FilmListFragment newInstance(int columnCount) {
        FilmListFragment fragment = new FilmListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    public void loadFilms()
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

        // Fetch a list of the Github repositories.
        Call<PaginatedResponse<FilmList>> call = cinemaService.listFilms();

        // Execute the call asynchronously. Get a positive or negative callback.
        call.enqueue(new Callback<PaginatedResponse<FilmList>>() {

            @Override
            public void onResponse(Call<PaginatedResponse<FilmList>> call, Response<PaginatedResponse<FilmList>> response) {
                PaginatedResponse<FilmList> page = response.body();

                if(page != null)
                {
                    FilmList films = page.getEmbedded();
                    afficherFilms(films.getFilms());
                }
            }

            @Override
            public void onFailure(Call<PaginatedResponse<FilmList>> call, Throwable t) {
                System.out.println("fail");
                t.printStackTrace();
                call.cancel();
            }
        });
    }

    public void afficherFilms(List<Film> films) {
        this.films = films;
        this.recyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_film_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            boolean filmsLoaded = false;
            if (getArguments() != null && savedInstanceState != null)
            {
                films = (List<Film>)getArguments().getSerializable(ARG_FILMS);
                filmsLoaded = true;
            }
            else
            {
                films = new ArrayList<>();
            }

            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerViewAdapter = new FilmListRecyclerViewAdapter(this.films, mListener);
            recyclerView.setAdapter(recyclerViewAdapter);

            if (!filmsLoaded)
            {
                loadFilms();
            }
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Film film);
    }
}
