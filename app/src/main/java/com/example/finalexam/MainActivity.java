package com.example.finalexam;

import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private Spinner mSpinner;
    private TextView deaths;
    private TextView confirmed;
    private TextView recovered;
    private TextView critical;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    protected void onStart(){
        super.onStart();
        mSpinner = (Spinner) findViewById(R.id.spinner);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
                if(parent.getItemAtPosition(position).toString().trim().equals("Global")){
                    volleyGlobalGet();
                }else {
                    volleyGet((String) parent.getItemAtPosition(position));
                    // geData((String) parent.getItemAtPosition(position));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
    }
    public void volleyGet(String country){
        String[] splited = country.split("\\s+");
        //String country_code = splited[0].replace("o","");
        String country_code=splited[0].replaceAll("\\p{P}","");
        String url = "https://corona-api.com/countries/"+country_code;


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONObject jsonArray = response.getJSONObject("data");
                    JSONObject jr = jsonArray.getJSONObject("latest_data");

                    deaths = (TextView)findViewById(R.id.textView);
                    confirmed = (TextView)findViewById(R.id.textView4);
                    recovered = (TextView)findViewById(R.id.textView6);
                    critical = (TextView)findViewById(R.id.textView8);
                    deaths.setText(jr.getString("deaths"));
                    confirmed.setText(jr.getString("confirmed"));
                    recovered.setText(jr.getString("recovered"));
                    critical.setText(jr.getString("critical"));

                    Log.v("item",  jr.toString());


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(jsonObjectRequest);

    }

    public void volleyGlobalGet(){

        String url = "https://corona-api.com/timeline";


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {


                    JSONArray jsonArray = response.getJSONArray("data");

                        JSONObject jr = jsonArray.getJSONObject(0);



                    deaths = (TextView)findViewById(R.id.textView);
                    confirmed = (TextView)findViewById(R.id.textView4);
                    recovered = (TextView)findViewById(R.id.textView6);
                    critical = (TextView)findViewById(R.id.textView8);
                    deaths.setText(jr.getString("deaths"));
                    confirmed.setText(jr.getString("confirmed"));
                    recovered.setText(jr.getString("recovered"));
                    critical.setText(jr.getString("active"));

                    Log.v("item",  jr.toString());


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(jsonObjectRequest);

    }

public void notifyDeaths(){
    NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
            .setContentTitle("Deaths")
            .setContentText("Test")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT);

}


}

