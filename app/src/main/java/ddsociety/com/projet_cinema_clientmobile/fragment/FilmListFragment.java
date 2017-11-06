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
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ddsociety.com.projet_cinema_clientmobile.App;
import ddsociety.com.projet_cinema_clientmobile.R;
import ddsociety.com.projet_cinema_clientmobile.api.utils.PaginatedResponse;
import ddsociety.com.projet_cinema_clientmobile.model.Categorie;
import ddsociety.com.projet_cinema_clientmobile.model.Film;
import ddsociety.com.projet_cinema_clientmobile.model.list.FilmList;
import ddsociety.com.projet_cinema_clientmobile.service.CinemaService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class FilmListFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String ARG_CATEGORIE = "categorie";
    private static final String ARG_FILMS = "films";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private Categorie categorie;
    private List<Film> films;
    private OnListFragmentInteractionListener mListener;
    private FilmListRecyclerViewAdapter recyclerViewAdapter;
    private RecyclerView recyclerView;

    @Inject
    CinemaService cinemaService;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FilmListFragment() {
    }

    public static FilmListFragment newInstance(Categorie categorie) {
        return newInstance(1, categorie);
    }

    public static FilmListFragment newInstance() {
        return newInstance(1, null);
    }

    public static FilmListFragment newInstance(int columnCount, Categorie categorie) {
        FilmListFragment fragment = new FilmListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        args.putSerializable(ARG_CATEGORIE, categorie);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((App)getActivity().getApplication()).getNetComponent().inject(this);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
            categorie = (Categorie) getArguments().getSerializable(ARG_CATEGORIE);
        }
    }

    public void loadFilms()
    {
        Call<PaginatedResponse<FilmList>> call;
        // Fetch a list of the Github repositories.
        if(categorie != null)
            call = cinemaService.listFilmsByCategorie(categorie.getCodeCat());
        else
            call = cinemaService.listFilms();

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
        if (this.films != null) {
            this.films.clear();
            this.films.addAll(films);
        }
        else {
            this.films = films;
        }
        if(getArguments() != null)
        {
            this.getArguments().putSerializable(ARG_FILMS, (Serializable) this.films);
        }
        this.recyclerViewAdapter.notifyDataSetChanged();
//        recyclerViewAdapter = new FilmListRecyclerViewAdapter(this.films, mListener);
//        recyclerView.setAdapter(recyclerViewAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_film_list, container, false);
        TextView titleView = (TextView) view.findViewById(R.id.list_title);
        titleView.setText((categorie != null) ? categorie.getLibelleCat() : "Dernier films ajoutés");

        View rView = view.findViewById(R.id.film_list);
        // Set the adapter
        if (rView instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) rView;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            this.films = new ArrayList<>();

            recyclerViewAdapter = new FilmListRecyclerViewAdapter(this.films, mListener);
            recyclerView.setAdapter(recyclerViewAdapter);

            if (getArguments() != null && savedInstanceState != null)
            {
                afficherFilms((List<Film>)getArguments().getSerializable(ARG_FILMS));
            }
            else
            {
//                films = new ArrayList<>();
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
