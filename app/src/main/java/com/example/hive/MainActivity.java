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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity{

    private EditText estEmail, estPassword;
    private Button eLogin;
    private TextView estRegister;

    boolean isValide = false;
    private static boolean estValide;


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
                    finish();
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
                        validate(inputEmail, inputPassword);
                    }

                }
            });

        }

        private void validate(String email, String password){
            String url = "http://os-vps418.infomaniak.ch:1180/l2_gr_8/login.php";
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            boolean estValide = response.trim().equals("Success");
                            if(!estValide){
                                Toast.makeText(MainActivity.this, "Email/Password invalide !", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(MainActivity.this, "Login successful !", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(MainActivity.this, MainMenu.class);
                                startActivity(intent);
                                //finish();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
            ){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("email", email);
                    params.put("password", password);

                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
            requestQueue.add(stringRequest);
        }


}