package com.example.senatorojonla.ubhs;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.example.senatorojonla.ubhs.Network.Mysingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends AppCompatActivity {
    Button reg;
    Button login;
    EditText usernam;
    EditText password;
    private String loginurl = "https://ubhs.000webhostapp.com/index.php";
    AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        reg = findViewById(R.id.regbtn);
        login = findViewById(R.id.Loginbtn);
        usernam = findViewById(R.id.username);
        password = findViewById(R.id.password);


        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenRegpage();
            }
        });
//        login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Loginbutton();
//            }
//        });

    }


    public void OpenRegpage() {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }

//        public void Loginbutton(){
//            if(usernam.getText().toString().equals("admin")&& password.getText().toString().equals("adminpass")){
//            Toast.makeText(LoginActivity.this,getString(R.string.mytoast),Toast.LENGTH_SHORT).show();
//            Intent letsgo= new Intent(this, Userdash.class);
//            startActivity(letsgo);
//            } else {
//            Toast.makeText(LoginActivity.this,"username and password not correct",Toast.LENGTH_SHORT).show();
//            }
//            }


    public void SignIn(View view) {


        awesomeValidation.addValidation(LoginActivity.this, R.id.username, "[a-zA-Z\\s]+", R.string.usernameerr);
        awesomeValidation.addValidation(LoginActivity.this, R.id.password, "[a-zA-Z\\s]+", R.string.passworderr);

        if (awesomeValidation.validate()) {
            StringRequest jsonArrayRequest = new StringRequest(Request.Method.POST, loginurl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String code = jsonObject.getString("code");

                            if (code.equals("success")) {
                                String message = jsonObject.getString("message");
                                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                                String user_id = jsonObject.getString("user_id");
                                String surname = jsonObject.getString("surname");
                                String othernames = jsonObject.getString("othernames");
                                String username = jsonObject.getString("username");
                                String contact = jsonObject.getString("contact");
                                String email = jsonObject.getString("email");
                                String phone = jsonObject.getString("phone_number");
                                String sex = jsonObject.getString("sex");
                                String status = jsonObject.getString("status");

                                Toast.makeText(LoginActivity.this,response,Toast.LENGTH_LONG).show();

                                final UserPreferences userPreferences = new UserPreferences(getApplicationContext());
                                userPreferences.saveUserProfile(user_id, username, surname, othernames, phone, contact, email, sex, status);
                                Intent intent = new Intent(LoginActivity.this, Userdash.class);
                                intent.putExtra("surname",surname);
                                intent.putExtra("othernmes",othernames);
                                intent.putExtra("username",username);
                                intent.putExtra("contact",contact);
                                intent.putExtra("email",email);
                                intent.putExtra("phone_number",phone);
                                startActivity(intent);
                                LoginActivity.this.finish();
                            } else {
                                String message = jsonObject.getString("message");
                                Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_LONG).show();
                                showSnackbarMessage(message);
                            }

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("ERROR", "onErrorResponse: " + error.getMessage());
                    if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                        Toast.makeText(LoginActivity.this, "Connection MSG: " + error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        error.printStackTrace();
                    } else if (error instanceof AuthFailureError) {
                        Toast.makeText(LoginActivity.this, "Failure Authenticating the Request...", Toast.LENGTH_LONG).show();
                        error.printStackTrace();

                    } else if (error instanceof ServerError) {
                        Toast.makeText(LoginActivity.this, "Error Response from the Server...", Toast.LENGTH_LONG).show();
                        error.printStackTrace();

                    } else if (error instanceof ParseError) {
                        Toast.makeText(LoginActivity.this, "Server Error...", Toast.LENGTH_LONG).show();
                        error.printStackTrace();

                    } else if (error instanceof NetworkError) {
                        Toast.makeText(LoginActivity.this, "Network Error, Check Your Network Connection...", Toast.LENGTH_LONG).show();
                        error.printStackTrace();

                    }


                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("username", usernam.getText().toString());
                    params.put("password", password.getText().toString());

                    return params;
                }

            };

            Mysingleton.getInstance(LoginActivity.this).addtorequestque(jsonArrayRequest);

//            Intent intent = new Intent(LoginActivity.this, Userdash.class);
//            startActivity(intent);
//            LoginActivity.this.finish();

        } else {
            Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_LONG).show();
        }

    }

    private void showSnackbarMessage(String message) {

    }


//    public void Loginbutton(){
//        login = findViewById(R.id.Loginbtn);
//        usernam = findViewById(R.id.username) ;
//        password = findViewById(R.id.password);
//        login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(usernam.getText().toString().equals("admin")&& password.getText().toString().equals("adminpass")){
//                    Toast.makeText(LoginActivity.this,getString(R.string.mytoast),Toast.LENGTH_SHORT).show();
//                    Intent letsgo= new Intent(LoginActivity.this, Userdash.class);
//                    startActivity(letsgo);
//                } else {
//                    Toast.makeText(LoginActivity.this,"username and password not correct",Toast.LENGTH_SHORT).show();
//                }
//
//
//            }
//        });
//    }


}
