package com.mobile.finalproject.viewHolder;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.finalproject.R;
import com.mobile.finalproject.interfaces.ItemClickListener;

public class ProductViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener
{

    public TextView txtProductName, txtProductDescription, txtProductPrice;
    public ImageView imageView;
    public ItemClickListener clickListener;

    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = (ImageView) itemView.findViewById(R.id.product_image);
        txtProductName = (TextView) itemView.findViewById(R.id.product_name);
        txtProductDescription = (TextView) itemView.findViewById(R.id.product_description);
        txtProductPrice = (TextView) itemView.findViewById(R.id.product_price);

        Log.i("ProductViewHolder", "Output: " + txtProductName + "  "  + txtProductDescription +
                "  " + "Price/hr = " + txtProductPrice + "$");
        
    }

    public void setItemClickListener(ItemClickListener listener){
        this.clickListener = listener;
    }

    @Override
    public void onClick(View view) {
        clickListener.onClick(view, getAdapterPosition(), false);
    }
}
