package com.example.jose.androidkamis.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jose.androidkamis.Interface.ItemClickListener;
import com.example.jose.androidkamis.R;

/**
 * Created by josetrinidad on 28/02/18.
 */

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView product_name;
    public ImageView product_image;
    private ItemClickListener itemClickListener;

    public ProductViewHolder(View itemView) {
        super(itemView);

        product_name = itemView.findViewById(R.id.product_name);
        product_image = itemView.findViewById(R.id.product_image);
        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(), false);
    }
}
