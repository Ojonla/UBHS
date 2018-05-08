package com.example.senatorojonla.ubhs;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
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

public class Userdash extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
String surname, othernames, username, contact, phonenumber, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userdash);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        surname=getIntent().getExtras().getString(surname);
        othernames=getIntent().getExtras().getString(othernames);
        username=getIntent().getExtras().getString(username);
        contact=getIntent().getExtras().getString(contact);
        email=getIntent().getExtras().getString(email);
        phonenumber=getIntent().getExtras().getString(phonenumber);








               DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        final TextView textView = findViewById(R.id.userprofile_txt);
        UserPreferences userPreferences = new UserPreferences(this);
        textView.setText("Welcome: " + userPreferences.getUsername());
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
        getMenuInflater().inflate(R.menu.userdash, menu);
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

        if (id == R.id.profi) {
            Intent jo = new Intent(this, Profile.class);
            jo.putExtra("sname",surname);
            jo.putExtra("onames",othernames);
            jo.putExtra("uname",username);
            jo.putExtra("ctact",contact);
            jo.putExtra("emaill",email);
            jo.putExtra("phone_numbers",phonenumber);
            startActivity(jo);
            // Handle the camera action
        } else if (id == R.id.vab) {
            Intent go = new Intent(this, ViewAvBus.class);
            startActivity(go);

        } else if (id == R.id.hb) {
            Intent go = new Intent(this, ViewAvBus.class);
            startActivity(go);

        } else if (id == R.id.logt) {

            finish();
        }  else if (id == R.id.ext) {
            AlertDialog.Builder builder= new AlertDialog.Builder(Userdash.this);
            builder.setMessage("Do you want to exit the application?");
            builder.setCancelable(true);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialogInterface, int id) {
                    finish();
                }


            }); builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int id) {
                    dialogInterface.cancel();

                }
            });
            AlertDialog alertDialog= builder.create();
                    alertDialog.show();

        }   else if (id == R.id.adbus) {
            Intent go = new Intent(this, AddBus.class);
            startActivity(go);

        } else if (id == R.id.rembus) {
            Intent go = new Intent(this, Removebus.class);
            startActivity(go);

        } else if (id == R.id.vab1) {
            Intent go = new Intent(this, ViewAvBus.class);
            startActivity(go);

        }  else if (id == R.id.vhb) {

        }   else if (id == R.id.logt1) {
            finish();

        }  else if (id == R.id.exts) {
            AlertDialog.Builder builder= new AlertDialog.Builder(Userdash.this);
            builder.setMessage("Do you want to exit the application?");
            builder.setCancelable(true);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialogInterface, int id) {
                    finish();
                }


            }); builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int id) {
                    dialogInterface.cancel();

                }
            });
            AlertDialog alertDialog= builder.create();
            alertDialog.show();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
