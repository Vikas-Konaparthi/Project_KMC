package com.example.kmc.SPLogin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.kmc.Individual;
import com.example.kmc.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
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

public class SPMasterReport extends AppCompatActivity {

    public Toolbar toolbar;
    ArrayList<Individual> datalist;
    FirebaseFirestore db;
    String village1;
    String village2;
    ProgressBar progressBar;
    TextView total;
    int total_amount=0;
    Individual obj;
    LinearLayout linearLayout;
    Bitmap bitmap;
    Context context;
    List<DocumentSnapshot> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        if(Build.VERSION.SDK_INT>=24){
            try{
                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                m.invoke(null);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        datalist=new ArrayList<>();
        total=(TextView) findViewById(R.id.total);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString("village1");
            String value2 = extras.getString("village2");
            //The key argument here must match that used in the other activity
            village1 = value;
            village2 = value2;
        }
        db=FirebaseFirestore.getInstance();
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        linearLayout=(LinearLayout) findViewById(R.id.lld);



        db.collection("individuals").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        list =queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot d:list)
                        {
                            obj=d.toObject(Individual.class);
                            if(obj.getVillage().toLowerCase(Locale.ROOT).equals(village1.toLowerCase(Locale.ROOT)) || (obj.getVillage().toLowerCase(Locale.ROOT).equals(village2.toLowerCase(Locale.ROOT))) ){
                                if(obj.getSpApproved().equals("yes"))
                                    total_amount+=Float.parseFloat(obj.getApprovalAmount());
                                datalist.add(obj);
                            }

                        }
                        total.setText("Total Amount Approved: "+String.valueOf(total_amount/100000.0)+"L/"+String.valueOf(list.size()*10)+"L");
                        progressBar.setVisibility(View.GONE);
                    }
                });

        toolbar = findViewById(R.id.toolbar);
        setActionBar(toolbar);
        context=getApplicationContext();

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
        String Fnamexls="excelSheet"+System.currentTimeMillis()+ ".xls";
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
            for(DocumentSnapshot d:list)
            {
                obj=d.toObject(Individual.class);
                if(obj.getVillage().toLowerCase(Locale.ROOT).equals(village1.toLowerCase(Locale.ROOT)) || (obj.getVillage().toLowerCase(Locale.ROOT).equals(village2.toLowerCase(Locale.ROOT))) ){
                    if(obj.getSpApproved().equals("yes"))
                    {
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
            Toast.makeText(context, "Excel Downloading...", Toast.LENGTH_SHORT).show();
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


                Toast.makeText(context, "Excel Downloaded Successfully!!", Toast.LENGTH_SHORT).show();
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
        Toast.makeText(this, dir.toString(), Toast.LENGTH_SHORT).show();
//        openFolder(file.toString());
    }
    public void openFolder(String location)
    {
        // location = "/sdcard/my_folder";
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri mydir = Uri.parse("file://"+location);
        intent.setDataAndType(mydir,"application/*");    // or use */*
        startActivity(intent);
    }
}