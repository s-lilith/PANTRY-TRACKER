package com.example.pantrytracker_v1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class AddProductActivity extends AppCompatActivity {

    Button prodCreation;
    EditText insDescr, insName, insImage;
    String authentication, token, barcode, name, description, image;
    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        Bundle bundle = getIntent().getExtras();
        token = bundle.getString("token");
        barcode = bundle.getString("barcode");
        authentication = bundle.getString("auth");

        viewsInit();





        prodCreation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = insName.getText().toString();
                description = insDescr.getText().toString();
                image = insImage.getText().toString();

                if (TextUtils.isEmpty(insName.getText())) {
                    insName.setError("Campo richiesto");
                } else if (TextUtils.isEmpty(insDescr.getText())) {
                    insDescr.setError("Campo richiesto");
                } else {
                    postNewProduct(token, barcode, name, description, authentication);
                }
            }
        });


    }




    private void postNewProduct(String token, String barcode, String name, String description, String authentication) {

        RequestQueue requestQueue = Volley.newRequestQueue(AddProductActivity.this);

        String URL = "https://lam21.iot-prism-lab.cs.unibo.it/products";

        JSONObject object = new JSONObject();

        try {
            object.put("test", false);
            object.put("token", token);
            object.put("name", name);
            object.put("description", description);
            object.put("barcode", barcode);
            if (TextUtils.isEmpty(insImage.getText())) {
                object.put("img", null);
            } else {
                object.put("img", image);
            }

        } catch (JSONException exception) {
            exception.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL, object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getApplicationContext(), "Prodotto inserito", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "C'è qualcosa che non va, ritenta più tardi", Toast.LENGTH_SHORT).show();

                    }
                }) {
            public Map<String, String> getHeaders() {
                Map<String, String> data = new HashMap<String, String>();
                data.put("Authorization", "Bearer " + authentication);

                return data;
            }
        };

        requestQueue.add(request);

    }

    private void viewsInit() {

        prodCreation = findViewById(R.id.prodCreation);
        insDescr = findViewById(R.id.insDescr);
        insName = findViewById(R.id.insName);
        insImage = findViewById(R.id.insImage);



    }

}