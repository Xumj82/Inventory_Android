//package com.adproject.android.inventory.Filter;
//
//
//import android.widget.Filter;
//
//import com.adproject.android.inventory.Entity.Retrieval;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class Myfilter extends Filter {
//
//    @Override
//    protected FilterResults performFiltering(CharSequence constraint) {
//        FilterResults results = new FilterResults();
//
//        List<Retrieval> newValues = new ArrayList<Retrieval>();
//        String filterString = constraint.toString().trim()
//                .toLowerCase();
//
//        // 如果搜索框内容为空，就恢复原始数据
//        if (TextUtils.isEmpty(filterString)) {
//            newValues = mBackData;
//        } else {
//            // 过滤出新数据
//            for (ListBean str : mBackData) {
//                if (-1 != str.getJwsmc().toLowerCase()
//                        .indexOf(filterString)) {
//                    newValues.add(str);
//                }
//            }
//        }
//
//        results.values = newValues;
//        results.count = newValues.size();
//
//        return results;
//    }
//
//    @Override
//    protected void publishResults(CharSequence constraint, FilterResults results) {
//        mData = (List<ListBean>) results.values;
//
//        if (results.count > 0) {
//            mAdapter.notifyDataSetChanged(); // 通知数据发生了改变
//        } else {
//            mAdapter.notifyDataSetInvalidated(); // 通知数据失效
//        }
//    }
//}
