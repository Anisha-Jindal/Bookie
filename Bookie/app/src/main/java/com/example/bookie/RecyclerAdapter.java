package com.example.bookie;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.ContentFrameLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    Context context;
    ArrayList<model_allbook> model_allbooks = new ArrayList<>();

    public RecyclerAdapter(Context context, ArrayList<model_allbook> model_allbooks) {
        this.context = context;
        this.model_allbooks = model_allbooks;
    }

    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new RecyclerAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.custom_allbok, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerAdapter.ViewHolder viewHolder, int i) {

        viewHolder.tv_book_title.setText(model_allbooks.get(i).getName());
        viewHolder.tv_author_name.setText(model_allbooks.get(i).getAuthor());
        viewHolder.btn_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myMessageDialog(model_allbooks.get(viewHolder.getAdapterPosition()).getAuthor(),
                        model_allbooks.get(viewHolder.getAdapterPosition()).getName(),
                        model_allbooks.get(viewHolder.getAdapterPosition()).getEmail(),
                        model_allbooks.get(viewHolder.getAdapterPosition()).getMobile(),viewHolder);


            }
        });
    }

    private void myMessageDialog(String author, String name, final String email, final String mobile, ViewHolder viewHolder) {
        AlertDialog scheduleDialog;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        AlertDialog.Builder builderAlert = new AlertDialog.Builder(context);
        final View country_alert_view = inflater.inflate(R.layout.contact, null);

        TextView textmakeCall =country_alert_view.findViewById(R.id.textmakeCall);
        TextView textsendMessage =country_alert_view.findViewById(R.id.textsendMessage);
        TextView textsendEmail =country_alert_view.findViewById(R.id.textsendEmail);

        textmakeCall.setText(mobile);
        textsendMessage.setText(mobile);
        textsendEmail.setText(email);


        textmakeCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mobile != null && !mobile.isEmpty() && mobile.length() >= 10) {

                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + mobile));
                    context.startActivity(intent);
                } else {
                    Toast.makeText(context, "Missing contact number.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        textsendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mobile != null && !mobile.isEmpty() && mobile.length() >= 10) {
                    Uri uri = Uri.parse("smsto:" + mobile);
                    Intent it = new Intent(Intent.ACTION_SENDTO, uri);
                    it.putExtra("sms_body", "The SMS text");
                    context.startActivity(it);
                } else {
                    Toast.makeText(context, "Missing contact number.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        textsendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (email != null && !email.isEmpty() && email.length() > 3) {
                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "" + email, null));
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
                    emailIntent.putExtra(Intent.EXTRA_TEXT, "Body");
                    context.startActivity(emailIntent);
                } else {
                    Toast.makeText(context, "Missing Email-Id .", Toast.LENGTH_SHORT).show();
                }
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
        return model_allbooks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_book_title, tv_author_name;
        Button btn_more;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_book_title = itemView.findViewById(R.id.tv_book_title);
            tv_author_name = itemView.findViewById(R.id.tv_author_name);
            btn_more=itemView.findViewById(R.id.btn_more);
        }
    }
}
