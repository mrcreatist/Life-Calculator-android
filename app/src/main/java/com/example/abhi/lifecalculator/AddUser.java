package com.example.abhi.lifecalculator;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Calendar;


public class AddUser extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    TextView name;
    Button addUserButton, selectDateButton, readData;
    int birthYear, birthMonth, birthDay;
    int flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        flag = 0;

        name = (EditText) findViewById(R.id.inputName);
        addUserButton = (Button) findViewById(R.id.addUser);
        selectDateButton = (Button) findViewById(R.id.selectDate);

        selectDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                birthYear = c.get(Calendar.YEAR);
                birthMonth = c.get(Calendar.MONTH);
                birthDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(AddUser.this, AddUser.this, birthYear, birthMonth, birthDay);
                datePickerDialog.show();
            }
        });

        addUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (name.getText().toString().matches("")) {
                    Toast.makeText(AddUser.this, "Name field is empty", Toast.LENGTH_SHORT).show();
                } else if (selectDateButton.getText().toString().matches("Select Date")) {
                    Toast.makeText(AddUser.this, "Please select a date first", Toast.LENGTH_SHORT).show();
                } else {
                    // Data entered properly
                    String fileName = "UserDB.txt";
                    String data;
                    try {
                        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(getApplicationContext().openFileOutput(fileName, Context.MODE_PRIVATE));
                        data = name.getText().toString() + "\n";
                        outputStreamWriter.write(data);
                        data = selectDateButton.getText().toString();
                        outputStreamWriter.write(data);
                        Toast.makeText(AddUser.this, "Data successfully written", Toast.LENGTH_SHORT).show();
                        outputStreamWriter.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        Log.e("Exception", "File write failed: " + e.toString());
                    }
                }
            }
        });
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        birthYear = i;
        birthMonth = ++i1;
        birthDay = i2;
        selectDateButton.setText(birthDay + "/" + birthMonth + "/" + birthYear);

        flag++;
    }

    @Override
    public void onBackPressed() {
        Intent myIntent = new Intent(AddUser.this, MainActivity.class);
        startActivity(myIntent);
    }
}
