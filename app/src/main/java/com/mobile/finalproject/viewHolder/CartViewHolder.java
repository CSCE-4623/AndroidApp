package com.mobile.finalproject.viewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mobile.finalproject.R;
import com.mobile.finalproject.interfaces.ItemClickListener;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtProductName, txtProductSubTotal, txtProductQuantity;
    public ImageView btnAddProductQty, btnSubtractProductQty, btnDeleteProductQty;
    private ItemClickListener itemClickListener;
    private CartViewHolder.ClickListener mClickListener;
    private DatabaseReference cartListRef;
    public TextView txtTaxes,txtTotal, txtGrandTotal;
    public long itemID;

    //Interface to send callbacks...
    public interface ClickListener{
        public void onItemClick(View view, int position);

        void onDeleteClick(int position);
    }

    public CartViewHolder(View itemView)
    {
        super(itemView);

        txtProductName = itemView.findViewById(R.id.item_name);
        txtProductSubTotal = itemView.findViewById(R.id.item_subtotal);
        txtProductQuantity = itemView.findViewById(R.id.item_quantity);


        initializeOnClickListener(itemView);
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
    private void initializeOnClickListener(View itemView) {


        btnDeleteProductQty = itemView.findViewById(R.id.btn_deleteProduct);
        btnAddProductQty  = itemView.findViewById(R.id.btn_addProductQty);
        btnSubtractProductQty = itemView.findViewById(R.id.btn_subtractProductQty);
        cartListRef = FirebaseDatabase.getInstance().getReference().child("transaction").child("User View").child("items");


    }

}
