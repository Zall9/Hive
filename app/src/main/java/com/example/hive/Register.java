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
                validate(regPrenom, regNom, regEmail, regPassword, regReentrerPassword);

            }
        });

        estLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Register.this, MainActivity.class));
            }
        });

    }

    private void validate(String prenom, String nom, String email, String password, String reentrerPassword){
        String url = "http://os-vps418.infomaniak.ch:1180/l2_gr_8/signup.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        boolean estValide = response.trim().equals("Success");
                        if(prenom.isEmpty() || nom.isEmpty() || password.length() < 8){
                            Toast.makeText(Register.this, "Rentrer tous les champs, le password doit avoir au moins 8 caractères", Toast.LENGTH_SHORT).show();
                        }else if(!password.equals(reentrerPassword)){
                            Toast.makeText(Register.this, "Les deux password doivent être identiques !", Toast.LENGTH_SHORT).show();
                        }else if(estValide){
                            Intent intent = new Intent(Register.this, MainActivity.class);
                            startActivity(intent);
                            Toast.makeText(Register.this, "Inscription Réussie !", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(Register.this, response.trim(), Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Register.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("prenom", prenom);
                params.put("nom", nom);
                params.put("email", email);
                params.put("password", password);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(Register.this);
        requestQueue.add(stringRequest);
    }




}
