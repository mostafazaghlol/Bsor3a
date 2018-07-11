package com.mostafa.android.bsor3a.Recycler;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mostafa.android.bsor3a.MainActivity;
import com.mostafa.android.bsor3a.MainNavigationActivity;
import com.mostafa.android.bsor3a.PerviousShipmentsDetailsActivity;
import com.mostafa.android.bsor3a.R;

/**
 * Created by mostafa on 6/27/18.
 */

public class ShippingViewHolder extends RecyclerView.ViewHolder {

    public TextView ShippingName, ShippingFromTo;

    public ShippingViewHolder(View itemView) {
        super(itemView);
        ShippingName = (TextView) itemView.findViewById(R.id.ShippingName);
        ShippingFromTo = (TextView) itemView.findViewById(R.id.ShippingFromTo);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int postion = getAdapterPosition();
                Intent openDetails = new Intent(view.getContext(), PerviousShipmentsDetailsActivity.class);
                openDetails.putExtra("id", postion);
                openDetails.putExtra("idTag", MainActivity.NEWOROLD);
                view.getContext().startActivity(openDetails);
            }
        });
    }


}
