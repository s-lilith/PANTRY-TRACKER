package com.example.pantrytracker_v1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ChosenProductActivity extends AppCompatActivity {

    String token, authorization, id, name, description, user, image, barcode;
    TextView prodName, prodDesc;
    ImageView productPicture;
    Button addToPantry, deleteProd, voteBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chosen_product);

        Bundle bundle= getIntent().getExtras();
        token= bundle.getString("token");
        authorization= bundle.getString("auth");
        id= bundle.getString("id");
        name= bundle.getString("name");
        description= bundle.getString("description");
        image= bundle.getString("image");
        user= bundle.getString("user");
        barcode= bundle.getString("barcode");

        viewsInit();



        //INSERIMENTO PRODOTTI NELLA DISPENSA
        addToPantry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addProduct();
            }
        });

        //ELIMINAZIONE PRODOTTO
        deleteProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder= new AlertDialog.Builder(ChosenProductActivity.this);
                builder.setTitle("Alert");
                builder.setMessage("Vuoi eliminare questo prodotto?");
                builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteProduct(id, authorization);
                        dialogInterface.dismiss();
                    }
                });
                builder.create();
                builder.show();
            }
        });

        //VALUTAZIONE PRODOTTO
        voteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postPreferance(token, id, authorization);
            }
        });

        //INSERIMENTO IMMAGINE
        Glide
                .with(ChosenProductActivity.this)
                .load(image)
                .error(Glide.with(ChosenProductActivity.this)
                        .load("https://cdn-icons-png.flaticon.com/512/1251/1251660.png?w=740"))
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        Toast.makeText(getApplicationContext(), "Errore caricamento immagine", Toast.LENGTH_SHORT).show();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(productPicture);


    }

    private void addProduct() {

        DatabaseHelper helper= new DatabaseHelper(ChosenProductActivity.this);

        @SuppressLint ("SimpleDateFormat") DateFormat dateFormat= new SimpleDateFormat("dd/MM/yyyy");
        String insertDate= dateFormat.format(new Date());
        

        Boolean checkData =helper.insertData(name, description, insertDate, barcode, user);
        
        if (checkData) {
            Toast.makeText(getApplicationContext(), "Prodotto aggiunto alla dispensa", Toast.LENGTH_SHORT).show();
            Intent intent1= new Intent(getApplicationContext(), MainActivity.class);
            intent1.putExtra("response", authorization);
            intent1.putExtra("user", user);
            startActivity(intent1);
            overridePendingTransition(0,0);

        } else {
            Toast.makeText(getApplicationContext(), "E' avvenuto un errore durante l'inserimento del prodotto, ritenta ", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteProduct(String id, String authorization){
        RequestQueue requestQueue= Volley.newRequestQueue(ChosenProductActivity.this);

        String URL= "https://lam21.iot-prism-lab.cs.unibo.it/products/"+id;

        StringRequest stringRequest= new StringRequest(Request.Method.DELETE, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "Prodotto eliminato", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ChosenProductActivity.this, ProductActivity.class);
                        intent.putExtra("auth", authorization);
                        ChosenProductActivity.this.startActivity(intent);
                        finish();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Impossibile eliminare prodotto, ritenta più tardi!", Toast.LENGTH_SHORT).show();
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

    private void postPreferance(String token, String id, String authorization){

        RequestQueue requestQueue= Volley.newRequestQueue(ChosenProductActivity.this);

        String URL= "https://lam21.iot-prism-lab.cs.unibo.it/votes";

        int voto= 1;

        JSONObject object= new JSONObject();
        try {
            object.put("token", token);
            object.put("rating", voto);
            object.put("productId", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request= new JsonObjectRequest(Request.Method.POST, URL, object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getApplicationContext(), "Il tuo voto è stato inserito ", Toast.LENGTH_LONG).show();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Errore, sicuro di non aver già votato?", Toast.LENGTH_SHORT).show();

            }
        }){
             public Map<String, String> getHeaders(){
                Map<String, String> data= new HashMap<String, String>();
                data.put("Authorization", "Bearer " + authorization);
                return data;
            }
        };
        requestQueue.add(request);
    }

    private void viewsInit() {
        prodName=findViewById(R.id.prodName);
        prodDesc= findViewById(R.id.prodDesc);
        productPicture= findViewById(R.id.productPicture);
        addToPantry= findViewById(R.id.addToPantry);
        deleteProd= findViewById(R.id.deleteProd);
        voteBtn= findViewById(R.id.vote);

        prodName.setText(name);
        prodDesc.setText(description);
    }
}