package com.example.senatorojonla.ubhs;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
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

public class AddBus extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner spinner;
    EditText busname, category, model, hire_price, status, regno;
    String categoryString;
    private String add_busurl = "https://ubhs.000webhostapp.com/addbus.php";


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
        categoryString = parent.getItemAtPosition(position).toString();
        //Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bus);

        busname=findViewById(R.id.bus_name);
        model=findViewById(R.id.model);
        regno=findViewById(R.id.regno);
        hire_price=findViewById(R.id.price);
        categoryString = "";

        spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Category, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

    }


    public void AddBus(View view) {
        StringRequest jsonArrayRequest = new StringRequest(Request.Method.POST, add_busurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String code = jsonObject.getString("code");
                        if (code.equals("success")) {
                            String message = jsonObject.getString("message");
                            Toast.makeText(AddBus.this, message, Toast.LENGTH_LONG).show();
                            AddBus.this.finish();
                        } else {
                            String message = jsonObject.getString("message");
                            showAlertDialog(message);
                            Toast.makeText(AddBus.this, "Bus Not Added", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(AddBus.this, "Connection Could Not be Establish At The Moment, Try Later...", Toast.LENGTH_LONG).show();
                    error.printStackTrace();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(AddBus.this, "Failure Authenticating the Request...", Toast.LENGTH_LONG).show();
                    error.printStackTrace();

                } else if (error instanceof ServerError) {
                    Toast.makeText(AddBus.this, "Error Response from the Server...", Toast.LENGTH_LONG).show();
                    error.printStackTrace();

                } else if (error instanceof ParseError) {
                    Toast.makeText(AddBus.this, "Server Error...", Toast.LENGTH_LONG).show();
                    error.printStackTrace();

                } else if (error instanceof NetworkError) {
                    Toast.makeText(AddBus.this, "Network Error, Check Your Network Connection...", Toast.LENGTH_LONG).show();
                    error.printStackTrace();

                }


            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", busname.getText().toString());
                params.put("regno", regno.getText().toString());
                params.put("category", categoryString);
                params.put("model", model.getText().toString());
                params.put("hire_price", hire_price.getText().toString());
                return params;
            }

        };

        Mysingleton.getInstance(AddBus.this).addtorequestque(jsonArrayRequest);

    }

    private void showAlertDialog(String message) {

    }

    private void showSnackbarMessage(String message) {
    }

}

