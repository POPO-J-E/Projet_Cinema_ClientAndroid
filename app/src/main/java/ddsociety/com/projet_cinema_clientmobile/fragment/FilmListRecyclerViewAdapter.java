package ddsociety.com.projet_cinema_clientmobile.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ddsociety.com.projet_cinema_clientmobile.R;
import ddsociety.com.projet_cinema_clientmobile.fragment.FilmListFragment.OnListFragmentInteractionListener;
import ddsociety.com.projet_cinema_clientmobile.model.Film;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Film} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class FilmListRecyclerViewAdapter extends RecyclerView.Adapter<FilmListRecyclerViewAdapter.ViewHolder> {

    private final List<Film> films;
    private final OnListFragmentInteractionListener mListener;

    public FilmListRecyclerViewAdapter(List<Film> films, OnListFragmentInteractionListener listener) {
        this.films = films;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_film_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.film = films.get(position);
        holder.titreView.setText(films.get(position).getTitre());
        holder.infosView.setText(films.get(position).getInfos());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.film);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return films.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView titreView;
        public final TextView infosView;
        public Film film;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            titreView = view.findViewById(R.id.film_titre);
            infosView = view.findViewById(R.id.film_infos);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + titreView.getText() + "'";
        }
    }
}
