package com.example.bookie;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

public class CreteBook extends AppCompatActivity {

    EditText et_bookname,et_book_author;
    ImageView et_book_image;
    Button btn_create;

    private static ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crete_book);
        et_bookname=findViewById(R.id.et_bookname);
        et_book_author=findViewById(R.id.et_book_author);
        et_book_image=findViewById(R.id.et_book_image);
        btn_create=findViewById(R.id.btn_create);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading..");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setProgress(0);


        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                progressDialog.show();
                RequestQueue requestQueue = Volley.newRequestQueue(CreteBook.this);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://www.wanted.today/wanted_demo/insert_info.php", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Log.d("data",s);
                        progressDialog.dismiss();
                        Toast.makeText(CreteBook.this, ""+s, Toast.LENGTH_SHORT).show();

                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            if (jsonObject.getString("success").equals("1")) {
                                Toast.makeText(CreteBook.this, "Book Created", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();

                                finish();
                                startActivity(getIntent());
                                et_bookname.setText(null);
                                et_book_author.setText(null);
                            }
                            else {
                                progressDialog.dismiss();
                                Toast.makeText(CreteBook.this, "Try Again", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        progressDialog.dismiss();
                        Toast.makeText(CreteBook.this, ""+volleyError, Toast.LENGTH_SHORT).show();

                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params = new HashMap<>();
                        params.put("name",et_bookname.getText().toString());
                        params.put("author",et_book_author.getText().toString());
                       // params.put("image",)
                        params.put("user_id",LocalSharedPreferences.getUserid(CreteBook.this));
                        return params;
                    }
                };

                requestQueue.add(stringRequest);

            }
        });


    }
}
