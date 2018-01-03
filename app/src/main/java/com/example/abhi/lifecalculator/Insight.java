package com.example.abhi.lifecalculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Insight extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insight);

        // reading the content of the file
        int birthYear, birthMonth, birthDay;
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

            int splitStringLength = splitStringData.length;

            for (int i = 1; i < splitStringLength; i += 2) {

                String splitDate[] = splitStringData[i].split("/");
                birthDay = Integer.parseInt(splitDate[0]);
                birthMonth = Integer.parseInt(splitDate[1]);
                birthYear = Integer.parseInt(splitDate[2]);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
