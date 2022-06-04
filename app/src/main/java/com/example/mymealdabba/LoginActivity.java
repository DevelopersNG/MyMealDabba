package com.example.mymealdabba;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.mymealdabba.databinding.ActivityLoginBinding;
import com.example.mymealdabba.model.DataModelUsers;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    String url = Utils.URL+"login-otp";
    ActivityLoginBinding b;
    Context context;
    String mobile;
    DataModelUsers model;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b =ActivityLoginBinding.inflate(getLayoutInflater());
//        Bundle bundle = getIntent().getExtras();
//        String data = bundle.getString("response");
        setContentView(b.getRoot());
        context = this;
        sessionManager=new SessionManager(context);


        listener();
    }



    private void listener() {
        b.btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkForm()) {
                    submitForm();
                }
            }


        });

        b.signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });
        b.lblTerm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://mymealdabba.com/terms"));
                try {
                    startActivity(intent);
                } catch (Exception e) {
                    // intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=app.com.mymealdabba"));
                }
            }
        });
    }
//check form.....
    private boolean checkForm() {
         mobile = b.txtMobile.getText().toString().trim();
        if (mobile.isEmpty()) {
            b.txtMobile.setError("Please enter mobile number");
            b.txtMobile.setFocusableInTouchMode(true);
            b.txtMobile.requestFocus();
            return false;
        } else if (!Utils.isValidMobile(mobile)) {
            b.txtMobile.setError("Invalid mobile number");
            b.txtMobile.setFocusableInTouchMode(true);
            b.txtMobile.requestFocus();
            return false;
        }else {
            b.txtMobile.setError(null);
        }
        return true;



    }
//send request to server for login using OTP
    private void submitForm() {
        final ProgressDialog progressDialog = ProgressDialog.show(context, null, "processing...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Log.e("response", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String code = jsonObject.getString("result");
                    if (code.equalsIgnoreCase("1")) {

                        Intent intent = new Intent(context, OtpVerificationActivity.class);
                        intent.putExtra("id", jsonObject.getString("UserID"));
                        intent.putExtra("mobile", mobile);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } else {
                        Toast.makeText(context, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context, "Something went wrong, try again.", Toast.LENGTH_SHORT).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        error.printStackTrace();
                        Toast.makeText(context, "Sorry, something went wrong. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("apikey", Utils.API_KEY);
                params.put("ContactNo", mobile);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.myGetMySingleton(context).myAddToRequest(stringRequest);
    }
}