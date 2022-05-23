package com.example.mymealdabba;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.utils.widget.ImageFilterView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Header;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import java.io.InputStream;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class LandingPage extends AppCompatActivity {
    ImageView imageViewFacebook, imageViewTwitter, imageViewInstagram;

   ArrayList<String>cityList;
   ArrayAdapter cityAdapter;
   TextView lblContactNo;
    //    InputStream is = null;
//    String result = null;
//    String line = null;
    Button btnGo1;
//    Spinner cityDropDown;
    String TEST_URL="http://192.168.0.105/mymealdabba/city.json";

          AutoCompleteTextView  autoCompleteTextViewLocations,autoCompleteTextViewService;
    RequestQueue requestQueue;

    private class PostAsyncTask extends AsyncTask<String,Void,JSONObject> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected JSONObject doInBackground(String... params) {
            String value="mmdnashik";
            Map postData = new HashMap<>();
            postData.put("key",value);
            return post(TEST_URL,postData);
        }

        @Override
        protected void onPostExecute(JSONObject response) {
            super.onPostExecute(response);
            //All your UI operation can be performed here
            //Response string can be converted to JSONObject/JSONArray like
            try {
                Toast.makeText(LandingPage.this, String.format("%s : %s",response.getString("Cities")), Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(LandingPage.this, String.format("%s","Something went wrong!!!!!!"), Toast.LENGTH_LONG).show();

            }
            System.out.println(response);
        }
    }


    public JSONObject post(String REQUEST_URL,Map<String, Object> params) {
        JSONObject jsonObject = null;
        BufferedReader reader = null;
        Log.e("******** url - ",REQUEST_URL+"");
        try { URL url = new URL(REQUEST_URL);
            StringBuilder postData = new StringBuilder();
            for (Map.Entry<String, Object> param : params.entrySet()) {
                if (postData.length() != 0) postData.append('&');
                postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
            }
            Log.e("******** - ",postData+"");
            byte[] postDataBytes = postData.toString().getBytes("UTF-8");
            Log.e("******** - ",postData.toString().getBytes("UTF-8")+"");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setConnectTimeout(8000);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setDoOutput(true);
            connection.getOutputStream().write(postDataBytes);
            connection.connect();

            StringBuilder sb;
            int statusCode = connection.getResponseCode();
            Log.e("******** statuscode- - ",statusCode+"");
            if (statusCode == 200) {
                sb = new StringBuilder();
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }

                System.out.println(sb);
                jsonObject = new JSONObject(sb.toString());
            }
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return jsonObject;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);
        ViewPager mViewPager = (ViewPager) findViewById(R.id.viewPage);

        imageViewFacebook = findViewById(R.id.imageViewFacebook);
        imageViewTwitter = findViewById(R.id.imageViewTwitter);
        imageViewInstagram = findViewById(R.id.imageViewInstagram);
        lblContactNo = findViewById(R.id.lblContactNo);

        requestQueue = Volley.newRequestQueue(this);


//        cityAdapter=new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line,cityList);
        autoCompleteTextViewLocations = findViewById(R.id.autoCompleteTextViewLocations);
        autoCompleteTextViewService = findViewById(R.id.autoCompleteTextViewService);
        btnGo1 = findViewById(R.id.btnGo1);

        new PostAsyncTask().execute();


//        class AsyncT extends AsyncTask<Void,Void,Void>{
//
//            @Override
//            protected Void doInBackground(Void... params) {
//
//                try {
//                    URL url = new URL("https://192.168.1.34/mymealdabba/mmdwebservice/search/getAllCities"); //Enter URL here
//                    HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
//                    httpURLConnection.setDoOutput(true);
//                    httpURLConnection.setRequestMethod("POST"); // here you are telling that it is a POST request, which can be changed into "PUT", "GET", "DELETE" etc.
//                    httpURLConnection.setRequestProperty("Content-Type", "application/json"); // here you are setting the `Content-Type` for the data you are sending which is `application/json`
//                    httpURLConnection.connect();
//
//                    JSONObject jsonObject = new JSONObject();
//                    jsonObject.put("apikey", "mmdnashik");
//
//                    DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
//                    wr.writeBytes(jsonObject.toString());
//                    wr.flush();
//                    wr.close();
//
//                } catch (MalformedURLException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//                return null;
//            }
//
//
//        }


    }



}