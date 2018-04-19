package com.github.beetrox.packapp;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
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
        ListItem listItem = listItems.get(i);
        listItemViewHolder.viewName.setText(listItem.getName());
        listItemViewHolder.viewCategory.setText(listItem.getCategory());
        listItemViewHolder.viewStatus.setText(listItem.getStatus());
//        listItemViewHolder.vAmount.setText(li.getAmount());
//        listItemViewHolder.viewBackground;

        Resources resources = listItemViewHolder.itemView.getResources();
        String status = listItem.getStatus();
        String category = listItem.getCategory();

        if (status.equals("red")) {
            listItemViewHolder.itemView.setBackgroundColor(resources.getColor(R.color.redStatus));
        } else if (status.equals("yellow")) {
            listItemViewHolder.itemView.setBackgroundColor(resources.getColor(R.color.yellowStatus));
        } else if (status.equals("green")) {
            listItemViewHolder.itemView.setBackgroundColor(resources.getColor(R.color.greenStatus));
        }
    }

    @Override
    public ListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.list_item_card, parent, false);

//        Resources resources = itemView.getResources();
//        itemView.setBackgroundColor(resources.getColor(R.color.redStatus));

        return new ListItemViewHolder(itemView);
    }

    class ListItemViewHolder extends RecyclerView.ViewHolder {

        protected TextView viewName;
        protected TextView viewAmount;
        protected TextView viewCategory;
        protected TextView viewStatus;
        protected Drawable viewBackground;

        public ListItemViewHolder(View view) {
            //make constructor
            super(view);
            viewName = view.findViewById(R.id.listItemName);
            viewAmount = view.findViewById(R.id.listItemAmount);
            viewCategory = view.findViewById(R.id.listItemCategory);
            viewStatus = view.findViewById(R.id.listItemStatus);
            viewBackground = view.getBackground();
        }
    }

}
