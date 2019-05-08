package com.example.divvy.Controllers;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.divvy.Listing;
import com.example.divvy.R;

import java.util.ArrayList;

public class ListingListAdapter extends RecyclerView.Adapter<ListingListAdapter.ListingListViewHolder> {
    private ArrayList<Listing> listings;

    public ListingListAdapter(ArrayList<Listing> listings){
        this.listings = listings;
        System.out.println(listings.toString());
    }
    @NonNull
    @Override
    public ListingListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.fragment_listing,viewGroup,false);
        ListingListViewHolder listViewHolder = new ListingListViewHolder(view);
        return listViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListingListViewHolder listingListViewHolder, int position) {
        listingListViewHolder.owner.setText("Owner!");
        // listingListViewHolder.owner.setText(listings.get(position).getOwner().getFullName());
        listingListViewHolder.title.setText(listings.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return listings.size();
    }

    public class ListingListViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView owner;

        public ListingListViewHolder(@NonNull View listing) {
            super(listing);
            title = listing.findViewById(R.id.listing_frag_title);
            owner = listing.findViewById(R.id.listing_frag_owner);
        }
    }
}
