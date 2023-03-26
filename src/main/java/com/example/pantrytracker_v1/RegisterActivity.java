package com.example.pantrytracker_v1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    EditText mNameRegisterEdit, mMailRegisterEdit, mPasswordRegisterEdit;
    Button mRegisterBtn;
    TextView mLoginRedirTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        viewsInit();

        //LOGIN REDIRECT
        mLoginRedirTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

        //REGISTER BUTTON
        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(mNameRegisterEdit.getText())){
                    mNameRegisterEdit.setError("Username richiesto");
                }
                else if (TextUtils.isEmpty(mMailRegisterEdit.getText())){
                    mMailRegisterEdit.setError("Email richiesta");
                }
                else if (TextUtils.isEmpty(mPasswordRegisterEdit.getText())){
                    mPasswordRegisterEdit.setError("Password richiesta");
                }
                else if (mPasswordRegisterEdit.getText().toString().length() < 6){
                    mPasswordRegisterEdit.setError("Password deve contenere almeno 6 caratteri");
                }

                else{
                    registerUser(mNameRegisterEdit.getText().toString(), mMailRegisterEdit.getText().toString(), mPasswordRegisterEdit.getText().toString());
                }
            }

            private void registerUser(String username, String email, String password) {
                RequestQueue requestQueue= Volley.newRequestQueue(RegisterActivity.this);
                String URL = "https://lam21.iot-prism-lab.cs.unibo.it/users";
                StringRequest stringRequest= new StringRequest(com.android.volley.Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String message= "C'è qualcosa che non va, ritenta più tardi ...";
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

                    }
                }) {
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> data= new HashMap<String, String>();
                        data.put("username", username);
                        data.put("password", password);
                        data.put("email", password);
                        return data;
                    }
                };
                requestQueue.add(stringRequest);
            }
        });
    }

    private void viewsInit() {
        mNameRegisterEdit = findViewById(R.id.nameRegisterEdit);
        mMailRegisterEdit = findViewById(R.id.mailRegisterEdit);
        mPasswordRegisterEdit = findViewById(R.id.passwordRegisterEdit);

        mRegisterBtn = findViewById(R.id.registerBtn);

        mLoginRedirTxt = findViewById(R.id.loginRedirTxt);
    }
}