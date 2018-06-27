package com.mostafa.android.bsor3a.Recycler;

/**
 * Created by mostafa on 6/27/18.
 */
public class Shipping {
    private String shippingName, From, To;

    public String getFrom() {
        return From;
    }

    public String getShippingName() {
        return shippingName;
    }

    public String getTo() {
        return To;
    }

    private Shipping() {
    }

    public Shipping(String mshippingName, String mfrom, String mto) {
        this.shippingName = mshippingName;
        this.From = mfrom;
        this.To = mto;
    }
}
