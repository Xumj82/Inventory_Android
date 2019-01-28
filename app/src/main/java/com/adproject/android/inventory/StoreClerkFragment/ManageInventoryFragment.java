package com.adproject.android.inventory.StoreClerkFragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adproject.android.inventory.Adapter.InventoryAdapter;
import com.adproject.android.inventory.Entity.Catalogue;
import com.adproject.android.inventory.R;

import java.util.ArrayList;
import java.util.List;

public class ManageInventoryFragment extends ListFragment {
    public static final String TAG = "content";

    public static ManageInventoryFragment newInstance(String content) {
        ManageInventoryFragment fragment = new ManageInventoryFragment();
        Bundle args = new Bundle();
        args.putString(TAG, content);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.storeclerk_fragment_inventory, container, false);
        getActivity().setTitle("Inventory List");
        getCatalogue();
        return(v);
    }

    private  void  getCatalogue(){
        new AsyncTask<Void, Void, List<Catalogue>>() {
            @Override
            protected List<Catalogue> doInBackground(Void... voids) {
                List<Catalogue> catalogueList = new ArrayList<Catalogue>();
                catalogueList = Catalogue.listCatalogues();
                return  catalogueList;
            }

            @Override
            protected void onPostExecute(List<Catalogue> result) {
                try {
                    InventoryAdapter adapter = new InventoryAdapter(getActivity(),result);
                    setListAdapter(adapter);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }.execute();
    }
}
