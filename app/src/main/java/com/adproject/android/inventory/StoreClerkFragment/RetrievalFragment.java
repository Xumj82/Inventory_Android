package com.adproject.android.inventory.StoreClerkFragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import com.adproject.android.inventory.Adapter.RetrievalListAdapter;
import com.adproject.android.inventory.Entity.Retrieval;
import com.adproject.android.inventory.R;
import com.adproject.android.inventory.StoreClerkActivities.RetrievalDetailsActivity;

import java.util.ArrayList;
import java.util.List;

public class RetrievalFragment extends Fragment {
    private View view;
    ListView listView;
    static List<Retrieval> retrievalList;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        NavigationView navigationView = (NavigationView) getActivity().findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(1).setChecked(true);
        getActivity().setTitle("Retrieval");
        final Intent intent = new Intent(getActivity(),RetrievalDetailsActivity.class);
        listView = getActivity().findViewById(R.id.listRetireval);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Retrieval retrieval = (Retrieval)parent.getAdapter().getItem(position);
                intent.putExtra("requestId",retrieval.get("requestId"));
                intent.putExtra("itemDescription",retrieval.get("itemDescription"));
                intent.putExtra("neededQuantity",retrieval.get("neededQuantity"));
                intent.putExtra("availableQuantity",retrieval.get("availableQuantity"));
                intent.putExtra("binNumber",retrieval.get("binNumber"));
                intent.putExtra("remarks",retrieval.get("remarks"));
                intent.putExtra("orderid",retrieval.get("orderid"));
                startActivity(intent);
            }
        });
        final List<Retrieval> retrievals = new ArrayList<>();
        final SearchView searchView = getActivity().findViewById(R.id.searchRetrieval);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(!(retrievalList==null)){
                    retrievals.clear();
                    for(Retrieval retrieval : retrievalList){
                        if(retrieval.get("requestId").contains(searchView.getQuery().toString())||retrieval.get("itemDescription").contains(searchView.getQuery().toString())){
                            retrievals.add(retrieval);
                        }
                    }
                    try {
                        RetrievalListAdapter retrievalListAdapter = new RetrievalListAdapter(getActivity(), retrievals);
                        listView.setAdapter(retrievalListAdapter);
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.storeclerk_fragment_retrieval, container, false);
        GetRetrievals();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        GetRetrievals();
    }

    void GetRetrievals(){
        new AsyncTask<Void,Void,List<Retrieval>>(){

            @Override
            protected List<Retrieval> doInBackground(Void... voids) {
                return Retrieval.GetRetrievals();
            }

            @Override
            protected void onPostExecute(List<Retrieval> retrievals) {
                try {
                    retrievalList = retrievals;
                    RetrievalListAdapter retrievalListAdapter = new RetrievalListAdapter(getActivity(), retrievals);
                    listView = getActivity().findViewById(R.id.listRetireval);
                    listView.setAdapter(retrievalListAdapter);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }.execute();
    }

}
