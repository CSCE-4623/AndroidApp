package com.mobile.finalproject;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.finalproject.model.Products;
import com.mobile.finalproject.viewHolder.ProductViewHolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductSearchAdapter extends RecyclerView.Adapter<ProductViewHolder>
{

    public Context context;
    public ArrayList<Products> arrayList;
    public ProductSearchAdapter(Context c, ArrayList<Products> arrayList){

        this.context =c;
        this.arrayList =arrayList;

    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.product_items_layout,parent,false);

        return new ProductViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {

        Products products = arrayList.get(position);

        //display data
        holder.txtProductName.setText(products.getName());
        holder.txtProductDescription.setText(products.getDescription());
        holder.txtProductPrice.setText("$" + products.getPrice()  + "/day");
        Picasso.get().load(products.getFileLocation()).into(holder.imageView);
        Log.i("HomeActivity", "Output: " + products.getName() + "  "  + products.getDescription() +
                "  " + "Price/hr = " + products.getPrice() + "$");

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
