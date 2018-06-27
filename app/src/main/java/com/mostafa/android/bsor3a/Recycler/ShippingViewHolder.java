package com.mostafa.android.bsor3a.Recycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.mostafa.android.bsor3a.R;

/**
 * Created by mostafa on 6/27/18.
 */

public class ShippingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView ShippingName, ShippingFromTo;

    public ShippingViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        ShippingName = (TextView) itemView.findViewById(R.id.ShippingName);
        ShippingFromTo = (TextView) itemView.findViewById(R.id.ShippingFromTo);
    }

    @Override
    public void onClick(View view) {

    }
}
