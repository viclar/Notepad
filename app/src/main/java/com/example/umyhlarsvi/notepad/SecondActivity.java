package com.example.umyhlarsvi.notepad;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SecondActivity extends MainActivity {
    EditText edt;
    Button btn;
    String date;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skriv);
        edt = (EditText)findViewById(R.id.edttext);
        btn = (Button)findViewById(R.id.spara);

        intent = getIntent();
        String edttext = intent.getStringExtra("change");
        Log.i("TAG", "I start: " + edttext);

        if(edttext != null) {
            edt.setText(edttext);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int index = intent.getIntExtra("index", 0);
                    if(edt.getText().toString().trim().length() > 0) {
                        myList.arraylist.get(index).text = edt.getText().toString();
                        date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                        myList.arraylist.get(index).datum = date;
                    }
                    else{
                        myList.arraylist.remove(index);
                    }
                    Intent intent = new Intent(SecondActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            });
        } else {
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (edt.getText().toString().trim().length() > 0) {
                        myList.arraylist.add(new Tag());
                        myList.arraylist.get(myList.arraylist.size() - 1).text = edt.getText().toString();
                        date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                        myList.arraylist.get(myList.arraylist.size() - 1).datum = date;
                    }
                    Intent intent = new Intent(SecondActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            });
        }

}



    @Override
    protected void onPause() {
        super.onPause();
        Spara();
    }

}
