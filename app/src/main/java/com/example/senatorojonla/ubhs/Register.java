package com.example.senatorojonla.ubhs;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
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

public class Register extends AppCompatActivity {
    Button reg;
    EditText surname, othernames, username, password, emailadd, contactadd, phonnum;
    String sex = "";
    private String registerurl = "https://ubhs.000webhostapp.com/registration.php";
    AwesomeValidation awesomeValidation;

    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        awesomeValidation= new AwesomeValidation(ValidationStyle.BASIC);

        surname=findViewById(R.id.surname);
        othernames=findViewById(R.id.othernames);
        username=findViewById(R.id.username);
        emailadd=findViewById(R.id.email);
        contactadd=findViewById(R.id.address);
        phonnum=findViewById(R.id.phone_number);
        password=findViewById(R.id.password);
        //sex = (String)findViewById(R.id.)
        sex="";

    }

    public void SignUp(View view) {
        awesomeValidation.addValidation(Register.this, R.id.surname, "[a-zA-Z\\s]+", R.string.surnameerr);
        awesomeValidation.addValidation(Register.this, R.id.othernames, "[a-zA-Z\\s]+", R.string.othernameserr);
        awesomeValidation.addValidation(Register.this, R.id.username, "[a-zA-Z\\s]+", R.string.usernameerr);
        awesomeValidation.addValidation(Register.this, R.id.password, "[a-zA-Z\\s]+", R.string.passworderr);
        awesomeValidation.addValidation(Register.this, R.id.address, "[a-zA-Z\\s]+", R.string.addresserr);
        awesomeValidation.addValidation(Register.this, R.id.phone_number, RegexTemplate.TELEPHONE, R.string.phoneerr);
        awesomeValidation.addValidation(Register.this, R.id.email, android.util.Patterns.EMAIL_ADDRESS, R.string.emailerr);
        if (awesomeValidation.validate()) {


//            progressDialog.setMessage("Registering...");
//            progressDialog.show();
            Toast.makeText(getApplicationContext(), "Registrin.....", Toast.LENGTH_LONG).show();
            StringRequest jsonArrayRequest = new StringRequest(Request.Method.POST, registerurl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < response.length(); i++) {

                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String code = jsonObject.getString("code");
                            if (code.equals("success")) {
                                String message = jsonObject.getString("message");
//                                Intent intent = new Intent(Register.this, LoginActivity.class);
//                                startActivity(intent);
                               // Register.this.finish();
                            } else {

                                String message = jsonObject.getString("message");
                                showAlertDialog(message);
                            }

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Intent intent = new Intent(Register.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                        Toast.makeText(Register.this, "Connection Could Not be Establish At The Moment, Try Later...", Toast.LENGTH_LONG).show();
                        error.printStackTrace();
                    } else if (error instanceof AuthFailureError) {
                        Toast.makeText(Register.this, "Failure Authenticating the Request...", Toast.LENGTH_LONG).show();
                        error.printStackTrace();

                    } else if (error instanceof ServerError) {
                        Toast.makeText(Register.this, "Error Response from the Server...", Toast.LENGTH_LONG).show();
                        error.printStackTrace();

                    } else if (error instanceof ParseError) {
                        Toast.makeText(Register.this, "Server Error...", Toast.LENGTH_LONG).show();
                        error.printStackTrace();

                    } else if (error instanceof NetworkError) {
                        Toast.makeText(Register.this, "Network Error, Check Your Network Connection...", Toast.LENGTH_LONG).show();
                        error.printStackTrace();

                    }


                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("surname", surname.getText().toString());
                    params.put("password", password.getText().toString());
                    params.put("othername", othernames.getText().toString());
                    params.put("username", username.getText().toString());
                    params.put("email", emailadd.getText().toString());
                    params.put("contact", contactadd.getText().toString());
                    params.put("phonenum", phonnum.getText().toString());
                    params.put("sex", sex);
                    return params;
                }

            };

            Mysingleton.getInstance(Register.this).addtorequestque(jsonArrayRequest);

        }
        else
            {
                Toast.makeText(Register.this, "Error", Toast.LENGTH_LONG).show();
            }
    }

    private void showAlertDialog(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    private void showSnackbarMessage(String message) {
    }

    public void SelectSex(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.male:
                if (checked) {
                    sex = "male";
                } else {
                    sex = "";
                }

                break;
            case R.id.female:
                if (checked) {
                    sex = "female";
                } else {
                    sex = "";
                }

                break;
        }
    }
}
