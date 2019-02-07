package com.adproject.android.inventory;


import  android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.adproject.android.inventory.Connection.AccountConnection;
import com.adproject.android.inventory.DeptHeadFragments.DeptHeadFragment;
import com.adproject.android.inventory.DeptHeadFragments.DeptRepFragment;
import com.adproject.android.inventory.DeptHeadFragments.DetailsFragment;
import com.adproject.android.inventory.DeptHeadFragments.HomeFragment;
import com.adproject.android.inventory.DeptHeadFragments.RequestFragment;


public class DeptHeadActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public String email;
    public String userid;
    public String username;
    public String userdept;

    FragmentManager fm;
    FragmentTransaction ft;
    Fragment home;
    ListFragment requestlist;
    Fragment detail;
    Fragment deptrep;
    Fragment depthead;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.depthead_activity);
        setTitle("Home");
        //数据
        email = getIntent().getExtras().getString("email");
        userid = getIntent().getExtras().getString("userid");
        username = getIntent().getExtras().getString("name");
        userdept = getIntent().getExtras().getString("dept");

        //toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //hide
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        //Drawer
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //Header
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        TextView navEmail = (TextView) headerView.findViewById(R.id.textHeadEmail);
        TextView navName = headerView.findViewById(R.id.textHeadName);
        navName.setText("Hello,"+username);
        navEmail.setText(email);

        //fragment
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        detail =  new DetailsFragment();
        home = HomeFragment.newInstance("Home");
        ft.add(R.id.conten_frame,home);
        ft.commit();


    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }else if(!getTitle().toString().equals("Request Details")){
            fm.beginTransaction().replace(R.id.conten_frame,home,"Home").commit();
            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.getMenu().getItem(0).setChecked(true);
        }else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.depthead_setting_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id==R.id.nav_Home){
            fm.beginTransaction().replace(R.id.conten_frame,home,"Home").commit();
        }else if (id == R.id.nav_request) {
            requestlist = RequestFragment.newInstance("Request");
                //ft.add(R.id.conten_frame, requestlist);
            Bundle args = new Bundle();
            args.putSerializable("userid", userid);
            requestlist.setArguments(args);
            fm.beginTransaction().replace(R.id.conten_frame,requestlist,"Request").addToBackStack("Request").commit();
        } else if (id == R.id.nav_deptRep) {
            deptrep = new DeptRepFragment();
            Bundle args = new Bundle();
            args.putSerializable("userid", userid);
            deptrep.setArguments(args);
            fm.beginTransaction().replace(R.id.conten_frame,deptrep,"DeptRep").addToBackStack("DeptRep").commit();
        } else if (id == R.id.nav_deptHead) {
            depthead = new DeptHeadFragment();
            Bundle args = new Bundle();
            args.putSerializable("userid", userid);
            depthead.setArguments(args);
            fm.beginTransaction().replace(R.id.conten_frame,depthead).addToBackStack(null).commit();
        } else if (id == R.id.nav_logout) {
            AccountConnection accountConnection = new AccountConnection();
            accountConnection.Logout();
            final Intent login = new Intent(this,LoginActivity.class);
            finish();
            startActivity(login);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }




}
