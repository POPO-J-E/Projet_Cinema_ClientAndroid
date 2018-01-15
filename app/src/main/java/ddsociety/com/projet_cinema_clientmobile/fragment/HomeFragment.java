package ddsociety.com.projet_cinema_clientmobile.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

import ddsociety.com.projet_cinema_clientmobile.App;
import ddsociety.com.projet_cinema_clientmobile.R;
import ddsociety.com.projet_cinema_clientmobile.api.utils.PaginatedResponse;
import ddsociety.com.projet_cinema_clientmobile.model.Categorie;
import ddsociety.com.projet_cinema_clientmobile.model.list.CategorieList;
import ddsociety.com.projet_cinema_clientmobile.service.CinemaService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    @Inject
    CinemaService cinemaService;

    private OnAddFilminteractionListener mListener;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
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

        ((App)getActivity().getApplication()).getNetComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        // Inflate the layout for this fragment

        view.findViewById(R.id.btn_home_add_film).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddFilmClick();
            }
        });

        addFilmList(null);
        loadCategories();

        return view;
    }

    private void onAddFilmClick() {
        mListener.onAddFilmClickInteraction();
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
        if (context instanceof OnAddFilminteractionListener) {
            mListener = (OnAddFilminteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnAddFilminteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Set title
        updateTitle();
    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser) {
            // Set title
            updateTitle();
        }
    }

    public void updateTitle()
    {
        Activity activity = getActivity();
        if(activity instanceof AppCompatActivity)
        {
            AppCompatActivity appCompatActivity = (AppCompatActivity)activity;
            ActionBar actionBar = appCompatActivity.getSupportActionBar();
            if(actionBar != null)
            {
                actionBar.setTitle(R.string.app_name);
                actionBar.setHomeButtonEnabled(true);
            }
        }
    }

    public interface OnAddFilminteractionListener {
        // TODO: Update argument type and name
        void onAddFilmClickInteraction();
    }
}
