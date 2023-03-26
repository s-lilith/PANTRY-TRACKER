package com.example.pantrytracker_v1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class UserActivity extends AppCompatActivity {

    String authorization, user, response;
    Button logoutBtn;
    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        Bundle bundle=getIntent().getExtras();
        authorization = bundle.getString("auth");
        user= bundle.getString("user");
        response= bundle.getString("response");


        viewsInit();

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences= getSharedPreferences("checkbox", MODE_PRIVATE);
                SharedPreferences.Editor editor= preferences.edit();
                editor.putString("remember", "false");
                editor.apply();

                Intent intent= new Intent(UserActivity.this, LoginActivity.class);
                intent.putExtra("response", response);
                intent.putExtra("user", user);
                UserActivity.this.startActivity(intent);

                finish();
            }
        });

        bottomNavigationView.setSelectedItemId(R.id.userNav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.homeNav:
                        Intent intent1= new Intent(getApplicationContext(), MainActivity.class);
                        intent1.putExtra("response", authorization);
                        intent1.putExtra("user", user);
                        startActivity(intent1);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.listNav:
                        Intent intent2= new Intent(getApplicationContext(), PantryActivity.class);
                        intent2.putExtra("auth", authorization);
                        intent2.putExtra("user", user);
                        startActivity(intent2);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.userNav:
                        return true;

                }
                return false;
            }
        });
    }

    private void viewsInit() {
        bottomNavigationView= findViewById(R.id.bottomNav);
        logoutBtn= findViewById(R.id.logoutBtn);
    }
}