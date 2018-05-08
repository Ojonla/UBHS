package com.example.senatorojonla.ubhs;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
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

public class Removebus extends AppCompatActivity {

    EditText remreg;
    String RemoveBusurl="https://entophytic-election.000webhostapp.com/RemoveBus.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_removebus);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        remreg=findViewById(R.id.remreg);
    }

    public void RemoveBus(View view) {
        StringRequest jsonArrayRequest = new StringRequest(Request.Method.POST, RemoveBusurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String code = jsonObject.getString("code");
                        if (code.equals("success")) {
                            String message = jsonObject.getString("message");
                            Toast.makeText(Removebus.this, message, Toast.LENGTH_LONG).show();
                            Removebus.this.finish();
                        } else {
                            String message = jsonObject.getString("message");
                            showAlertDialog(message);
                            Toast.makeText(Removebus.this, "Bus Not Added", Toast.LENGTH_LONG).show();
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
                    Toast.makeText(Removebus.this, "Connection Could Not be Establish At The Moment, Try Later...", Toast.LENGTH_LONG).show();
                    error.printStackTrace();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(Removebus.this, "Failure Authenticating the Request...", Toast.LENGTH_LONG).show();
                    error.printStackTrace();

                } else if (error instanceof ServerError) {
                    Toast.makeText(Removebus.this, "Error Response from the Server...", Toast.LENGTH_LONG).show();
                    error.printStackTrace();

                } else if (error instanceof ParseError) {
                    Toast.makeText(Removebus.this, "Server Error...", Toast.LENGTH_LONG).show();
                    error.printStackTrace();

                } else if (error instanceof NetworkError) {
                    Toast.makeText(Removebus.this, "Network Error, Check Your Network Connection...", Toast.LENGTH_LONG).show();
                    error.printStackTrace();

                }


            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("regno", remreg.getText().toString());
                return params;
            }

        };

        Mysingleton.getInstance(Removebus.this).addtorequestque(jsonArrayRequest);

    }

    private void showAlertDialog(String message) {

    }

    private void showSnackbarMessage(String message) {
    }
    }

