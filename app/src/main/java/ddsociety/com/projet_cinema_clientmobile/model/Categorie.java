package ddsociety.com.projet_cinema_clientmobile.model;

import java.io.Serializable;

/**
 * Created by kifkif on 12/10/2017.
 */
public class Categorie implements Serializable{
    private String codeCat;
    private String libelleCat;

    public Categorie() {
    }

    public Categorie(String codeCat, String libelleCat) {
        this.codeCat = codeCat;
        this.libelleCat = libelleCat;
    }

    public String getCodeCat() {
        return codeCat;
    }

    public void setCodeCat(String codeCat) {
        this.codeCat = codeCat;
    }

    public String getLibelleCat() {
        return libelleCat;
    }

    public void setLibelleCat(String libelleCat) {
        this.libelleCat = libelleCat;
    }

    @Override
    public String toString() {
        return "Categorie{" +
                "codeCat='" + codeCat + '\'' +
                ", libelleCat='" + libelleCat + '\'' +
                '}';
    }
}
