package ddsociety.com.projet_cinema_clientmobile.api.utils;

import com.google.gson.annotations.SerializedName;

/**
 * Created by kifkif on 12/10/2017.
 */

public class PaginatedResponse<D> {
    @SerializedName("_embedded")
    private D embedded;

    @SerializedName("_links")
    private Links links;

    private Page page;

    public D getEmbedded() {
        return embedded;
    }

    public void setEmbedded(D embedded) {
        this.embedded = embedded;
    }

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }
}
