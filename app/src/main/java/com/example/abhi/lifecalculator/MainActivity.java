package com.example.abhi.lifecalculator;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

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

                // Toast.makeText(MainActivity.this, currentDay + "-" + currentMonth + "-" + currentYear, Toast.LENGTH_SHORT).show();

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
        int totalDays = 0;
        int birthYearCopy = birthYear;
        int birthMonthCopy = birthMonth;

        // calculating number of days between (birthYear + 1) and (currentYear - 1)
        birthYearCopy++;
        while (birthYearCopy != currentYear) {
            totalDays += daysInYear(birthYearCopy);
            birthYearCopy++;
        }

        // calculating number of days left from (birthMonth + 1) till the completion of birth year
        birthMonthCopy++;
        while (birthMonthCopy != 13) {
            totalDays += daysInMonth(birthMonthCopy, birthYear);
            birthMonthCopy++;
        }

        // calculating number of days left in the month
        int monthValue;
        monthValue = daysInMonth(birthMonth, birthYear);
        totalDays += (monthValue - birthDay);

        // calculating number of days to be added till (currentMonth - 1)
        int i = 0;
        while (i != currentMonth) {
            daysInMonth(i, currentYear);
            i++;
        }

        // adding currentMonth days to total number of Days
        totalDays += currentDay;

        yearNumber.setText(Integer.toString(totalDays));

    }

    public int daysInMonth(int month, int year) {

        int monthValue = 0;
        if (month <= 7) {
            if (month % 2 == 0) {
                if (month == 2) {
                    if (year % 4 == 0) {
                        monthValue = 29;
                    } else {
                        monthValue = 28;
                    }
                } else {
                    monthValue = 30;
                }
            } else {
                monthValue = 31;
            }
        } else {
            if (month % 2 == 0) {
                monthValue = 31;
            } else {
                monthValue = 30;
            }
        }

        return monthValue;
    }

    public int daysInYear(int year) {
        if (year % 4 == 0) {
            return 366;
        } else {
            return 365;
        }
    }
}
