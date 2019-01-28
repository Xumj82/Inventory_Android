package com.adproject.android.inventory.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.adproject.android.inventory.Entity.User;
import com.adproject.android.inventory.R;

import java.util.List;

public class UserAdapter extends BaseAdapter {
    private List<User> userList;
    private Context mContext;
    public UserAdapter(Context mContext, List<User> mList) {
        this.mContext = mContext;
        this.userList = mList;
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public Object getItem(int position) {
        return userList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater _LayoutInflater=LayoutInflater.from(mContext);
        convertView=_LayoutInflater.inflate(R.layout.spinner_row, null);
        if(convertView!=null)
        {
            TextView _TextView1=(TextView)convertView.findViewById(R.id.textDeptRepRow);
            _TextView1.setText(userList.get(position).get("Name"));
        }
        return convertView;
    }
}
