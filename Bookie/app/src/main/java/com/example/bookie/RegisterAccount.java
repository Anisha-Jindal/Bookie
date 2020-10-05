package com.example.bookie;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterAccount extends AppCompatActivity {
    EditText et_name, et_userid,et_password,et_mob,et_collage_name,et_address;
    Button btn_submit;

    private static ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_account);

        et_name=findViewById(R.id.et_name);
        et_userid=findViewById(R.id.et_userid);
        et_password=findViewById(R.id.et_password);
        et_mob=findViewById(R.id.et_mob);
        et_collage_name=findViewById(R.id.et_collage_name);
        et_address=findViewById(R.id.et_address);
        btn_submit=findViewById(R.id.btn_submit);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading..");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setProgress(0);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CallAPIRegister();
                progressDialog.show();

            }
        });






    }

    private void CallAPIRegister() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://www.wanted.today/wanted_demo/register_user.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Toast.makeText(RegisterAccount.this, ""+s, Toast.LENGTH_SHORT).show();
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.getString("success").equals("1")) {
                        progressDialog.dismiss();

                        Toast.makeText(RegisterAccount.this, "Your Account Register", Toast.LENGTH_SHORT).show();

                        Intent i = new Intent(RegisterAccount.this,Login.class);
                        startActivity(i);
                    }

                    else{
                        Toast.makeText(RegisterAccount.this, "Try Again", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(RegisterAccount.this, ""+volleyError, Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params= new HashMap<>();
                params.put("name",et_name.getText().toString());
                params.put("email",et_userid.getText().toString());
                params.put("password",et_password.getText().toString());
                params.put("mobile",et_mob.getText().toString());
                params.put("college",et_collage_name.getText().toString());
                params.put("address",et_address.getText().toString());

                Log.d("wkljafsfdsjoewgfh",params.toString());


                return  params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
