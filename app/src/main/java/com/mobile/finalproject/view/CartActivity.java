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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mobile.finalproject.R;
import com.mobile.finalproject.model.Transaction;
import com.mobile.finalproject.viewHolder.CartViewHolder;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;

public class CartActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    RecyclerView recycler_itemlist;
    private RecyclerView.LayoutManager layoutManager;
    public static TextView tv_total;
    public static int total=0;
    String jsonCartList;
    private Button btnCheckout;
    private TextView txtTotal;
    private TextView txtSubTotal;
    private TextView txtTaxes;
    private DatabaseReference cartListRef;
    Transaction transaction;

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
        txtTotal =(TextView) findViewById(R.id.tv_total);
        txtSubTotal = (TextView) findViewById(R.id.tv_subtotal);
        txtTaxes = (TextView) findViewById(R.id.tv_taxes);
        btnCheckout = (Button) findViewById(R.id.btn_placeorder);

        recycler_itemlist =(RecyclerView) findViewById(R.id.recycler_cart);
        recycler_itemlist.setHasFixedSize(true);
       // recycler_itemlist.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));

        layoutManager = new LinearLayoutManager(this);
        recycler_itemlist.setLayoutManager(layoutManager);

     //   this.displaySidebar();

    }

    @Override
    protected void onStart() {
        super.onStart();

//        CheckOrderState();


        cartListRef = FirebaseDatabase.getInstance().getReference().child("transaction");

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

                cartViewHolder.txtProductName.setText(transaction.getPname());
                cartViewHolder.txtProductSubTotal.setText(String.valueOf(transaction.getSubTotal()) + " $");
                cartViewHolder.txtProductQuantity.setText(String.valueOf(transaction.getTransactionQty()));
/*
                cartViewHolder.txtProductName.setText("Name");
                cartViewHolder.txtProductSubTotal.setText("0.0" + " $");
                cartViewHolder.txtProductQuantity.setText("0 units");*/
            }
            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_cart, parent, false);
                CartViewHolder  holder = new CartViewHolder(view, transaction_list);
                return  holder;
            }
        };

        recycler_itemlist.setAdapter(adapter);
        adapter.startListening();
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

    private void getIntentData(){
        if(getIntent()!=null && getIntent().getExtras()!=null){
            // Get the Required Parameters for sending Order to server.
        }
    }

   /* public static void calculateTotal(){
        int i=0;
        total=0;
        while(i<ItemListAdapter.selecteditems.size()){
            total=total + ( Integer.valueOf(ItemListAdapter.selecteditems.get(i).getRate()) * Integer.valueOf(ItemListAdapter.selecteditems.get(i).getQuantity()) );
            i++;
        }
        tv_total.setText(""+total);
    }

    public void insertOrder(View view){

        if(total>0){

            Gson gson = new Gson();
            jsonCartList = gson.toJson(ItemListAdapter.selecteditems);

            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            //Yes button clicked
                            placeOrderRequest();
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            //No button clicked
                            break;
                    }
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
            builder.setMessage("Do you want to place Order ?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();

        }else{
            Toast.makeText(CartActivity.this,"No items in Cart !",Toast.LENGTH_LONG).show();
        }


    }
*/

    private void placeOrderRequest(){
        //Send Request to Server with required Parameters
    /*
   jsonCartList - Consists of Objects of all product selected.
    */

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
}