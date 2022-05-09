package com.example.multiuimobileproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.FrameLayout;

import com.example.multiuimobileproject.databinding.ActivityMainBinding;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()){

                case R.id.not:
                    replaceActivity(new NotgosterActivity());
                    break;
                case R.id.harita:
                    replaceActivity(new MapsActivity());
                    break;
                case R.id.kilo:
                    replaceActivity(new KiloBoyActivity());
                    break;

            }


            return true;
        });

        
        Intent i = new Intent(getApplicationContext(),NotgosterActivity.class);
        startActivity(i);


    }


    private void replaceActivity(Activity activity){

        Intent intent = new Intent(this,activity.getClass());
        startActivity(intent);
        this.finish();
    }

}