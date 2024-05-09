package com.example.findandlostapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Calendar;

public class ItemDetails extends AppCompatActivity {


    EditText name, phone, desc, date, location;

    RadioGroup radioGroup;
    RadioButton radioButton;
    Button add_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        name = findViewById(R.id.Name_txt);
        phone = findViewById(R.id.Phone_txt);
        desc = findViewById(R.id.desc_txt);
        date = findViewById(R.id.Date_txt);
        location = findViewById(R.id.Address_txt);
        add_btn = findViewById(R.id.submit_btn);
        radioGroup = findViewById(R.id.radioGroup);


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                 radioButton = (RadioButton) group.findViewById(checkedId);
                //Toast.makeText(ItemDetails.this, radioButton.getText(), Toast.LENGTH_SHORT).show();
            }
        });

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();

                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        ItemDetails.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                date.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                            }
                        }, year,month,day);
                datePickerDialog.show();
            }
        });

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDatabaseHelper mydb = new MyDatabaseHelper(ItemDetails.this);
                mydb.add_item(radioButton.getText().toString().trim(),
                        name.getText().toString().trim(),
                        phone.getText().toString().trim(),
                        desc.getText().toString().trim(),
                        date.getText().toString().trim(),
                        location.getText().toString().trim());
                Intent intent = new Intent(ItemDetails.this, MainActivity.class);
                startActivity(intent);

            }
        });



    }
}