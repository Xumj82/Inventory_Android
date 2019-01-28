package com.adproject.android.inventory.StoreClerkFragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.adproject.android.inventory.Adapter.InventoryAdapter;
import com.adproject.android.inventory.Adapter.RequestAdapter;
import com.adproject.android.inventory.DeptHeadFragments.RequestFragment;
import com.adproject.android.inventory.EditCatalogueActivity;
import com.adproject.android.inventory.Entity.Catalogue;
import com.adproject.android.inventory.Entity.Request;
import com.adproject.android.inventory.R;
import com.adproject.android.inventory.StoreClerkActivity;

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
