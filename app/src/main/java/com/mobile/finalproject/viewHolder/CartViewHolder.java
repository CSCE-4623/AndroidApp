package com.mobile.finalproject.viewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mobile.finalproject.R;
import com.mobile.finalproject.interfaces.ItemClickListener;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtProductName, txtProductSubTotal, txtProductQuantity;
    private ItemClickListener itemClickListener;


    public CartViewHolder(View itemView)
    {
        super(itemView);

        txtProductName = itemView.findViewById(R.id.item_name);
        txtProductSubTotal = itemView.findViewById(R.id.item_subtotal);
        txtProductQuantity = itemView.findViewById(R.id.item_quantity);
    }

    @Override
    public void onClick(View view)
    {
        itemClickListener.onClick(view, getAdapterPosition(), false);
    }

    public void setItemClickListener(ItemClickListener itemClickListener)
    {
        this.itemClickListener = itemClickListener;
    }
}
