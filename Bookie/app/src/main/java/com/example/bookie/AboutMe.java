package com.example.bookie;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.textclassifier.TextLinks;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AboutMe extends AppCompatActivity {
    EditText et_name, et_email,et_password, et_mob, et_collage, et_address;
    Button btn_edit;
    Context context;
    private static ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);
        context = AboutMe.this;

        et_name=findViewById( R.id.et_name);
        et_email=findViewById( R.id.et_email);
        et_password=findViewById(R.id.et_password);
        et_mob=findViewById( R.id.et_mob);
        et_collage=findViewById( R.id.et_collage);
        et_address=findViewById( R.id.et_address);

        btn_edit=findViewById(R.id.btn_edit);

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditAPI();
                progressDialog.show();
            }
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading..");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setProgress(0);
        progressDialog.show();
        CallApiInfo();
    }

    private void EditAPI() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequested = new StringRequest(Request.Method.POST, "http://www.wanted.today/wanted_demo/update_user.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                progressDialog.dismiss();
                Log.d("dsfd",s);
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.getString("success").equals("1")) {
                        finish();
                        startActivity(getIntent());
                        Toast.makeText(context, "Info Updated", Toast.LENGTH_SHORT).show();

                    } else{
                        Toast.makeText(context, "Try Again", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                progressDialog.dismiss();
                Toast.makeText(context, ""+volleyError, Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id",LocalSharedPreferences.getUserid(context));
                params.put("name",et_name.getText().toString());
                params.put("email",LocalSharedPreferences.getemail(context));
                params.put("password",et_password.getText().toString());
                params.put("mobile",et_mob.getText().toString());
                params.put("college",et_collage.getText().toString());
                params.put("address",et_address.getText().toString());

                return  params;
            }
        };
        requestQueue.add(stringRequested);

    }

    private void CallApiInfo() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://www.wanted.today/wanted_demo/view.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                progressDialog.dismiss();
              //  Toast.makeText(context, ""+s, Toast.LENGTH_SHORT).show();
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray jsonArray= jsonObject.getJSONArray("data");
                    for (int i =0;i<jsonArray.length();i++){
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                        String  nameee = jsonObject1.getString("name");
                      //  Toast.makeText(context, ""+nameee, Toast.LENGTH_SHORT).show();

                        et_name.setText(jsonObject1.getString("name"));
                        et_email.setText(jsonObject1.getString("email"));
                        et_password.setText(jsonObject1.getString("password"));
                        et_mob.setText(jsonObject1.getString("mobile"));
                        et_collage.setText(jsonObject1.getString("college"));
                        et_address.setText(jsonObject1.getString("address"));



                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                progressDialog.dismiss();
                Toast.makeText(context, ""+volleyError, Toast.LENGTH_SHORT).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", LocalSharedPreferences.getUserid(context));
                return params;
            }
        };
        requestQueue.add(stringRequest);


    }
}
