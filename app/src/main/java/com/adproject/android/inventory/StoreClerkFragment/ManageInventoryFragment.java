package com.adproject.android.inventory.StoreClerkFragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.adproject.android.inventory.Adapter.InventoryAdapter;
import com.adproject.android.inventory.StoreClerkActivities.EditCatalogueActivity;
import com.adproject.android.inventory.Entity.Catalogue;
import com.adproject.android.inventory.R;

import java.util.ArrayList;
import java.util.List;

public class ManageInventoryFragment extends ListFragment {
    public static final String TAG = "content";
    static List<Catalogue> catalogues;

    public static ManageInventoryFragment newInstance(String content) {
        ManageInventoryFragment fragment = new ManageInventoryFragment();
        Bundle args = new Bundle();
        args.putString(TAG, content);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.storeclerk_fragment_inventory, container, false);
        getActivity().setTitle("Inventory List");
        return(v);
    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        NavigationView navigationView = (NavigationView) getActivity().findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(3).setChecked(true);
        final SearchView searchView = getActivity().findViewById(R.id.searchView);
        final List<Catalogue> catalogueList = new ArrayList<>();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(!(catalogues==null)) {
                        catalogueList.clear();
                        for (Catalogue catalogue : catalogues) {
                            if(catalogue.get("Description").contains(searchView.getQuery().toString())){
                                catalogueList.add(catalogue);
                            }
                        }
                        try {
                            InventoryAdapter adapter = new InventoryAdapter(getContext(), catalogueList);
                            setListAdapter(adapter);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }


    @Override
    public void onResume() {
        super.onResume();
        getCatalogue();
    }

    public void onListItemClick(ListView l, View v, int position, long id) {
        Catalogue selected = (Catalogue) getListAdapter().getItem(position);
        Intent intent = new Intent(this.getActivity(), EditCatalogueActivity.class);
        intent.putExtra("ItemID", selected.get("ItemID"));
        intent.putExtra("Description", selected.get("Description"));
        intent.putExtra("Quantity", selected.get("Quantity"));
        intent.putExtra("Category", selected.get("Category"));
        intent.putExtra("MeasureUnit", selected.get("MeasureUnit"));
        intent.putExtra("Price", selected.get("Price"));
        intent.putExtra("BinNumber", selected.get("BinNumber"));
        startActivity(intent);
    }

    private  void  getCatalogue(){
        new AsyncTask<Void, Integer, List<Catalogue>>() {
            @Override
            protected List<Catalogue> doInBackground(Void... voids) {
                publishProgress(View.VISIBLE);
                List<Catalogue> catalogueList = new ArrayList<Catalogue>();
                catalogueList = Catalogue.listCatalogues();
                publishProgress(View.INVISIBLE);
                 return catalogueList;
            }

            @Override
            protected void onPostExecute(List<Catalogue> result) {
                try {
                        catalogues = result;
                        InventoryAdapter adapter = new InventoryAdapter(getActivity(), result);
                        setListAdapter(adapter);

                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                ProgressBar progressBar = getActivity().findViewById(R.id.progressBar2);
                progressBar.setVisibility(values[0]);
            }

        }.execute();
    }
}
