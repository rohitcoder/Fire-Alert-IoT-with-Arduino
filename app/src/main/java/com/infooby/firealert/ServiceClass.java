package com.infooby.firealert;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Hashtable;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Rohit Kumar on 1/14/2018.
 */

public class ServiceClass extends Service
{

    private Timer timer = new Timer();


    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                sendRequestToServer();   //Your code here
            }
        }, 0, 1*60*1000);//5 Minutes
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }
    public void sendRequestToServer(){

        StringRequest req = new StringRequest(Request.Method.POST, "http://justwash.in/old/rohitcoder/fcm_notify.php", new Response.Listener<String>() {
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
                fcm_token = "rohit";
                params.put("token",fcm_token);
                return params;
            }
        };
        RequestQueue processdata = Volley.newRequestQueue(getApplicationContext());
        processdata.add(req);
    }
}
