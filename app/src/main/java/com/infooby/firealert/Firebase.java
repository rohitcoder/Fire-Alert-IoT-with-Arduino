package com.infooby.firealert;

import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.util.Hashtable;
import java.util.Map;

/**
 * Created by Rohit Kumar on 1/12/2018.
 */
public class Firebase extends FirebaseInstanceIdService {
    public static final  String TAG = "MyFirebaseInsIdService";
    @Override
    public void onTokenRefresh() {
        final String refreshToken = FirebaseInstanceId.getInstance().getToken();

        StringRequest reqforLogin = new StringRequest(Request.Method.POST, "http://justwash.in/rohitcoder/fcm_accept.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Toast.makeText(getApplicationContext(),"SENT",Toast.LENGTH_LONG).show();
                } catch (Exception e) {
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
                fcm_token = refreshToken;
                params.put("token",fcm_token);
                return params;
            }
        };
        RequestQueue processdata = Volley.newRequestQueue(this);
        processdata.add(reqforLogin);
    }
}
