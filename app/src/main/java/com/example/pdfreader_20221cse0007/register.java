package com.example.pdfreader_20221cse0007;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class register extends AppCompatActivity {
    EditText eid,ename;
    Button ins,del,dis;
    TextView res;
    MyDBHandler db;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        eid = findViewById(R.id.idBox);
        ename = findViewById(R.id.nameBox);
        ins = findViewById(R.id.insertBtn);
        del = findViewById(R.id.deleteBtn);
        dis = findViewById(R.id.displayBtn);
        res = findViewById(R.id.result);
        db = new MyDBHandler(getApplicationContext(),"students",null,1);
        ins.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                String sid = eid.getText().toString();
                String sname = ename.getText().toString();
                db.insertRecord(sid,sname);
                Toast.makeText(getApplicationContext(),"Inserted",Toast.LENGTH_LONG).show();
            }
        });
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sid = eid.getText().toString();
                db.deleteRecord(sid);
                Toast.makeText(getApplicationContext(),"Deleted",Toast.LENGTH_LONG).show();
            }
        });
        dis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tabledata = db.displayRecord();
                res.setText(tabledata);
            }
        });
    }
}

