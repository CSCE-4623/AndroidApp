package com.mobile.finalproject.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mobile.finalproject.MapsActivity;
import com.mobile.finalproject.R;
import com.mobile.finalproject.model.Transaction;
import com.mobile.finalproject.viewHolder.CartViewHolder;

import java.text.DecimalFormat;
import java.util.Iterator;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;

public class CartActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    RecyclerView recycler_itemlist;
    private RecyclerView.LayoutManager layoutManager;
    public static TextView tv_total;

    String jsonCartList;
    private Button btnCheckout;
    private TextView txtGrandTotal;
    private TextView txtTotal;
    private TextView txtTaxes;
    private DatabaseReference cartListRef;

    public Double taxes = 0.0 , total = 0.0 , grandTotal = 0.0;
    private static DecimalFormat roundTotal = new DecimalFormat("0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);


        //Set back button to activity
        androidx.appcompat.app.ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setTitle("Shopping Cart");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        txtGrandTotal =(TextView) findViewById(R.id.tv_grand_total);
        txtTotal = (TextView) findViewById(R.id.tv_total);
        txtTaxes = (TextView) findViewById(R.id.tv_taxes);
        btnCheckout = (Button) findViewById(R.id.btn_placeorder);

        recycler_itemlist =(RecyclerView) findViewById(R.id.recycler_cart);
        recycler_itemlist.setHasFixedSize(true);
        cartListRef = FirebaseDatabase.getInstance().getReference().child("transaction").child("User View").child("items");
       // recycler_itemlist.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));

        layoutManager = new LinearLayoutManager(this);
        recycler_itemlist.setLayoutManager(layoutManager);

     //   this.displaySidebar();
       // initializeOnClickListener();


    }



    @Override
    protected void onStart() {
        super.onStart();

//        CheckOrderState();




        FirebaseRecyclerOptions<Transaction> options =
                new FirebaseRecyclerOptions.Builder<Transaction>()
                        .setQuery(cartListRef, Transaction.class)
                        .build();

        FirebaseRecyclerAdapter<Transaction, CartViewHolder> adapter
                = new FirebaseRecyclerAdapter<Transaction, CartViewHolder>(options) {
            Transaction transaction_list;
            @Override
            protected void onBindViewHolder(@NonNull CartViewHolder cartViewHolder, int i, @NonNull Transaction transaction) {
                transaction_list = transaction;
                Log.i("Debug", "Transaction Name: " +transaction.getPname());


                handleClickListeners(cartViewHolder, transaction);


                setItemsCardView(cartViewHolder, transaction);


                getTotals(transaction);

            }
            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_cart, parent, false);
                CartViewHolder  holder = new CartViewHolder(view);
                return  holder;
            }
        };


        recycler_itemlist.setAdapter(adapter);
        adapter.startListening();
    }

    private void setItemsCardView(CartViewHolder cardView, Transaction transaction) {
        cardView.txtProductName.setText(transaction.getPname());
        cardView.txtProductSubTotal.setText("$ " + String.valueOf(transaction.getSubTotal()) );
        cardView.txtProductQuantity.setText(String.valueOf(transaction.getTransactionQty()));
    }

    private void getTotals(Transaction t) {

       // total = total +  t.getSubTotal();

        cartListRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot>items =dataSnapshot.getChildren().iterator();
                while (items.hasNext()){

                    DataSnapshot item = items.next();
                    Long total = Long.valueOf(item.child("subTotal").getValue().toString());
                    taxes = total * 0.0975;
                    taxes = Double.valueOf(roundTotal.format(taxes));
                    grandTotal = total + taxes;
                    txtTotal.setText(String.valueOf(total));
                    txtTaxes.setText(String.valueOf(taxes));
                    txtGrandTotal.setText(String.valueOf(grandTotal));


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });




    }

    private void handleClickListeners(final CartViewHolder cartViewHolder, final Transaction t) {

        //button to add products to database
        cartViewHolder.btnAddProductQty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cartListRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            if(t.getTransactionQty() >0) {
                                Long qty = t.getTransactionQty();
                                qty++;
//                                t.setTransactionQty(qty);
                                //update cart information
//                            cartViewHolder.txtProductQuantity.setText(String.valueOf(t.getTransactionQty()));
                                Long subtotal = t.getPrice() * qty;

//                            cartViewHolder.txtProductSubTotal.setText(String.valueOf(t.getSubTotal()));
                              //  setItemsCardView(cartViewHolder, t);
                                Long itemId = t.getItemID();

                                updateData(dataSnapshot, itemId, qty, subtotal);
                               // getTotals(t);
                            }
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });

        cartViewHolder.btnDeleteProductQty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartListRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Long itemId = t.getItemID();
                        dataSnapshot.getRef().child(Long.toString(itemId)).removeValue();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }
        });

        cartViewHolder.btnSubtractProductQty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i("Debug!!", "Yesss" + String.valueOf(t.getItemID()) );
                cartListRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            if(t.getTransactionQty() >0) {
                                Long qty = t.getTransactionQty();
                                qty--;

                                Long subtotal = t.getPrice() * qty;
                                Long itemId = t.getItemID();
                                updateData(dataSnapshot, itemId, qty, subtotal);

                                setItemsCardView(cartViewHolder, t);
                           //     getTotals(t);
                            }
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                 /*Log.i("Debug" ,"Subtract Product!!!");
                        cartListRef.child("transaction")
                                .child(String.valueOf(transaction.getItemID()))
                                .removeValue()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            //item removed

                                        }
                                    }
                                });*/

            }
        });


    }

    private void updateData(DataSnapshot dataSnapshot, Long itemId, Long qty, Long subtotal) {

        dataSnapshot.getRef().child(Long.toString(itemId)).child("transactionQty").setValue(qty);
        dataSnapshot.getRef().child(Long.toString(itemId)).child("subTotal").setValue(subtotal);
    }

    public void subtractQty(){

    }
    public Long addQty(long transactionQty){
        transactionQty++;
        return transactionQty;
    }
    public void deleteItem(){

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.

                this.finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }



    public void displaySidebar(){
        View v = getLayoutInflater().inflate(R.layout.activity_cart,null);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Shopping Cart");
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(CartActivity.this);

        //display user name in side drawer
        View headerView = navigationView.getHeaderView(0);
        TextView userNameTextView = headerView.findViewById(R.id.user_profile_name);
        CircleImageView profileImageView = headerView.findViewById(R.id.user_profile_image);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        // Handle navigation view item clicks here.
        int id = menuItem.getItemId();

        if (id == R.id.nav_cart)
        {
            Intent intent = new Intent(this, CartActivity.class);
            startActivity(intent);

        }
        else if (id == R.id.nav_orders)
        {

        }
        else if (id == R.id.nav_categories)
        {

        }
        else if (id == R.id.nav_settings)
        {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_logout)
        {
            Paper.book().destroy();
            //Send user to Login Screen
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // USING THIS METHOD TO TEST TRANSITION TO CONFIRM SCREEN FOR NOW - KEVIN
    public void insertOrder(View view){
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }
    public void updateValue(Long itemId, String pname, Long qty, Long subTotal ){
        Log.i("Debug", "item clicked  and qty is  " +  qty);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().
                getReference("transaction").child("User View").child("items").child(Long.toString(itemId));

        Transaction tran = new Transaction(itemId,pname,qty,subTotal);
        databaseReference.setValue(tran);

    }
}