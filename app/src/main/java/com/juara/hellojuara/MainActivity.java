package com.juara.hellojuara;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText txtUsername, txtPassword;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        btnLogin = findViewById(R.id.btnLogin);

        txtUsername.setText("SamDedy");
        txtPassword.setText("12345");
    }

    public void login(View view){
        String pesan = "Username adalah "+txtUsername.getText().toString()+"Passwordnya adalah "+txtPassword.getText().toString();
        Toast.makeText(MainActivity.this, pesan, Toast.LENGTH_SHORT).show();
    }
}