package com.example.pdfreader_20221cse0007;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.joanzapata.pdfview.PDFView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Chapters extends AppCompatActivity {
    private Button btnOpenSearchableSpinner;
    private PDFView pdfView;
    private List<String> chapterList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapters);
        btnOpenSearchableSpinner=findViewById(R.id.btnOpenSearchableSpinner);
        chapterList=new ArrayList<>();
        pdfView=findViewById(R.id.pdfView);
        loadChaptersFromFile();
        btnOpenSearchableSpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSearchableSpinnerDialog();
            }
        });


    }
    private void openSearchableSpinnerDialog(){
        Dialog dialog=new Dialog(Chapters.this);
        dialog.setContentView(R.layout.dialog_searchable_spinner);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true);
        EditText etSearch=dialog.findViewById(R.id.etSearch);
        ListView lvItems=dialog.findViewById(R.id.lvItems);
        adapter = new ArrayAdapter<>(this,R.layout.spinner_item,chapterList);
        lvItems.setAdapter(adapter);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem=adapter.getItem(position);
                loadPdf(selectedItem);
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    private int extractPageNumber(String chapter){
        String pageNumberStr=chapter.replaceAll(".*\\(Page(\\d+)\\).*","$1").trim();
        return Integer.parseInt(pageNumberStr);

    }
    private void loadChaptersFromFile() {
        try {
            String[] pdfFileNames = getResources().getStringArray(R.array.pdf_file_names);
            for(String fileName : pdfFileNames){
                chapterList.add(fileName);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error loading chapters. ", Toast.LENGTH_SHORT).show();
        }
    }
    private void loadPdf(String pdfName){
        pdfView.fromAsset(pdfName).load();
    }
}