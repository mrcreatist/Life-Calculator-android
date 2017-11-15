package com.example.abhi.lifecalculator;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    int currentYear, currentMonth, currentDay;
    int birthYear, birthMonth, birthDay;

    TextView name;
    TextView yearNumber;
    EditText getName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = (TextView) findViewById(R.id.name);
        yearNumber = (TextView) findViewById(R.id.yearNumber);
        getName = (EditText) findViewById(R.id.editText);

        Button button = (Button) findViewById(R.id.button);
        Button selectDate = (Button) findViewById(R.id.selectDate);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name.setText(getName.getText());
                calculate();
            }
        });

        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar c = Calendar.getInstance();

                currentYear = c.get(Calendar.YEAR);
                currentMonth = c.get(Calendar.MONTH);
                currentDay = c.get(Calendar.DAY_OF_MONTH);

                Toast.makeText(MainActivity.this, currentDay + "-" + currentMonth + "-" + currentYear, Toast.LENGTH_SHORT).show();

                birthYear = c.get(Calendar.YEAR);
                birthMonth = c.get(Calendar.MONTH);
                birthDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, MainActivity.this, birthYear, birthMonth, birthDay);
                datePickerDialog.show();
            }
        });
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        birthYear = i;
        birthMonth = i1;
        birthDay = i2;

    }

    public void calculate() {
        int age;
        age = currentYear - birthYear;
        yearNumber.setText(Integer.toString(age));
    }
}
