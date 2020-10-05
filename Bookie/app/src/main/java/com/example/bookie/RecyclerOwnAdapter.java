package com.example.bookie;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.VoiceInteractor;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.textclassifier.TextLinks;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerOwnAdapter extends RecyclerView.Adapter<RecyclerOwnAdapter.ViewHolder> {
    Context context;
    ArrayList<model_ownbook> model_ownbooks = new ArrayList<>();


    public RecyclerOwnAdapter(Context context, ArrayList<model_ownbook> model_ownbooks) {
        this.context = context;
        this.model_ownbooks = model_ownbooks;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new RecyclerOwnAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.custom_ownbook, viewGroup, false));

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        viewHolder.progressDialog = new ProgressDialog(context);
        viewHolder.progressDialog.setCancelable(true);
        viewHolder.progressDialog.setMessage("Loading");
        viewHolder.progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        viewHolder.progressDialog.setProgress(0);
        viewHolder.tv_book_title.setText(model_ownbooks.get(i).getName());
        viewHolder.tv_author_name.setText(model_ownbooks.get(i).getAuthor());
        viewHolder.tv_mob.setText(LocalSharedPreferences.getemail(context));

        Glide.with(context).load(model_ownbooks.get(i).getImage())
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_book_black)
                .dontAnimate().into(viewHolder.imge_book);

        viewHolder.btn_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewHolder.progressDialog.show();
                RequestQueue requestQueue = Volley.newRequestQueue(context);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://www.wanted.today/wanted_demo/delete_info.php", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Toast.makeText(context, ""+s, Toast.LENGTH_SHORT).show();
                        viewHolder.progressDialog.dismiss();

                        model_ownbooks.remove(viewHolder.getAdapterPosition());
                        notifyDataSetChanged();

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        viewHolder.progressDialog.dismiss();

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("id", model_ownbooks.get(i).getId());
                        params.put("user_id", LocalSharedPreferences.getUserid(context));
                        return params;
                    }
                };
                requestQueue.add(stringRequest);

            }
        });

        viewHolder.btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewHolder.progressDialog.show();

                myMessageDialog(model_ownbooks.get(viewHolder.getAdapterPosition()).getId(),
                        model_ownbooks.get(viewHolder.getAdapterPosition()).getName(),
                        model_ownbooks.get(viewHolder.getAdapterPosition()).getAuthor(),
                        model_ownbooks.get(viewHolder.getAdapterPosition()).getImage(),viewHolder);


            }
        });

    }


    private void myMessageDialog(final String id, String name, String author, String image, final ViewHolder viewHolder) {

        AlertDialog scheduleDialog;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        AlertDialog.Builder builderAlert = new AlertDialog.Builder(context);
        final View country_alert_view = inflater.inflate(R.layout.custom_edit_own_book, null);
        final EditText et_bookname = country_alert_view.findViewById(R.id.et_bookname);
        final EditText et_bookauthor = country_alert_view.findViewById(R.id.et_bookauthor);

        et_bookname.setText(name);
        et_bookauthor.setText(author);

        Button btn_updated = country_alert_view.findViewById(R.id.btn_updated);
        btn_updated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewHolder.progressDialog.show();
                RequestQueue requestQueue = Volley.newRequestQueue(context);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://www.wanted.today/wanted_demo/update_book_info.php", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Toast.makeText(context, "" + s, Toast.LENGTH_SHORT).show();
                        viewHolder.progressDialog.dismiss();

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        viewHolder.progressDialog.dismiss();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("name", et_bookname.getText().toString());
                        params.put("author", et_bookauthor.getText().toString());
                        params.put("id", id);
                        params.put("user_id", LocalSharedPreferences.getUserid(context));
                        return params;
                    }
                };
                requestQueue.add(stringRequest);

            }
        });

        builderAlert.setView(country_alert_view);


        builderAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builderAlert.setCancelable(false);
        scheduleDialog = builderAlert.create();
        builderAlert.show();

    }

    @Override
    public int getItemCount() {
        return model_ownbooks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ProgressDialog progressDialog;
        TextView tv_book_title, tv_author_name, tv_mob;
        CircleImageView imge_book;
        Button btn_update, btn_remove;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_book_title = itemView.findViewById(R.id.tv_book_title);
            tv_author_name = itemView.findViewById(R.id.tv_author_name);
            tv_mob = itemView.findViewById(R.id.tv_mob);
            imge_book = itemView.findViewById(R.id.imge_book);
            btn_update = itemView.findViewById(R.id.btn_update);
            btn_remove = itemView.findViewById(R.id.btn_remove);


        }
    }
}
