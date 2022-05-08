package com.example.multiuimobileproject;

import androidx.appcompat.app.AppCompatActivity;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.multiuimobileproject.Entities.DBHandler;
import com.example.multiuimobileproject.Entities.kiloBoy;
import com.example.multiuimobileproject.databinding.ActivityKiloBoyBinding;
<<<<<<< Updated upstream
<<<<<<< Updated upstream
import com.example.multiuimobileproject.databinding.ActivityMapsBinding;
=======
import com.example.multiuimobileproject.databinding.ActivityMainBinding;
>>>>>>> Stashed changes
=======
import com.example.multiuimobileproject.databinding.ActivityMainBinding;
>>>>>>> Stashed changes

import java.util.Calendar;

public class KiloBoyActivity extends AppCompatActivity {




    private DatePickerDialog datePickerDialog;
    private Button dateButton;
    private DBHandler dbHandler;
    private ActivityKiloBoyBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
<<<<<<< Updated upstream
        binding = ActivityKiloBoyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.bottomNavigationView.setSelectedItemId(R.id.kiloboy);


        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()){

                case R.id.not:
                    replaceActivity(new NotgosterActivity());
                    break;
                case R.id.harita:
                    replaceActivity(new MapsActivity());
                    break;
                case R.id.kiloboy:
                    replaceActivity(new KiloBoyActivity());
                    break;
                case R.id.sakamatik:
                    replaceActivity(new JokeActivity());
                    break;

            }

            return true;
        });

=======
        setContentView(R.layout.activity_kilo_boy);
        binding = ActivityKiloBoyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
<<<<<<< Updated upstream
>>>>>>> Stashed changes

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

=======

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

>>>>>>> Stashed changes
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


        Button btnKaydet = (Button)findViewById(R.id.btnKaydet);
        Button btnListele = (Button)findViewById(R.id.btnListele);
        EditText boyValue = (EditText) findViewById(R.id.editTextBoy);
        EditText kiloValue = (EditText) findViewById(R.id.editTextKilo);

        initDatePicker();
        dateButton = findViewById(R.id.datePickerButton);
        dateButton.setText(getTodaysDate());

        dbHandler = new DBHandler(KiloBoyActivity.this);

        btnKaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                kiloBoy kb = new kiloBoy();
                kb.boy = boyValue.getText().toString();
                kb.kilo = kiloValue.getText().toString();
                kb.tarih = dateButton.getText().toString();

                if (kb.boy.isEmpty() && kb.kilo.isEmpty() && kb.tarih.isEmpty()) {
                    Toast.makeText(KiloBoyActivity.this, "Please enter all the data..", Toast.LENGTH_SHORT).show();
                    return;
                }

                // on below line we are calling a method to add new
                // course to sqlite data and pass all our values to it.
                dbHandler.addNewEntry(kb.boy, kb.kilo, kb.tarih);

                // after adding the data we are displaying a toast message.
                Toast.makeText(KiloBoyActivity.this, "Kilo Boy bilgisi kaydedildi", Toast.LENGTH_SHORT).show();
                boyValue.setText("Boy");
                kiloValue.setText("Kilo");
                dateButton.setText(getTodaysDate());
            }
        });

        btnListele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),listKiloBoyActivity.class);
                startActivity(i);
            }
        });
    }



    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month +1;
        int day = cal.get(Calendar.DAY_OF_MONTH);

        if (month > 9){
            return (day+"."+month+"."+year);
        }else{
            return (day+"."+"0"+month+"."+year);
        }
//        return makeDateString(day,month,year);
    }

    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1;
//                String date = makeDateString(day, month, year);
                if (month > 9){
                    dateButton.setText(day+"."+month+"."+year);
                }else{
                    dateButton.setText(day+"."+"0"+month+"."+year);
                }

            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = R.style.MyAlertDialogStyle;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

    }

    public void openDatePicker (View view)
    {
        datePickerDialog.show();
    }

//    public void dbInsert(kiloBoy kb){
//
//        try {
//            SQLiteDatabase database = this.openOrCreateDatabase("KiloBoyDB",MODE_PRIVATE,null);
//            database.execSQL("CREATE TABLE IF NOT EXISTS KiloBoyDB (kilo INT, boy INT,tarih VARCHAR)");
//            database.execSQL("INSERT INTO KiloBoyDB (kilo, boy, tarih) VALUES (kb.kilo,kb.boy,'kb.tarih')");
//
//            Toast.makeText(getApplicationContext(),"Kayit Edildi",Toast.LENGTH_LONG).show();
//
//            Cursor cursor = database. rawQuery( "SELECT * FROM KiloBoyDB",null);
//            int kiloIx = cursor.getColumnIndex("kilo");
//            int boyIx = cursor.getColumnIndex("boy");
//            int tarihIx = cursor.getColumnIndex("tarih");
//
//            while (cursor.moveToNext()) {
//                System.out.println("Boy" + cursor.getInt(kiloIx));
//                System.out.println("Kilo" + cursor.getInt(boyIx));
//                System.out.println("Tarih" + cursor.getString(tarihIx));
//            }
//            cursor.close();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

//    public Date tarihDonustur(String tarih)throws Exception{
//        Date date=new SimpleDateFormat("yyyy.MM.dd").parse(tarih);
//        return date;
//    }

<<<<<<< Updated upstream
<<<<<<< Updated upstream

    private void replaceActivity(Activity activity){
        Intent intent = new Intent(this, activity.getClass());
=======
    private void replaceActivity(Activity activity){

        Intent intent = new Intent(this,activity.getClass());
>>>>>>> Stashed changes
=======
    private void replaceActivity(Activity activity){

        Intent intent = new Intent(this,activity.getClass());
>>>>>>> Stashed changes
        startActivity(intent);
        this.finish();
    }

}