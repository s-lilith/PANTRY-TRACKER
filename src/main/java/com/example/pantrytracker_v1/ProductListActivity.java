package com.example.pantrytracker_v1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class  ProductListActivity extends AppCompatActivity {

    ListView productListview;
    SearchView searchBar;
    FloatingActionButton addNewProduct;

    String productString;
    ArrayList<String> arrayList= new ArrayList<>();

    String token, barcode, authorization, user;
    List<Product> productList= new ArrayList<Product>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        Bundle bundle= getIntent().getExtras();
        productString= bundle.getString("response");
        barcode= bundle.getString("barcode");
        authorization= bundle.getString("auth");
        user= bundle.getString("user");

        viewsInit();

        JSONObject jsonObject= null;
        try {
            jsonObject= new JSONObject(productString);
            JSONArray jsonArray= jsonObject.getJSONArray("products");
            token= jsonObject.getString("token");

            for (int i=0; i<jsonArray.length(); i++){
                String id= jsonArray.getJSONObject(i).getString("id");
                String name= jsonArray.getJSONObject(i).getString("name");
                String description= jsonArray.getJSONObject(i).getString("description");
                String barcode= jsonArray.getJSONObject(i).getString("barcode");
                String image;

                if(jsonArray.getJSONObject(i).isNull("img")){
                    image= "urly.it/3nwkf";
                }else{
                    image= jsonArray.getJSONObject(i).getString("img");
                }

                Product product= new Product(id, name, description, barcode, image);
                productList.add(product);

                String s= name + "- " + description;
                arrayList.add(s);
            }

        }catch (JSONException exception){
            exception.printStackTrace();
        }

        //LISTVIEW
        ArrayAdapter<String> adapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1 , arrayList);
        productListview.setAdapter(adapter);

        productListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
                Product chosenProduct= new Product(productList.get(i).getId(),productList.get(i).getName(), productList.get(i).getDescription(), productList.get(i).getBarcode(), productList.get(i).getImage());
                Intent intent= new Intent(ProductListActivity.this, ChosenProductActivity.class);
                intent.putExtra("token", token);
                intent.putExtra("auth", authorization);
                intent.putExtra("id", chosenProduct.getId());
                intent.putExtra("name", chosenProduct.getName());
                intent.putExtra("description", chosenProduct.getDescription());
                intent.putExtra("barcode", barcode);
                intent.putExtra("image", chosenProduct.getImage());
                intent.putExtra("user", user);
                ProductListActivity.this.startActivity(intent);
                System.out.println(chosenProduct.getId() + "\n"+chosenProduct.getName() + "\n" + chosenProduct.getDescription());
                finish();
            }
        });

       //NEW PRODUCT
        addNewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(ProductListActivity.this, AddProductActivity.class);
                intent.putExtra("token", token); //Optional parameters
                intent.putExtra("barcode", barcode);
                intent.putExtra("auth", authorization);
                ProductListActivity.this.startActivity(intent);
                finish();
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

    private void viewsInit() {
        productListview= findViewById(R.id.productLv);
        searchBar= findViewById(R.id.search_bar);
        addNewProduct= findViewById(R.id.addNewProduct);
    }
}