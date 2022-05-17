package com.example.kmc;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kmc.CLogin.CollectorUserDetails;
import com.example.kmc.CLogin.CollectorZone;

import java.util.ArrayList;

public class myadapterVillage extends RecyclerView.Adapter<myadapterVillage.myviewholder>{
    ArrayList<Mandals> datalist;

    public myadapterVillage(ArrayList<Mandals> datalist) {
        this.datalist = datalist;
    }

    @NonNull
    @Override
    public myadapterVillage.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow,parent,false);
        return new myadapterVillage.myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myadapterVillage.myviewholder holder, @SuppressLint("RecyclerView") int position) {
        holder.t1.setText(datalist.get(position).getVillage());


        holder.t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(holder.t1.getContext(), CollectorZone.class);

                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("village",datalist.get(position).getVillage());
                holder.t1.getContext().startActivity(i);

            }
        });
    }




    @Override
    public int getItemCount() {
        return datalist.size();
    }

    class myviewholder extends RecyclerView.ViewHolder
    {
        TextView t1;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            t1=itemView.findViewById(R.id.t1);
        }
    }
}