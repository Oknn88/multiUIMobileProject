package com.example.multiuimobileproject;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.multiuimobileproject.Entities.DBHandler;
import com.example.multiuimobileproject.Entities.kiloBoy;

import java.util.ArrayList;
import java.util.List;

public class listKiloBoyActivity extends AppCompatActivity {
    ListView list;
    private DBHandler dbHandler;

    String[] maintitle;
    String[] subtitle;
    ArrayList<kiloBoy> kb = new ArrayList<kiloBoy>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_kilo_boy);

        dbHandler = new DBHandler(listKiloBoyActivity.this);
        kb = dbHandler.getAllItems();

        maintitle = new String[kb.size()];
        subtitle = new String[kb.size()];

        for (int i = 0; i<kb.size();i++){
            maintitle[i] = kb.get(i).tarih;
            subtitle[i] = "Kilo: " + kb.get(i).kilo + "Boy: " + kb.get(i).boy;
        }

        CustomListViewKilo adapter=new CustomListViewKilo(this, maintitle, subtitle);
        list=(ListView)findViewById(R.id.list);
        list.setAdapter(adapter);

    }
}