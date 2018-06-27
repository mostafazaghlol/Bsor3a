package com.mostafa.android.bsor3a.Recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mostafa.android.bsor3a.R;

import java.util.List;

/**
 * Created by mostafa on 6/27/18.
 */

public class ShippingAdapter extends RecyclerView.Adapter<ShippingViewHolder> {
    private List<Shipping> itemList;
    private Context context;

    public ShippingAdapter(List<Shipping> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public ShippingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        try {
            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shipping, null, false);
            RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutView.setLayoutParams(lp);
            ShippingViewHolder rcv = new ShippingViewHolder(layoutView);
            return rcv;
        } catch (Exception e) {
            Log.e("onCreateViewHolder", e.getMessage().toString());
        }
        return null;
    }

    @Override
    public void onBindViewHolder(ShippingViewHolder holder, int position) {
        try {
            holder.ShippingName.setText(itemList.get(position).getShippingName());
            String palce = itemList.get(position).getFrom() + "_" + itemList.get(position).getTo();
            holder.ShippingFromTo.setText(palce);
        } catch (Exception e) {
            Log.e("onBindViewHolder", e.getMessage().toString());
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}
