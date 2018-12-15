package info.juanmendez.android.customloader.model;

import java.util.ArrayList;

/**
 * Created by Juan on 7/15/2015.
 */
public class GithubSearch
{
    public int getTotal_count() {
        return total_count;
    }

    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }

    public Boolean getIncomplete_results() {
        return incomplete_results;
    }

    public void setIncomplete_results(Boolean incomplete_results) {
        this.incomplete_results = incomplete_results;
    }

    public ArrayList<Repo> getItems() {
        return items;
    }

    public void setItems(ArrayList<Repo> items) {
        this.items = items;
    }

    private int total_count = 0;
    private Boolean incomplete_results = false;
    private ArrayList<Repo> items;
}
