package com.infooby.firealert;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.Calendar;
import java.util.Hashtable;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    WebView webView;

    final String token = FirebaseInstanceId.getInstance().getToken();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Context context = MainActivity.this;
        webView = (WebView) findViewById(R.id.web_frame);
        Intent i= new Intent(context, ServiceClass.class);
        context.startService(i);
        submitToken();
        gotoPage();
    }
    private void gotoPage(){

        String text = "http://justwash.in/rohitcoder/web.html";

        WebSettings webSettings = webView.getSettings();
        webSettings.setBuiltInZoomControls(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setSupportZoom(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setDefaultTextEncodingName("utf-8");
        webView.setWebViewClient(new Callback());  //HERE IS THE MAIN CHANGE
        webView.loadUrl(text);

    }
    private class Callback extends WebViewClient {  //HERE IS THE MAIN CHANGE.

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return (false);
        }
        public void onReceivedError(WebView view,int errorCode,String description,String fallingUrl){
            webView.loadUrl("file:///android_asset/error.html");
        }

    }
    public void  submitToken(){

        StringRequest req = new StringRequest(Request.Method.POST, "http://justwash.in/rohitcoder/fcm_accept.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(),"ERROR - Report it to Rohit",Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volerror) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new Hashtable<String,String>();
                String fcm_token;
                fcm_token = token;
                params.put("token",fcm_token);
                return params;
            }
        };
        RequestQueue processdata = Volley.newRequestQueue(getApplicationContext());
        processdata.add(req);
    }
}
