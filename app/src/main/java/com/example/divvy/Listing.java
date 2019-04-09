package com.example.divvy;

import android.os.Parcel;
import android.os.Parcelable;

class Listing {
    String listingName;
    String desc;
    int numGroup;
    int id;

    Listing(){
        listingName = "";
        desc = "No desc";
        numGroup = -1;
        id = -1;
    }
}
