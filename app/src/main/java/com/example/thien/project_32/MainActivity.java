package com.example.thien.project_32;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button button_1, button_2 , intent_map;
    TextView hi_acc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_1 = (Button) findViewById(R.id.button_1);
        button_2 = (Button) findViewById(R.id.button_2);
        intent_map = (Button) findViewById(R.id.intent_map);
        hi_acc =(TextView) findViewById(R.id.Hi_acc);
        hi_acc.setText("Hi "+my_javaclass.name);

        button_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleButtons_2();
                Intent intent = new Intent(MainActivity.this, MyService.class);
                intent.putExtra("INIT_DATA",my_javaclass.name);
                startService(intent);
            }
        });

        button_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleButtons_3();
                stopService(new Intent(MainActivity.this,MyService.class));

            }
        });
        intent_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                startService(intent);
            }
        });
    }

    private void toggleButtons_2() {
        if (button_1.isEnabled()) {
            button_2.setEnabled(true);
            button_1.setEnabled(false);

        } else {
            button_2.setEnabled(false);
            button_1.setEnabled(true);
        }
    }

    private void toggleButtons_3() {
        if (button_2.isEnabled()) {
            button_1.setEnabled(true);
            button_2.setEnabled(false);
        } else {
            button_1.setEnabled(false);
            button_2.setEnabled(true);
        }
    }
}
