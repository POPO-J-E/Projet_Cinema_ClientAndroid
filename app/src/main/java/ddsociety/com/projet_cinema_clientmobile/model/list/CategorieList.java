package ddsociety.com.projet_cinema_clientmobile.model.list;

import java.util.ArrayList;
import java.util.List;

import ddsociety.com.projet_cinema_clientmobile.model.Categorie;

/**
 * Created by kifkif on 12/10/2017.
 */

public class CategorieList {
    private List<Categorie> categories;

    public CategorieList() {
        this.categories = new ArrayList<>();
    }

    public List<Categorie> getCategories() {
        return categories;
    }

    public void setCategories(List<Categorie> categories) {
        this.categories = categories;
    }
}
