package com.example.abhi.lifecalculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;

public class Insight extends AppCompatActivity {

    private static final String fileName = "UserDB.txt";

    TextView insightUserName, insightHoroscopeSign;
    TextView dataBirthDetails, dataBirthDay;
    TextView dataDays, dataMonths, dataWeek;
    TextView dataHour, dataMinutes, dataSeconds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insight);

        // Initializing the TextViews
        insightUserName = findViewById(R.id.insightUserName);
        insightHoroscopeSign = findViewById(R.id.insightHoroscopeSign);
        dataBirthDetails = findViewById(R.id.dataBirthDetails);
        dataBirthDay = findViewById(R.id.dataBirthDay);
        dataDays = findViewById(R.id.dataDays);
        dataMonths = findViewById(R.id.dataMonths);
        dataWeek = findViewById(R.id.dataWeek);
        dataHour = findViewById(R.id.dataHour);
        dataMinutes = findViewById(R.id.dataMinutes);
        dataSeconds = findViewById(R.id.dataSeconds);

        // need a value from ManActivity in a bundle.
        Bundle bundle = getIntent().getExtras();
        int id = bundle.getInt("ID");
        id++;

        // reading from the DB
        String temp = readFromFile();

        // finding id and getting data
        String data[] = temp.split("-");
        String dataToWork[] = new String[2];

        try {
            int x = id;
            for (int i = 0; i < data.length - 1; i += 3) {
                String g = Integer.toString(x);
                String s = data[i];
                if (g.compareTo(s) == 0) {
                    dataToWork[0] = data[i + 1];              // Name
                    dataToWork[1] = data[i + 2];              // DOB
                } else {
                    //Toast.makeText(this, "Error: ID Not Found", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Exception", Toast.LENGTH_SHORT).show();
        }

        // segregating and parsing variable data
        String dataDOB[] = dataToWork[1].split("/");
        int birthDay = Integer.parseInt(dataDOB[0]);
        int birthMonth = Integer.parseInt(dataDOB[1]);
        int birthYear = Integer.parseInt(dataDOB[2]);

        // getting current day details
        int currentDay, currentMonth, currentYear;
        currentYear = Calendar.getInstance().get(Calendar.YEAR);
        currentMonth = Calendar.getInstance().get(Calendar.MONTH);
        currentDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        currentMonth++;


        //calculating work
        int receivingArray[] = countDays(birthDay, birthMonth, birthYear, currentDay, currentMonth, currentYear);

        // display the final result
        insightUserName.setText(dataToWork[0].toString());
        insightHoroscopeSign.setText(calculateHoroscopeSign(birthMonth, birthDay));

        dataBirthDetails.setText(dataDOB[0].toString() + "/" + dataDOB[1].toString() + "/" + dataDOB[2].toString());
        //dataBirthDay = findViewById(R.id.dataBirthDay);
        dataDays.setText(Integer.toString(receivingArray[0]));
        dataMonths.setText(Integer.toString(receivingArray[1]));
        dataWeek.setText(Integer.toString(receivingArray[1] * 4));
        dataHour.setText(Integer.toString(receivingArray[1] * 7 * 24));
        dataMinutes.setText(Integer.toString(receivingArray[1] * 7 * 24 * 60));
        dataSeconds.setText(Integer.toString(receivingArray[1] * 7 * 24 * 60 * 60));
    }

    @Override
    public void onBackPressed() {
        this.finish();

    }


    // SUPPORTING FUNCTIONS

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
        valueReturn[0] = totalDays;
        valueReturn[1] = totalMonths;
        return valueReturn;
    }

    // sub-supporting methods

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
}
