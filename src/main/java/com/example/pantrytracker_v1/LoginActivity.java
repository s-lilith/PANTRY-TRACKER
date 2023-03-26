package com.example.pantrytracker_v1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {


    TextView mRegisterRedirTxt;
    EditText mMailLoginEdit, mPasswordLoginEdit;
    Button mLoginBtn;
    CheckBox rememberMe;

    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        viewsInit();

        SharedPreferences preferences= getSharedPreferences("checkbox", MODE_PRIVATE);
        String checkbox= preferences.getString("remember", "");
        if(checkbox.equals("true")){
            loginUser(mMailLoginEdit.getText().toString(), mPasswordLoginEdit.getText().toString());
        }else if(checkbox.equals("false")){
            Toast.makeText(this, "Accedi per favore", Toast.LENGTH_SHORT).show();
        }

        //REGISTER REDIRECT
        mRegisterRedirTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                finish();
            }
        });

        //REMEMBER CHECKBOX
        rememberMe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()){
                    SharedPreferences preferences= getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor= preferences.edit();
                    editor.putString("remember", "true");
                    editor.apply();

                } else if (!compoundButton.isChecked()){
                    SharedPreferences preferences= getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor= preferences.edit();
                    editor.putString("remember", "false");
                    editor.apply();
                }
            }
        });

        //LOGIN BUTTON
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = mMailLoginEdit.getText().toString();
                loginUser(mMailLoginEdit.getText().toString(), mPasswordLoginEdit.getText().toString());
            }


        });



    }

    public void loginUser(String mail, String password) {
        RequestQueue requestQueue= Volley.newRequestQueue(LoginActivity.this);
        String URL= "https://lam21.iot-prism-lab.cs.unibo.it/auth/login";
        StringRequest stringRequest= new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.substring(16);
                response = response.substring(0, response.length() - 2);
                switchActivity(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String message= "C'è qualcosa che non va, ritenta più tardi ...";
                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data= new HashMap<String, String>();
                data.put("email", password);
                data.put("password", password);

                return data;
            }
        };

        requestQueue.add(stringRequest);
    }

    public void switchActivity(final String response){
        Intent intent= new Intent(LoginActivity.this, MainActivity.class);
        intent.putExtra("response", response);
        intent.putExtra("user", user);
        LoginActivity.this.startActivity(intent);
    }
    private void viewsInit() {
        mRegisterRedirTxt=findViewById(R.id.registerRedirTxt);
        mMailLoginEdit= findViewById(R.id.mailLoginEdit);
        mPasswordLoginEdit= findViewById(R.id.passwordLoginEdit);
        mLoginBtn= findViewById(R.id.loginBtn);
        rememberMe= findViewById(R.id.rememberMe);


    }
}