package com.anang.kelasdigital;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.anang.kelasdigital.entity.Login;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private LoginService loginService;

    private EditText etUserName, etPassword;
    private Button btnLogin;
    private ProgressBar progressBar;

    private SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //init
        loginService = ApiClient.getClient().create(LoginService.class);

        etUserName = findViewById(R.id.etUserName);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        progressBar = findViewById(R.id.pb);

        sessionManager = new SessionManager(LoginActivity.this);

        //listener
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                actionLogin();
            }
        });
    }

    private void actionLogin(){
        HashMap<String, Object> reqBodyMap = new HashMap<>();

        reqBodyMap.put("uuid", sessionManager.get(SessionManager.UUID, null));
        reqBodyMap.put("user_name", etUserName.getText().toString());
        reqBodyMap.put("password", etPassword.getText().toString());

//        RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),(new JSONObject(reqBodyMap)).toString());
        Call<Login> callLogin = loginService.login(reqBodyMap);
        callLogin.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                progressBar.setVisibility(View.GONE);
                if(response.body().getStatus() == 200){
                    //set session
                    sessionManager.store(SessionManager.TOKEN, response.body().getToken());

                    // starting new activity.
                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(),response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(),t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}