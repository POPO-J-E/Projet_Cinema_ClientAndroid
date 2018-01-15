package ddsociety.com.projet_cinema_clientmobile.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import ddsociety.com.projet_cinema_clientmobile.App;
import ddsociety.com.projet_cinema_clientmobile.R;
import ddsociety.com.projet_cinema_clientmobile.api.utils.PaginatedResponse;
import ddsociety.com.projet_cinema_clientmobile.model.Categorie;
import ddsociety.com.projet_cinema_clientmobile.model.Film;
import ddsociety.com.projet_cinema_clientmobile.model.list.CategorieList;
import ddsociety.com.projet_cinema_clientmobile.service.CinemaService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FilmFormFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FilmFormFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FilmFormFragment extends Fragment {
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

    private boolean create;
    private SpinAdapter adapter;

    public FilmFormFragment() {
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
    public static FilmFormFragment newInstance(Film film) {
        return newInstance(film.getNoFilm());
    }

    // TODO: Rename and change types and number of parameters
    public static FilmFormFragment newInstance(int film) {
        FilmFormFragment fragment = new FilmFormFragment();
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
        view = inflater.inflate(R.layout.fragment_film_form, container, false);

        if (getArguments() != null) {
            noFilm = getArguments().getInt(ARG_FILM);

            if(noFilm != 0)
            {
                create = false;
                loadFilm();
            }
            else
            {
                create = true;
                afficherFilm(new Film());
            }
        }

        return view;
    }

    private void loadFilm() {
        Call<Film> call;
        call = cinemaService.getFilm(noFilm);
        System.out.println(noFilm);
        System.out.println(call);

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

    public void afficherFilm(final Film film)
    {
        this.film = film;
        System.out.println(film);
        Categorie[] categories;

        ((EditText)view.findViewById(R.id.film_form_titre)).setText(film.getTitre());
        ((EditText)view.findViewById(R.id.film_form_budget)).setText(String.valueOf(film.getBudget()));
        ((EditText)view.findViewById(R.id.film_form_duree)).setText(String.valueOf(film.getDuree()));
        ((EditText)view.findViewById(R.id.film_form_recette)).setText(String.valueOf(film.getMontantRecette()));

        loadCategories();

//        ((EditText)view.findViewById(R.id.film_form_sortie)).setText(film.getDateSortie().);
        ((Button)view.findViewById(R.id.film_form_submit)).setText(create ? "Ajouter" : "Modifier");
        ((Button)view.findViewById(R.id.film_form_submit)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonSubmitPressed();
            }
        });
    }

    public void loadCategories()
    {
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
        Categorie[] cats = new Categorie[categories.size()];
        cats = categories.toArray(cats);
        Spinner spinner = ((Spinner)view.findViewById(R.id.film_form_categorie));
        adapter = new SpinAdapter(this.getContext(),
                android.R.layout.simple_spinner_item,
                cats);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Here you get the current item (a User object) that is selected by its position
                Categorie categorie = adapter.getItem(position);
                // Here you can do the action you want to...
                film.setCategorie(categorie);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonSubmitPressed() {

        film.setTitre(((EditText)view.findViewById(R.id.film_form_titre)).getText().toString());
        film.setBudget(Integer.valueOf(((EditText)view.findViewById(R.id.film_form_budget)).getText().toString()));
        film.setDuree(Integer.valueOf(((EditText)view.findViewById(R.id.film_form_duree)).getText().toString()));
        film.setMontantRecette(Integer.valueOf(((EditText)view.findViewById(R.id.film_form_recette)).getText().toString()));

        System.out.println(film);

        Call call;
        call = create ? cinemaService.addFilm(film) : cinemaService.updateFilm(noFilm, film);
        System.out.println(noFilm);
        System.out.println(call);

        // Execute the call asynchronously. Get a positive or negative callback.
        call.enqueue(new Callback<Film>() {

            @Override
            public void onResponse(Call call, Response response) {
                System.out.println(response.body());
                if (mListener != null) {
                    mListener.onFragmentBackInteraction();
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

    public class SpinAdapter extends ArrayAdapter<Categorie>{

        // Your sent context
        private Context context;
        // Your custom values for the spinner (User)
        private Categorie[] values;

        public SpinAdapter(Context context, int textViewResourceId,
                           Categorie[] values) {
            super(context, textViewResourceId, values);
            this.context = context;
            this.values = values;
        }

        @Override
        public int getCount(){
            return values.length;
        }

        @Override
        public Categorie getItem(int position){
            return values[position];
        }

        @Override
        public long getItemId(int position){
            return position;
        }


        // And the "magic" goes here
        // This is for the "passive" state of the spinner
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // I created a dynamic TextView here, but you can reference your own  custom layout for each spinner item
            TextView label = new TextView(context);
            label.setTextColor(Color.BLACK);
            // Then you can get the current item using the values array (Users array) and the current position
            // You can NOW reference each method you has created in your bean object (User class)
            label.setText(values[position].getLibelleCat());

            // And finally return your dynamic (or custom) view for each spinner item
            return label;
        }

        // And here is when the "chooser" is popped up
        // Normally is the same view, but you can customize it if you want
        @Override
        public View getDropDownView(int position, View convertView,
                                    ViewGroup parent) {
            TextView label = new TextView(context);
            label.setTextColor(Color.BLACK);
            label.setText(values[position].getLibelleCat());

            return label;
        }
    }
}
