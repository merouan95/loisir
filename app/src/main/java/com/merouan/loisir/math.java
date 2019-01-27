package com.merouan.loisir;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class math extends AppCompatActivity {
    ImageButton jretour;
    Button upd;
    Button ad;
    Button ss;
    Button mp;
    Button dv;
     public String check;
    EditText edt;
    TextView rr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math);
    // mapper layout_button et buttonjava
        jretour=findViewById(R.id.bretour);

        jretour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it1=new Intent(math.this,MainActivity.class);
                startActivity(it1);
            }
        });


        //generation des nombres del'equation
        generation();

        //lecture de la reponse de l'utilisateur
        //edt=(EditText)findViewById(R.id.editText);
       // edt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
         //   @Override
        //   public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
           //   String r=edt.getText().toString();
           //     if (r.equals(check)){
             //       Toast.makeText(getApplicationContext(),R.string.correct_toast,Toast.LENGTH_SHORT).show();
               //     rr.setText("votre reponse est vrai");
               //  }
              //  else{
              //      Toast.makeText(getApplicationContext(),R.string.incorrect_toast,Toast.LENGTH_SHORT).show();
               //     rr.setText("votre reponse est fausse");

           //     }
           //     return false;
           // }
       // });
        //check the answer
        ad=findViewById(R.id.add);
        ss=findViewById(R.id.sous);
        mp=findViewById(R.id.mul);
        dv=findViewById(R.id.div);
        ad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            checkAnswer("+");
            }
        });

        ss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer("-");
            }
        });

        mp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer("*");
            }
        });

        dv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer("/");
            }
        });


        //mise a jour des questions
        upd=findViewById(R.id.maj);
        upd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generation();
                rr=(TextView)  findViewById(R.id.reponse);
                rr.setText("your answer is :");

            }
        });


    }
    public void generation(){
        TextView aa=(TextView) findViewById(R.id.a);
        TextView bb=(TextView) findViewById(R.id.b);
        TextView cc=(TextView) findViewById(R.id.c);
        rr=(TextView)  findViewById(R.id.reponse);
        Random rd1 = new Random();
        int m= 1+rd1.nextInt(10);

        Random rd2 = new Random();
        int n= 1+rd2.nextInt(10);

        Random rd3 = new Random();
        int op= rd3.nextInt(4);

        int result=22;
        //String check;

        switch (op)
        { case 0:
            result=m+n;
            check="+";
            break;

            case 1:
                result=m-n;
                check="-";
                break;

            case 2:
                result=m*n;
                check="*";
                break;

            case 3:
                result=m/n;
                check="/";
                break;

        }

        aa.setText(String.valueOf(m));
        bb.setText(String.valueOf(n));
        cc.setText(String.valueOf(result));}

        public void checkAnswer(String a){
            if (a.equals(check)){
                Toast.makeText(getApplicationContext(),R.string.correct_toast,Toast.LENGTH_SHORT).show();
                rr.setText("Your answer is TRUEEE!!!");
                generation();
            }
            else{
                Toast.makeText(getApplicationContext(),R.string.incorrect_toast,Toast.LENGTH_SHORT).show();
                rr.setText("Your answer is FALSEEEEE!!!!");
                Toast.makeText(getApplicationContext(),R.string.essai,Toast.LENGTH_SHORT).show();
            }
        }

}
