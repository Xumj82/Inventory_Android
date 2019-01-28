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

import com.adproject.android.inventory.Adapter.RetrievalListAdapter;
import com.adproject.android.inventory.Entity.Retrieval;
import com.adproject.android.inventory.R;
import com.adproject.android.inventory.RetrievalDetailsActivity;

import java.util.List;

public class RetrievalFragment extends Fragment {
    private View view;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle("Retrieval");
        final Intent intent = new Intent(getActivity(),RetrievalDetailsActivity.class);
        ListView listView = getActivity().findViewById(R.id.listRetireval);
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
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.storeclerk_fragment_retrieval, container, false);
        this.GetRetrievals();
        return view;
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
                    RetrievalListAdapter retrievalListAdapter = new RetrievalListAdapter(getActivity(), retrievals);
                    ListView listView = getActivity().findViewById(R.id.listRetireval);
                    listView.setAdapter(retrievalListAdapter);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }.execute();
    }

}
