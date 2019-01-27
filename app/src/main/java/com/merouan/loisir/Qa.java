package com.merouan.loisir;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Qa extends AppCompatActivity {
    Button jretour;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qa);
        // mapper layout_button et buttonjava
        jretour=findViewById(R.id.bretour);
        jretour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it1=new Intent(Qa.this,MainActivity.class);
                startActivity(it1);
            }
        });
    }
}
