package com.juara.hellojuara;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MainMenu extends AppCompatActivity {

    String username, password;
    TextView txtUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        txtUsername = findViewById(R.id.txtUsername);
        username = getIntent().getStringExtra("username");
        password = getIntent().getStringExtra("password");

        txtUsername.setText(username);

    }
}