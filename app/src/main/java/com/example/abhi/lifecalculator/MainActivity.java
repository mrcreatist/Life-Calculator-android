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
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    int currentYear, currentMonth, currentDay;
    int birthYear, birthMonth, birthDay;
    int daysLeftInBirthMonth, daysLeftInBirthYear, daysInMidYears, daysInCurrentYearExceptCurrentMonth;
    int totalDays, totalMonths, totalYears, residueDays;

    FloatingActionButton floatingActionButton;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<CardLayout> cardLayoutList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainActivity.this, AddUser.class);
                startActivity(myIntent);
            }
        });

        // getting current day details
        currentYear = Calendar.getInstance().get(Calendar.YEAR);
        currentMonth = Calendar.getInstance().get(Calendar.MONTH);
        currentDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

        // READING THE DATABASE
        String fileName = "UserDB.txt", temp = "";
        try {
            // opening user database
            FileInputStream fin = openFileInput(fileName);
            int c;
            while ((c = fin.read()) != -1) {

                //collecting data in temp
                temp = temp + Character.toString((char) c);
            }
            fin.close();

            //splitting data
            String splitStringData[] = temp.split("\n");

/*            for(int i = 0; i < splitStringData.length; i++) {
                Toast.makeText(this, splitStringData[i], Toast.LENGTH_SHORT).show();
            }*/

            int splitStringLength = splitStringData.length;

//            Toast.makeText(this, Integer.toString(splitStringLength), Toast.LENGTH_SHORT).show();

            for (int i = 1; i < splitStringLength; i += 2) {

                //Toast.makeText(this, Integer.toString(i), Toast.LENGTH_SHORT).show();

                String splitDate[] = splitStringData[i].split("/");

                birthDay = Integer.parseInt(splitDate[0]);
                birthMonth = Integer.parseInt(splitDate[1]);
                birthYear = Integer.parseInt(splitDate[2]);

                Toast.makeText(this, "name: " + splitStringData[i - 1], Toast.LENGTH_SHORT).show();
                Toast.makeText(this, Integer.toString(calculateDays(birthYear, birthMonth)), Toast.LENGTH_SHORT).show();
                Toast.makeText(this, Integer.toString(calculateMonths()), Toast.LENGTH_SHORT).show();

                cardLayoutList = new ArrayList<>();

                CardLayout cardLayoutObject = new CardLayout(
                        "" + splitStringData[i - 1],
                        "" + calculateDays(birthYear, birthMonth),
                        "" + calculateMonths()
                );

                cardLayoutList.add(cardLayoutObject);

                adapter = new MyAdapter(cardLayoutList, this);
                recyclerView.setAdapter(adapter);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


/*
        cardLayoutList = new ArrayList<>();

        for (int i = 0; i < splitStringData.length; i = i + 2) {
            CardLayout cardLayoutObject = new CardLayout(
                    "" + splitStringData[i],
                    "" + i + 1,
                    "" + i + 1
            );
            cardLayoutList.add(cardLayoutObject);
        }

        adapter = new MyAdapter(cardLayoutList, this);
        recyclerView.setAdapter(adapter);*/
    }

    @Override
    public void onBackPressed() {

    }

    // Main Methods

    public int calculateDays(int birthYearCopy, int birthMonthCopy) {

        totalDays = 0;
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

        int tempMonth = 0;
        if (birthYearCopy != currentYear) {
            while (++birthYearCopy != currentYear) {
                daysInMidYears += daysInYear(birthYearCopy);
                tempMonth += 12;
            }
            totalMonths += tempMonth;
            totalDays += daysInMidYears;
        }


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

    public String calculateHoroscopeSign(int birthDay, int birthMonth) {
        //calculation in progress.
        return "aquarius";
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
