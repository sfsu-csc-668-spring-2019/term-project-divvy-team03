package com.example.divvy.models;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.divvy.R;

import java.util.List;

public class ChatBoxAdapter extends RecyclerView.Adapter<ChatBoxAdapter.MyViewHolder> {
    private List<Message> MessageList;

    public  class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView username;
        public TextView message;


        public MyViewHolder(View view) {
            super(view);

            username = (TextView) view.findViewById(R.id.username);
            message = (TextView) view.findViewById(R.id.message);

        }
    }
// in this adaper constructor we add the list of messages as a parameter so that
// we will passe  it when making an instance of the adapter object in our activity



    public ChatBoxAdapter(List<Message>MessagesList) {

        this.MessageList = MessagesList;


    }

    @Override
    public int getItemCount() {
        return MessageList.size();
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.messasge, parent, false);



        return new ChatBoxAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ChatBoxAdapter.MyViewHolder holder, final int position) {

        //binding the data from our ArrayList of object to the item.xml using the viewholder



        Message m = MessageList.get(position);
        holder.username.setText(m.getSender());
        holder.message.setText(m.getMessage());

    }




}
