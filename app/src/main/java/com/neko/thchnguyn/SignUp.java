package com.neko.thchnguyn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.FetchData;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class SignUp extends AppCompatActivity {

    TextInputEditText inputFullName, inputUsername, inputPassword, inputEmail;
    Button buttonSign;
    TextView viewLogin;
    ProgressBar PGRS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        inputFullName = findViewById(R.id.fullname);
        inputUsername = findViewById(R.id.username);
        inputPassword = findViewById(R.id.password);
        inputEmail = findViewById(R.id.email);
        buttonSign = findViewById(R.id.buttonSignUp);
        viewLogin = findViewById(R.id.loginText);
        PGRS = findViewById(R.id.progress);

        viewLogin.setOnClickListener(new View.OnClickListener(){
            @Override
                    public void onClick(View v){
                        Intent intent = new Intent(getApplicationContext(), Login.class);
                        startActivity(intent);
                        finish();
            }
        });

        buttonSign.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                String fullname, username, password, email;
                fullname = String.valueOf(inputFullName.getText());
                username = String.valueOf(inputUsername.getText());
                password = String.valueOf(inputPassword.getText());
                email = String.valueOf(inputEmail.getText());

                if (!fullname.equals("") && !username.equals("") && !password.equals("") && !email.equals("")) {
                    PGRS.setVisibility(View.VISIBLE);
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            String[] field = new String[4];
                            field[0] = "fullname";
                            field[1] = "username";
                            field[2] = "password";
                            field[3] = "email";
                            String[] data = new String[4];
                            data[0] = fullname;
                            data[1] = username;
                            data[2] = password;
                            data[3] = email;
                            PutData putData = new PutData("http://192.168.0.156/log/signup.php","POST",field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    PGRS.setVisibility(View.GONE);
                                    String result = putData.getResult();
                                    if(result.equals("Sign Up Success")){
                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), Login.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "All fields required", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}