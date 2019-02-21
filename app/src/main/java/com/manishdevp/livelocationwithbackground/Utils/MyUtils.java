package com.manishdevp.livelocationwithbackground.Utils;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MyUtils {
    public static List<String> getFullAddress(double latitude, double longitude, Context context) {
        List<String> addressList = new ArrayList<>();
        Address locationAddress = getGeocodes(latitude, longitude, context);
        String fulladdress = "";

        if (locationAddress != null) {
            String address = locationAddress.getAddressLine(0);
            String city = locationAddress.getLocality();
            String state = locationAddress.getAdminArea();
            String country = locationAddress.getCountryName();
            String postalCode = locationAddress.getPostalCode();

            addressList.add(address);
            addressList.add(city);
            addressList.add(state);
            addressList.add(country);
            addressList.add(postalCode);

        }
        return addressList;
    }

    // get adddress
    public static Address getGeocodes(double latitude, double longitude, Context context) {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(context, Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
            return addresses.get(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
