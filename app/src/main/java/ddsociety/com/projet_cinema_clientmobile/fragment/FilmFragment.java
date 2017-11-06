package ddsociety.com.projet_cinema_clientmobile.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import javax.inject.Inject;

import ddsociety.com.projet_cinema_clientmobile.App;
import ddsociety.com.projet_cinema_clientmobile.R;
import ddsociety.com.projet_cinema_clientmobile.model.Film;
import ddsociety.com.projet_cinema_clientmobile.service.CinemaService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FilmFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FilmFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FilmFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_FILM = "film";
    // TODO: Rename and change types of parameters
    private int noFilm;
    private Film film;

    private OnFragmentInteractionListener mListener;
    private View view;

    @Inject
    CinemaService cinemaService;

    public FilmFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param film Parameter 1.
     * @return A new instance of fragment FilmFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FilmFragment newInstance(Film film) {
        return newInstance(film.getNoFilm());
    }

    // TODO: Rename and change types and number of parameters
    public static FilmFragment newInstance(int film) {
        FilmFragment fragment = new FilmFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_FILM, film);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((App)getActivity().getApplication()).getNetComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_film, container, false);

        if (getArguments() != null) {
            noFilm = getArguments().getInt(ARG_FILM);

            loadFilm();
        }

        return view;
    }

    private void loadFilm() {
        Call<Film> call;
        call = cinemaService.getFilm(noFilm);

        // Execute the call asynchronously. Get a positive or negative callback.
        call.enqueue(new Callback<Film>() {

            @Override
            public void onResponse(Call<Film> call, Response<Film> response) {
                Film film = response.body();

                if(film != null)
                {
                    afficherFilm(film);
                }
            }

            @Override
            public void onFailure(Call<Film> call, Throwable t) {
                System.out.println("fail");
                t.printStackTrace();
                call.cancel();
            }
        });
    }

    public void afficherFilm(Film film)
    {
        this.film = film;
        System.out.println(film.getTitre());

        ((TextView)view.findViewById(R.id.film_titre)).setText(film.getTitre());
        ((TextView)view.findViewById(R.id.film_infos)).setText(film.getInfos());
        ((TextView)view.findViewById(R.id.film_categorie)).setText(film.getCategorie().getLibelleCat());
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonBackPressed() {
        if (mListener != null) {
            mListener.onFragmentBackInteraction();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentBackInteraction();
    }
}
