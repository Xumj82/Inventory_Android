package com.adproject.android.inventory.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.adproject.android.inventory.Entity.Catalogue;
import com.adproject.android.inventory.Entity.Request;
import com.adproject.android.inventory.R;

import java.util.List;

public class InventoryAdapter extends BaseAdapter implements Filterable {
        private List<Catalogue> catalogues;
        private Context mContext;

        public InventoryAdapter(Context mContext, List<Catalogue> catalogues) {
            this.mContext = mContext;
            this.catalogues = catalogues;
        }
        @Override
        public int getCount() {
            return catalogues.size();
        }

        @Override
        public Object getItem(int position) {
            return catalogues.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater _LayoutInflater=LayoutInflater.from(mContext);
            convertView=_LayoutInflater.inflate(R.layout.storeclerk_inventory_row, null);
            if(convertView!=null)
            {
                TextView _TextView1=(TextView)convertView.findViewById(R.id.inventoryrow2);
                _TextView1.setText(catalogues.get(position).get("Description"));
                TextView _TextView2=(TextView)convertView.findViewById(R.id.inventoryrow1);

                _TextView2.setText(catalogues.get(position).get("ItemID"));
            }
            return convertView;
        }

        @Override
        public Filter getFilter() {
            return null;
        }
    }


