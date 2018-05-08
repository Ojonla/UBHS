package com.example.senatorojonla.ubhs;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.senatorojonla.ubhs.Adapter.HospitalAdapter;
import com.example.senatorojonla.ubhs.Adapter.HospitalRecycler;
import com.example.senatorojonla.ubhs.Model.HospitalModel;
import com.example.senatorojonla.ubhs.Network.Mysingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Profile extends AppCompatActivity  {
    private static int RESULT_LOAD_IMAGE = 1;
    TextView surname, othernames, username, contact, phonenumber, email;
    String sname, uname, oname, ctact,phone_numbers,emaill;

    SwipeRefreshLayout swipeRefreshLayout;
    ArrayList<HospitalModel> mHospitals;
    private HospitalAdapter mhosptialAdapter;
    private String url = "https://https://ubhs.000webhostapp.com/profile.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        surname =findViewById(R.id.sname);
        othernames=findViewById(R.id.onames);
        username = findViewById(R.id.uname);
        contact = findViewById(R.id.addr);
        phonenumber= findViewById(R.id.phone);
        email = findViewById(R.id.email);

        sname=getIntent().getExtras().getString(sname);
        oname=getIntent().getExtras().getString(oname);
        uname=getIntent().getExtras().getString(uname);
        ctact=getIntent().getExtras().getString(ctact);
        emaill=getIntent().getExtras().getString(emaill);
        phone_numbers=getIntent().getExtras().getString(phone_numbers);



        surname.setText(sname);
        othernames.setText(oname);
        username.setText(uname);
        contact.setText(ctact);
        phonenumber.setText(phone_numbers);
        email.setText(emaill);


        UserPreferences userPreferences = new UserPreferences(this);
//        email.setText(userPreferences.getText(email));
//        surname.setText(userPreferences.getText(surname));
//        othernames.setText(userPreferences.getText(othernames));






    }
}