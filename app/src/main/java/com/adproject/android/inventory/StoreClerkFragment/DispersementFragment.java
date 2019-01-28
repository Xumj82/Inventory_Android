package com.adproject.android.inventory.StoreClerkFragment;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.adproject.android.inventory.Adapter.DepartmentAdpter;
import com.adproject.android.inventory.Entity.Department;
import com.adproject.android.inventory.R;

import java.util.List;

public class DispersementFragment extends Fragment {
    private View view;
    private Spinner spinner;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.storeclerk_fragment_dispersement, container, false);
        GetDisbursementList();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle("Dispersement List");
        spinner = getActivity().findViewById(R.id.spinnerDepartmentName);
        if(spinner!=null) {
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    TextView textRep = getActivity().findViewById(R.id.textRepresentative);
                    TextView textCP = getActivity().findViewById(R.id.textCollectionPoint);
                    Department department = (Department) parent.getItemAtPosition(position);
                    textRep.setText(department.get("DepartmentRep"));
                    textCP.setText(department.get("CollectionPoint"));
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
    }

    void GetDisbursementList(){
        new AsyncTask<Void,Void,List<Department>>(){

            @Override
            protected List<Department> doInBackground(Void... voids) {
                return Department.GetDisbursementList();
            }

            @Override
            protected void onPostExecute(List<Department> departments) {
                try {
                    DepartmentAdpter adpter = new DepartmentAdpter(getActivity(), departments);
                    spinner = getActivity().findViewById(R.id.spinnerDepartmentName);
                    spinner.setAdapter(adpter);

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }.execute();
    }

    void GetDisbursements(){
        new AsyncTask<Void,Void,List<Department>>(){

            @Override
            protected List<Department> doInBackground(Void... voids) {
                return Department.GetDisbursementList();
            }

            @Override
            protected void onPostExecute(List<Department> departments) {
                try {


                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }.execute();

    }

}
