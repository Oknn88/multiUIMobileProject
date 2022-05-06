package com.example.multiuimobileproject;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.example.multiuimobileproject.databinding.ActivityNotalBinding;
import com.google.android.material.snackbar.Snackbar;
import com.example.multiuimobileproject.databinding.ActivityNotalBinding;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class NotalActivity extends AppCompatActivity
{
    private ActivityNotalBinding binding;
    ActivityResultLauncher<Intent> activityResultLauncher;//gorsel secince ne olacak vs
    ActivityResultLauncher<String>permissionLauncher;//izin alınca nolcak
    Bitmap selectedImage;
    EditText date,time;
    Calendar calendar;
    DatePickerDialog.OnDateSetListener dateSetListener;
    TimePickerDialog.OnTimeSetListener timeSetListener;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNotalBinding.inflate(getLayoutInflater());
        View view= binding.getRoot();
        setContentView(view);
        database=this.openOrCreateDatabase("NotAlma",MODE_PRIVATE,null);
        registerLauncher();
        dateAndTime();

        Intent intent=getIntent();
        String info=intent.getStringExtra("info");
        if (info.equals("new"))
        {
            //new not
            binding.notText.setText("");
            binding.dateText.setText("");
            binding.timeText.setText("");
            binding.buttonKaydet.setVisibility(View.VISIBLE);
            binding.imageView2.setImageResource(R.drawable.handmaking);
        }else{
            int notId= intent.getIntExtra("notid",0);
            binding.buttonKaydet.setVisibility(View.INVISIBLE);

            try {
                Cursor cursor=database.rawQuery("SELECT * FROM notlar WHERE id=?",new String[]{String.valueOf(notId)});//son degisiklik burada
                int notumIx=cursor.getColumnIndex("gnot");
                int tarihimIx=cursor.getColumnIndex("gtarih");
                int saatimIx=cursor.getColumnIndex("gsaat");

                while (cursor.moveToNext())
                {
                    binding.notText.setText(cursor.getString(notumIx));
                    binding.dateText.setText(cursor.getString(tarihimIx));
                    binding.timeText.setText(cursor.getString(saatimIx));
                }
                cursor.close();
            }catch (Exception e){
                e.printStackTrace();
            }




        }

    }

    public void dateAndTime()
    {
        //tarih ve zaman icin
        date = findViewById(R.id.dateText);
        time=findViewById(R.id.timeText);

        date.setInputType(InputType.TYPE_NULL);
        time.setInputType(InputType.TYPE_NULL);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog(date);

            }
        });

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimeDialog(time);

            }
        });



    }
    private void showTimeDialog(EditText time)
    {
        calendar= Calendar.getInstance();

        timeSetListener=new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                calendar.set(Calendar.MINUTE,minute);

                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm");
                time.setText(simpleDateFormat.format(calendar.getTime()));


            }
        };

        new TimePickerDialog(NotalActivity.this,timeSetListener,calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false).show();



    }
    private void showDateDialog(EditText date) {
        calendar=Calendar.getInstance();

        dateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);

                SimpleDateFormat simpleDateFormat= new SimpleDateFormat("dd/MM/yyyy");
                date.setText(simpleDateFormat.format(calendar.getTime()));

            }
        };

        new DatePickerDialog(NotalActivity.this,dateSetListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();


    }
    public void delete(View view){

    }

    public void save(View view)//save onclick methodu tanımlandı.
    {
        String Notum= binding.notText.getText().toString();
        String Tarihim=binding.dateText.getText().toString();
        String Saatim= binding.timeText.getText().toString();

        //Bitmap smallImage=makeSmallerImage(selectedImage,300);

        ByteArrayOutputStream outputStream= new ByteArrayOutputStream();
       // smallImage.compress(Bitmap.CompressFormat.PNG,50,outputStream);
        byte[] byteArray=outputStream.toByteArray();

        try {
            database=this.openOrCreateDatabase("NotAlma",MODE_PRIVATE,null);
            database.execSQL("CREATE TABLE IF NOT EXISTS notlar(id INTEGER PRIMARY KEY,gnot VARCHAR,gtarih VARCHAR,gsaat VARCHAR,image BLOB)");
            String sqlString="INSERT INTO notlar(gnot,gtarih,gsaat,image) VALUES (?,?,?,?)";
            SQLiteStatement sqLiteStatement=database.compileStatement(sqlString);
            sqLiteStatement.bindString(1,Notum);
            sqLiteStatement.bindString(2,Tarihim);
            sqLiteStatement.bindString(3,Saatim);
            sqLiteStatement.bindBlob(4,byteArray);
            sqLiteStatement.execute();

        }catch (Exception e){
            e.printStackTrace();
        }

        //bildirim islemleri
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
        {
            NotificationChannel channel = new NotificationChannel("My Notification","My Notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);


        }

        //notification code goes here
        NotificationCompat.Builder builder= new NotificationCompat.Builder(NotalActivity.this,"My Notification");
        builder.setContentTitle("Bildirim");
        builder.setContentText("Yeni bir not kaydedildi.");
        builder.setSmallIcon(R.drawable.ic_launcher_background);
        builder.setAutoCancel(true);


        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(NotalActivity.this);
        managerCompat.notify(1,builder.build());





        Intent intent=new Intent(NotalActivity.this,NotgosterActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//bundan onceki butun aktivityleri kapat sadece gidecegimi ac
        startActivity(intent);


    }

    //gorseli kucult
   /* public Bitmap makeSmallerImage(Bitmap image,int maximumSize) {
        int width=image.getWidth();
        int height=image.getHeight();

        float bitmapRatio=(float) width/(float) height;

        if (bitmapRatio>1)
        {//yataysa
            width=maximumSize;
            height=(int) (width/bitmapRatio);
        }else{
            height=maximumSize;
            width=(int)(height*bitmapRatio);
        }
        return image.createScaledBitmap(image,width,height,true);
    }

    */

    public void selectImage(View view)//selectImage onclick methodu tanımlandı.
    {
        //izin verilmemiş mi kontrol et,
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_EXTERNAL_STORAGE))
            {
                Snackbar.make(view,"İzne ihtiyacım var",Snackbar.LENGTH_INDEFINITE).setAction("İzin Ver", new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        //request permission
                        permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
                    }
                }).show();
            }
            else{
                //reguest permission
                permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
            }

        }
        else
        {
            //galeriye git
            Intent intentgalerry = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            activityResultLauncher.launch(intentgalerry);

        }


    }
    //resaultlauncer in ne yapacagını burda belirleyecegiz
    private void registerLauncher()
    {
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK) {
                    Intent intentFromResult = result.getData();
                    if (intentFromResult != null) {
                        Uri imageData = intentFromResult.getData();
                        binding.imageView2.setImageURI(imageData);
                        try {

                            if (Build.VERSION.SDK_INT >= 28) {
                                ImageDecoder.Source source = ImageDecoder.createSource(getContentResolver(), imageData);
                                selectedImage = ImageDecoder.decodeBitmap(source);
                                binding.imageView2.setImageBitmap(selectedImage);
                            }else{
                                selectedImage=MediaStore.Images.Media.getBitmap(NotalActivity.this.getContentResolver(),imageData);
                                binding.imageView2.setImageBitmap(selectedImage);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });


        permissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
            @Override
            public void onActivityResult(Boolean result) {

                if (result) {
                    //izin verildi(permission granted)
                    Intent intentgalerry = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    activityResultLauncher.launch(intentgalerry);
                } else {
                    //izin verilmedi(permission denied)
                    Toast.makeText(NotalActivity.this, "İzin Lazım", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
}