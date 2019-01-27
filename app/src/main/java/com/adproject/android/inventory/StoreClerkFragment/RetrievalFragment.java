package com.adproject.android.inventory.StoreClerkFragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.adproject.android.inventory.Adapter.RetrievalListAdapter;
import com.adproject.android.inventory.Entity.Retrieval;
import com.adproject.android.inventory.R;

import java.util.List;

public class RetrievalFragment extends Fragment {
    private View view;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle("Retrieval");
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
