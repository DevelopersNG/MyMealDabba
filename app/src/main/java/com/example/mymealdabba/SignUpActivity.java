package com.example.mymealdabba;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.mymealdabba.databinding.ActivitySignUpBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {
    String url = Utils.URL + "signup";
    private ProgressDialog progressDialog;
    FrameLayout frameLayoutTxtOPT;
    ActivitySignUpBinding b;
    Context context;
    String otpVerifyUrl = Utils.URL + "verifyOTP";
    String name = "";
    String phone = "";
    String email = "";
    String otp;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());


        context = this;
        listener();
        b.btnNameClear.setEnabled(false);
        b.btnEmailClear.setEnabled(false);
        b.btnMobileClear.setEnabled(false);
        b.btnOTPClear.setEnabled(false);
    }

    private void listener() {

        b.txtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text1 = b.txtName.getText().toString().trim();
                b.btnNameClear.setEnabled(!text1.isEmpty());

            }
        });

        b.txtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text2 = b.txtEmail.getText().toString().trim();
                b.btnEmailClear.setEnabled(!text2.isEmpty());

            }
        });

        b.txtMobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text3 = b.txtMobile.getText().toString().trim();
                b.btnMobileClear.setEnabled(!text3.isEmpty());

            }
        });

        b.txtOTP.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text4 = b.btnOTPClear.getText().toString().trim();
                b.btnOTPClear.setEnabled(!text4.isEmpty());

            }
        });


        b.btnNameClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b.txtName.getText().clear();
            }
        });

        b.btnEmailClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b.txtEmail.getText().clear();
            }
        });
        b.btnMobileClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b.txtMobile.getText().clear();
            }
        });

        b.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b.txtOTP.getText().clear();
            }
        });


        b.btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkForm()) {
                    submitForm();
                }
            }
        });




        b.btnOTPClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkForm1()) {
                    verifyOtp();
                }
            }
        });
    }

    private boolean checkForm1() {
        otp = b.txtOTP.getText().toString().trim();
        if (otp.isEmpty() || otp.length() < 5) {
            b.txtOTP.setError("Please enter OTP number");
            b.txtOTP.setFocusableInTouchMode(true);
            b.txtOTP.requestFocus();
            return false;
        } else {
            b.txtOTP.setError(null);
        }
        return true;

    }


    private boolean checkForm() {
        name = b.txtName.getText().toString().trim();
        phone = b.txtMobile.getText().toString().trim();
        email = b.txtEmail.getText().toString().trim();

        if (name.isEmpty()) {
            b.txtName.setError("Please enter your name");
            b.txtName.setFocusableInTouchMode(true);
            b.txtName.requestFocus();
            return false;
        } else {
            b.txtName.setError(null);
        }
        if (phone.isEmpty()) {
            b.txtMobile.setError("Please enter mobile number");
            b.txtMobile.setFocusableInTouchMode(true);
            b.txtMobile.requestFocus();
            return false;
        } else if (!Utils.isValidMobile(phone)) {
            b.txtMobile.setError("Invalid mobile no.");
            b.txtMobile.setFocusableInTouchMode(true);
            b.txtMobile.requestFocus();
            return false;
        } else {
            b.txtMobile.setError(null);
        }

        if (email.isEmpty()) {
            b.txtEmail.setError("Please email id");
            b.txtEmail.setFocusableInTouchMode(true);
            b.txtEmail.requestFocus();
            return false;
        } else if (!Utils.isValidEmail(email)) {
            b.txtEmail.setError("Invalid email.");
            b.txtEmail.setFocusableInTouchMode(true);
            b.txtEmail.requestFocus();
            return false;
        } else {
            b.txtEmail.setError(null);
        }
        return true;
    }

    private void submitForm() {
        final ProgressDialog progressDialog = ProgressDialog.show(context, null, "processing...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Log.e("response", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String code = jsonObject.getString("IsNewSignup");
                    userId= jsonObject.getString("UserID");

                    if (code.equalsIgnoreCase("1")) {
                       // Toast.makeText(context, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(context, LoginActivity.class);
                        //   intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        b.lblTermsAndConditions.setVisibility(View.GONE);
                        b.btnSignup.setVisibility(View.GONE);
                        b.lblTerms.setVisibility(View.GONE);

                        b.lblOTPMessage.setVisibility(View.VISIBLE);
                        b.frameLayoutTxtOPT.setVisibility(View.VISIBLE);
                        b.btnOTPClear.setVisibility(View.VISIBLE);
                        b.btnResendOTP.setVisibility(View.VISIBLE);
                        b.btnSubmit.setVisibility(View.VISIBLE);
                    } else {
                        Toast.makeText(context, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context, "Sorry, something went wrong. Please try again.", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
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
                params.put("Name", name);
                params.put("ContactNo", phone);
                params.put("Email", email);

                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.myGetMySingleton(context).myAddToRequest(stringRequest);
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
                        Toast.makeText(context, "User Successfully Registerd", Toast.LENGTH_SHORT).show();
                        // sessionManager.createSessionLogin(userId);
                        Intent intent = new Intent(context, LoginActivity.class);
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

}