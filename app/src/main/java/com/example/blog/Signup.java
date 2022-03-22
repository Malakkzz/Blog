package com.example.blog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Signup extends AppCompatActivity {

    Button signup;
    TextView username, email, pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        email = findViewById(R.id.email);
        signup = findViewById(R.id.signup);
        username = findViewById(R.id.username);
        pass = findViewById(R.id.password);


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usern = username.getText().toString();
                Intent intent = new Intent(getApplicationContext(), MainPage.class);
                intent.putExtra("username", usern);
                startActivity(intent);
                startActivity(intent);
            }
        });

    }
}