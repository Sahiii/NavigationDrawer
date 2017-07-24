package com.example.manishacomputer.navigation;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.manishacomputer.navigation.DbHndler.User_profile;
import com.example.manishacomputer.navigation.Nav_view_menu.AboutusFragment;
import com.example.manishacomputer.navigation.Nav_view_menu.BloodbankFragment;
import com.example.manishacomputer.navigation.Nav_view_menu.EventFragment;
import com.example.manishacomputer.navigation.Nav_view_menu.HomeFragment;
import com.example.manishacomputer.navigation.Nav_view_menu.ProfileFragment;
import com.example.manishacomputer.navigation.Nav_view_menu.SearchList;
import com.example.manishacomputer.navigation.Nav_view_menu.WithdrawFragment;

public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private User_profile dbUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        dbUser=new User_profile(NavigationActivity.this);
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
        FragmentManager fragmentManager=getSupportFragmentManager();
        getSupportActionBar().setTitle("Home");
        fragmentManager.beginTransaction().replace(R.id.frame,new HomeFragment()).addToBackStack(null).commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
        getMenuInflater().inflate(R.menu.navigation, menu);
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
        FragmentManager fragmentManager=getSupportFragmentManager();
        int id = item.getItemId();
        if (id == R.id.nav_home) {
       getSupportActionBar().setTitle("Home");
        fragmentManager.beginTransaction().replace(R.id.frame,new HomeFragment()).addToBackStack(null).commit();
            // Handle the camera actiong
        } else if (id == R.id.nav_profile) {
getSupportActionBar().setTitle("Profile");
 fragmentManager.beginTransaction().replace(R.id.frame,new  ProfileFragment()).addToBackStack(null).commit();

        } else if (id == R.id.nav_searchlist) {

            getSupportActionBar().setTitle("Search List");
            fragmentManager.beginTransaction().replace(R.id.frame,new SearchList()).addToBackStack(null).commit();
        } else if (id == R.id.nav_bloodbank) {

            getSupportActionBar().setTitle("Bloodbank");
            fragmentManager.beginTransaction().replace(R.id.frame,new BloodbankFragment()).addToBackStack(null).commit();
        } else if (id == R.id.nav_withdraw) {

            getSupportActionBar().setTitle("Withdraw");
            fragmentManager.beginTransaction().replace(R.id.frame,new WithdrawFragment()).addToBackStack(null).commit();
        } else if (id == R.id.nav_event) {

            getSupportActionBar().setTitle("Event");
            fragmentManager.beginTransaction().replace(R.id.frame,new EventFragment()).addToBackStack(null).commit();
        }else if (id == R.id.nav_about) {

            getSupportActionBar().setTitle("Aboutus");
            fragmentManager.beginTransaction().replace(R.id.frame,new AboutusFragment()).addToBackStack(null).commit();
        }else if (id == R.id.nav_logout) {
            dbUser.DeleteData();
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
