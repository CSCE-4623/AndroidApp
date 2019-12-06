package com.mobile.finalproject.viewHolder;

import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mobile.finalproject.R;
import com.mobile.finalproject.interfaces.ItemClickListener;
import com.mobile.finalproject.model.Transaction;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtProductName, txtProductSubTotal, txtProductQuantity;
    public ImageButton btnAddProductQty, btnSubtractProductQty, btnDeleteProductQty;
    private ItemClickListener itemClickListener;
    private CartViewHolder.ClickListener mClickListener;
    private DatabaseReference cartListRef;
    public TextView txtTaxes,txtTotal, txtGrandTotal;

    //Interface to send callbacks...
    public interface ClickListener{
        public void onItemClick(View view, int position);
    }

    public CartViewHolder(View itemView, final Transaction transaction)
    {
        super(itemView);

        cartListRef = FirebaseDatabase.getInstance().getReference().child("transaction").child("User View").child("items");

        txtProductName = itemView.findViewById(R.id.item_name);
        txtProductSubTotal = itemView.findViewById(R.id.item_subtotal);
        txtProductQuantity = itemView.findViewById(R.id.item_quantity);
        btnDeleteProductQty = itemView.findViewById(R.id.btn_deleteProduct);
        btnAddProductQty  = itemView.findViewById(R.id.btn_addProductQty);
        btnSubtractProductQty = itemView.findViewById(R.id.btn_subtractProductQty);


        itemView.setOnClickListener(new View.OnClickListener() {
            //  int position = getAdapterPosition();
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.btn_addProductQty:

                        Log.i("Debug" , "Add Product!!!");

                        break;
                    case R.id.btn_deleteProduct:

                        Log.i("Debug" ,"Delete Product!!!");
                        break;
                    case R.id.btn_subtractProductQty:
                        Log.i("Debug" ,"Subtract Product!!!");
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
                                });

                        break;
                }
            }

        });



        /*btnDeleteProductQty.setOnClickListener(this);
        btnAddProductQty.setOnClickListener(this);
        btnSubtractProductQty.setOnClickListener(this);*/

    }

    @Override
    public void onClick(View view)
    {

        itemClickListener.onClick(view, getAdapterPosition(), false);
    }

    public void setItemClickListener(CartViewHolder.ClickListener itemClickListener)
    {
        this.mClickListener = itemClickListener;
    }


}
