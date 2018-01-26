package com.creatist.abhi.lifecalculator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Welcome extends AppCompatActivity {

    Button letsCountButton;
    TextView appHeading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        appHeading = findViewById(R.id.textView6);
        letsCountButton = (Button) findViewById(R.id.letsCountButton);

        letsCountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(Welcome.this, MainActivity.class);
                startActivity(myIntent);
            }
        });

        appHeading.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(Welcome.this, "Life isn't about finding yourself. Life is about creating yourself.\n" +
                        "\n" +
                        "- George Bernard Shaw", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    public void onBackPressed() {
        this.finish();
    }
}
