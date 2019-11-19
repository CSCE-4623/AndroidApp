package com.mobile.finalproject.view;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.finalproject.R;
import com.mobile.finalproject.model.Products;
import com.mobile.finalproject.model.Transaction;
import com.mobile.finalproject.viewHolder.ProductViewHolder;

import java.util.ArrayList;
import java.util.List;

/*import admin.employee.R;
import admin.employee.listeners.DeleteItemListener;
import admin.employee.listeners.QuantityListener;
import admin.employee.services.models.Item;*/


public class CartListAdapter extends RecyclerView.Adapter implements CartListInterface {
    public static List <Products> selecteditems;
    private List callListResponses = new ArrayList<>();
    final List templist=new ArrayList<>();
    private Activity context;
    int lastPosition=0;


    public CartListAdapter(Activity context, List callListResponses)
    {
        super();
        this.context = context;
        this.callListResponses=callListResponses;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.layout_item_cart, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }



    public void onBindViewHolder(final ProductViewHolder holder, int position) {
        final Transaction call = (Transaction) callListResponses.get(position);

       /* holder.itemname.setText(call.getItemID());
        holder.itemprice.setText(call.getPrice()+" $");
        holder.tv_quantity.setText(call.getQuantity());

        holder.cart_minus_img.setOnClickListener(new QuantityListener(context, holder.tv_quantity,call,false));
        holder.cart_plus_img.setOnClickListener(new QuantityListener(context, holder.tv_quantity,call,true));
        holder.img_deleteitem.setOnClickListener(new DeleteItemListener(context,call,this));*/
    }

    //Animating single element
    private void setAnimation(View viewToAnimate, int position)
    {
        if (position > lastPosition) {
           /* Animation animation = AnimationUtils.loadAnimation(context, R.anim.push_right_in);
            viewToAnimate.startAnimation(animation);
            lastPosition=position;*/
        }
        position++;
    }

    @Override
    public int getItemCount() {
        //Log.d("Size List:",String.valueOf(callListResponses.size()));
        if(callListResponses!=null){
            return callListResponses.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView itemdescription;
        private TextView itemprice,itemname, itemsize,tv_quantity;
        ImageView cart_minus_img, cart_plus_img,img_deleteitem;

        public ViewHolder(View itemView) {
            super(itemView);
            /*cart_minus_img=(ImageView) itemView.findViewById(R.id.cart_minus_img);
            cart_plus_img=(ImageView) itemView.findViewById(R.id.cart_plus_img);
            img_deleteitem=(ImageView) itemView.findViewById(R.id.img_deleteitem);
            itemname=(TextView) itemView.findViewById(R.id.itemname);
            itemprice=(TextView) itemView.findViewById(R.id.itemprice);
            itemsize=(TextView) itemView.findViewById(R.id.itemsize);
            tv_quantity=(TextView) itemView.findViewById(R.id.tv_quantity);*/

        }
    }



}

