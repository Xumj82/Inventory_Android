package com.adproject.android.inventory.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.adproject.android.inventory.Entity.Request;
import com.adproject.android.inventory.R;

import java.util.List;

public class RequestItemAdapter extends BaseAdapter {
    private List<Request> requests;
    private Context mContext;

    public RequestItemAdapter(Context mContext, List<Request> mList) {
        this.mContext = mContext;
        requests = mList;
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
        convertView=_LayoutInflater.inflate(R.layout.depthead_item_row, null);
        if(convertView!=null)
        {
            TextView _TextView1=(TextView)convertView.findViewById(R.id.textDetailItemName);
            _TextView1.setText(requests.get(position).get("Description"));
            TextView _TextView2=(TextView)convertView.findViewById(R.id.textDetialItemQty);
            _TextView2.setText(requests.get(position).get("Qty"));
            TextView _TextView3=(TextView)convertView.findViewById(R.id.textView2);
            _TextView3.setText(requests.get(position).get("Status"));
        }
        return convertView;
    }

}
