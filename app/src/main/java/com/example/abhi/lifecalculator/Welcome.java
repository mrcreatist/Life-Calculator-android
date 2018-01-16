package com.example.abhi.lifecalculator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Welcome extends AppCompatActivity {

    Button letsCountButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        letsCountButton = (Button) findViewById(R.id.letsCountButton);

        letsCountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(Welcome.this, MainActivity.class);
                startActivity(myIntent);
            }
        });
    }

    public void onBackPressed() {
        this.finish();
    }
}
