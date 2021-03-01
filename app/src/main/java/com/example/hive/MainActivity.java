package com.example.hive;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity{
        private EditText estEmail, estPassword;
        private Button eLogin;
        private TextView estRegister;

        boolean isValide = false;


        protected void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.login);
            estEmail = findViewById(R.id.text_pseudo);
            estPassword = findViewById(R.id.text_password);
            eLogin = findViewById(R.id.button_login);
            estRegister = findViewById(R.id.link_to_sign_up);

            estRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this, Register.class));
                }
            });


            eLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String inputEmail = estEmail.getText().toString();
                    String inputPassword = estPassword.getText().toString();

                    if(inputEmail.isEmpty() || inputPassword.isEmpty()){
                        Toast.makeText(MainActivity.this, "Rentrer les champs correctement!", Toast.LENGTH_SHORT).show();
                    }else{
                        isValide = validate(inputEmail, inputPassword);

                        if(!isValide){
                            Toast.makeText(MainActivity.this, "Email/Password invalide !", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(MainActivity.this, "Login successful !", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(MainActivity.this, MainMenu.class);
                            startActivity(intent);
                            //finish();
                        }

                    }

                }
            });

        }

        private  boolean validate(String email, String password){
            if(Register.credentials != null){
                if(email.equals(Register.credentials.getEmail()) && password.equals(Register.credentials.getPassword())){
                    return true;
                }
            }

            return false;
        }



        public void register(View view){
            Intent intent = new Intent(this, Register.class);
            startActivity(intent);
            finish();
        }


}