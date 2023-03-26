package com.example.pantrytracker_v1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class PantryActivity extends AppCompatActivity {
    
    String user, response, authorization;
    
    ListView pantryList;
    BottomNavigationView bottomNavigationView;
    
    DatabaseHelper helper = new DatabaseHelper(PantryActivity.this);

    SearchView searchBar;

    ArrayList<String> list= new ArrayList<>();
    List<Product> productList= new ArrayList<Product>();
    
    
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantry);
        
        Bundle bundle= getIntent().getExtras();
        user= bundle.getString("user");
        response= bundle.getString("auth");
        authorization = bundle.getString("auth");

        viewsInit();
        pantryList= findViewById(R.id.pantryList);
        bottomNavigationView= findViewById(R.id.bottomNav);

        //LEGGO DATI DAL DB
        Cursor cursor= helper.getData(user);
        showData(cursor);

        //SEARCHBAR

        //BOTTON NAV
        bottomNavigationView.setSelectedItemId(R.id.listNav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.homeNav:
                        Intent intent= new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("response", response);
                        intent.putExtra("user", user);
                        startActivity(intent);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.listNav:
                        return true;
                    case R.id.userNav:
                        Intent intent2= new Intent(getApplicationContext(), UserActivity.class);
                        intent2.putExtra("auth", authorization);
                        intent2.putExtra("user", user);
                        startActivity(intent2);
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
        

    }

    private void viewsInit() {
        pantryList= findViewById(R.id.pantryList);
        bottomNavigationView= findViewById(R.id.bottomNav);
        searchBar=findViewById(R.id.search_bar2);

    }


    private void showData(Cursor cursor) {
        list.clear();
        if (cursor.getCount()==0){
            Toast.makeText(getApplicationContext(), "Dispensa vuota", Toast.LENGTH_SHORT).show();
        }
        
        while (cursor.moveToNext()){
            String id = cursor.getString(0);
            String name = cursor.getString(1);
            String description =cursor.getString(2);
            String date = cursor.getString(3);
            String barcode = cursor.getString(4);
            String union =  name + "\n"+ description + ".\nAggiunto il: " + date  + "\nBarcode: " + barcode; //aggiungere data di scadenza
            list.add(union);

            Product product= new Product(id, name, description, barcode, date);
            productList.add(product);
        }

        //POPOLO LISTVIEW
        ArrayAdapter<String> adapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1 , list);

        pantryList.setAdapter(adapter);
        pantryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String clickedProduct= (String) pantryList.getItemAtPosition(i);
                String deleteProduct= productList.get(i).getId();

                AlertDialog.Builder builder= new AlertDialog.Builder(PantryActivity.this);
                builder.setTitle("Alert");
                builder.setMessage("Vuoi eliminare questo prodotto?");
                builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        helper.deleteData(deleteProduct);
                        dialogInterface.dismiss();
                        finish();
                        startActivity(getIntent());
                    }
                });
                builder.create();
                builder.show();

            }
        });

        //SEARCHBAR
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);

                if (adapter.getCount() == 0){
                    Toast.makeText(getApplicationContext(), "La ricerca non ha prodotto risultati, effettua una nuova ricerca!", Toast.LENGTH_SHORT).show();
                }
                return false;

            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);

                return false;
            }
        });
    }
}