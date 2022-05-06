package com.example.multiuimobileproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        Intent i = new Intent(getApplicationContext(),KiloBoyActivity.class);
        startActivity(i);

         */

        Button ikincisayfayagec=(Button)findViewById(R.id.ikincisayfagecis);

        ikincisayfayagec.setOnClickListener(new View.OnClickListener() //mainden notgoster sayfasına gecis
        {
            @Override
            public void onClick(View view) {
                Intent ikincisayfagecis=new Intent(MainActivity.this, NotgosterActivity.class);
                startActivity(ikincisayfagecis);
            }
        });
    }


}