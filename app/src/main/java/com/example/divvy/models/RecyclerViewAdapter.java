package com.example.divvy.models;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.divvy.Controllers.ImageSelector;
import com.example.divvy.R;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    private List list;

    public  class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView username;
        private TextView message;
        private ImageView image;



        public MyViewHolder(View view) {
            super(view);
            username = view.findViewById(R.id.username);
            message = view.findViewById(R.id.message);
            image = view.findViewById(R.id.imageView);

        }
    }
// in this adaper constructor we add the list of messages as a parameter so that
// we will passe  it when making an instance of the adapter object in our activity



    public RecyclerViewAdapter(List list) {

        this.list = list;


    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message, parent, false);



        return new RecyclerViewAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewAdapter.MyViewHolder holder, final int position) {

        //binding the data from our Arr ayList of object to the item.xml using the viewholder
        Object item = this.list.get(position).getClass();
        if(item.equals(Message.class)){
            Message message = (Message)this.list.get(position);
            holder.username.setText(message.getSender());
            holder.message.setText(message.getMessage());
        }else if(item.getClass().equals(ImageMessage.class)){
            ImageMessage message = (ImageMessage) this.list.get(position);
            holder.username.setText(message.getSender());
            holder.message.setText(message.getMessage());
            holder.image.setImageDrawable(ImageSelector.LoadImageFromWebOperations(message.getImage()));
        }


    }




}
