package com.mostafa.android.bsor3a.Recycler;

/**
 * Created by mostafa on 6/27/18.
 */
public class Shipping {
    private String From, To, id, name, code_order, id_client, creation_date, status, id_reciver, map_address_client, client_lat, client_lag, map_address_reciver, reciver_lag, reciver_lat, total_price, promo_id, delivery_time, space, rate_client, rate_delivery, date_delivery;

    public Shipping() {
    }
    public String getFrom() {
        return From;
    }

    public String Name() {
        return name;
    }

    public String getTo() {
        return To;
    }


    public Shipping(String mshippingName, String mfrom, String mto,
                    String mid, String mcode_order, String mid_client, String mcreation_date,
                    String mstatus, String mid_reciver, String mmap_address_client, String mclient_lat
            , String mclient_lag, String mmap_address_reciver, String mreciver_lag, String mreciver_lat
            , String mtotal_price, String mpromo_id, String mdelivery_time, String mspace, String mrate_client
            , String mrate_delivery, String mdate_delivery) {
        this.name = mshippingName;
        this.From = mfrom;
        this.To = mto;
        this.id = mid;
        this.code_order = mcode_order;
        this.id_client = mid_client;
        this.creation_date = mcreation_date;
        this.status = mstatus;
        this.id_reciver = mid_reciver;
        this.map_address_client = mmap_address_client;
        this.client_lat = mclient_lat;
        this.client_lag = mclient_lag;
        this.map_address_reciver = mmap_address_reciver;
        this.reciver_lat = mreciver_lat;
        this.reciver_lag = mreciver_lag;
        this.total_price = mtotal_price;
        this.promo_id = mpromo_id;
        this.delivery_time = mdelivery_time;
        this.space = mspace;
        this.rate_client = mrate_client;
        this.rate_delivery = mrate_delivery;
        this.date_delivery = mdate_delivery;
    }

    public String getId() {
        return id;
    }

    public String getClient_lag() {
        return client_lag;
    }

    public String getName() {
        return name;
    }

    public String getClient_lat() {
        return client_lat;
    }

    public String getCode_order() {
        return code_order;
    }

    public String getCreation_date() {
        return creation_date;
    }

    public String getDate_delivery() {
        return date_delivery;
    }

    public String getDelivery_time() {
        return delivery_time;
    }

    public String getId_client() {
        return id_client;
    }

    public String getId_reciver() {
        return id_reciver;
    }

    public String getMap_address_client() {
        return map_address_client;
    }

    public String getMap_address_reciver() {
        return map_address_reciver;
    }

    public String getPromo_id() {
        return promo_id;
    }

    public String getRate_client() {
        return rate_client;
    }

    public String getRate_delivery() {
        return rate_delivery;
    }

    public String getReciver_lag() {
        return reciver_lag;
    }

    public String getReciver_lat() {
        return reciver_lat;
    }

    public String getSpace() {
        return space;
    }

    public String getStatus() {
        return status;
    }

    public String getTotal_price() {
        return total_price;
    }

}
