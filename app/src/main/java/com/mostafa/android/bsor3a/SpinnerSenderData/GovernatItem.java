package com.mostafa.android.bsor3a.SpinnerSenderData;


import android.os.Parcel;

/**
 * Created by mostafa salah zaghloul
 * you can reach me on 01148295140 on 7/8/18.
 */

public class GovernatItem {
    private String mGovernatName, id;
    private int mFlagImage;

    public GovernatItem(String governat, int flagImage) {
        mGovernatName = governat;
        mFlagImage = flagImage;
    }

    public GovernatItem(String india) {
        mGovernatName = india;
    }

    public GovernatItem(String state_name, String mid) {
        mGovernatName = state_name;
        id = mid;

    }

    public String getId() {
        return id;
    }

    public int getmFlagImage() {
        return mFlagImage;
    }

    public String getmGovernatName() {
        return mGovernatName;
    }

}