package com.example.bookie;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.textclassifier.TextLinks;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.os.Build.VERSION_CODES.O;

public class OwnBook extends AppCompatActivity {
    RecyclerView recycler;
    ArrayList<model_ownbook> model_ownbooks = new ArrayList<>();
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_own_book);
        recycler = findViewById(R.id.recycler);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading..");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setProgress(0);

        OWNBOOKAPI();
        progressDialog.show();
    }

    private void OWNBOOKAPI() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://www.wanted.today/wanted_demo/view_book_info.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
            //    Toast.makeText(OwnBook.this, "" + s, Toast.LENGTH_SHORT).show();
              progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                        String title = jsonObject1.getString("author");
                    //    Toast.makeText(OwnBook.this, "" + title, Toast.LENGTH_SHORT).show();

                        model_ownbooks.add(new model_ownbook(jsonObject1.getString("id"), jsonObject1.getString("name"),
                                jsonObject1.getString("author"), jsonObject1.getString("image"),
                                jsonObject1.getString("user_id"), jsonObject1.getString("created_date")));

                        recycler.setLayoutManager(new LinearLayoutManager(OwnBook.this));
                        recycler.setAdapter(new RecyclerOwnAdapter(OwnBook.this, model_ownbooks));


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                progressDialog.dismiss();
                Toast.makeText(OwnBook.this, ""+volleyError, Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", LocalSharedPreferences.getUserid(OwnBook.this));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
