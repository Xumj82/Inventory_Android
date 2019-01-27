package com.adproject.android.inventory.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.adproject.android.inventory.Entity.Retrieval;
import com.adproject.android.inventory.R;

import java.util.List;

public class RetrievalListAdapter extends BaseAdapter {
    protected Context context;
    protected List<Retrieval> retrievals;
    public RetrievalListAdapter(Activity activity,List<Retrieval> retrievalList){
        context = activity;
        retrievals = retrievalList;
    }
    @Override
    public int getCount() {
        return retrievals.size();
    }

    @Override
    public Retrieval getItem(int position) {
        return retrievals.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater _LayoutInflater=LayoutInflater.from(context);
        convertView=_LayoutInflater.inflate(R.layout.storeclerk_retrieval_listrow, null);
        if(convertView!=null)
        {
            TextView _TextView1=(TextView)convertView.findViewById(R.id.textView11);
            _TextView1.setText(retrievals.get(position).get("requestId"));
            TextView _TextView2=(TextView)convertView.findViewById(R.id.textView12);
            _TextView2.setText(retrievals.get(position).get("itemDescription"));
        }
        return convertView;
    }

}
