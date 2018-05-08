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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.senatorojonla.ubhs.Adapter.HospitalAdapter;
import com.example.senatorojonla.ubhs.Adapter.HospitalRecycler;
import com.example.senatorojonla.ubhs.Model.HospitalModel;
import com.example.senatorojonla.ubhs.Network.Mysingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ViewAvBus extends AppCompatActivity implements HospitalRecycler.ClickListener,SwipeRefreshLayout.OnRefreshListener {
    private static int RESULT_LOAD_IMAGE = 1;

    EditText busname, category, model, hire_price, status, regno;
    String categoryString;
    String bus_status;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    ArrayList<HospitalModel> mHospitals;
    private HospitalAdapter mhosptialAdapter;
    private String url = "https://ubhs.000webhostapp.com/view_av_bus.php";
    String hireurl = "https://ubhs.000webhostapp.com/Hire_bus.php";

    String busRegnoVaL = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_av_bus);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        mHospitals = new ArrayList<>();
        swipeRefreshLayout = findViewById(R.id.swipelayout);
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(ViewAvBus.this));
        recyclerView.addOnItemTouchListener(new HospitalRecycler.
                RecyclerTouchListener(ViewAvBus.this, recyclerView, this));
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                busRegnoVaL = mHospitals.get(position).getBusReg();
                updateBusInfo();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        fetchOnline();

        //swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchHospitals();
            }
        });

        busname=findViewById(R.id.bus_name);
        model= findViewById(R.id.model);
        regno= findViewById(R.id.regno);
        hire_price= findViewById(R.id.price);
        categoryString = "";


       /* Button buttonLoadImage = findViewById(R.id.button);
        buttonLoadImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });*/
    }

    private void updateBusInfo() {
        StringRequest jsonArrayRequest = new StringRequest(Request.Method.POST, hireurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String code = jsonObject.getString("code");
                        if (code.equals("success")) {
                            String message = jsonObject.getString("message");
                            fetchHospitals();
                            Toast.makeText(ViewAvBus.this, "bus hired successfully", Toast.LENGTH_LONG).show();
                            ViewAvBus.this.finish();
                        } else {
                            String message = jsonObject.getString("message");
                            showAlertDialog(message);
                            Toast.makeText(ViewAvBus.this, "Hire Bus Failed", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(ViewAvBus.this, "Connection Could Not be Establish At The Moment, Try Later...", Toast.LENGTH_LONG).show();
                    error.printStackTrace();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(ViewAvBus.this, "Failure Authenticating the Request...", Toast.LENGTH_LONG).show();
                    error.printStackTrace();

                } else if (error instanceof ServerError) {
                    Toast.makeText(ViewAvBus.this, "Error Response from the Server...", Toast.LENGTH_LONG).show();
                    error.printStackTrace();

                } else if (error instanceof ParseError) {
                    Toast.makeText(ViewAvBus.this, "Server Error...", Toast.LENGTH_LONG).show();
                    error.printStackTrace();

                } else if (error instanceof NetworkError) {
                    Toast.makeText(ViewAvBus.this, "Network Error, Check Your Network Connection...", Toast.LENGTH_LONG).show();
                    error.printStackTrace();

                }


            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("regno", busRegnoVaL);
                return params;
            }

        };

        Mysingleton.getInstance(ViewAvBus.this).addtorequestque(jsonArrayRequest);


    }

    private void fetchHospitals() {
        if (isOnline(ViewAvBus.this)) {
            if (mHospitals != null) {
                mHospitals.clear();
                fetchOnline();
            }

        } else {
            if (mHospitals != null) {
                mHospitals.clear();
                popultateDevelopersList();
                swipeRefreshLayout.setRefreshing(false);
                showSnackBarMessage("No Internet Connection");
                //Toast.makeText(MainActivity.this, "No internet connection", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void fetchOnline() {
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, (String) null, new com.android.volley.Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
              //  showSnackBarMessage("Total Data: " + response.length());
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        HospitalModel item = new HospitalModel(jsonObject.getString("busname"), jsonObject.getString("cat"),
                                jsonObject.getString("model"), jsonObject.getString("hire_price"), jsonObject.getString("regno"),jsonObject.getString("id"));
                        mHospitals.add(item);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                swipeRefreshLayout.setRefreshing(false);
                popultateDevelopersList();

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                Log.d("VOLLEY ERROR", "onErrorResponse: " + error.getMessage());
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    showSnackBarMessage("Connection Could Not be Establish At The Moment, Try Later...");
                    error.printStackTrace();
                } else if (error instanceof AuthFailureError) {
                    showSnackBarMessage("Failure Authenticating the Request...");
                    error.printStackTrace();

                } else if (error instanceof ServerError) {
                    showSnackBarMessage("Error Response from the Server...");
                    error.printStackTrace();

                } else if (error instanceof ParseError) {
                    showSnackBarMessage("Server Error...");
                    error.printStackTrace();

                } else if (error instanceof NetworkError) {
                    showSnackBarMessage("Network Error, Check Your Network Connection...");
                    error.printStackTrace();

                }
                swipeRefreshLayout.setRefreshing(false);
                popultateDevelopersList();


            }
        });

        Mysingleton.getInstance(ViewAvBus.this).addtorequestque(request);
    }

    private void popultateDevelopersList() {
        if (mHospitals.size() < 1) {
            Toast.makeText(getApplicationContext(), "No Available bus, please check back later", Toast.LENGTH_LONG).show();
            setHospitalAdapter(mHospitals);
        } else {
            Toast.makeText(getApplicationContext(), "List of available buses", Toast.LENGTH_LONG).show();
            setHospitalAdapter(mHospitals);
            //mDevelopersAdapter.notifyDataSetChanged();
        }
    }

    private void setHospitalAdapter(ArrayList<HospitalModel> mHospitals) {
        mhosptialAdapter = new HospitalAdapter(mHospitals, this);
        DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator();
        defaultItemAnimator.setAddDuration(1000);
        defaultItemAnimator.setMoveDuration(1000);
        defaultItemAnimator.setChangeDuration(1000);
        recyclerView.setItemAnimator(defaultItemAnimator);
        recyclerView.setAdapter(mhosptialAdapter);
    }

    public boolean isOnline(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activenet = connectivityManager.getActiveNetworkInfo();
        return activenet != null;
    }

    private void showSnackBarMessage(String s) {
        Toast.makeText(ViewAvBus.this, s, Toast.LENGTH_LONG).show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            ImageView imageView = findViewById(R.id.imageView);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));

        }


    }


    @Override
    public void onClick(View view, int position) {

    }

    @Override
    public void onLongClick(View view, int position) {

    }

    @Override
    public void onRefresh() {
        fetchHospitals();
    }

    public void HireBus(View view) {

//        Toast.makeText(this, "hire button clicked", Toast.LENGTH_SHORT).show();
//
//        StringRequest jsonArrayRequest = new StringRequest(Request.Method.POST, hireurl, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                try {
//                    JSONArray jsonArray = new JSONArray(response);
//                    for (int i = 0; i < response.length(); i++) {
//                        JSONObject jsonObject = jsonArray.getJSONObject(i);
//                        String code = jsonObject.getString("code");
//                        if (code.equals("success")) {
//                            String message = jsonObject.getString("message");
//                            Toast.makeText(ViewAvBus.this, message, Toast.LENGTH_LONG).show();
//                            ViewAvBus.this.finish();
//                        } else {
//                            String message = jsonObject.getString("message");
//                            showAlertDialog(message);
//                            Toast.makeText(ViewAvBus.this, "Bus Not Added", Toast.LENGTH_SHORT).show();
//                        }
//
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
//                    Toast.makeText(ViewAvBus.this, "Connection Could Not be Establish At The Moment, Try Later...", Toast.LENGTH_LONG).show();
//                    error.printStackTrace();
//                } else if (error instanceof AuthFailureError) {
//                    Toast.makeText(ViewAvBus.this, "Failure Authenticating the Request...", Toast.LENGTH_LONG).show();
//                    error.printStackTrace();
//
//                } else if (error instanceof ServerError) {
//                    Toast.makeText(ViewAvBus.this, "Error Response from the Server...", Toast.LENGTH_LONG).show();
//                    error.printStackTrace();
//
//                } else if (error instanceof ParseError) {
//                    Toast.makeText(ViewAvBus.this, "Server Error...", Toast.LENGTH_LONG).show();
//                    error.printStackTrace();
//
//                } else if (error instanceof NetworkError) {
//                    Toast.makeText(ViewAvBus.this, "Network Error, Check Your Network Connection...", Toast.LENGTH_LONG).show();
//                    error.printStackTrace();
//
//                }
//
//
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("name", busname.getText().toString());
//                params.put("regno", regno.getText().toString());
//                params.put("category", categoryString);
//                params.put("model", model.getText().toString());
//                params.put("hire_price", hire_price.getText().toString());
//                return params;
//            }
//
//        };
//
//        Mysingleton.getInstance(ViewAvBus.this).addtorequestque(jsonArrayRequest);
//
  }

    private void showAlertDialog(String message) {

    }

    private void showSnackbarMessage(String message) {
    }

    }
