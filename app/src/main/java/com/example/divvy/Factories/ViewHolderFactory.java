package com.example.divvy.Factories;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.divvy.Controllers.DetailedListingController;
import com.example.divvy.Controllers.ImageSelector;
import com.example.divvy.Controllers.UserProfileActivity;
import com.example.divvy.ListingService;
import com.example.divvy.NetworkReceiver;
import com.example.divvy.R;
import com.example.divvy.models.Listing;
import com.example.divvy.models.Message;
import com.example.divvy.models.Review;


public class ViewHolderFactory {

    public static MyViewHolder create(Object object, ViewGroup parent){
        if(object.getClass().equals(Message.class)){
            return new MessageViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.message, parent, false));
        } else if(object.getClass().equals(Listing.class)){
            return new ListingListViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_listing, parent, false));
        }else if(object.getClass().equals(Review.class)){
            return new ReviewViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.review_view_holder, parent, false));
        }
        return null;
    }

    public static abstract class MyViewHolder extends RecyclerView.ViewHolder {

        MyViewHolder(View view) {
            super(view);

        }

        public abstract void setUpUi(Object o);
    }





    public static class MessageViewHolder extends MyViewHolder{
        private TextView message, username;
        private ImageView image;

        public MessageViewHolder(View view){
            super(view);
            message = view.findViewById(R.id.message);
            username = view.findViewById(R.id.username);
            this.image = view.findViewById(R.id.image);
        }

        @Override public void setUpUi(Object o){
            Message message = (Message) o;
            this.username.setText(message.getSender());
            this.message.setText(message.getMessage());
            this.image.setImageBitmap(ImageSelector.decodeImage(message.getImage()));
        }
    }

    public static class ListingListViewHolder extends MyViewHolder implements NetworkReceiver.DataReceiver {
        TextView title, owner, view_listing_text;
        NetworkReceiver mReceiver;
        long listing_id;
        public ListingListViewHolder(@NonNull View listing) {
            super(listing);
            mReceiver = new NetworkReceiver(null ,this);
            title = listing.findViewById(R.id.listing_frag_title);
            owner = listing.findViewById(R.id.listing_frag_owner);
            owner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), UserProfileActivity.class);
                    intent.putExtra("owner", owner.getText().toString());
                    v.getContext().startActivity(intent);
                }
            });
            view_listing_text = listing.findViewById(R.id.view_listing_txt_btn);
            view_listing_text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ListingService.GetListingById(v.getContext(),mReceiver,listing_id);
                }
            });
        }

        @Override public void setUpUi(Object o){
            Listing listing = (Listing) o;
            this.title.setText(listing.getTitle());
            this.owner.setText(listing.getOwner());
            listing_id = listing.getListingid();
        }
        // DataReceiver
        @Override
        public void onReceiveResult(int resultCode, Bundle resultData) {
            //Go to new activity
            Intent intent = new Intent(this.title.getContext(),DetailedListingController.class);
            intent.putExtra("data", resultData.getSerializable("data"));
            this.title.getContext().startActivity(intent);
        }
    }

    public static class ReviewViewHolder extends MyViewHolder{
        TextView username, date, comment;
        RatingBar ratingBar;

        public ReviewViewHolder(@NonNull View listing) {
            super(listing);
            username = listing.findViewById(R.id.review_username);
            date = listing.findViewById(R.id.date);
            ratingBar = listing.findViewById(R.id.rating_bar);
            comment = listing.findViewById(R.id.comment);
        }

        @Override public void setUpUi(Object o){
            Review review = (Review) o;
            this.username.setText(review.getOwner());
            this.date.setText(review.getDate());
            this.ratingBar.setRating((float)review.getRating());
            this.comment.setText(review.getComment());
        }

    }


}
