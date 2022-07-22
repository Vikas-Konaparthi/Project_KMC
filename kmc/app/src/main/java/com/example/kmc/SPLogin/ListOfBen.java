package com.example.kmc.SPLogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.kmc.CLogin.CollectorSearchGrounding;
import com.example.kmc.Individual;
import com.example.kmc.NoteElements;
import com.example.kmc.R;
import com.example.kmc.SPAdapters.myadapter2;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ListOfBen extends AppCompatActivity {
    public Toolbar toolbar;
    RecyclerView recyclerView;

    ArrayList<Individual> datalist;
    FirebaseFirestore db;

    List<DocumentSnapshot> list3;
    Individual obj2;
    int totalAmount=0;
    int noOfBen;
    ArrayList<NoteElements> ne;
    String today;


    myadapter2 adapter;
    String village1;
    String village2;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spzone);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy");
        today = formatter.format(date);
        datalist=new ArrayList<>();
        ne=new ArrayList<>();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString("village1");
            String value2 = extras.getString("village2");
            //The key argument here must match that used in the other activity
            village1 = value;
            village2 = value2;
        }
        adapter=new myadapter2(datalist,village1,village2);
        recyclerView.setAdapter(adapter);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        db=FirebaseFirestore.getInstance();


        db.collection("individuals").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list =queryDocumentSnapshots.getDocuments();
                        list3 = queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot d:list)
                        {
                            Individual obj=d.toObject(Individual.class);
                            if(obj.getVillage().toLowerCase(Locale.ROOT).equals(village1.toLowerCase(Locale.ROOT)) || (obj.getVillage().toLowerCase(Locale.ROOT).equals(village2.toLowerCase(Locale.ROOT))) ){
                                if(!obj.getSpApproved().equals("yes"))
                                {
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
    public void search(View view) {
        Intent i = new Intent(this, SpListOfBenSearch.class);
        i.putExtra("village1",village1);
        i.putExtra("village2",village2);
        startActivity(i);
    }
    public void generateNote(View view) {
        db.collection("individuals").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list2 =queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot d:list2)
                        {
                            obj2=d.toObject(Individual.class);
                            if(obj2.getVillage().toLowerCase(Locale.ROOT).equals(village1.toLowerCase(Locale.ROOT))||obj2.getVillage().toLowerCase(Locale.ROOT).equals(village2.toLowerCase(Locale.ROOT))) {
                                if (obj2.getSpApproved().equals("yes")) {
                                    if(!obj2.getSpNote().equals("yes"))
                                    {
                                        updateData(obj2.getAadhar());
                                        noOfBen=noOfBen+1;
                                    }
                                }
                            }
                        }
                        NoteElements n=new NoteElements(totalAmount,noOfBen);
                        ne.add(n);
                        createPDF();
                    }
                });
        Toast.makeText(this, "Note Generated Successfully", Toast.LENGTH_SHORT).show();

    }
    private void updateData(String aadharNumber) {
        Map<String, Object> individualInfo = new HashMap<String, Object>();
        individualInfo.put("spNote", "yes");
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
                                            Toast.makeText(ListOfBen.this, "Error occured", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }else{
                            Toast.makeText(ListOfBen.this, "Failed", Toast.LENGTH_SHORT).show();
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
            String number="Special_Officer_Note"+System.currentTimeMillis();


            File file = new File(dir, "Document" + number + ".pdf");
            FileOutputStream fOut = new FileOutputStream(file);
            PdfWriter.getInstance(doc, fOut);

            //open the document
            doc.open();

            Paragraph p1 = new Paragraph("GOVERNMENT OF TELANGANA");
            Font paraFont = new Font(Font.COURIER);
            p1.setAlignment(Paragraph.ALIGN_CENTER);
            p1.setFont(paraFont);
            //add paragraph to document
            doc.add(p1);
            Paragraph p2 = new Paragraph("DISTRICT SCHEDULED CASTES SERVICES CO – OPERATIVE SOCIETY LTD.");
            p2.setAlignment(Paragraph.ALIGN_CENTER);
            p2.setFont(paraFont);
            //add paragraph to document
            doc.add(p2);
            Paragraph p3 = new Paragraph("KHAMMAM.");
            p3.setAlignment(Paragraph.ALIGN_CENTER);
            p3.setFont(paraFont);
            //add paragraph to document
            doc.add(p3);

            Paragraph p4 = new Paragraph(" ");
            p4.setAlignment(Paragraph.ALIGN_CENTER);
            p4.setFont(paraFont);
            //add paragraph to document

            Paragraph p5 = new Paragraph("Rc.No.E/298/SC/2021-DB-3\t ;Date:- "+today);
            p5.setAlignment(Paragraph.ALIGN_CENTER);
            p5.setFont(paraFont);
            //add paragraph to document
            doc.add(p5);

            Paragraph p7 = new Paragraph("FROM");
            p7.setAlignment(Paragraph.ALIGN_LEFT);
            p7.setFont(paraFont);
            //add paragraph to document
            doc.add(p7);
            doc.add(p4);
            Paragraph p26 = new Paragraph("Sri.");
            p26.setAlignment(Paragraph.ALIGN_LEFT);
            p26.setFont(paraFont);
            //add paragraph to document
            doc.add(p26);
            Paragraph p27 = new Paragraph(
                    "Special Officer  \n"+
                    ""+village1+"/"+village2+"\n"+
                    "Khammam.\n");
            p27.setAlignment(Paragraph.ALIGN_LEFT);
            p27.setFont(paraFont);
            //add paragraph to document
            doc.add(p27);
            doc.add(p4);
            Paragraph p8 = new Paragraph("Sir,");
            p8.setAlignment(Paragraph.ALIGN_LEFT);
            p8.setFont(paraFont);
            //add paragraph to document
            doc.add(p8);

            Paragraph p9 = new Paragraph("Sub:-\tDSCSCDS Ltd., Khammam District – Dalit Bandhu Scheme – "+village1+"/"+village2+"  –-  approval of beneficiaries "+ne.get(0).getNoOfBen()+" pertains to their preferred units under Dalit bandhu Scheme – Reg.");
            p9.setAlignment(Paragraph.ALIGN_CENTER);
            p9.setFont(paraFont);
            //add paragraph to document
            doc.add(p9);

            Paragraph p10 = new Paragraph("Ref:-");
            p10.setAlignment(Paragraph.ALIGN_LEFT);
            p10.setFont(paraFont);
            //add paragraph to document
            doc.add(p10);
            Paragraph p11 = new Paragraph("1.\tProgs of the District Collector & Chairman, DSCSCDS Ltd., Khammam Rc.No. E/298/SC/2021, Dated: "+today);
            p11.setAlignment(Paragraph.ALIGN_LEFT);
            p11.setFont(paraFont);
            //add paragraph to document
            doc.add(p11);
            Paragraph p12 = new Paragraph("2.\tBeneficiaries individual acceptancy letters duly recommended by the Panchayat Secretary, MPDO and  Special Officers of Concerned G.Ps/");
            p12.setAlignment(Paragraph.ALIGN_LEFT);
            p12.setFont(paraFont);
            //add paragraph to document
            doc.add(p12);

            Paragraph p13 = new Paragraph("3.\tThis office Lr.Rc.No. E/298/SC/2022, Dated: "+today+" addressed to the Branch Managers of Banks concerned.");
            p13.setAlignment(Paragraph.ALIGN_LEFT);
            p13.setFont(paraFont);
            //add paragraph to document
            doc.add(p13);
            Paragraph p14 = new Paragraph(
                    "4.\tInstructions of District Collector & Chairman, DSCSCDS Ltd., Khammam.\n"+
                            "5.\tLr.Rc.No.E/298/SE/"+village1+"/"+village2+"/, dt:-. "+today+" of D.P.M, KMM \n");
            p14.setAlignment(Paragraph.ALIGN_LEFT);
            p14.setFont(paraFont);
            //add paragraph to document
            doc.add(p14);
            Paragraph p15 = new Paragraph(
                    "***");
            p15.setAlignment(Paragraph.ALIGN_CENTER);
            p15.setFont(paraFont);
            //add paragraph to document
            doc.add(p15);
            Paragraph p16 = new Paragraph(
                    "Incompliance to the references 1st to 5th cited, under Dalit Bandhu Scheme, beneficiaries were selected from "+village1+"/"+village2+" and sanctioned Rs. 10.00 Lakh per each "+ne.get(0).getNoOfBen()+" beneficiary for the said purpose vide the proceedings under reference 1st cited.");
            p16.setAlignment(Paragraph.ALIGN_LEFT);
            p16.setFont(paraFont);
            //add paragraph to document
            doc.add(p16);
            doc.add(p4);
            Paragraph p18 = new Paragraph(
                    "Yours faithfully,");
            p18.setAlignment(Paragraph.ALIGN_RIGHT);
            p18.setFont(paraFont);
            //add paragraph to document
            doc.add(p18);

            Paragraph p19 = new Paragraph(
                    "Encls:-\t Annexure");
            p19.setAlignment(Paragraph.ALIGN_LEFT);
            p19.setFont(paraFont);
            //add paragraph to document
            doc.add(p19);
            doc.add(p4);
            Paragraph p20 = new Paragraph(
                    "\n" +
                            "Special Officer,\n");
            p20.setAlignment(Paragraph.ALIGN_RIGHT);
            p20.setFont(paraFont);
            //add paragraph to document
            doc.add(p20);
            Paragraph p21 = new Paragraph(
                    ""+village1+"/"+village2+"\n");
            p21.setAlignment(Paragraph.ALIGN_RIGHT);
            p21.setFont(paraFont);
            //add paragraph to document
            doc.add(p21);


            doc.setPageSize(PageSize.A4.rotate());
            doc.newPage();
            Paragraph p22 = new Paragraph("Statement showing the details of adjustment and release of amounts to their units in "+village1+" and "+village2+" in respect of Dalit Bandhu Scheme.");
            p22.setAlignment(Paragraph.ALIGN_LEFT);
            p22.setFont(paraFont);

            //add paragraph to document
            doc.add(p22);
            Paragraph p23 = new Paragraph(" ");
            p23.setAlignment(Paragraph.ALIGN_LEFT);
            p23.setFont(paraFont);

            //add paragraph to document
            doc.add(p23);

            PdfPTable userTable = new PdfPTable(6);
            userTable.addCell("Name of the Village");
            userTable.addCell("Name of the Unit");
            userTable.addCell("Name of the Beneficiary");
            userTable.addCell("DB Name of the Bank");
            userTable.addCell("DB Account Number");
            userTable.addCell("DB IFSC");

            for(DocumentSnapshot d:list3) {
                obj2 = d.toObject(Individual.class);
                if(obj2.getVillage().toLowerCase(Locale.ROOT).equals(village1.toLowerCase(Locale.ROOT))||obj2.getVillage().toLowerCase(Locale.ROOT).equals(village2.toLowerCase(Locale.ROOT))) {
                    if (obj2.getSpApproved().equals("yes")) {
                        if(!obj2.getSpNote().equals("yes")) {
                            userTable.addCell(obj2.getVillage());
                            userTable.addCell(obj2.getPreferredUnit());
                            userTable.addCell(obj2.getName());
                            userTable.addCell(obj2.getDbBankName());
                            userTable.addCell(obj2.getDbBankAccNo());
                            userTable.addCell(obj2.getDbBankIFSC());
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