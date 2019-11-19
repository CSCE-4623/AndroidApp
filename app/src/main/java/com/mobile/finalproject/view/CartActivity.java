package com.mobile.finalproject.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mobile.finalproject.R;
import com.mobile.finalproject.model.Transaction;
import com.mobile.finalproject.viewHolder.CartViewHolder;

public class CartActivity extends AppCompatActivity {

    RecyclerView recycler_itemlist;
    private RecyclerView.LayoutManager layoutManager;
    public static TextView tv_total;
    CartListAdapter cartListAdapter;
    public static int total=0;
    String jsonCartList;
    private Button btnCheckout;
    private TextView txtTotal;
    private TextView txtSubTotal;
    private TextView txtTaxes;

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


        //  recycler_itemlist.setRecycledViewPool(new RecyclerView.RecycledViewPool());
      //  recycler_itemlist.getRecycledViewPool().setMaxRecycledViews(0, 0);

//        cartListAdapter = new CartListAdapter(CartActivity.this,ProductListAdapter.selecteditems);
//        recycler_itemlist.setAdapter(cartListAdapter);

//        getIntentData();
//
//        calculateTotal();
    }

    @Override
    protected void onStart() {
        super.onStart();

//        CheckOrderState();


        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Transaction");

        FirebaseRecyclerOptions<Transaction> options =
                new FirebaseRecyclerOptions.Builder<Transaction>()
                        .setQuery(cartListRef, Transaction.class)
                        .build();

        FirebaseRecyclerAdapter<Transaction, CartViewHolder> adapter
                = new FirebaseRecyclerAdapter<Transaction, CartViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CartViewHolder cartViewHolder, int i, @NonNull Transaction transaction) {
                cartViewHolder.txtProductName.setText(transaction.getPname());
                cartViewHolder.txtProductSubTotal.setText(transaction.getSubTotal() + " $");
                cartViewHolder.txtProductQuantity.setText(transaction.getTransactionQty());
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

}