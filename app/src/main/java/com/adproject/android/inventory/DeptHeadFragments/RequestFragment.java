package com.adproject.android.inventory.DeptHeadFragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.adproject.android.inventory.Adapter.RequestAdapter;
import com.adproject.android.inventory.Entity.Request;
import com.adproject.android.inventory.R;

import java.util.ArrayList;
import java.util.List;

public class RequestFragment extends ListFragment{
    public static final String TAG = "content";
    private View view;
    private TextView textView;
    private String content;
    private String userid;
    String deptid;
    List<Request> requests;

    public static RequestFragment newInstance(String content) {
        RequestFragment fragment = new RequestFragment();
        Bundle args = new Bundle();
        args.putString(TAG, content);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        content = bundle != null ? bundle.getString(TAG) : "";

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.depthead_fragment_request, container, false);
        deptid = getActivity().getIntent().getExtras().getString("dept");
        getActivity().setTitle("Request List");
        getRequests(deptid);
        return view;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Request request = (Request) getListAdapter().getItem(position);
        display(request);
    }

    void display(Request details) {
        final String TAG = "DETAILS";
        getActivity().setTitle("Request Details");
        FragmentManager fm = getFragmentManager();
        FragmentTransaction trans = fm.beginTransaction();
        Fragment frag = new DetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable("details", details);
        frag.setArguments(args);
        trans.replace(R.id.conten_frame, frag, TAG);
        trans.addToBackStack(TAG).commit();
    }
    private void init() {

    }

    public void getRequests(String id){
        new AsyncTask<String, Integer, List<Request>>() {
            @Override
            protected List<Request> doInBackground(String... params) {
                List<Request> l1 = new ArrayList<>();
                List<Request> l2 = new ArrayList<>();
                List<Request> l3 = new ArrayList<>();
                l1 = Request.ReadOrderByDept(params[0]);
                for(Request r : l1){
                    l2 = Request.ReadRequestByOrderIDUserID(r.get("OrderID"),r.get("UserName"));
                    if(!(l2.size()==0)){
                        l3.add(r);
                    }
                }
                return  l3;
            }

            @Override
            protected void onPostExecute(List<Request> result) {
                try {
                    requests = result;
                    RequestAdapter adapter = new RequestAdapter(getActivity(),result);
                    setListAdapter(adapter);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }.execute(id);

    }


}
