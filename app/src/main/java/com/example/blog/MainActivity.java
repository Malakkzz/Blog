package com.example.blog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button login, signup;
    EditText username, password;
    String usern, pass;
    TextView info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = findViewById(R.id.login);
        signup = findViewById(R.id.signup);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        info = findViewById(R.id.info);

        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), MainPage.class);
                intent.putExtra("username", username.getText().toString());
                startActivity(intent);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Signup.class);
//                info.setText("username: "+username.getText().toString()+" Pass: " + password.getText().toString());
                startActivity(intent);
            }
        });
    }

    public void onSaveInstanceState(Bundle b) {
        super.onSaveInstanceState(b);
        b.putString("info", ""+info.getText().toString());
    }

    public void onRestoreInstanceState(Bundle b){
        super.onRestoreInstanceState(b);
        if(b!=null){
            String infoS = b.getString("info");
            if(infoS!=null){
                info.setText(infoS);
            }
        }
    }
}