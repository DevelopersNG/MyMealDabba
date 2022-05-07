package com.example.mymealdabba;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Paint;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.widget.Toast;

import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {


    //Fields
    Button btnNameClear, btnMobileClear, btnEmailClear, btnSignup, btnSubmit,btnOTPClear,btnResendOTP;
    EditText txtName, txtMobile, txtEmail,txtOTP,editTextConfirmOtp;
    TextView lblTerms1, lblTerms,lblOTPMessage;
    AppCompatButton buttonConfirm;
    private ProgressDialog progressDialog;
    FrameLayout frameLayoutTxtOPT;

    private static final String TAG = SignUpActivity.class.getSimpleName();
    private BroadcastReceiver mRegistrationBroadcastReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        btnNameClear = (Button) this.findViewById(R.id.btnNameClear);
        btnEmailClear = (Button) this.findViewById(R.id.btnEmailClear);
        btnMobileClear = (Button) this.findViewById(R.id.btnMobileClear);
        btnSignup = (Button) this.findViewById(R.id.btnSignup);
        btnSubmit = (Button)findViewById(R.id.btnSubmit) ;
        btnOTPClear = (Button)findViewById(R.id.btnOTPClear) ;
        btnOTPClear.setVisibility(View.GONE);
        btnResendOTP = (Button)findViewById(R.id.btnResendOTP);
        btnResendOTP.setPaintFlags(btnResendOTP.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        btnResendOTP.setVisibility(View.GONE);

        txtName = (EditText) this.findViewById(R.id.txtName);
        txtMobile = (EditText) this.findViewById(R.id.txtMobile);
        txtEmail = (EditText) this.findViewById(R.id.txtEmail);
        txtOTP = (EditText) this.findViewById(R.id.txtOTP);
        editTextConfirmOtp=findViewById(R.id.editTextOtp);
        buttonConfirm=findViewById(R.id.buttonConfirm);




        frameLayoutTxtOPT=findViewById(R.id.frameLayoutTxtOPT);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String names = txtName.getText().toString();
                String email = txtEmail.getText().toString();
                String numbers = txtMobile.getText().toString();



//                if (names.isEmpty()){
//                    txtName.setError("Enter The Name");
//                    txtName.requestFocus();
//                    return;
//                }else if (email.isEmpty()){
//                    txtEmail.setError("Field can't be empty");
//
//                }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
//                    txtEmail.setError("Please Enter a Valid Email Address");
//                    txtEmail.setError(null);
//                    return ;
//                }else if (numbers.isEmpty()){
//                    txtMobile.setError("Enter The Mobile No");
//                    txtMobile.requestFocus();
//
//                }else  if (numbers.equals("") || numbers.equals(null) || numbers.length() > 10 || numbers.length() < 9) {
//                    txtMobile.setError("Enter Only 10 digit number");
//                    return;
//
//
//                }else {
//
//
//                    androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(SignUpActivity.this);
//                    View view1 = getLayoutInflater().inflate(R.layout.otp_page, null);
//                    EditText otpedittext = (EditText) view1.findViewById(R.id.otpedittext);
//                    Button verifyotp = (Button) view1.findViewById(R.id.verifyotp);

//                    verifyotp.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            if (!otpedittext.getText().toString().isEmpty()) {
//                                Toast.makeText(SignUpActivity.this, "Successfully", Toast.LENGTH_SHORT).show();
//                            } else {
//                                Toast.makeText(SignUpActivity.this, "Successfully", Toast.LENGTH_SHORT).show();
//                            }
//
//                        }
//                    });
//
//                    builder.setView(view1);
//                    androidx.appcompat.app.AlertDialog dialog = builder.create();
//                    dialog.show();
//                    Intent intent = new Intent(getApplicationContext(), NavigationActivity.class);
//                    startActivity(intent);
//               }
//


//                String type="Signup";
//                BackgroundTask backgroundTask=new BackgroundTask(getApplicationContext());
//                backgroundTask.execute(type ,names,email,numbers);
//                finish();
               Intent intent=new Intent(getApplicationContext(),NavigationActivity.class);
               startActivity(intent);

           }
       });


   }
//    private void confirmOtp() throws JSONException {
//        //Creating a LayoutInflater object for the dialog box
//        LayoutInflater li = LayoutInflater.from(this);
//        //Creating a view to get the dialog box
//        View confirmDialog = li.inflate(R.layout.dialogconfirmotp, null);
//
//        //Initizliaing confirm button fo dialog box and edittext of dialog box
//        buttonConfirm = (AppCompatButton) confirmDialog.findViewById(R.id.buttonConfirm);
//        editTextConfirmOtp = (EditText) confirmDialog.findViewById(R.id.editTextOtp);
//
//        //Creating an alertdialog builder
//        AlertDialog.Builder alert = new AlertDialog.Builder(this);
//
//        //Adding our dialog box to the view of alert dialog
//        alert.setView(confirmDialog);
//
//        //Creating an alert dialog
//        final AlertDialog alertDialog = alert.create();
//
//        //Displaying the alert dialog
//        alertDialog.show();
//    }
    @Override
    public void onClick(View view) {
        
    }
}