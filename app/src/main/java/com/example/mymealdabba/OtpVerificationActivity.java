package com.example.mymealdabba;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import com.example.mymealdabba.databinding.ActivityOtpVerificationBinding;
import com.example.mymealdabba.ui.home.HomeFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class OtpVerificationActivity extends AppCompatActivity {
    ActivityOtpVerificationBinding b;
    Context context;
    SessionManager sessionManager;
    String url = Utils.URL + "login-otp";
    String otpVerifyUrl = Utils.URL + "verifyOTP";
    String otp;
    String mobile;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);
        mobile = getIntent().getStringExtra("mobile");
        userId = getIntent().getStringExtra("id");
        b = ActivityOtpVerificationBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());
        context = this;
        sessionManager = new SessionManager(context);
        listener();
        b.tvPhoneNumber.setText(mobile);
        b.user.setText(userId);
    }

    private void listener() {
        b.tvResendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resendOtp();
            }
        });

        b.btnVerifyOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkForm()) {
                    verifyOtp();
                }
            }
        });
    }


    private boolean checkForm() {
        otp = b.etOtpCode.getText().toString().trim();
        if (otp.isEmpty() || otp.length() < 5) {
            b.etOtpCode.setError("Please enter OTP number");
            b.etOtpCode.setFocusableInTouchMode(true);
            b.etOtpCode.requestFocus();
            return false;
        } else {
            b.etOtpCode.setError(null);
        }
        return true;

    }

    private void verifyOtp() {
        final ProgressDialog progressDialog = ProgressDialog.show(context, null, "processing...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, otpVerifyUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Log.e("response", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String code = jsonObject.getString("result");
                    if (code.equalsIgnoreCase("1")) {

                        sessionManager.createSessionLogin(userId);
                        Intent intent = new Intent(context, MessDetailsActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
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
                params.put("UserID", userId);
                params.put("OTP", otp);
                Log.e("params", params.toString());
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.myGetMySingleton(context).myAddToRequest(stringRequest);
    }

    private void resendOtp() {
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

//                       Intent intent = new Intent(context, OtpVerificationActivity.class);
                        // intent.putExtra("id", jsonObject.getString("UserID"));
//                        intent.putExtra("mobile", mobile);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        // startActivity(intent);
                        Toast.makeText(context, "OTP sent Successfully", Toast.LENGTH_SHORT).show();
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
                String mobile = b.tvPhoneNumber.getText().toString();
                params.put("ContactNo", mobile);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.myGetMySingleton(context).myAddToRequest(stringRequest);
    }
}