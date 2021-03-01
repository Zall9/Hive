package com.example.hive;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    private EditText estPrenom, estNom, estEmail, estPassword, estReentrerPassword;
    private Button btnRegister;
    private TextView estLogin;

    public static Credentials credentials;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        estPrenom = findViewById(R.id.text_prenom);
        estNom = findViewById(R.id.text_nom);
        estEmail = findViewById(R.id.text_email);
        estPassword = findViewById(R.id.text_pseudo);
        estReentrerPassword = findViewById(R.id.text_password);
        btnRegister = findViewById(R.id.button_sign_up);
        estLogin = findViewById(R.id.link_to_login);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String regPrenom = estPrenom.getText().toString();
                String regNom = estNom.getText().toString();
                String regEmail = estEmail.getText().toString();
                String regPassword = estPassword.getText().toString();
                String regReentrerPassword = estReentrerPassword.getText().toString();

                if(validate(regPrenom, regNom, regPassword, regReentrerPassword)){
                    credentials = new Credentials(regNom, regPrenom,regEmail, regPassword);
                    Intent intent = new Intent(Register.this, MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(Register.this, "Registration Successful !", Toast.LENGTH_SHORT).show();

                }
            }
        });

        estLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Register.this, MainActivity.class));
            }
        });

    }

    private boolean validate(String prenom, String nom, String password, String reentrerPassword){
        if(prenom.isEmpty() || nom.isEmpty() || password.length() < 8){
            Toast.makeText(this, "Rentrer tous les champs, le password doit avoir au moins 8 caractères", Toast.LENGTH_SHORT).show();
            return false;
        }else if(!password.equals(reentrerPassword)){
            Toast.makeText(this, "Les deux password doivent être identiques !", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }




}
