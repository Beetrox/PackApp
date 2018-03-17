package com.github.beetrox.packapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Owner on 09/03/2018.
 */

public class ListItemRecyclerAdapter extends RecyclerView.Adapter<ListItemRecyclerAdapter.ListItemViewHolder> {

    private List<ListItem> listItems;


    public ListItemRecyclerAdapter(List<ListItem> listItems) {
        this.listItems = listItems;
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    @Override
    public void onBindViewHolder(ListItemViewHolder listItemViewHolder, int i) {
        ListItem li = listItems.get(i);
        listItemViewHolder.vName.setText(li.getName());
        //listItemViewHolder.vAmount.setText(li.getAmount());
    }

    @Override
    public ListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_card, parent, false);

        return new ListItemViewHolder(itemView);
    }

    class ListItemViewHolder extends RecyclerView.ViewHolder {

        protected TextView vName;
        protected TextView vAmount;

        public ListItemViewHolder(View v) {
            //make constructor
            super(v);
            vName = (TextView) v.findViewById(R.id.listItemName);
            vAmount = (TextView) v.findViewById(R.id.listItemAmount);
        }
    }

}
