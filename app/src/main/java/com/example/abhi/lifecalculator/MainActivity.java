package com.example.abhi.lifecalculator;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    int currentYear, currentMonth, currentDay;
    int birthYear, birthMonth, birthDay;
    int daysLeftInBirthMonth, daysLeftInBirthYear, daysInMidYears, daysInCurrentYearExceptCurrentMonth, totalDays;
    int totalMonths, residueDays;
    int totalYears;
    FloatingActionButton floatingActionButton;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<CardLayout> cardLayoutList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        cardLayoutList = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            CardLayout cardLayoutObject = new CardLayout(
                    "UserName" + i + 1,
                    "" + i + 1,
                    "" + i + 1
            );
            cardLayoutList.add(cardLayoutObject);

        }

        adapter = new MyAdapter(cardLayoutList, this);
        recyclerView.setAdapter(adapter);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainActivity.this, AddUser.class);
                startActivity(myIntent);
            }
        });

        // READING THE DATABASE
        String fileName = "UserDB.txt";
        try {
            FileInputStream fin = openFileInput(fileName);
            int c;
            String temp = "";
            while ((c = fin.read()) != -1) {
                temp = temp + Character.toString((char) c);
            }
            Toast.makeText(this, "Database successfully accessed", Toast.LENGTH_SHORT).show();
            fin.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onBackPressed() {

    }

    // Main Methods

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
