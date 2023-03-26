package com.example.pantrytracker_v1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.HashMap;
import java.util.Map;

public class ProductActivity extends AppCompatActivity {


    EditText searchBar;
    Button searchBtn;
    String authorization, user;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        //AUTENTICAZIONE
        Bundle bundle=getIntent().getExtras();
        authorization = bundle.getString("auth");
        user= bundle.getString("user");

         viewsInit();

        //SEARCH BUTTON
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String barcode= searchBar.getText().toString();
                getProducts(barcode, authorization);
            }
        });

        //BOTTOM NAV VIEW
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
                        Intent intent3= new Intent(getApplicationContext(), UserActivity.class);
                        intent3.putExtra("auth", authorization);
                        intent3.putExtra("user", user);
                        startActivity(intent3);
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult= IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if(intentResult.getContents() != null){
            getProducts(intentResult.getContents(), authorization);
        }else{
            Toast.makeText(getApplicationContext(), "Qualcosa non va...", Toast.LENGTH_SHORT).show();
        }

    }

    public void getProducts(String barcode, String authorization) {

        RequestQueue requestQueue= Volley.newRequestQueue(ProductActivity.this);

        String URL= "https://lam21.iot-prism-lab.cs.unibo.it/products?barcode="+barcode;

        StringRequest stringRequest= new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Intent intent = new Intent(ProductActivity.this, ProductListActivity.class);
                intent.putExtra("response", response);
                intent.putExtra("barcode", barcode);
                intent.putExtra("auth", authorization);
                intent.putExtra("user", user);
                ProductActivity.this.startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "C'è qualche problema, ritenta piu tardi", Toast.LENGTH_SHORT).show();

            }
        }){
            public Map<String, String> getHeaders(){
                Map<String, String> data= new HashMap<String, String>();
                data.put("Authorization", "Bearer " + authorization);
                return data;
            }
        };

        requestQueue.add(stringRequest);
    }

    private void viewsInit() {

        searchBar= findViewById(R.id.search_bar);
        searchBtn= findViewById(R.id.search_btn);
        bottomNavigationView= findViewById(R.id.bottomNav);

    }
}