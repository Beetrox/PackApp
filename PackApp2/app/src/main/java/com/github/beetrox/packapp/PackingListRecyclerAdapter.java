package com.github.beetrox.packapp;

import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Owner on 09/03/2018.
 */

public class PackingListRecyclerAdapter extends RecyclerView.Adapter<PackingListRecyclerAdapter.PackingListViewHolder> {

    private List<PackingList> packingLists;


    public PackingListRecyclerAdapter(List<PackingList> packingLists) {
        this.packingLists = packingLists;
    }

    @Override
    public int getItemCount() {
        return packingLists.size();
    }

    @Override
    public void onBindViewHolder(PackingListViewHolder packingListViewHolder, int i) {
        PackingList pl = packingLists.get(i);
        packingListViewHolder.vName.setText(pl.getName());
    }

    @Override
    public PackingListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.packing_list_card, parent, false);

        return new PackingListViewHolder(itemView);
    }

    class PackingListViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        protected TextView vName;

        public PackingListViewHolder(View v) {
            //make constructor
            super(v);
            vName = (TextView) v.findViewById(R.id.listItemName);
            v.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {

        }
    }
}
