package com.example.kmc.CLogin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.kmc.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.kmc.District;
import com.example.kmc.Individual;
import com.example.kmc.R;
import com.example.kmc.SPElements;
import com.example.kmc.SPOfficer;
import com.example.kmc.Unit;
import com.example.kmc.UnitElements;
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

public class VillageDemo extends AppCompatActivity {
    FirebaseFirestore db;
    int unitCount=0;
    int grounding=0;
    ArrayList<UnitElements> unitList;

    String village;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db= FirebaseFirestore.getInstance();
        setContentView(R.layout.activity_demo);
        unitList = new ArrayList<>();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            village = extras.getString("village");
        }
        Toast.makeText(this, village, Toast.LENGTH_SHORT).show();
        db.collection("unit").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot d : list) {
                            Unit obj = d.toObject(Unit.class);
                            db.collection("individuals").get()
                                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                        @Override
                                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                                            for (DocumentSnapshot d : list) {
                                                Individual obj1 = d.toObject(Individual.class);
                                                if(obj1.getVillage().equalsIgnoreCase(village)){
                                                    if (obj.getUnitName().equalsIgnoreCase(obj1.getPreferredUnit())){
                                                        unitCount = unitCount + 1;
                                                    }
                                                    if (obj1.getPreferredUnit().equalsIgnoreCase(obj.getUnitName())&&obj1.getGroundingStatus().equals("yes"))
                                                        grounding = grounding + 1;
                                                }
                                            }
                                            UnitElements une=new UnitElements(obj.getUnitName(),String.valueOf(unitCount),String.valueOf(grounding));
                                            unitList.add(une);
                                            unitCount=0;
                                            grounding=0;
                                        }
                                    });
                        }
                    }
                });
    }

    public void unitReport(View view) {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT)
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
        }
        String Fnamexls=village+" Unit_wise_report"+System.currentTimeMillis()+ ".xls";
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
            Label label0 = new Label(0, 0, "Unit Name");
            Label label1 = new Label(1, 0, "Total NO of Units");
            Label label2 = new Label(2, 0, "Grounded");
            sheet.addCell(label0);
            sheet.addCell(label1);
            sheet.addCell(label2);
            int i=1;
            for(UnitElements s:unitList)
            {
                Label unitName = new Label(0,i,s.getUnitName());
                sheet.addCell(unitName);
                Label unitCount = new Label(1,i,s.getUnitCount());
                sheet.addCell(unitCount);
                Label grounding = new Label(2,i,s.getGrounding());
                sheet.addCell(grounding);
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
        Toast.makeText(this, "Unit wise Report Generated Successfully", Toast.LENGTH_SHORT).show();

    }
}

