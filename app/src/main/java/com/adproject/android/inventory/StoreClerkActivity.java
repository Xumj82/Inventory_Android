package com.adproject.android.inventory;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import com.adproject.android.inventory.StoreClerkFragment.DispersementFragment;
import com.adproject.android.inventory.StoreClerkFragment.HomeFragment;
import com.adproject.android.inventory.StoreClerkFragment.ManageInventoryFragment;
import com.adproject.android.inventory.StoreClerkFragment.RetrievalFragment;
import android.widget.TextView;

import com.adproject.android.inventory.StoreClerkFragment.HomeFragment;
import com.adproject.android.inventory.StoreClerkFragment.ManageInventoryFragment;

public class StoreClerkActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    FragmentManager fm;
    FragmentTransaction ft;
    HomeFragment homeFragment;

    public String email;
    public String userid;
    public String username;
    public String userdept;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.storeclerk_activity);
        //数据
        email = getIntent().getExtras().getString("email");
        userid = getIntent().getExtras().getString("userid");
        username = getIntent().getExtras().getString("name");
        userdept = getIntent().getExtras().getString("dept");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        TextView navEmail = (TextView) headerView.findViewById(R.id.textClerkEmail);
        TextView navName = headerView.findViewById(R.id.textClerkName);
        navName.setText("Hello,"+username);
        navEmail.setText(email);


        //fragment
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        homeFragment = new HomeFragment();
        ft.add(R.id.storeclerk_content_frame,homeFragment);
        ft.commit();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.storeclerk_setting_menu, menu);
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

        if (id == R.id.nav_home) {
            // Handle the camera action
            fm.beginTransaction().replace(R.id.storeclerk_content_frame,homeFragment,"Home").commit();
        } else if (id == R.id.nav_retireval) {
            RetrievalFragment retrievalFragment = new RetrievalFragment();
            fm.beginTransaction().replace(R.id.storeclerk_content_frame,retrievalFragment,"Retrieval").addToBackStack("Retrieval").commit();

        } else if (id == R.id.nav_dispersement) {
            DispersementFragment dispersementFragment = new DispersementFragment();
            fm.beginTransaction().replace(R.id.storeclerk_content_frame,dispersementFragment,"Dispersement").addToBackStack("Dispersement").commit();
        } else if (id == R.id.nav_inventory) {
            ManageInventoryFragment manageInventoryFragment = new ManageInventoryFragment();
            fm.beginTransaction().replace(R.id.storeclerk_content_frame,manageInventoryFragment,"Manage Inventory").addToBackStack("Manage Inventory").commit();
        } else if (id == R.id.nav_logout) {
            AccountConnection accountConnection = new AccountConnection();
            accountConnection.Logout();
            final Intent login = new Intent(this,LoginActivity.class);
            startActivity(login);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
