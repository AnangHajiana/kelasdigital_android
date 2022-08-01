package com.anang.kelasdigital;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private SessionManager sessionManager;

    private Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //init
        sessionManager = new SessionManager(MainActivity.this);

        btnLogout = findViewById(R.id.btnLogout);

        if(sessionManager.get(SessionManager.TOKEN, null) == null){
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        //uuid
        if(sessionManager.get(SessionManager.UUID, null) == null){
            String id = UUID.randomUUID().toString();
            sessionManager.store(SessionManager.UUID, id);
        }

        //listener
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //clear data session
                sessionManager.clear();
                //go to LoginActivity
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}