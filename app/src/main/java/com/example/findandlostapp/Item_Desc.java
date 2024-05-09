package com.example.findandlostapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Item_Desc extends AppCompatActivity {

    TextView item_type,item_dec, item_loc;
    Button remove;
    String id,type,desc,loc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_desc);

        item_type = findViewById(R.id.item_type_desc);
        item_dec = findViewById(R.id.item_desc_detail);
        item_loc = findViewById(R.id.item_loc_desc);
        remove = findViewById(R.id.item_del);
        getData();

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDatabaseHelper mydb = new MyDatabaseHelper(Item_Desc.this);
                mydb.deledata(id);

                finish();

            }
        });

    }


    void getData(){
        if(getIntent().hasExtra("id") && getIntent().hasExtra("type")
                && getIntent().hasExtra("desc") && getIntent().hasExtra("loc")){
            id = getIntent().getStringExtra("id");
            type = getIntent().getStringExtra("type");
            desc = getIntent().getStringExtra("desc");
            loc = getIntent().getStringExtra("loc");

            item_type.setText(type);
            item_dec.setText(desc);
            item_loc.setText(loc);
        }
    }
}