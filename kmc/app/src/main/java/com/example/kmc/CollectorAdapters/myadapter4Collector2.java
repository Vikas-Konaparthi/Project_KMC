package com.example.kmc.CollectorAdapters;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kmc.CLogin.CollectorUserDetails;
import com.example.kmc.CLogin.CollectorUserDetailsAmountToDB;
import com.example.kmc.Individual;
import com.example.kmc.List;
import com.example.kmc.R;
import com.example.kmc.SelectionElements;

import java.util.ArrayList;
import java.util.Locale;

public class myadapter4Collector2 extends RecyclerView.Adapter<myadapter4Collector2.myviewholder>
{
    ArrayList<Individual> datalist;
    ArrayList<SelectionElements> dataAadhar=new ArrayList<>();
    String village;
    private List list;
    String t;

    public myadapter4Collector2(ArrayList<Individual> datalist, String village, Context context, List l) {
        this.datalist = datalist;
        this.village=village;
        this.list=l;
    }

    @NonNull
    @Override
    public myadapter4Collector2.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow,parent,false);
        return new myadapter4Collector2.myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myadapter4Collector2.myviewholder holder, @SuppressLint("RecyclerView") int position) {
        holder.t1.setText(datalist.get(position).getName());
        holder.t2.setText(datalist.get(position).getStatus());
        holder.t3.setText("Preferred Unit: "+datalist.get(position).getPreferredUnit());
        holder.t4.setText("DB Account Amount: "+datalist.get(position).getDbAccount());
        holder.t5.setText("Approved Amount: "+datalist.get(position).getApprovalAmount());
        String inprogress="In Progress";
        String approve="approved";
        String reject="rejected";
        String collector_action_required1="Waiting for Collector Sanction";
        String collector_action_required2="Waiting for Collector Approval";

        if(datalist.get(position).getStatus().toLowerCase(Locale.ROOT).equals(approve.toLowerCase(Locale.ROOT)))
        {
            holder.t2.setTextColor(Color.parseColor("#00873E"));
        }else if(datalist.get(position).getSpApproved().equals("no")||datalist.get(position).getSpApproved2().equals("no")
        ||datalist.get(position).getSpApproved3().equals("no")||datalist.get(position).getSoApproved().equals("no")
        ||datalist.get(position).getCtrApproved().equals("no")||datalist.get(position).getCtrApproved2().equals("no"))
        {
            holder.t2.setTextColor(Color.parseColor("#FF0000"));
        }else if(datalist.get(position).getStatus().toLowerCase(Locale.ROOT).equals(collector_action_required1.toLowerCase(Locale.ROOT))||datalist.get(position).getStatus().toLowerCase(Locale.ROOT).equals(collector_action_required2.toLowerCase(Locale.ROOT)))
        {
            holder.t2.setTextColor(Color.parseColor("#06038D"));
        }
        else{
            //#00873E
            holder.t2.setTextColor(Color.parseColor("#F6BE00"));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(holder.t1.getContext(), CollectorUserDetailsAmountToDB.class);
                i.putExtra("uname",datalist.get(position).getName());
                i.putExtra("ufname",datalist.get(position).getFatherName());
                i.putExtra("uAge",datalist.get(position).getAge());
                i.putExtra("uHnumber",datalist.get(position).getHouseNo());
                i.putExtra("uVillage",datalist.get(position).getVillage());
                i.putExtra("uMandal",datalist.get(position).getMandal());
                i.putExtra("uDistrict",datalist.get(position).getDistrict());
                i.putExtra("uAadharNumber",datalist.get(position).getAadhar());
                i.putExtra("uMobileNo",datalist.get(position).getPhoneNo() );
                i.putExtra("uPreferredUnit",datalist.get(position).getPreferredUnit());
                i.putExtra("uBankName",datalist.get(position).getBankName());
                i.putExtra("uBankAccNumber",datalist.get(position).getBankAccNo());
                i.putExtra("upsUpload",datalist.get(position).getPsUpload());
                i.putExtra("usecOfficerUpload",datalist.get(position).getSecOfficerUpload());
                i.putExtra("uSPRemarks",datalist.get(position).getSp_remarks());
                i.putExtra("uSORemarks",datalist.get(position).getSo_remarks());
                i.putExtra("uSOApproved",datalist.get(position).getSecOfficerApproved());
                i.putExtra("uCollectorApproved",datalist.get(position).getCtrApproved());
                i.putExtra("uCollectorApprovalAmount",datalist.get(position).getApprovalAmount());
                i.putExtra("uDBAccount",datalist.get(position).getDbAccount());
                i.putExtra("uBankIFSC",datalist.get(position).getBankIFSC());
                i.putExtra("village",village);
                i.putExtra("uVendorIFSC",datalist.get(position).getVendorIFSC());
                i.putExtra("uVendorBankAccount",datalist.get(position).getVendorAccountNo());
                i.putExtra("uVendorName",datalist.get(position).getVendorName());
                i.putExtra("uGroundingStatus",datalist.get(position).getGroundingStatus());
                i.putExtra("uGroundingImage",datalist.get(position).getGrounding_img());
                i.putExtra("uApprovalAmount",datalist.get(position).getApprovalAmount());
                i.putExtra("uDbAccount",datalist.get(position).getDbAccount());
                i.putExtra("uSPApproved2",datalist.get(position).getSpApproved2());
                i.putExtra("uSPAmountApproved",datalist.get(position).getSpAmountApproved());
                i.putExtra("uRequestedAmount",datalist.get(position).getIndividualAmountRequired());
                i.putExtra("uDbBankName",datalist.get(position).getDbBankName());
                i.putExtra("uDbAccountNo",datalist.get(position).getDbBankAccNo());
                i.putExtra("uDbIFSC",datalist.get(position).getDbBankIFSC());
                i.putExtra("uStatus",datalist.get(position).getStatus());



                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                holder.t1.getContext().startActivity(i);
                ((Activity)holder.t1.getContext()).finish();
            }
        }) ;

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                holder.cardView.setCardBackgroundColor(Color.parseColor("#edbef7"));
                t = datalist.get(position).getAadhar();
                if (dataAadhar.contains(t)) {
                    Toast.makeText(view.getContext(), t+"Already Selected", Toast.LENGTH_SHORT).show();
                } else {
                    SelectionElements s= new SelectionElements(datalist.get(position).getAadhar(),datalist.get(position).getSpAmountApproved(),datalist.get(position).getStatus());
                    dataAadhar.add(s);
                    list.push(dataAadhar);
                    Toast.makeText(view.getContext (), t+" Added", Toast.LENGTH_SHORT).show();
                }

                holder.check.setVisibility(View.VISIBLE);
                return true;

            }

        });
//        holder.check.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                t = datalist.get(position).getAadhar();
//                    SelectionElements s= new SelectionElements(datalist.get(position).getAadhar(),datalist.get(position).getSpAmountApproved());
//                    dataAadhar.remove(s);
//                    list.push(dataAadhar);
//                    holder.cardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
//                    holder.check.setVisibility(View.GONE);
//                    Toast.makeText(view.getContext (), t+"Removed", Toast.LENGTH_SHORT).show();
//
//
//            }
//        });

//        holder.t1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(holder.t1.getContext(), CollectorUserDetailsAmountToDB.class);
//                i.putExtra("uname",datalist.get(position).getName());
//                i.putExtra("ufname",datalist.get(position).getFatherName());
//                i.putExtra("uAge",datalist.get(position).getAge());
//                i.putExtra("uHnumber",datalist.get(position).getHouseNo());
//                i.putExtra("uVillage",datalist.get(position).getVillage());
//                i.putExtra("uMandal",datalist.get(position).getMandal());
//                i.putExtra("uDistrict",datalist.get(position).getDistrict());
//                i.putExtra("uAadharNumber",datalist.get(position).getAadhar());
//                i.putExtra("uMobileNo",datalist.get(position).getPhoneNo() );
//                i.putExtra("uPreferredUnit",datalist.get(position).getPreferredUnit());
//                i.putExtra("uBankName",datalist.get(position).getBankName());
//                i.putExtra("uBankAccNumber",datalist.get(position).getBankAccNo());
//                i.putExtra("upsUpload",datalist.get(position).getPsUpload());
//                i.putExtra("usecOfficerUpload",datalist.get(position).getSecOfficerUpload());
//                i.putExtra("uSPRemarks",datalist.get(position).getSp_remarks());
//                i.putExtra("uSORemarks",datalist.get(position).getSo_remarks());
//                i.putExtra("uSOApproved",datalist.get(position).getSecOfficerApproved());
//                i.putExtra("uCollectorApproved",datalist.get(position).getCtrApproved());
//                i.putExtra("uCollectorApprovalAmount",datalist.get(position).getApprovalAmount());
//                i.putExtra("uDBAccount",datalist.get(position).getDbAccount());
//                i.putExtra("uBankIFSC",datalist.get(position).getBankIFSC());
//                i.putExtra("village",village);
//                i.putExtra("uVendorIFSC",datalist.get(position).getVendorIFSC());
//                i.putExtra("uVendorBankAccount",datalist.get(position).getVendorAccountNo());
//                i.putExtra("uVendorName",datalist.get(position).getVendorName());
//                i.putExtra("uGroundingStatus",datalist.get(position).getGroundingStatus());
//                i.putExtra("uGroundingImage",datalist.get(position).getGrounding_img());
//                i.putExtra("uApprovalAmount",datalist.get(position).getApprovalAmount());
//                i.putExtra("uDbAccount",datalist.get(position).getDbAccount());
//                i.putExtra("uSPApproved2",datalist.get(position).getSpApproved2());
//                i.putExtra("uSPAmountApproved",datalist.get(position).getSpAmountApproved());
//                i.putExtra("uRequestedAmount",datalist.get(position).getIndividualAmountRequired());
//
//                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                holder.t1.getContext().startActivity(i);
//                ((Activity)holder.t1.getContext()).finish();
//            }
//        });
//        holder.t2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(holder.t1.getContext(), CollectorUserDetailsAmountToDB.class);
//                i.putExtra("uname",datalist.get(position).getName());
//                i.putExtra("ufname",datalist.get(position).getFatherName());
//                i.putExtra("uAge",datalist.get(position).getAge());
//                i.putExtra("uHnumber",datalist.get(position).getHouseNo());
//                i.putExtra("uVillage",datalist.get(position).getVillage());
//                i.putExtra("uMandal",datalist.get(position).getMandal());
//                i.putExtra("uDistrict",datalist.get(position).getDistrict());
//                i.putExtra("uAadharNumber",datalist.get(position).getAadhar());
//                i.putExtra("uMobileNo",datalist.get(position).getPhoneNo() );
//                i.putExtra("uPreferredUnit",datalist.get(position).getPreferredUnit());
//                i.putExtra("uBankName",datalist.get(position).getBankName());
//                i.putExtra("uBankAccNumber",datalist.get(position).getBankAccNo());
//                i.putExtra("upsUpload",datalist.get(position).getPsUpload());
//                i.putExtra("usecOfficerUpload",datalist.get(position).getSecOfficerUpload());
//                i.putExtra("uSPRemarks",datalist.get(position).getSp_remarks());
//                i.putExtra("uSORemarks",datalist.get(position).getSo_remarks());
//                i.putExtra("uSOApproved",datalist.get(position).getSecOfficerApproved());
//                i.putExtra("uCollectorApproved",datalist.get(position).getCtrApproved());
//                i.putExtra("village",village);
//                i.putExtra("uBankIFSC",datalist.get(position).getBankIFSC());
//                i.putExtra("uVendorIFSC",datalist.get(position).getVendorIFSC());
//                i.putExtra("uVendorBankAccount",datalist.get(position).getVendorAccountNo());
//                i.putExtra("uVendorName",datalist.get(position).getVendorName());
//                i.putExtra("uGroundingStatus",datalist.get(position).getGroundingStatus());
//                i.putExtra("uGroundingImage",datalist.get(position).getGrounding_img());
//                i.putExtra("uApprovalAmount",datalist.get(position).getApprovalAmount());
//                i.putExtra("uDbAccount",datalist.get(position).getDbAccount());
//                i.putExtra("uSPApproved2",datalist.get(position).getSpApproved2());
//                i.putExtra("uSPAmountApproved",datalist.get(position).getSpAmountApproved());
//                i.putExtra("uRequestedAmount",datalist.get(position).getIndividualAmountRequired());
//                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                holder.t2.getContext().startActivity(i);
//                ((Activity)holder.t2.getContext()).finish();
//            }
//        });
    }




    @Override
    public int getItemCount() {
        return datalist.size();
    }

    class myviewholder extends RecyclerView.ViewHolder
    {
        TextView t1;
        TextView t2;
        TextView t3;
        TextView t4;
        TextView t5;
        ImageView check;
        CardView cardView;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            t1=itemView.findViewById(R.id.t1);
            t2=itemView.findViewById(R.id.t2);
            t3=itemView.findViewById(R.id.t3);
            t4=itemView.findViewById(R.id.t4);
            t5=itemView.findViewById(R.id.t5);
            check=itemView.findViewById(R.id.check);
            cardView=itemView.findViewById(R.id.cardview);

        }

    }
}

