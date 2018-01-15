package ddsociety.com.projet_cinema_clientmobile.model;

/**
 * Created by kifkif on 12/10/2017.
 */
public class Personnage {
    private int noPer;
    private String nomPers;

    private Film film;
    private Acteur acteur;

    public int getNoPer() {
        return noPer;
    }

    public void setNoPer(int noPer) {
        this.noPer = noPer;
    }

    public String getNomPers() {
        return nomPers;
    }

    public void setNomPers(String nomPers) {
        this.nomPers = nomPers;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public Acteur getActeur() {
        return acteur;
    }

    public void setActeur(Acteur acteur) {
        this.acteur = acteur;
    }
}
