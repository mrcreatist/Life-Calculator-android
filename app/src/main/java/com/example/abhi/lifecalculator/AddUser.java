package com.example.abhi.lifecalculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class AddUser extends AppCompatActivity {

    Button addUser;
    EditText inputLayoutFirstName, inputLayoutLastName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        addUser = (Button) findViewById(R.id.addUser);
        inputLayoutFirstName = (EditText) findViewById(R.id.inputLayoutFirstName);
        inputLayoutLastName = (EditText) findViewById(R.id.inputLayoutLastName);

        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String filename = "User_DB";
                String string = "Hello World";
                try {
                    FileOutputStream outputStream = openFileOutput(filename, MODE_PRIVATE);
                    outputStream.write(inputLayoutFirstName.getText().toString());
                    Toast.makeText(getApplicationContext(), "User Added", Toast.LENGTH_LONG).show();

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
