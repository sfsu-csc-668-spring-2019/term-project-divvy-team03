package com.example.divvy.Factories;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.divvy.Controllers.ImageSelector;
import com.example.divvy.R;
import com.example.divvy.models.ImageMessage;
import com.example.divvy.models.Listing;
import com.example.divvy.models.Message;
import com.example.divvy.models.Review;


public class ViewHolderFactory {

    public static MyViewHolder create(Object object, ViewGroup parent){
        if(object.getClass().equals(Message.class)){
            return new MessageViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.message, parent, false));
        }else if(object.getClass().equals(ImageMessage.class)){
            return new ImageMessageViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.image_message, parent, false));
        }else if(object.getClass().equals(Listing.class)){
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


    public static class ImageMessageViewHolder extends MyViewHolder{
        private TextView message, username;
        private ImageView image;

        public ImageMessageViewHolder(View view){
            super(view);
            message = view.findViewById(R.id.message);
            username = view.findViewById(R.id.username);
            image = view.findViewById(R.id.imageView);
        }
        @Override public void setUpUi(Object imageMessage){
            ImageMessage message = (ImageMessage) imageMessage;
            this.username.setText(message.getSender());
            this.message.setText(message.getMessage());
            this.image.setImageDrawable(ImageSelector.LoadImageFromWebOperations(""));
        }
    }

    public static class MessageViewHolder extends MyViewHolder{
        private TextView message, username, view_listing_text;

        public MessageViewHolder(View view){
            super(view);
            message = view.findViewById(R.id.message);
            username = view.findViewById(R.id.username);
        }

        @Override public void setUpUi(Object o){
            Message message = (Message) o;
            this.username.setText(message.getSender());
            this.message.setText(message.getMessage());
        }
    }

    public static class ListingListViewHolder extends MyViewHolder {
        TextView title, owner;

        public ListingListViewHolder(@NonNull View listing) {
            super(listing);
            title = listing.findViewById(R.id.listing_frag_title);
            owner = listing.findViewById(R.id.listing_frag_owner);
        }

        @Override public void setUpUi(Object o){
            Listing listing = (Listing) o;
            this.title.setText(listing.getTitle());
            this.owner.setText(listing.getUsername());
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
