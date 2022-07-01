package com.example.kmc.CLogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.kmc.CollectorAdapters.myadapter4Collector2;
import com.example.kmc.CollectorAdapters.myadapter4Collector3;
import com.example.kmc.Individual;
import com.example.kmc.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class CollectorAmountDBToBen extends AppCompatActivity {

    public Toolbar toolbar;
    RecyclerView recyclerView;

    ArrayList<Individual> datalist;
    FirebaseFirestore db;
    String village;
    ProgressBar progressBar;
    Individual obj2;
    Individual obj;
    List<DocumentSnapshot> list;

    myadapter4Collector3 adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collector_amount_dbto_ben);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        datalist=new ArrayList<>();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            village= extras.getString("village");
        }
        adapter=new myadapter4Collector3(datalist,village);
        recyclerView.setAdapter(adapter);
        db=FirebaseFirestore.getInstance();
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);



        db.collection("individuals").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        list =queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot d:list)
                        {
                            obj=d.toObject(Individual.class);
                            if(obj.getVillage().toLowerCase(Locale.ROOT).equals(village.toLowerCase(Locale.ROOT)))
                            {
                                if(obj.getSpApproved3().equals("yes") && obj.getSoApproved().equals("yes"))
                                    if(!obj.getCtrApproved2().equals("yes") &&  !obj.getCtrApproved2().equals("no")) {
                                        datalist.add(obj);
                                    }
                            }
                        }
                        adapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);
                    }
                });

        toolbar = findViewById(R.id.toolbar);
        setActionBar(toolbar);

    }
    public void generateNote(View view) {
        createPDF();
        db.collection("individuals").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list2 =queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot d:list2)
                        {
                            obj2=d.toObject(Individual.class);
                            if(obj2.getVillage().toLowerCase(Locale.ROOT).equals(village.toLowerCase(Locale.ROOT))) {
                                if (obj2.getCtrApproved2().equals("yes")) {
                                    if(!obj2.getCtrNote2().equals("yes"))
                                    {
                                        updateData(obj2.getAadhar());
                                    }
                                }
                            }
                        }
                    }
                });
        Toast.makeText(this, "Note Generated Successfully", Toast.LENGTH_SHORT).show();

    }
    private void updateData(String aadharNumber) {
        Map<String, Object> individualInfo = new HashMap<String, Object>();
        individualInfo.put("ctrNote2", "yes");
        Toast.makeText(this, aadharNumber, Toast.LENGTH_SHORT).show();
        db.collection("individuals").whereEqualTo("aadhar",aadharNumber)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful() && !task.getResult().isEmpty()){
                    DocumentSnapshot documentSnapshot=task.getResult().getDocuments().get(0);
                    String documentID=documentSnapshot.getId();
                    db.collection("individuals")
                            .document(documentID)
                            .update(individualInfo)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(CollectorAmountDBToBen.this, "Error occured", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{
                    Toast.makeText(CollectorAmountDBToBen.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void createPDF() {
        Document doc = new Document();
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT)
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
        }
        File dir = Environment.getExternalStoragePublicDirectory("kmc");
        String filePath = Environment.getExternalStorageDirectory()+File.separator+"km";
        try{
            dir.mkdirs();
        }catch (Exception e)
        {
            e.printStackTrace();
        }


        try {

            Log.d("PDFCreator", "PDF Path: " + dir);

            //This is for random name
            String number="VillageNote"+System.currentTimeMillis();

            File file = new File(dir, "Document" + number + ".pdf");
            FileOutputStream fOut = new FileOutputStream(file);
            PdfWriter.getInstance(doc, fOut);

            //open the document
            doc.open();

            Paragraph p1 = new Paragraph("I Here By Declare that these people are eligible for scheme : ");
            Font paraFont = new Font(Font.COURIER);
            p1.setAlignment(Paragraph.ALIGN_LEFT);
            p1.setFont(paraFont);

            //add paragraph to document
            doc.add(p1);
            Paragraph p2 = new Paragraph(" ");
            p1.setAlignment(Paragraph.ALIGN_LEFT);
            p1.setFont(paraFont);

            //add paragraph to document
            doc.add(p2);
            doc.setPageSize(PageSize.A4.rotate());
            doc.newPage();
            Paragraph p3 = new Paragraph("Statement showing the details of adjustment and release of amounts to their units in "+village+" in respect of Dalit Bandhu Scheme.");
            p1.setAlignment(Paragraph.ALIGN_LEFT);
            p1.setFont(paraFont);

            //add paragraph to document
            doc.add(p3);
            Paragraph p4 = new Paragraph(" ");
            p1.setAlignment(Paragraph.ALIGN_LEFT);
            p1.setFont(paraFont);

            //add paragraph to document
            doc.add(p4);

            PdfPTable userTable = new PdfPTable(12);
            userTable.addCell("Name of the Village");
            userTable.addCell("Name of the Unit");
            userTable.addCell("Unit cost");
            userTable.addCell("Name of the Beneficiary");
            userTable.addCell("DB Name of the Bank");
            userTable.addCell("DB Account Number");
            userTable.addCell("DB IFSC");
            userTable.addCell("Amount Released to the firm or Vendor");
            userTable.addCell("Name of the Agency");
            userTable.addCell("Agency Bank Name");
            userTable.addCell("Agency Bank Account Number");
            userTable.addCell("Agency Bank IFSC");

            for(DocumentSnapshot d:list) {
                obj2 = d.toObject(Individual.class);
                if(obj2.getVillage().toLowerCase(Locale.ROOT).equals(village.toLowerCase(Locale.ROOT))) {
                    if (obj2.getCtrApproved2().equals("yes")) {
                        if(!obj2.getCtrNote2().equals("yes")) {
                            userTable.addCell(obj2.getVillage());
                            userTable.addCell(obj2.getPreferredUnit());
                            userTable.addCell(obj2.getDbAccount());
                            userTable.addCell(obj2.getName());
                            userTable.addCell(obj2.getDbBankName());
                            userTable.addCell(obj2.getDbBankAccNo());
                            userTable.addCell(obj2.getDbBankIFSC());
                            userTable.addCell(obj.getApprovalAmount());
                            userTable.addCell(obj.getVendorAgency());
                            userTable.addCell(obj.getVendorBankName());
                            userTable.addCell(obj.getVendorAccountNo());
                            userTable.addCell(obj.getVendorIFSC());
                        }
                    }
                }



            }

            doc.add(userTable);

            Phrase footerText = new Phrase("Khammam District Administration App");
            HeaderFooter pdfFooter = new HeaderFooter(footerText, true);
            doc.setFooter(pdfFooter);

//            Toast.makeText(getApplicationContext(), "success pdf", Toast
//                    .LENGTH_LONG).show();

        } catch (DocumentException de) {
            Log.e("PDFCreator", "DocumentException:" + de);
        } catch (IOException e) {
            Log.e("PDFCreator", "ioException:" + e);
        } finally {
            doc.close();
        }

    }

}