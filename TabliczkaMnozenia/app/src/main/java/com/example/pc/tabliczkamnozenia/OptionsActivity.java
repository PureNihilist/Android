package com.example.pc.tabliczkamnozenia;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class OptionsActivity extends AppCompatActivity {

    RadioButton radioButton;
    RadioGroup radioGroup, radioGroup2;
    static OptionsActivity instance;
    int min, max;
    static int store_selectedId = 0;
    static int store_imageId = 0;
    static Drawable store_draw = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        instance = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioGroup2 = (RadioGroup) findViewById(R.id.radioGroup2);
        if(store_selectedId != 0) {
            radioGroup.check(store_selectedId);
        }
        if(store_draw != null) {
            radioGroup2.check(store_imageId);
        }
    }

    public void pushButton(View v) {

        radioButton = (RadioButton) v;

        int selectedId = radioGroup.getCheckedRadioButtonId();
        store_selectedId = selectedId;

        radioButton = (RadioButton) findViewById(selectedId);
        String text = radioButton.getText().toString();

        if (selectedId == R.id.rb3) {
            min = 50;
            max = 100;
        } else if(selectedId == R.id.rb0) {
            min = 0;
            max = 10;
        } else if(selectedId == R.id.rb1){
            min = 10;
            max = 20;
        } else if (selectedId == R.id.rb2){
            min = 20;
            max = 50;
        }
    }

    public void setWallpaper(View v) {
        radioButton = (RadioButton) v;
        int selectedId = radioGroup2.getCheckedRadioButtonId();
        store_imageId = selectedId;
        radioButton = (RadioButton) findViewById(selectedId);
        store_draw = radioButton.getBackground();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public static OptionsActivity getActivityInstance(){
        return instance;
    }
    public int getMin(){
        return min;
    }
    public int getMax(){
        return max;
    }
    public Drawable getImageId() {return store_draw;}
}
