package com.example.kmc.CLogin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toolbar;

import com.example.kmc.COverview.myadapterMandalOverView;
import com.example.kmc.CollectorAdapters.myadapter4Collector4;
import com.example.kmc.District;
import com.example.kmc.Individual;
import com.example.kmc.MandalElements;
import com.example.kmc.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.kmc.COverview.myadapterMandalOverView;
import com.example.kmc.CollectorAdapters.myadapter4Collector4;
import com.example.kmc.District;
import com.example.kmc.Individual;
import com.example.kmc.MandalElements;
import com.example.kmc.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class CollectorMandalOverview extends AppCompatActivity {

    public Toolbar toolbar;
    RecyclerView recyclerView;

    ArrayList<MandalElements> datalist;
    Individual obj;
    List<DocumentSnapshot> list;
    FirebaseFirestore db;
    String district;
    String constituency;
    ProgressBar progressBar;
    int totalRegistered;
    int totalSelected;
    double totalApprovedAmount;
    double dbAccountAmount;
    int grounding;
    double totalDbAmount;

    myadapterMandalOverView adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collector_mandal_overview);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        datalist=new ArrayList<>();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            district= extras.getString("district");
            constituency= extras.getString("constituency");
        }
        adapter=new myadapterMandalOverView(datalist,district);
        recyclerView.setAdapter(adapter);
        db=FirebaseFirestore.getInstance();
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        db.collection(district).whereEqualTo("constituency",constituency).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list =queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot d:list) {
                            District obj = d.toObject(District.class);
                            obj.setUid(d.getId().toString());
                            db.collection("individuals").whereEqualTo("mandal",obj.getUid()).get()
                                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                        @Override
                                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                            List<DocumentSnapshot> list =queryDocumentSnapshots.getDocuments();

                                            for(DocumentSnapshot d:list)
                                            {
                                                Individual obj2=d.toObject(Individual.class);
                                                totalRegistered=totalRegistered+1;
                                                if(obj2.getSpApproved().equals("yes"))
                                                {
                                                    totalSelected=totalSelected+1;
                                                }
                                                totalDbAmount = totalDbAmount+Integer.parseInt(obj2.getApprovalAmount())+Integer.parseInt(obj2.getDbAccount());
                                                totalApprovedAmount=totalApprovedAmount+Integer.parseInt(obj2.getApprovalAmount());
                                                dbAccountAmount=dbAccountAmount+Integer.parseInt(obj2.getDbAccount());
                                                if(obj2.getGroundingStatus().equals("yes")||Integer.parseInt(obj2.getApprovalAmount())>=990000)
                                                {
                                                    grounding=grounding+1;
                                                }
                                            }
                                            MandalElements ob=new MandalElements(obj.getUid(),String.valueOf(totalRegistered),String.valueOf(totalSelected),String.valueOf(totalApprovedAmount/100000.0),String.valueOf(dbAccountAmount/100000.0),String.valueOf(grounding),String.valueOf(totalDbAmount/100000.0));
                                            datalist.add(ob);
                                            adapter.notifyDataSetChanged();
                                            totalRegistered=0;
                                            totalSelected=0;
                                            totalApprovedAmount=0;
                                            dbAccountAmount=0;
                                            grounding=0;
                                            totalDbAmount = 0;

                                        }
                                    });
                        }
                        progressBar.setVisibility(View.GONE);
                    }

                });
        db.collection("individuals").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        list =queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot d:list) {
                            obj = d.toObject(Individual.class);
                        }
                    }
                });

        toolbar = findViewById(R.id.toolbar);
        setActionBar(toolbar);
    }
    public void generateXL(View view) throws IOException {
        getPermission();

//        openFolder(filePath+"/"+Fnamexls);
    }
    private void getPermission(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT)
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
        }
        String Fnamexls=constituency+"ConstituencyReport"+System.currentTimeMillis()+ ".xls";
        File dir = Environment.getExternalStoragePublicDirectory("kmc");
        String filePath = Environment.getExternalStorageDirectory()+File.separator+"km";
        try{
            dir.mkdirs();
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        File file = new File(dir, Fnamexls);

        WorkbookSettings wbSettings = new WorkbookSettings();
        wbSettings.setLocale(new Locale("en", "EN"));

        WritableWorkbook workbook;

        try {
            workbook = Workbook.createWorkbook(file, wbSettings);
            //workbook.createSheet("Report", 0);
            WritableSheet sheet = workbook.createSheet("First Sheet", 0);
            Label label0 = new Label(0,0,"Name");
//            Label label2 = new Label(1,0,"Father Name");
//            Label label1 = new Label(2,0,"Aadhar");
            Label label1 = new Label(1,0,"Village");
            Label label2 = new Label(2,0,"Mandal");
            Label label3 = new Label(3,0,"Preferred Unit");
//            Label label6 = new Label(6,0,"Bank Name");
//            Label label7 = new Label(7,0,"Bank Acc No");
//            Label label8 = new Label(8,0,"Bank IFSC");
//            Label label9 = new Label(9,0,"Vendor Name");
//            Label label10 = new Label(10,0,"Vendor Agency");
//            Label label11 = new Label(11,0,"Vendor Bank Name");
//            Label label12 = new Label(12,0,"Vendor Bank Acc No");
//            Label label13 = new Label(13,0,"Vendor Bank IFSC");
//            Label label14 = new Label(14,0,"DB Bank Name");
//            Label label15 = new Label(15,0,"DB Bank Acc No");
//            Label label16 = new Label(16,0,"DB Bank IFSC");
            Label label4 = new Label(4,0,"Grounding Status");
            Label label5 = new Label(5,0,"Approved Amount");
            Label label6 = new Label(6,0,"DB Amount");
            int i=1;
            for(DocumentSnapshot d:list) {
                obj = d.toObject(Individual.class);
                if(obj.getConstituency().toLowerCase(Locale.ROOT).equals(constituency.toLowerCase(Locale.ROOT)))
                {
                    if (obj.getSpApproved().equals("yes")){
                        Label name = new Label(0,i,obj.getName());
                        sheet.addCell(name);
//                        Label fname = new Label(1,i,obj.getFatherName());
//                        sheet.addCell(fname);
//                        Label  aadhar= new Label(2,i,obj.getAadhar());
//                        sheet.addCell(aadhar);
                        Label village = new Label(1,i,obj.getVillage());
                        sheet.addCell(village);
                        Label mandal = new Label(2,i,obj.getMandal());
                        sheet.addCell(mandal);
                        Label unit = new Label(3,i,obj.getPreferredUnit());
                        sheet.addCell(unit);
//                        Label bname = new Label(6,i,obj.getBankName());
//                        sheet.addCell(bname);
//                        Label bacc = new Label(7,i,obj.getBankAccNo());
//                        sheet.addCell(bacc);
//                        Label bifsc = new Label(8,i,obj.getBankIFSC());
//                        sheet.addCell(bifsc);
//                        Label vname = new Label(9,i,obj.getVendorName());
//                        sheet.addCell(vname);
//                        Label vAgency = new Label(10,i,obj.getVendorAgency());
//                        sheet.addCell(vAgency);
//                        Label vBankName = new Label(11,i,obj.getVendorBankName());
//                        sheet.addCell(vBankName);
//                        Label VBankAcc = new Label(12,i,obj.getVendorAccountNo());
//                        sheet.addCell(VBankAcc);
//                        Label vIFSC = new Label(13,i,obj.getVendorIFSC());
//                        sheet.addCell(vIFSC);
//                        Label dbBankName = new Label(14,i,obj.getDbBankName());
//                        sheet.addCell(dbBankName);
//                        Label dbAccount = new Label(15,i,obj.getDbBankAccNo());
//                        sheet.addCell(dbAccount);
//                        Label dbIFSC = new Label(16,i,obj.getDbBankIFSC());
//                        sheet.addCell(dbIFSC);
                        Label gStatus = new Label(4,i,obj.getGroundingStatus());
                        sheet.addCell(gStatus);
                        Label aAmount = new Label(5,i,obj.getApprovalAmount());
                        sheet.addCell(aAmount);
                        Label dbAmount = new Label(6,i,obj.getDbAccount());
                        sheet.addCell(dbAmount);
                        i++;
                    }
                }

            }




//            Label label1 = new Label(0,1,"first");
//            Label label = new Label(0, 2, "SECOND");
//            Label label4 = new Label(1,1,String.valueOf(a));
            Toast.makeText(this, "Excel Downloading...", Toast.LENGTH_SHORT).show();
            try {
                sheet.addCell(label0);
//                sheet.addCell(label1);
//                sheet.addCell(label2);
                sheet.addCell(label1);
                sheet.addCell(label2);
                sheet.addCell(label3);
//                sheet.addCell(label6);
//                sheet.addCell(label7);
//                sheet.addCell(label8);
//                sheet.addCell(label9);
//                sheet.addCell(label10);
//                sheet.addCell(label11);
//                sheet.addCell(label12);
//                sheet.addCell(label13);
//                sheet.addCell(label14);
//                sheet.addCell(label15);
//                sheet.addCell(label16);
                sheet.addCell(label4);
                sheet.addCell(label5);
                sheet.addCell(label6);


                Toast.makeText(this, "Excel Downloaded Successfully!!", Toast.LENGTH_SHORT).show();
            } catch (RowsExceededException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();


            } catch (WriteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();


            }


            workbook.write();
            try {
                workbook.close();
            } catch (WriteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            //createExcel(excelSheet);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (RowsExceededException e) {
            e.printStackTrace();
        } catch (WriteException e) {
            e.printStackTrace();
        }
//        openFolder(file.toString());
    }
    public void openFolder(String location)
    {
        // location = "/sdcard/my_folder";
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri mydir = Uri.parse("file://"+location);
        intent.setDataAndType(mydir,"application/");    // or use */
        startActivity(intent);
    }
}
