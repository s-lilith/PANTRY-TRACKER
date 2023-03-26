package com.example.pantrytracker_v1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.material.bottomnavigation.BottomNavigationView;




public class MainActivity extends AppCompatActivity {

    String token, authorization, id, name, description, user, image, barcode;
    Button addProduct;
    CardView card1, card2, card3, card4;
    ImageView suggestedPic1, suggestedPic2, suggestedPic3, suggestedPic4;
    TextView suggestedTitle1, suggestedTitle2, suggestedTitle3, suggestedTitle4;
    TextView suggestedBarcode1, suggestedBarcode2, suggestedBarcode3, suggestedBarcode4;
    BottomNavigationView bottomNavigationView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewsInit();

        //AUTENTICAZIONE
        Bundle bundle=getIntent().getExtras();
        authorization= bundle.getString("response");
        user= bundle.getString("user");

        id= bundle.getString("id");
        name= bundle.getString("name");
        description= bundle.getString("description");
        image= bundle.getString("image");
        barcode= bundle.getString("barcode");

        //ADD PRODUCT BUTTON
        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this, ProductActivity.class);
                intent.putExtra("auth", authorization);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });

        //GET PIC
        getSuggestedPic();


        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id= "cl4bh34wd109261eniviedhdb3";
                String name= "Spaghetti";
                String description= "Barilla";
                String barcode= "1111";
                String image= "https://d2f5fuie6vdmie.cloudfront.net/asset/ita/2020/32/224112b23d6ee1965c0255590c4bfd231145be28.jpeg";

                Product suggestedProduct1= new Product(id,name, description, barcode, image);
                Intent intent= new Intent(MainActivity.this, ChosenProductActivity.class);
                intent.putExtra("token", token);
                intent.putExtra("auth", authorization);
                intent.putExtra("id", id);
                intent.putExtra("name", name);
                intent.putExtra("description", description);
                intent.putExtra("barcode", barcode);
                intent.putExtra("image", image);
                intent.putExtra("user", user);

                MainActivity.this.startActivity(intent);
                finish();
            }
        });

        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id= "cl4bh1hsv109051enihphgc1z9";
                String name= "Passata di Pomodoro";
                String description= "Mutti";
                String barcode= "2222";
                String image= "https://ardentesupermercati.gospesa.it/13312-large_default/mutti-passata-mutti-di-pomodoro-700-g.jpg";

                Product suggestedProduct2= new Product(id,name, description, barcode, image);
                Intent intent= new Intent(MainActivity.this, ChosenProductActivity.class);
                intent.putExtra("token", token);
                intent.putExtra("auth", authorization);
                intent.putExtra("id", id);
                intent.putExtra("name", name);
                intent.putExtra("description", description);
                intent.putExtra("barcode", barcode);
                intent.putExtra("image", image);
                intent.putExtra("user", user);

                MainActivity.this.startActivity(intent);
                finish();
            }
        });

        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id= "cl4bh51b4109461eni4fo0xhq6";
                String name= "Parnigiano Reggiano";
                String description= "D.O.P.";
                String barcode= "3333";
                String image= "https://cdn-2.parmashop.com/7443-large_default/parmigiano-reggiano-dop-forma-intera-di-collina-qual-invecchiato.jpg";

                Product suggestedProduct3= new Product(id,name, description, barcode, image);
                Intent intent= new Intent(MainActivity.this, ChosenProductActivity.class);
                intent.putExtra("token", token);
                intent.putExtra("auth", authorization);
                intent.putExtra("id", id);
                intent.putExtra("name", name);
                intent.putExtra("description", description);
                intent.putExtra("barcode", barcode);
                intent.putExtra("image", image);
                intent.putExtra("user", user);

                MainActivity.this.startActivity(intent);
                finish();
            }
        });

        card4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id= "cl4bh6yjq109671enibb18wjjv";
                String name= "Chardonnay";
                String description= "Reguta";
                String barcode= "4444";
                String image= "https://www.reguta.com/wp-content/uploads/2017/04/Chardonnay-1.png";

                Product suggestedProduct4= new Product(id,name, description, barcode, image);
                Intent intent= new Intent(MainActivity.this, ChosenProductActivity.class);
                intent.putExtra("token", token);
                intent.putExtra("auth", authorization);
                intent.putExtra("id", id);
                intent.putExtra("name", name);
                intent.putExtra("description", description);
                intent.putExtra("barcode", barcode);
                intent.putExtra("image", image);
                intent.putExtra("user", user);

                MainActivity.this.startActivity(intent);
                finish();
            }
        });


        //BOTTOM NAV VIEW
        bottomNavigationView.setSelectedItemId(R.id.homeNav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.homeNav:
                        return true;
                    case R.id.listNav:
                        Intent intent= new Intent(getApplicationContext(), PantryActivity.class);
                        intent.putExtra("auth", authorization);
                        intent.putExtra("user", user);
                        startActivity(intent);
                        overridePendingTransition(0,0);
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

    private void getSuggestedPic() {

        Glide.
                with(MainActivity.this)
                .load("https://d2f5fuie6vdmie.cloudfront.net/asset/ita/2020/32/224112b23d6ee1965c0255590c4bfd231145be28.jpeg")
                .error(Glide.with(MainActivity.this)
                        .load("https://cdn-icons-png.flaticon.com/512/1251/1251660.png?w=740"))
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(suggestedPic1);

        Glide.
                with(MainActivity.this)
                .load("https://ardentesupermercati.gospesa.it/13312-large_default/mutti-passata-mutti-di-pomodoro-700-g.jpg")
                .error(Glide.with(MainActivity.this)
                        .load("https://cdn-icons-png.flaticon.com/512/1251/1251660.png?w=740"))
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(suggestedPic2);
        Glide.
                with(MainActivity.this)
                .load("https://cdn-2.parmashop.com/7443-large_default/parmigiano-reggiano-dop-forma-intera-di-collina-qual-invecchiato.jpg")
                .error(Glide.with(MainActivity.this)
                        .load("https://cdn-icons-png.flaticon.com/512/1251/1251660.png?w=740"))
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(suggestedPic3);

        Glide.
                with(MainActivity.this)
                .load("https://www.reguta.com/wp-content/uploads/2017/04/Chardonnay-1.png")
                .error(Glide.with(MainActivity.this)
                        .load("https://cdn-icons-png.flaticon.com/512/1251/1251660.png?w=740"))
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(suggestedPic4);



    }

    private void viewsInit() {
        addProduct= findViewById(R.id.addProduct);
        bottomNavigationView= findViewById(R.id.bottomNav);
        card1= findViewById(R.id.card1);
        card2= findViewById(R.id.card2);
        card3= findViewById(R.id.card3);
        card4= findViewById(R.id.card4);
        suggestedPic1= findViewById(R.id. suggestedPic1);
        suggestedPic2= findViewById(R.id. suggestedPic2);
        suggestedPic3= findViewById(R.id. suggestedPic3);
        suggestedPic4= findViewById(R.id. suggestedPic4);
        suggestedTitle1= findViewById(R.id.suggestedTitle1);
        suggestedTitle2= findViewById(R.id.suggestedTitle2);
        suggestedTitle3= findViewById(R.id.suggestedTitle3);
        suggestedTitle4= findViewById(R.id.suggestedTitle4);
        suggestedBarcode1= findViewById(R.id.suggestedBarcode1);
        suggestedBarcode2= findViewById(R.id.suggestedBarcode2);
        suggestedBarcode3= findViewById(R.id.suggestedBarcode3);
        suggestedBarcode4= findViewById(R.id.suggestedBarcode4);

        suggestedTitle1.setText("Spaghetti");
        suggestedTitle2.setText("Passata di Pomodoro");
        suggestedTitle3.setText("Parmigiano Reggiano");
        suggestedTitle4.setText("Chardonnay");

        suggestedBarcode1.setText("1111");
        suggestedBarcode2.setText("2222");
        suggestedBarcode3.setText("3333");
        suggestedBarcode4.setText("4444");

    }


}