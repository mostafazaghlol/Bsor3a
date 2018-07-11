package com.mostafa.android.bsor3a.SpinnerSenderData;

/**
 * Created by mostafa salah zaghloul
 * you can reach me on 01148295140 on 7/8/18.
 */

public class CityItem {
    private String mGovernatName, id;
    private int mFlagImage;

    public CityItem(String governat, int flagImage) {
        mGovernatName = governat;
        mFlagImage = flagImage;
    }

    public CityItem(String india) {
        mGovernatName = india;
    }

    public CityItem(String state_name, String mid) {
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
