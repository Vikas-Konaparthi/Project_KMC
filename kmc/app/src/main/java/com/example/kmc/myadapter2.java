package com.example.kmc;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kmc.PSLogin.userDetails;
import com.example.kmc.SPLogin.SPUserDetails;

import java.util.ArrayList;

public class myadapter2 extends RecyclerView.Adapter<myadapter2.myviewholder>
{
    ArrayList<Individual> datalist;

    public myadapter2(ArrayList<Individual> datalist) {
        this.datalist = datalist;
    }

    @NonNull
    @Override
    public myadapter2.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow,parent,false);
        return new myadapter2.myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, @SuppressLint("RecyclerView") int position) {
        holder.t1.setText(datalist.get(position).getName());

        holder.t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(holder.t1.getContext(), SPUserDetails.class);
                i.putExtra("uname",datalist.get(position).getName());
                i.putExtra("ufname",datalist.get(position).getFatherName());
                i.putExtra("uAge",datalist.get(position).getAge());
                i.putExtra("uHnumber",datalist.get(position).getHouseNo());
                i.putExtra("uAadharNumber",datalist.get(position).getAadhar());
                i.putExtra("uMobileNo",datalist.get(position).getPhoneNo() );
                i.putExtra("uPreferredUnit",datalist.get(position).getPreferredUnit());
                i.putExtra("uBankName",datalist.get(position).getBankName());
                i.putExtra("uBankAccNumber",datalist.get(position).getBankAccNo());
                i.putExtra("psUpload",datalist.get(position).getPsUpload());


                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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
