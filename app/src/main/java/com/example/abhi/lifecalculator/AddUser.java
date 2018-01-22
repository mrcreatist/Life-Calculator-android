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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;


public class AddUser extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private static final String fileName = "UserDB.txt";

    TextView name;
    Button selectDateButton;
    FloatingActionButton addUserFAB;

    int birthYear, birthMonth, birthDay;
    int currentYear, currentMonth, currentDay;
    int id = 0, flag = 0;

    String buttonText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        name = (EditText) findViewById(R.id.inputName);
        selectDateButton = (Button) findViewById(R.id.selectDate);
        addUserFAB = findViewById(R.id.addUserFAB);

        buttonText = selectDateButton.getText().toString();

        selectDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                currentYear = c.get(Calendar.YEAR);
                currentMonth = c.get(Calendar.MONTH);
                currentDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(AddUser.this, AddUser.this, currentYear, currentMonth, currentDay);
                datePickerDialog.show();
            }
        });

        addUserFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String sample[];
                sample = name.getText().toString().split("-");

                if (name.getText().toString().matches("")) {
                    // empty username
                    Toast.makeText(AddUser.this, "Name field is empty", Toast.LENGTH_SHORT).show();
                } else if (selectDateButton.getText().toString().matches(buttonText.toString())) {
                    // empty date
                    Toast.makeText(AddUser.this, "Date field is empty", Toast.LENGTH_SHORT).show();
                } else if (currentYear < birthYear) {
                    // invalid birth year
                    Toast.makeText(AddUser.this, "Please select a valid year", Toast.LENGTH_SHORT).show();
                } else if (currentMonth + 2 <= birthMonth && birthYear == currentYear) {
                    // invalid birth month
                    Toast.makeText(AddUser.this, "Please select a valid month", Toast.LENGTH_SHORT).show();
                } else if (currentDay < birthDay && currentMonth + 1 == birthMonth && currentYear == birthYear) {
                    // invalid birth day
                    Toast.makeText(AddUser.this, "Please select a valid day", Toast.LENGTH_SHORT).show();
                } else if (sample.length != 1) {
                    // hyphen in user name
                    Toast.makeText(AddUser.this, "hyphen('-') in not allowed in event names", Toast.LENGTH_SHORT).show();
                    //Toast.makeText(AddUser.this, Integer.toString(sample.length), Toast.LENGTH_SHORT).show();
                } else {
                    // validated data, proceeding to to add event.
                    try {
                        writeToFile(getID() + "-");
                        writeToFile(name.getText().toString() + "-");
                        writeToFile(selectDateButton.getText().toString() + "-");
                        Toast.makeText(AddUser.this, "Entry Successful", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(AddUser.this, "addUser: Exception occurred", Toast.LENGTH_SHORT).show();
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

    // SUPPORTING OPERATIONS

    int getID() {
        try {
            String s = readFromFile();
            if (s != "") {
                String z[] = s.split("-");
                int currentID = Integer.parseInt(z[z.length - 3]);
                currentID++;
                return currentID;
            } else {
                return 1;
            }
        } catch (Exception e) {
            Toast.makeText(this, "Exception in generating ID", Toast.LENGTH_SHORT).show();
        }
        return 0;
    }

    void writeToFile(String data) {
        String text = readFromFile() + data;
        FileOutputStream fileOutputStream = null;

        try {
            fileOutputStream = openFileOutput(fileName, MODE_PRIVATE);
            fileOutputStream.write(text.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
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
}
