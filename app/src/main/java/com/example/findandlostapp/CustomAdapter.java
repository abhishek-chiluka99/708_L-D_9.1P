package com.example.findandlostapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;

    private Activity activity;

    private ArrayList item_id, item_type, item_desc, item_date, item_loc;

    public CustomAdapter(Activity activity, Context context, ArrayList item_id, ArrayList item_type, ArrayList item_desc, ArrayList item_date,ArrayList item_loc){
        this.activity = activity;
        this.context = context;
        this.item_id = item_id;
        this.item_type = item_type;
        this.item_desc = item_desc;
        this.item_date = item_date;
        this.item_loc = item_loc;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

       // holder.item_id_txt.setText(String.valueOf(item_id.get(position)));
        holder.item_type_txt.setText(String.valueOf(item_type.get(position)));
        holder.item_desc_txt.setText(String.valueOf(item_desc.get(position)));
//        holder.item_date_txt.setText(String.valueOf(item_date.get(position)));
//        holder.item_loc_txt.setText(String.valueOf(item_loc.get(position)));

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Item_Desc.class);
                intent.putExtra("id", String.valueOf(item_id.get(holder.getAdapterPosition())));
                intent.putExtra("type", String.valueOf(item_type.get(holder.getAdapterPosition())));
                intent.putExtra("desc",String.valueOf(item_desc.get(holder.getAdapterPosition())));
                intent.putExtra("loc",String.valueOf(item_loc.get(holder.getAdapterPosition())));
                activity.startActivityForResult(intent,1);
            }
        });

    }

    @Override
    public int getItemCount() {
        return item_id.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView item_type_txt, item_desc_txt, item_id_txt, item_loc_txt, item_date_txt;

        LinearLayout mainLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
          //  item_id_txt = itemView.findViewById(R.id.item_id);
            item_type_txt = itemView.findViewById(R.id.item_type);
            item_desc_txt = itemView.findViewById(R.id.item_desc);
//            item_date_txt = itemView.findViewById(R.id.item_date);
//            item_loc_txt = itemView.findViewById(R.id.item_loc);
            mainLayout  = itemView.findViewById(R.id.mainLayout);
        }
    }
}
