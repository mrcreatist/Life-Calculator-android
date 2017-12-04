package com.example.abhi.lifecalculator;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    int currentYear, currentMonth, currentDay;
    int birthYear, birthMonth, birthDay;
    int daysLeftInBirthMonth, daysLeftInBirthYear, daysInMidYears, daysInCurrentYearExceptCurrentMonth, totalDays;
    int totalMonths, residueDays;
    int totalYears;

    int getX, getY;
    // double totalYearsFloat;

    TextView userName, yearNumber, monthNumber;
    EditText getName;
    FloatingActionButton floatingActionButton;
    boolean isOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String filename = "User_DB";
        String message;
        try {
            FileInputStream inputStream = openFileInput(filename);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer = new StringBuffer();
            while ((message = bufferedReader.readLine()) != null) {

            }

            Toast.makeText(getApplicationContext(), "User Added", Toast.LENGTH_LONG).show();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        userName = (TextView) findViewById(R.id.userName);
        yearNumber = (TextView) findViewById(R.id.yearNumber);
        monthNumber = (TextView) findViewById(R.id.monthNumber);
        getName = (EditText) findViewById(R.id.editText);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);

        final Button submitButton = (Button) findViewById(R.id.submitButton);
        final Button selectDate = (Button) findViewById(R.id.selectDate);

        userName.setText("");
        yearNumber.setText("");
        monthNumber.setText("");

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userName.setText(getName.getText());
                int x = calculateDays(birthYear, birthMonth);
                monthNumber.setText(Integer.toString(calculateMonths()));
                yearNumber.setText(Integer.toString(calculateYears()));
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

        floatingActionButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainActivity.this, AddUser.class);
                startActivity(myIntent);
            }
        });
    }

    @Override
    public void onBackPressed() {

    }

    // Main Methods

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        birthYear = i;
        birthMonth = ++i1;
        birthDay = i2;

    }

    public int calculateDays(int birthYearCopy, int birthMonthCopy) {
        totalDays = 0;
        // int birthYearCopy = birthYear;
        // int birthMonthCopy = birthMonth;

        daysLeftInBirthMonth = 0;
        daysLeftInBirthYear = 0;
        daysInMidYears = 0;
        daysInCurrentYearExceptCurrentMonth = 0;
        totalMonths = 0;

        /*STAGE 1
        *
        * Calculating the number of days required to complete the birth year
        * */

        // number of days left in completion of birth month
        int monthValue = daysInMonth(birthMonthCopy, birthYearCopy);
        daysLeftInBirthMonth = (monthValue - birthDay);
        totalDays += daysLeftInBirthMonth;

        // number of months left in completion of year after the birth month
        while (++birthMonthCopy != 13) {
            daysLeftInBirthYear += daysInMonth(birthMonthCopy, birthYearCopy);
            totalMonths++;
        }
        totalDays += daysLeftInBirthYear;


        /*STAGE 2
        *
        * Calculating the number of days in the mid years.
        * mid years = from (birth year + 1) till (current year - 1)
        * */

        int tempmonth = 0;
        while (++birthYearCopy != currentYear) {
            daysInMidYears += daysInYear(birthYearCopy);
            tempmonth += 12;
        }
        totalMonths += tempmonth;
        totalDays += daysInMidYears;


        /*STAGE 3
        *
        * Calculating the number of Days of current year
        * */

        // calculating number of days till (current month - 1)
        int i = 0;
        while (i++ != currentMonth) {
            daysInCurrentYearExceptCurrentMonth += daysInMonth(i, currentYear);
            totalMonths++;
        }
        totalDays += daysInCurrentYearExceptCurrentMonth;

        // adding the number of days till date of current month
        totalDays += currentDay;

        return totalDays;
    }

    public int calculateMonths() {
        residueDays = daysLeftInBirthMonth + currentDay;

        while (residueDays > 29) {
            residueDays -= 30;
            totalMonths++;
        }

        return totalMonths;
    }

    public int calculateYears() {
        totalYears = totalMonths / 12;
        //totalYearsFloat = totalMonths / 12;
        return totalYears;
    }

    // Supporting Methods

    public int daysInMonth(int month, int year) {

        int monthValue = 0;
        // jan, feb, mar, apr, may, jun, jul
        if (month <= 7) {
            // feb, apr, jun
            if (month % 2 == 0) {
                // exceptional case: feb
                if (month == 2) {
                    // check for leap year
                    if (year % 4 == 0) {
                        // check for centurial leap year
                        if (year % 100 == 0) {
                            year = year / 100;
                            if (year % 4 == 0) {
                                monthValue = 29;
                            } else {
                                monthValue = 28;
                            }
                        } else {
                            monthValue = 29;
                        }
                    } else {
                        monthValue = 28;
                    }
                } else {
                    monthValue = 30;
                }
            }
            // jan mar may jul
            else {
                monthValue = 31;
            }
        }
        // aug, sep, oct, nov, dec
        else {
            // aug, oct, dec
            if (month % 2 == 0) {
                monthValue = 31;
            }
            // sep, nov
            else {
                monthValue = 30;
            }
        }

        // returning final value
        return monthValue;
    }

    public int daysInYear(int year) {
        // check for leap year
        if (year % 4 == 0) {
            // check for centurial year
            if (year % 100 == 0) {
                year = year / 100;
                if (year % 4 == 0) {
                    return 366;
                } else {
                    return 365;
                }
            } else {
                return 366;
            }
        } else {
            return 365;
        }
    }
}
