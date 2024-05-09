package com.example.findandlostapp;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Item_List extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<String> item_id, item_type, item_name, item_phone, item_desc, item_date, item_loc;

    MyDatabaseHelper mydb;
    CustomAdapter customAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);
        recyclerView = findViewById(R.id.recycleview);

        mydb = new MyDatabaseHelper(Item_List.this);
        item_id = new ArrayList<>();
        item_type=new ArrayList<>();
        item_name = new ArrayList<>();
        item_phone = new ArrayList<>();
        item_desc = new ArrayList<>();
        item_date = new ArrayList<>();
        item_loc = new ArrayList<>();

      storedata();
      //  Log.i("value", item_id.get(0));
        customAdapter = new CustomAdapter(Item_List.this, this, item_id,item_type,item_desc,item_date,item_loc);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(Item_List.this));


    }

    void storedata(){
        Cursor cursor = mydb.readAllData();
        if(cursor.getCount() == 0){
            Toast.makeText(this, "No Items lost of Found", Toast.LENGTH_SHORT).show();
        }else {
            while (cursor.moveToNext()){
                item_id.add(cursor.getString(0));
                item_type.add(cursor.getString(1));
                item_name.add(cursor.getString(2));
                item_phone.add(cursor.getString(3));
                item_desc.add(cursor.getString(4));
                item_date.add(cursor.getString(5));
                item_loc.add(cursor.getString(6));

            }
        }
    }

    public void onRestart()
    {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }
}