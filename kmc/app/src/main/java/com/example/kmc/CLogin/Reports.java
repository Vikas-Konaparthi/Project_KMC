package com.example.kmc.CLogin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.kmc.Individual;
import com.example.kmc.R;
import com.example.kmc.SPElements;
import com.example.kmc.SPOfficer;
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

public class Reports extends AppCompatActivity {

    FirebaseFirestore db;
    ArrayList<SPElements> datalist;
    String name;
    String village1;
    String village2;
    int sppending;
    ProgressBar progressBar;
    List<DocumentSnapshot> list2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);
        db=FirebaseFirestore.getInstance();
        datalist=new ArrayList<>();
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        db.collection("spofficer").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot d : list) {
                            SPOfficer sp = d.toObject(SPOfficer.class);


                            db.collection("individuals").get()
                                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                        @Override
                                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                            list2 = queryDocumentSnapshots.getDocuments();
                                            for (DocumentSnapshot d : list2) {
                                                Individual obj = d.toObject(Individual.class);
                                                if((obj.getVillage().equals(sp.getVillage1())||obj.getVillage().equals(sp.getVillage2()))&&((obj.getSpApproved().equals(""))||(obj.getPsApproved().equals("yes")&&obj.getSpApproved2().equals(""))||(obj.getSoApproved().equals("yes")&&obj.getSpApproved3().equals(""))))
                                                {
                                                    sppending=sppending+1;
                                                }

                                            }
                                            SPElements spe=new SPElements(sp.getName(),sp.getVillage1(),sp.getVillage2(),String.valueOf(sppending));
                                            datalist.add(spe);
                                            sppending=0;
                                        }


                                    });

                        }
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }

    public void spReport(View view) {
        Log.d("SP111111", datalist.get(0).getName());
        Log.d("SP222222", datalist.get(1).getName());

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT)
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
        }
        String Fnamexls="SP_pending_report"+System.currentTimeMillis()+ ".xls";
        File dir = Environment.getExternalStoragePublicDirectory("kmc");
        String filePath = Environment.getExternalStorageDirectory()+File.separator+"kmc";
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
            Label label0 = new Label(0, 0, "Name");
            Label label1 = new Label(1, 0, "Village 1");
            Label label2 = new Label(2, 0, "Village 2");
            Label label3 = new Label(3, 0, "Pending");
            sheet.addCell(label0);
            sheet.addCell(label1);
            sheet.addCell(label2);
            sheet.addCell(label3);
            int i=1;
            for(SPElements s:datalist)
            {
                               Label name = new Label(0,i,s.getName());
                                sheet.addCell(name);
                               Label village = new Label(1,i,s.getVillage1());
                sheet.addCell(village);
                                Label village2 = new Label(2,i,s.getVillage2());
                sheet.addCell(village2);
                Label pending = new Label(3,i,s.getPending());
                sheet.addCell(pending);
                                i++;
            }


            workbook.write();
            try {
                workbook.close();
            } catch (WriteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (RowsExceededException e) {
            e.printStackTrace();
        } catch (WriteException e) {
            e.printStackTrace();
        }
        Toast.makeText(this, "SP Pending Report Generated Successfully", Toast.LENGTH_SHORT).show();

    }

    public void unitReport(View view) {

    }
}