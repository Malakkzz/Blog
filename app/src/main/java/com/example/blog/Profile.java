package com.example.blog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Profile extends AppCompatActivity {

    String username;
    Button editBtn;
    EditText profileusr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profileusr = findViewById(R.id.profileusr);
        editBtn = findViewById(R.id.editbtn);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");

        profileusr.setText(username);

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newusername = profileusr.getText().toString();
                Intent intent = new Intent(getApplicationContext(),MainPage.class);
                if(username != newusername){
                    intent.putExtra("username",newusername);
                    setResult(RESULT_OK,intent);
                    finish();
                }
                setResult(RESULT_CANCELED,intent);
                finish();
            }
        });

    }
}