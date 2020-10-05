package com.example.bookie;

import android.app.ProgressDialog;
import android.nfc.NfcAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

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

public class ViewAllBooks extends AppCompatActivity {
    RecyclerView recycler;
    ArrayList<model_allbook> model_allbooks = new ArrayList<>();
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_books);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading..");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setProgress(0);

        recycler = findViewById(R.id.recycler);
        CallApi();
        progressDialog.show();
    }

    private void CallApi() {

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, "http://www.wanted.today/wanted_demo/all_books_info.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                //  Toast.makeText(ViewAllBooks.this, "" + s, Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                        String Name = jsonObject1.getString("name");
                        //   Toast.makeText(ViewAllBooks.this, ""+Name, Toast.LENGTH_SHORT).show();

                        model_allbooks.add(new model_allbook(jsonObject1.getString("name"), jsonObject1.getString("author"),
                                jsonObject1.getString("image"), jsonObject1.getString("created_date"), jsonObject1.getString("email"),
                                jsonObject1.getString("mobile"), jsonObject1.getString("college"), jsonObject1.getString("address")));
                        recycler.setLayoutManager(new LinearLayoutManager(ViewAllBooks.this));
                        recycler.setAdapter(new RecyclerAdapter(ViewAllBooks.this, model_allbooks));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                progressDialog.dismiss();
                Toast.makeText(ViewAllBooks.this, "" + volleyError, Toast.LENGTH_SHORT).show();

            }
        });
        requestQueue.add(request);

    }
}
