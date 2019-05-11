package com.example.divvy.models;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<ViewHolderFactory.MyViewHolder> {
    private List list;


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
    public ViewHolderFactory.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ViewHolderFactory.create(this.list.get(getItemCount() - 1), parent);
    }

    @Override
    public void onBindViewHolder(final ViewHolderFactory.MyViewHolder holder, final int position) {

        //binding the data from our Arr ayList of object to the item.xml using the viewholder
        holder.setUpUi(this.list.get(position));

    }




}
