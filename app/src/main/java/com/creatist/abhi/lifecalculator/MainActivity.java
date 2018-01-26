package com.creatist.abhi.lifecalculator;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String fileName = "UserDB.txt";

    int currentYear, currentMonth, currentDay;
    int birthYear, birthMonth, birthDay;
    int backButtonCount;

    FloatingActionButton floatingActionButton;
    TextView newHereText;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<CardLayout> cardLayoutList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newHereText = findViewById(R.id.newHereText);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        floatingActionButton = findViewById(R.id.floatingActionButton);
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
        currentMonth++;

        // READING THE DATABASE
        String temp = readFromFile();

        //splitting data
        String splitStringData[] = temp.split("-");
        int splitStringLength = splitStringData.length;

        if (splitStringLength > 1) {             // RecyclerView display check

            newHereText.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.VISIBLE);

            int[] receivingArray;
            cardLayoutList = new ArrayList<>();

            for (int i = 0; i < (splitStringLength / 3); i++) {

                //Toast.makeText(this, "i:" + i, Toast.LENGTH_SHORT).show();
                String splitDate[] = splitStringData[(i * 3) + 2].split("/");

                birthDay = Integer.parseInt(splitDate[0]);
                birthMonth = Integer.parseInt(splitDate[1]);
                birthYear = Integer.parseInt(splitDate[2]);

                receivingArray = countDays(birthDay, birthMonth, birthYear, currentDay, currentMonth, currentYear);

                CardLayout cardLayoutObject = new CardLayout(
                        "" + splitStringData[(i * 3) + 1],
                        "" + receivingArray[0],
                        "" + receivingArray[1],
                        "" + calculateHoroscopeSign(birthMonth, birthDay),
                        findHoroscopeImage(calculateHoroscopeSign(birthMonth, birthDay))
                );

                cardLayoutList.add(cardLayoutObject);


                adapter = new MyAdapter(cardLayoutList, this);
                recyclerView.setAdapter(adapter);
            }

        } else {                                   // newHereText display

            recyclerView.setVisibility(View.INVISIBLE);
            newHereText.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public void onBackPressed() {
        if (backButtonCount >= 1) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            backButtonCount = 0;
            startActivity(intent);
        } else {
            Toast.makeText(this, "Press the back button once again to exit.", Toast.LENGTH_SHORT).show();
            backButtonCount++;
        }

    }

    String readFromFile() {
        String temp = "";
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = openFileInput(fileName);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String text;
            while ((text = bufferedReader.readLine()) != null) {
                temp += text;
            }
            return temp;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return temp;
    }

    // Main Methods

    public String calculateHoroscopeSign(int birthMonth, int birthDay) {

        if (birthMonth == 3) {
            if (birthDay >= 21) {
                return "Aries";
            } else {
                return "Pisces";
            }
        } else if (birthMonth == 4) {
            if (birthDay >= 20) {
                return "Taurus";
            } else {
                return "Aries";
            }
        } else if (birthMonth == 5) {
            if (birthDay >= 21) {
                return "Gemini";
            } else {
                return "Taurus";
            }
        } else if (birthMonth == 6) {
            if (birthDay >= 21) {
                return "Cancer";
            } else {
                return "Gemini";
            }
        } else if (birthMonth == 7) {
            if (birthDay >= 23) {
                return "Leo";
            } else {
                return "Cancer";
            }
        } else if (birthMonth == 8) {
            if (birthDay >= 23) {
                return "Virgo";
            } else {
                return "Leo";
            }
        } else if (birthMonth == 9) {
            if (birthDay >= 23) {
                return "Libra";
            } else {
                return "Virgo";
            }
        } else if (birthMonth == 10) {
            if (birthDay >= 23) {
                return "Scorpio";
            } else {
                return "Libra";
            }
        } else if (birthMonth == 11) {
            if (birthDay >= 22) {
                return "Sagittarius";
            } else {
                return "Scorpio";
            }
        } else if (birthMonth == 12) {
            if (birthDay >= 22) {
                return "Capricorn";
            } else {
                return "Sagittarius";
            }
        } else if (birthMonth == 1) {
            if (birthDay >= 20) {
                return "Aquarius";
            } else {
                return "Capricorn";
            }
        } else if (birthMonth == 2) {
            if (birthDay >= 19) {
                return "Pisces";
            } else {
                return "Aquarius";
            }
        } else {
            return "Unable to find";
        }
    }

    int calculateAge(int birthDay, int birthMonth, int birthYear, int currentDay, int currentMonth, int currentYear) {
        int year = currentYear - birthYear;
        if (currentMonth > birthMonth) {
            return year;
        } else if (currentMonth == birthMonth) {
            if (currentDay > birthDay || currentDay == birthDay) {
                return year;
            } else {
                return --year;
            }
        } else {
            return --year;
        }
    }

    int[] countDays(int birthDay, int birthMonth, int birthYear, int currentDay, int currentMonth, int currentYear) {
        int[] valueReturn = new int[2];
        int totalDays = 0, totalMonths = 0;
        for (int year = birthYear; year <= currentYear; year++) {
            //System.out.println("year:" + year);
            for (int month = monthOfTheYear(year, birthMonth, birthYear); month <= monthInYear(month, year, currentMonth, currentYear); month++) {
                //System.out.print("month: " + month + " Days: ");
                for (int day = firstDayOfTheMonth(month, year, birthDay, birthMonth, birthYear); day <= totalDaysInMonth(month, year, currentDay, currentMonth, currentYear); day++) {
                    //System.out.print(day + " ");
                    totalDays++;
                }
                totalMonths++;
                //System.out.println();
            }
            //System.out.println();
        }
        totalMonths--;
        valueReturn[0] = totalDays;
        valueReturn[1] = totalMonths;
        return valueReturn;
    }

    // Supporting Methods

    int monthOfTheYear(int year, int birthMonth, int birthYear) {
        if (year == birthYear) {
            return birthMonth;
        } else {
            return 1;
        }
    }

    int monthInYear(int month, int year, int currentMonth, int currentYear) {
        if (year == currentYear) {
            if (month == (currentMonth + 1)) {
                return 0;
            } else {
                return month;
            }
        } else {
            if (month >= 13) {
                return 0;
            } else {
                return month;
            }
        }
    }

    int firstDayOfTheMonth(int month, int year, int birthDay, int birthMonth, int birthYear) {
        if (year == birthYear) {
            if (month == birthMonth) {
                return ++birthDay;
            } else {
                return 1;
            }
        } else {
            return 1;
        }
    }

    int totalDaysInMonth(int month, int year, int currentDay, int currentMonth, int currentYear) {
        if (year == currentYear) {
            if (month == currentMonth) {
                return currentDay;
            } else {
                return daysInMonth(month, year);
            }
        } else {
            return daysInMonth(month, year);
        }
    }

    int daysInMonth(int month, int year) {
        if (month > 7) {
            if (month % 2 == 0) {
                return 31;
            } else {
                return 30;
            }
        } else {
            if (month == 2) {
                if (isLeapYear(year)) {
                    return 29;
                } else {
                    return 28;
                }
            } else {
                if (month % 2 == 0) {
                    return 30;
                } else {
                    return 31;
                }
            }
        }

    }

    boolean isLeapYear(int year) {
        if (year % 4 == 0) {
            if (year % 100 == 0) {
                year /= 100;
                if (year % 4 == 0) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    int findHoroscopeImage(String horoscopeSign) {

        //return R.drawable.sagittarius;

        switch (horoscopeSign) {
            case "Aquarius":
                return R.drawable.aquarius;
            case "Aries":
                return R.drawable.aries;
            case "Cancer":
                return R.drawable.cancer;
            case "Capricorn":
                return R.drawable.capricorn;
            case "Gemini":
                return R.drawable.gemini;
            case "Leo":
                return R.drawable.leo;
            case "Libra":
                return R.drawable.libra;
            case "Pisces":
                return R.drawable.pisces;
            case "Sagittarius":
                return R.drawable.sagittarius;
            case "Scorpio":
                return R.drawable.scorpio;
            case "Taurus":
                return R.drawable.taurus;
            case "Virgo":
                return R.drawable.virgo;
            default:
                return 0;
        }
    }
}
