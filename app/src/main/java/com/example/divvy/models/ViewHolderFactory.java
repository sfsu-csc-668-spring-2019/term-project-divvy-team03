package com.example.divvy.models;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.divvy.R;

import org.w3c.dom.Text;

import java.util.List;

public class ViewHolderFactory {

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
        }
        @Override public void setUpUi(Object imageMessage){

        }
    }

    public static class MessageViewHolder extends MyViewHolder{
        private TextView message, username;

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

    public static MyViewHolder create(Object object, ViewGroup parent){

        if(object.getClass().equals(Message.class)){
            return new MessageViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.message, parent, false));
        }else if(object.getClass().equals(ImageMessage.class)){
            return new MessageViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.message, parent, false));
        }
        return null;
    }
}
