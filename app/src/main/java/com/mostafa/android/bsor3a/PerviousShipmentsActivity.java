package com.mostafa.android.bsor3a;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mostafa.android.bsor3a.Recycler.Shipping;
import com.mostafa.android.bsor3a.Recycler.ShippingAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PerviousShipmentsActivity extends AppCompatActivity {
    @BindView(R.id.recycler_view)
    RecyclerView mHistoryRecyclerView;
    private RecyclerView.Adapter mHistoryAdapter;
    private RecyclerView.LayoutManager mHistoryLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pervious_shipments);
        setBar.setStatusBarColored(this);
        ButterKnife.bind(this);
        mHistoryRecyclerView.setNestedScrollingEnabled(true);
        mHistoryRecyclerView.setHasFixedSize(true);
        mHistoryLayoutManager = new LinearLayoutManager(PerviousShipmentsActivity.this);
        mHistoryRecyclerView.setLayoutManager(mHistoryLayoutManager);
        mHistoryAdapter = new ShippingAdapter(getDataSetHistory(), PerviousShipmentsActivity.this);
        mHistoryRecyclerView.setAdapter(mHistoryAdapter);
        mHistoryAdapter.notifyDataSetChanged();
    }


    public void back(View view) {
        onBackPressed();
    }

    public List<Shipping> getDataSetHistory() {
        List<Shipping> x = new ArrayList<>();
        x.add(new Shipping(getString(R.string.fackdata), getString(R.string.fackdata), getString(R.string.fackdata)));
        x.add(new Shipping(getString(R.string.fackdata), getString(R.string.fackdata), getString(R.string.fackdata)));
        x.add(new Shipping(getString(R.string.fackdata), getString(R.string.fackdata), getString(R.string.fackdata)));
        x.add(new Shipping(getString(R.string.fackdata), getString(R.string.fackdata), getString(R.string.fackdata)));
        return x;
    }
}
