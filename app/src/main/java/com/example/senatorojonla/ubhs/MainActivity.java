package com.example.senatorojonla.ubhs;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.senatorojonla.ubhs.Network.Mysingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    Button nnew;
    Button nexi;
    String url="https://ubhs.000webhostapp.com/trial.php";
    String url2="https://ubhs.000webhostapp.com/trial.php";


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        nnew = findViewById(R.id.newbttn);
        nexi = findViewById(R.id.exbtn);
       nexi.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               OpenLoginActivity();
           }
       });

       nnew.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               OpenRegister();
           }
       });
    }
    public void OpenLoginActivity(){
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);

    }

    private void showAlertDialog(String message) {
    }

    public void OpenRegister(){
        StringRequest jsonArrayRequest = new StringRequest(Request.Method.POST, url2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String code = jsonObject.getString("code");
                        if (code.equals("success")) {
                            String message = jsonObject.getString("message");

                            Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
                            Intent intent=new Intent(MainActivity.this,Register.class);
                            startActivity(intent);

                        } else {
                            String message = jsonObject.getString("message");
                            showAlertDialog(message);
                            Toast.makeText(MainActivity.this, "Please check your network", Toast.LENGTH_LONG).show();
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Toast.makeText(MainActivity.this, "Connection Could Not be Establish At The Moment, Try Later...", Toast.LENGTH_LONG).show();
                    error.printStackTrace();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(MainActivity.this, "Failure Authenticating the Request...", Toast.LENGTH_LONG).show();
                    error.printStackTrace();

                } else if (error instanceof ServerError) {
                    Toast.makeText(MainActivity.this, "Error Response from the Server...", Toast.LENGTH_LONG).show();
                    error.printStackTrace();

                } else if (error instanceof ParseError) {
                    Toast.makeText(MainActivity.this, "Server Error...", Toast.LENGTH_LONG).show();
                    error.printStackTrace();

                } else if (error instanceof NetworkError) {
                    Toast.makeText(MainActivity.this, "Network Error, Check Your Network Connection...", Toast.LENGTH_LONG).show();
                    error.printStackTrace();

                }


            }
        }) ;

        Mysingleton.getInstance(MainActivity.this).addtorequestque(jsonArrayRequest);
   }

   /* public void openLoginActivity() {
        nnew = findViewById(R.id.newbttn);
        nexi = findViewById(R.id.exbtn);
        nexi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLoginActivity();
                Intent intent;
                intent = new Intent("com.example.senatorojonla.ubhs.LoginActivity");
                startActivity(intent);
            }
        });

    }*/



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
}
