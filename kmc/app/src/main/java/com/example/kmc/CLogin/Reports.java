package com.example.kmc.CLogin;

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
import com.example.kmc.Mandals;
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

public class Reports extends AppCompatActivity {

    FirebaseFirestore db;
    ArrayList<SPElements> datalist;
    String name;
    String village1;
    String village2;
    int sppending;
    String district;
    String const_selected;
    public Spinner Constituency;
    public Spinner Mandal;
    public Spinner Village;
    String mandal_selected;
    String village_selected;
    ProgressBar progressBar;
    List<DocumentSnapshot> list2;
    int unitCount=0;
    int grounding=0;
    ArrayList<UnitElements> districtUnitList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);
        db=FirebaseFirestore.getInstance();
        Constituency = (Spinner) findViewById(R.id.spinner_constituency);
        Mandal=(Spinner)findViewById(R.id.spinner_mandal);
        Village=(Spinner)findViewById(R.id.spinner_village);
        datalist=new ArrayList<>();
        districtUnitList = new ArrayList<>();
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            district=extras.getString("district");
            //The key argument here must match that used in the other activity
        }
//        progressBar.setVisibility(View.VISIBLE);
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
                                                if((obj.getVillage().equals(sp.getVillage1())||obj.getVillage().equals(sp.getVillage2()))&&((obj.getSpApproved().equals("NA"))||(obj.getPsApproved().equals("yes")&&obj.getSpApproved2().equals("NA"))||(obj.getSoApproved().equals("yes")&&obj.getSpApproved3().equals("NA"))))
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
//                        progressBar.setVisibility(View.GONE);
                    }
                });
        List<String> constituencies = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item,
                constituencies);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Constituency.setAdapter(adapter);
        db.collection(district+"_constituencies").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list =queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot d:list) {

                            District obj1 = d.toObject(District.class);

                            obj1.setUid(d.getId().toString());
                            constituencies.add(obj1.getUid());
                        }
                        adapter.notifyDataSetChanged();
                        const_selected=adapter.getItem(0);
                    }

                });

//        Constituency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//
//            @Override
//            public void onItemSelected(AdapterView<?> arg0, View arg1,int position, long arg3) {
//                Constituency.setSelection(position);
//                const_selected = Constituency.getSelectedItem().toString();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> arg0) {
//
//            }
//
//        });
        List<String> mandals = new ArrayList<>();
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item,
                mandals);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Mandal.setAdapter(adapter1);


        Constituency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,int position, long arg3) {
                mandals.clear();
                Constituency.setSelection(position);
                const_selected = Constituency.getSelectedItem().toString();

                db.collection(district).whereEqualTo("constituency",const_selected).get()
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
                                                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                                                    mandals.add(obj.getUid());
                                                    adapter1.notifyDataSetChanged();
                                                    mandal_selected=adapter.getItem(0);
                                                }
                                            });

                                }

                            }

                        });
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }

        });

        List<String> villages = new ArrayList<>();
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item,
                villages);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Village.setAdapter(adapter2);



        Mandal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,int position, long arg3) {
                villages.clear();
                Mandal.setSelection(position);
                mandal_selected = Mandal.getSelectedItem().toString();

                db.collection(district).document(mandal_selected).collection("villages").get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                List<DocumentSnapshot> list =queryDocumentSnapshots.getDocuments();
                                for(DocumentSnapshot d:list) {
                                    Mandals obj = d.toObject(Mandals.class);
                                    db.collection("individuals").whereEqualTo("village",obj.getVillage()).get()
                                            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                                @Override
                                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                                                    villages.add(obj.getVillage());
                                                    adapter2.notifyDataSetChanged();
                                                    village_selected=adapter2.getItem(0);

                                                }
                                            });
                                }
                            }
                        });
            }




            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }

        });

        Village.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,int position, long arg3) {

                Village.setSelection(position);
                village_selected = Village.getSelectedItem().toString();
//                selected_village.replaceAll("\\s+","");
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }

        });




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
                                                if(obj1.getDistrict().equalsIgnoreCase(district)){
                                                    if (obj.getUnitName().equalsIgnoreCase(obj1.getPreferredUnit())){
                                                        unitCount = unitCount + 1;
                                                    }
                                                    if (obj1.getPreferredUnit().equalsIgnoreCase(obj.getUnitName())&&obj1.getGroundingStatus().equals("yes"))
                                                        grounding = grounding + 1;

                                                }

                                            }
                                            UnitElements une=new UnitElements(obj.getUnitName(),String.valueOf(unitCount),String.valueOf(grounding));
                                            districtUnitList.add(une);
                                            unitCount=0;
                                            grounding=0;
                                        }
                                    });
                        }
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
//        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT)
//        {
//            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
//        }
//        String Fnamexls="Unit_wise_report"+System.currentTimeMillis()+ ".xls";
//        File dir = Environment.getExternalStoragePublicDirectory("kmc");
//        String filePath = Environment.getExternalStorageDirectory()+File.separator+"kmc";
//        try{
//            dir.mkdirs();
//        }catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//
//        File file = new File(dir, Fnamexls);
//
//        WorkbookSettings wbSettings = new WorkbookSettings();
//        wbSettings.setLocale(new Locale("en", "EN"));
//
//        WritableWorkbook workbook;
//
//        try {
//            workbook = Workbook.createWorkbook(file, wbSettings);
//            //workbook.createSheet("Report", 0);
//            WritableSheet sheet = workbook.createSheet("First Sheet", 0);
//            Label label0 = new Label(0, 0, "Unit Name");
//            Label label1 = new Label(1, 0, "Total NO of Units");
//            Label label2 = new Label(2, 0, "Grounded");
//            sheet.addCell(label0);
//            sheet.addCell(label1);
//            sheet.addCell(label2);
//            int i=1;
//            for(UnitElements s:unitList)
//            {
//                Label unitName = new Label(0,i,s.getUnitName());
//                sheet.addCell(unitName);
//                Label unitCount = new Label(1,i,s.getUnitCount());
//                sheet.addCell(unitCount);
//                Label grounding = new Label(2,i,s.getGrounding());
//                sheet.addCell(grounding);
//                i++;
//            }
//
//
//            workbook.write();
//            try {
//                workbook.close();
//            } catch (WriteException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (RowsExceededException e) {
//            e.printStackTrace();
//        } catch (WriteException e) {
//            e.printStackTrace();
//        }
//        Toast.makeText(this, "Unit wise Report Generated Successfully", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this, Demo.class);
        i.putExtra("constituency",const_selected);
        startActivity(i);

    }
    public void DistrictUnitReport(View v){


        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT)
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
        }
        String Fnamexls=district+" district Unit_wise_report"+System.currentTimeMillis()+ ".xls";
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
            for(UnitElements s:districtUnitList)
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
        Toast.makeText(this, " District Unit wise Report Generated Successfully", Toast.LENGTH_SHORT).show();

    }

    public void mandalUnitReport(View view) {
        Intent i = new Intent(this, MandalDemo.class);
        i.putExtra("mandal",mandal_selected);
        startActivity(i);
    }

    public void villageUnitReport(View view) {
        Intent i = new Intent(this, VillageDemo.class);
        i.putExtra("village",village_selected);
        startActivity(i);
    }
}