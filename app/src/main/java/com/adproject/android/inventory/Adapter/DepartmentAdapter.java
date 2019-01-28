package com.adproject.android.inventory.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.adproject.android.inventory.Entity.Department;
import com.adproject.android.inventory.R;

import java.util.List;

public class DepartmentAdapter extends BaseAdapter {
    Activity activity;
    List<Department> departments;

    public DepartmentAdapter(Activity activity, List<Department> departments){
        this.activity = activity;
        this.departments = departments;
    }
    @Override
    public int getCount() {
        return departments.size();
    }

    @Override
    public Department getItem(int position) {
        return departments.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater _LayoutInflater=LayoutInflater.from(activity);
        convertView=_LayoutInflater.inflate(R.layout.spinner_row, null);
        if(convertView!=null)
        {
            TextView _TextView1=(TextView)convertView.findViewById(R.id.textDeptRepRow);
            _TextView1.setText(departments.get(position).get("DepartmentName").trim());

        }
        return convertView;
    }
}
