package com.adproject.android.inventory.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.adproject.android.inventory.Entity.Request;
import com.adproject.android.inventory.R;

import java.util.List;

public class RequestAdapter extends BaseAdapter implements Filterable {
    private List<Request> requests;
    private Context mContext;

    public RequestAdapter(Context mContext, List<Request> mList) {
        this.mContext = mContext;
        this.requests = mList;
    }
    @Override
    public int getCount() {
        return requests.size();
    }

    @Override
    public Object getItem(int position) {
        return requests.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater _LayoutInflater=LayoutInflater.from(mContext);
        convertView=_LayoutInflater.inflate(R.layout.depthead_request_row, null);
        if(convertView!=null)
        {
            TextView _TextView1=(TextView)convertView.findViewById(R.id.requestrow1);
            _TextView1.setText(requests.get(position).get("Employee"));
            TextView _TextView2=(TextView)convertView.findViewById(R.id.requestrow2);

            _TextView2.setText(requests.get(position).get("RequestDate"));
        }
        return convertView;
    }

    @Override
    public Filter getFilter() {
        return null;
    }
}
