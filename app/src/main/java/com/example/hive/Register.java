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
    private EditText estName, estEmail, estPassword, estReentrerPassword;
    private TextView tvStatus;
    private Button btnRegister;
    private String URL = "http://os-vps418.infomaniak.ch:1180/l2_gr_8/register.php";
    private String name, email, password, reentrerpassword;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        estName = findViewById(R.id.text_full_name);
        estEmail = findViewById(R.id.text_email);
        estPassword = findViewById(R.id.text_pseudo);
        estReentrerPassword = findViewById(R.id.text_password);
        tvStatus = findViewById(R.id.tvStatus);
        btnRegister = findViewById(R.id.button_sign_up);
        name = email = password = reentrerpassword ="";
        

    }

    public void save(View view){
        name = estName.getText().toString().trim();
        email = estEmail.getText().toString().trim();
        password = estPassword.getText().toString().trim();
        reentrerpassword = estReentrerPassword.getText().toString().trim();
        if(!password.equals(reentrerpassword)){
            Toast.makeText(this, "Password Mismatch", Toast.LENGTH_SHORT).show();
        }
        else if(!name.equals("") && !email.equals("") && !password.equals("")){
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.equals("success")) {
                        tvStatus.setText("Success Register");
                        btnRegister.setClickable(false);
                    } else if (response.equals("failure")) {
                        tvStatus.setText("Something went wrong");
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> data = new HashMap<>();
                    data.put("name", name);
                    data.put("email", email);
                    data.put("password", password);
                    return data;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        }
    }

    public void login(View view){
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        finish();
    }
}
