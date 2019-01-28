package com.adproject.android.inventory.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.adproject.android.inventory.Entity.Catalogue;
import com.adproject.android.inventory.R;

import java.util.List;

public class CatalogueAdapter extends BaseAdapter {
    private List<Catalogue> catalogues;
    private Context mContext;

    public CatalogueAdapter(Context mContext, List<Catalogue> catalogues) {
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
        convertView=_LayoutInflater.inflate(R.layout.storeclerk_catalogue_row, null);
        if(convertView!=null)
        {
            TextView _TextView1=(TextView)convertView.findViewById(R.id.textitemDescription);
            _TextView1.setText(catalogues.get(position).get("Description"));
            TextView _TextView2=(TextView)convertView.findViewById(R.id.textquantity);
            _TextView2.setText(catalogues.get(position).get("Quantity"));
            TextView _TextView3=(TextView)convertView.findViewById(R.id.textuom);
            _TextView3.setText(catalogues.get(position).get("MeasureUnit"));
        }
        return convertView;
    }
}
