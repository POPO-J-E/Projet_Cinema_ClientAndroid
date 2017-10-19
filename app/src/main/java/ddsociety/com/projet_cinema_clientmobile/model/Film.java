package ddsociety.com.projet_cinema_clientmobile.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by kifkif on 12/10/2017.
 */
public class Film implements Serializable{
    private int noFilm;
    private int budget;
    private Date dateSortie;
    private int duree;
    private int montantRecette;
    private String titre;

    private Realisateur realisateur;
    private Categorie categorie;
    private List<Personnage> personnages;

    public int getNoFilm() {
        return noFilm;
    }

    public void setNoFilm(int noFilm) {
        this.noFilm = noFilm;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public Date getDateSortie() {
        return dateSortie;
    }

    public void setDateSortie(Date dateSortie) {
        this.dateSortie = dateSortie;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public int getMontantRecette() {
        return montantRecette;
    }

    public void setMontantRecette(int montantRecette) {
        this.montantRecette = montantRecette;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public Realisateur getRealisateur() {
        return realisateur;
    }

    public void setRealisateur(Realisateur realisateur) {
        this.realisateur = realisateur;
    }

    public List<Personnage> getPersonnages() {
        return personnages;
    }

    public void setPersonnages(List<Personnage> personnages) {
        this.personnages = personnages;
    }

    public String getInfos()
    {
        return String.format("%s, %d min", (new SimpleDateFormat("yyyy")).format(getDateSortie()), getDuree());
    }
}
