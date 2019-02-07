package com.adproject.android.inventory.StoreClerkFragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.adproject.android.inventory.Adapter.DisbursementAdapter;
import com.adproject.android.inventory.Adapter.DepartmentAdapter;
import com.adproject.android.inventory.Entity.Catalogue;
import com.adproject.android.inventory.Entity.Department;
import com.adproject.android.inventory.R;
import com.adproject.android.inventory.StoreClerkActivities.SignatureActivity;

import java.util.ArrayList;
import java.util.List;

public class DispersementFragment extends Fragment {
    private View view;
    private Spinner spinner;
    private List<Catalogue> catalogueList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.storeclerk_fragment_dispersement, container, false);
        GetDisbursementList();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle("Dispersement List");
        NavigationView navigationView = (NavigationView) getActivity().findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(2).setChecked(true);
        spinner = getActivity().findViewById(R.id.spinnerDepartmentName);
        Button btnSign = getActivity().findViewById(R.id.buttonSign);
        final Intent signature = new Intent(getActivity(),SignatureActivity.class);
        if(spinner!=null) {
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    TextView textRep = getActivity().findViewById(R.id.textRepresentative);
                    TextView textCP = getActivity().findViewById(R.id.textCollectionPoint);
                    Department department = (Department) parent.getItemAtPosition(position);
                    textRep.setText(department.get("DepartmentRep"));
                    textCP.setText(department.get("CollectionPoint"));
                    GetDisbursements(department);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            btnSign.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(catalogueList!=null) {
                            ArrayList<String> orderids = new ArrayList<>();
                            String orderid = "";
                            Bundle bundle = new Bundle();
                            int i = 0;
                            for (Catalogue c : catalogueList) {
                                if(!c.get("ItemID").equals(orderid)){
                                    orderid = c.get("ItemID");
                                    orderids.add(orderid);
                                }
                            }
                            signature.putStringArrayListExtra("orderids",orderids);
                            startActivity(signature);
                        }else {
                            Toast.makeText(getActivity().getApplicationContext(), "wait...",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
            });

        }
    }

    void GetDisbursementList(){
        new AsyncTask<Void,Void,List<Department>>(){

            @Override
            protected List<Department> doInBackground(Void... voids) {
                List<Department> departments = new ArrayList<>();
                    for (Department department : Department.GetDisbursementList()) {
                        departments.add(department);
                    }
                    return departments;
            }

            @Override
            protected void onPostExecute(List<Department> departments) {
                try {
                    DepartmentAdapter adpter = new DepartmentAdapter(getActivity(), departments);
                    spinner = getActivity().findViewById(R.id.spinnerDepartmentName);
                    spinner.setAdapter(adpter);

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }.execute();
    }

    void GetDisbursements(Department department){
        new AsyncTask<Department,Void,List<Catalogue>>(){

            @Override
            protected List<Catalogue> doInBackground(Department... departments) {
                return Catalogue.GetDisbursementsByDept(departments[0]);
            }

            @Override
            protected void onPostExecute(List<Catalogue> catalogues) {
                try {
                    catalogueList = catalogues;
                    DisbursementAdapter adapter = new DisbursementAdapter(getActivity(),catalogues);
                    ListView listView = getActivity().findViewById(R.id.listDispersement);
                    listView.setAdapter(adapter);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }.execute(department);

    }

}
