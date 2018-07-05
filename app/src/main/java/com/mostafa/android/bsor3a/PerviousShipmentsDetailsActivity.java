package com.mostafa.android.bsor3a;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mostafa.android.bsor3a.Recycler.Shipping;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PerviousShipmentsDetailsActivity extends AppCompatActivity {
    @BindView(R.id.ShDate)
    TextView TxDate;
    @BindView(R.id.ShFrom)
    TextView TxFrom;
    @BindView(R.id.ShTo)
    TextView TxTo;
    @BindView(R.id.ShCost)
    TextView TxCost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pervious_shipments_details);
        setBar.setStatusBarColored(this);
        ButterKnife.bind(this);
        int id = getIntent().getIntExtra("id", 0);
        Toast.makeText(this, "" + String.valueOf(id), Toast.LENGTH_SHORT).show();
        Shipping shippingObject = PerviousShipmentsActivity.dataList.get(id);
        String Date = shippingObject.getDate_delivery();
        String From = shippingObject.getFrom();
        String To = shippingObject.getTo();
        String price = shippingObject.getTotal_price();
        if (Date.equals("null")) {
            Date = getString(R.string.Empty_Field);
        } else {
            if (Date.length() > 10) {
                Date = Date.substring(0, 10);
            }
        }
        if (From == null) {
            From = "Empty Field";
        } else {
            if (From.length() > 10) {
                From = From.substring(0, 10);
            }
        }
        if (To == null) {
            To = "Empty Field";
        } else {
            if (To.length() > 10) {
                To = To.substring(0, 10);
            }
        }
        TxDate.setText(Date);
        TxFrom.setText(From);
        TxTo.setText(To);
        TxCost.setText(price);
    }

    public void back(View view) {
        onBackPressed();
    }
}
