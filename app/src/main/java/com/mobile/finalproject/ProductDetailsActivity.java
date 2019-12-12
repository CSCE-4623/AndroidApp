package com.mobile.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mobile.finalproject.model.Products;
import com.mobile.finalproject.view.CartActivity;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ProductDetailsActivity extends AppCompatActivity {

    private FloatingActionButton addToCartBtn;
    private ImageView productImage;
    private ElegantNumberButton numberButton;
    private TextView productPrice, productDescription, productName;
    private Button cartButton;

    private String productID = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        Intent intent = getIntent();
        productID = intent.getStringExtra(HomeActivity.ITEM_ID);
        addToCartBtn = (FloatingActionButton) findViewById((R.id.cart_button));
        productImage = (ImageView) findViewById(R.id.product_image_details);
        numberButton = (ElegantNumberButton) findViewById(R.id.number_btn);
        productPrice = (TextView) findViewById(R.id.product_price_details);
        productDescription = (TextView) findViewById(R.id.product_description_details);
        productName = (TextView) findViewById(R.id.product_name_details);
        cartButton = (Button) findViewById(R.id.add_to_cart_button);
        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ProductDetailsActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });
        cartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addingToCartList();
                Intent intent = new Intent(ProductDetailsActivity.this, HomeActivity.class);
                startActivity(intent);
                //Toast.makeText(getApplicationContext(), "Product has been added", Toast.LENGTH_SHORT).show();
            }
        });
        getProductDetails(productID);

    }
    private void addingToCartList(){
        String saveCurrentTime, saveCurrentDate;
        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss, a");
        saveCurrentTime = currentTime.format(calForDate.getTime());
        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("transaction");
        final HashMap<String, Object> cartMap = new HashMap<>();
        cartMap.put("id",productID);
        cartMap.put("name",productName.getText().toString());
        cartMap.put("price",Long.valueOf (productPrice.getText().toString()));
        cartMap.put("description",productDescription.getText().toString());
        cartMap.put("quantity",Long.valueOf(numberButton.getNumber()));
        cartMap.put("date",saveCurrentDate);
        cartMap.put("time",saveCurrentTime);

        cartListRef.child("User View")./*child(Prevalent.currentOnlineUser.getPhone()).*/child("items")
                .child(productID)
                .updateChildren(cartMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            cartListRef.child("Admin View")./*child(Prevalent.currentOnlineUser.getPhone()).*/child("items")
                                    .child(productID)
                                    .updateChildren(cartMap)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                           if(task.isSuccessful()){
                                               Toast.makeText(ProductDetailsActivity.this, "Item Added to Cart", Toast.LENGTH_SHORT).show();
                                           }
                                        }
                                    });
                        }
                    }
                });

    }

    private void getProductDetails(String productID){
        DatabaseReference productsRef = FirebaseDatabase.getInstance().getReference().child("items");
        productsRef.child(productID).addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    Products products = dataSnapshot.getValue(Products.class);
                    productName.setText(products.getName());
                    productPrice.setText(String.valueOf(products.getPrice()));
                    productDescription.setText(products.getDescription());
                    Picasso.get().load(products.getFileLocation()).into(productImage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
