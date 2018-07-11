package com.mostafa.android.bsor3a.SpinnerSenderData;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mostafa.android.bsor3a.R;

import java.util.ArrayList;

/**
 * Created by mostafa salah zaghloul
 * you can reach me on 01148295140 on 7/8/18.
 */

public class GovernteAdapter extends ArrayAdapter<GovernatItem> {
    public GovernteAdapter(Context context, ArrayList<GovernatItem> countryList) {
        super(context, 0, countryList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.governat_item, parent, false
            );
        }

        TextView textViewName = convertView.findViewById(R.id.text_view_name);

        GovernatItem currentItem = getItem(position);

        if (currentItem != null) {
            textViewName.setText(currentItem.getmGovernatName());
        }

        return convertView;
    }
}