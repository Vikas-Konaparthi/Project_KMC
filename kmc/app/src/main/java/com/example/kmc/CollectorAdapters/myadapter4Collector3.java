package com.example.kmc.CollectorAdapters;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kmc.CLogin.CollectorUserDetailsAmountDBToBen;
import com.example.kmc.CLogin.CollectorUserDetailsAmountToDB;
import com.example.kmc.Individual;
import com.example.kmc.R;

import java.util.ArrayList;
import java.util.Locale;

public class myadapter4Collector3 extends RecyclerView.Adapter<myadapter4Collector3.myviewholder>
{
    ArrayList<Individual> datalist;
    String village;

    public myadapter4Collector3(ArrayList<Individual> datalist, String village) {
        this.datalist = datalist;
        this.village=village;
    }

    @NonNull
    @Override
    public myadapter4Collector3.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow,parent,false);
        return new myadapter4Collector3.myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myadapter4Collector3.myviewholder holder, @SuppressLint("RecyclerView") int position) {
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
                Intent i = new Intent(holder.t1.getContext(), CollectorUserDetailsAmountDBToBen.class);
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
                i.putExtra("uDbAccount",datalist.get(position).getDbAccount());
                i.putExtra("uBankIFSC",datalist.get(position).getBankIFSC());
                i.putExtra("village",village);
                i.putExtra("uGroundingStatus",datalist.get(position).getGroundingStatus());
                i.putExtra("uGroundingImage",datalist.get(position).getGrounding_img());
                i.putExtra("uApprovalAmount",datalist.get(position).getApprovalAmount());
                i.putExtra("uSPApproved2",datalist.get(position).getSpApproved2());
                i.putExtra("uSPAmountApproved",datalist.get(position).getSpAmountApproved());
                i.putExtra("uRequestedAmount",datalist.get(position).getIndividualAmountRequired());
                i.putExtra("usoRemarks",datalist.get(position).getSo_remarks());
                i.putExtra("usoQuotationAmount",datalist.get(position).getSo_quotation_amount());
                i.putExtra("psRequestedAmount",datalist.get(position).getPsRequestedAmountToBeneficiary());
                i.putExtra("uVendorIFSC",datalist.get(position).getVendorIFSC());
                i.putExtra("uVendorBankIFSC",datalist.get(position).getVendorIFSC());
                i.putExtra("uVendorName",datalist.get(position).getVendorName());
                i.putExtra("uVendorBankAccountNo",datalist.get(position).getVendorAccountNo());
                i.putExtra("uVendorAgency",datalist.get(position).getVendorAgency());
                i.putExtra("uVendorBankName",datalist.get(position).getVendorBankName());
                i.putExtra("uDbBankName",datalist.get(position).getDbBankName());
                i.putExtra("uDbAccountNo",datalist.get(position).getDbBankAccNo());
                i.putExtra("uDbIFSC",datalist.get(position).getDbBankIFSC());
                i.putExtra("uStatus",datalist.get(position).getStatus());


                //            i.putExtra("upsRequestedAmountToBeneficiary",datalist.get(position).getPsRequestedAmountToBeneficiary());


                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                holder.t1.getContext().startActivity(i);
                ((Activity)holder.t1.getContext()).finish();
            }
        }) ;
//        holder.t1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(holder.t1.getContext(), CollectorUserDetailsAmountDBToBen.class);
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
//                i.putExtra("uDbAccount",datalist.get(position).getDbAccount());
//                i.putExtra("uBankIFSC",datalist.get(position).getBankIFSC());
//                i.putExtra("village",village);
//                i.putExtra("uGroundingStatus",datalist.get(position).getGroundingStatus());
//                i.putExtra("uGroundingImage",datalist.get(position).getGrounding_img());
//                i.putExtra("uApprovalAmount",datalist.get(position).getApprovalAmount());
//                i.putExtra("uSPApproved2",datalist.get(position).getSpApproved2());
//                i.putExtra("uSPAmountApproved",datalist.get(position).getSpAmountApproved());
//                i.putExtra("uRequestedAmount",datalist.get(position).getIndividualAmountRequired());
//                i.putExtra("usoRemarks",datalist.get(position).getSo_remarks());
//                i.putExtra("usoQuotationAmount",datalist.get(position).getSo_quotation_amount());
//                i.putExtra("psRequestedAmount",datalist.get(position).getPsRequestedAmountToBeneficiary());
//                i.putExtra("uVendorIFSC",datalist.get(position).getVendorIFSC());
//                i.putExtra("uVendorBankIFSC",datalist.get(position).getVendorIFSC());
//                i.putExtra("uVendorName",datalist.get(position).getVendorName());
//                i.putExtra("uVendorBankAccountNo",datalist.get(position).getVendorAccountNo());
//                i.putExtra("uVendorAgency",datalist.get(position).getVendorAgency());
//                i.putExtra("uVendorBankName",datalist.get(position).getVendorBankName());
//                //            i.putExtra("upsRequestedAmountToBeneficiary",datalist.get(position).getPsRequestedAmountToBeneficiary());
//
//
//                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                holder.t1.getContext().startActivity(i);
//                ((Activity)holder.t1.getContext()).finish();
//            }
//        });
//        holder.t2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(holder.t1.getContext(), CollectorUserDetailsAmountDBToBen.class);
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
//                i.putExtra("uDbAccount",datalist.get(position).getDbAccount());
//                i.putExtra("uBankIFSC",datalist.get(position).getBankIFSC());
//                i.putExtra("village",village);
//                i.putExtra("uGroundingStatus",datalist.get(position).getGroundingStatus());
//                i.putExtra("uGroundingImage",datalist.get(position).getGrounding_img());
//                i.putExtra("uApprovalAmount",datalist.get(position).getApprovalAmount());
//                i.putExtra("uSPApproved2",datalist.get(position).getSpApproved2());
//                i.putExtra("uSPAmountApproved",datalist.get(position).getSpAmountApproved());
//                i.putExtra("uRequestedAmount",datalist.get(position).getIndividualAmountRequired());
//                i.putExtra("usoRemarks",datalist.get(position).getSo_remarks());
//                i.putExtra("usoQuotationAmount",datalist.get(position).getSo_quotation_amount());
//                i.putExtra("psRequestedAmount",datalist.get(position).getPsRequestedAmountToBeneficiary());
//                i.putExtra("uVendorIFSC",datalist.get(position).getVendorIFSC());
//                i.putExtra("uVendorBankIFSC",datalist.get(position).getVendorIFSC());
//                i.putExtra("uVendorName",datalist.get(position).getVendorName());
//                i.putExtra("uVendorBankAccountNo",datalist.get(position).getVendorAccountNo());
//                i.putExtra("uVendorAgency",datalist.get(position).getVendorAgency());
//                i.putExtra("uVendorBankName",datalist.get(position).getVendorBankName());
//    //            i.putExtra("upsRequestedAmountToBeneficiary",datalist.get(position).getPsRequestedAmountToBeneficiary());
//
//
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

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            t1=itemView.findViewById(R.id.t1);
            t2=itemView.findViewById(R.id.t2);
            t3=itemView.findViewById(R.id.t3);
            t4=itemView.findViewById(R.id.t4);
            t5=itemView.findViewById(R.id.t5);

        }
    }
}

