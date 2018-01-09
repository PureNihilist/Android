package com.example.pc.tabliczkamnozenia;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button button, checkButton, buttonOptions;
    TextView textViewEq;
    EditText editTextRes,editTextTruRes;
    Random rand;
    String x = "",y = "";
    static int store_min=0,store_max=0;
    ConstraintLayout constraintLayout;
    int max = 10,min = 0;

    public void init() {
        rand = new Random();
        textViewEq = (TextView)findViewById((R.id.textViewEq));
        editTextRes = (EditText)findViewById(R.id.editTextRes);
        editTextTruRes = (EditText)findViewById(R.id.editTextTruRes);
        editTextTruRes.setEnabled(false);
        checkButton = (Button) findViewById(R.id.buttonCheck);
        buttonOptions = (Button) findViewById(R.id.buttonOptions);
        buttonOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go_options = new Intent(MainActivity.this,OptionsActivity.class);
                startActivity(go_options);
            }
        });

        constraintLayout = (ConstraintLayout) findViewById(R.id.cL);
        generate();

        final MediaPlayer applauseSoundMP = MediaPlayer.create(this,R.raw.applause);
        final MediaPlayer sad_tromboneSoundMP = MediaPlayer.create(this,R.raw.sad_trombone);

        checkButton.setOnClickListener(new View.OnClickListener() {

            boolean state = false;
            @Override
            public void onClick(View v){

                if(checkButton.getText().equals("Następne")){
                    editTextTruRes.setText("");
                    editTextRes.setText("");
                    generate();
                    state = false;
                    return;
                }
                editTextRes = (EditText)findViewById(R.id.editTextRes);
                editTextTruRes = (EditText)findViewById(R.id.editTextTruRes);

                String text = editTextRes.getText().toString();
                if(!text.equals("")){

                    int val_x = Integer.valueOf(x);
                    int val_y = Integer.valueOf(y);
                    int result = val_x*val_y;
                    String str_res = String.valueOf(result);
                    System.out.println(val_x + " " + val_y + " " + result + " " + text);
                    if (str_res.equals(text)) {
                        applauseSoundMP.start();
                        editTextTruRes.setText("Dobrze! Gratulacje.");
                    } else {
                        sad_tromboneSoundMP.start();
                        editTextTruRes.setText("Żle! Poprawny wynik: " + str_res);
                    }
                 //   editTextRes.setText("");
                    checkButton.setText("Następne");
                    state = true;
                } else {
                    editTextTruRes.setText("Proszę podać wynik");
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    public void pushButton(View v) {
        textViewEq = (TextView)findViewById((R.id.textViewEq));
        editTextRes = (EditText)findViewById(R.id.editTextRes);
        button = (Button) v;
        CharSequence buttonSeq = button.getText();
        editTextRes.append(buttonSeq);
    }

    @Override
    public void onRestart() {
        super.onRestart();
        editTextTruRes.setText("");
        editTextRes.setText("");
        generate();
    }


    public void generate() {

        OptionsActivity oa =OptionsActivity.getActivityInstance();
        if (oa != null) {
            Drawable wallpapeDrawable = oa.getImageId();
            if(wallpapeDrawable != null)
            constraintLayout.setBackground(wallpapeDrawable);
        }
        min = 0;
        max = 10;
        if(store_min != 0 && store_max != 0) {
            min = store_min;
            max = store_max;
        }
        if (oa != null) {
            if(oa.getMax() != 0)
            max = oa.getMax();
            if(oa.getMin() != 0)
            min = oa.getMin();

            store_max = max;
            store_min = min;
        }
        x = Integer.toString(rand.nextInt((max-min)+1)+min);
        y = Integer.toString(rand.nextInt((max-min)+1)+min);

        CharSequence seq = x+" x "+y;
        textViewEq.setText(seq);
        checkButton.setText("Sprawdź");
    }
}