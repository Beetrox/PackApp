package com.github.beetrox.packapp;

import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Owner on 09/03/2018.
 */

public class PackingListRecyclerAdapter extends RecyclerView.Adapter<PackingListRecyclerAdapter.PackingListViewHolder> {

    private List<PackingList> packingLists;
    private MainActivity mainActivity;


    public PackingListRecyclerAdapter(List<PackingList> packingLists, MainActivity mainActivity) {
        this.packingLists = packingLists;
        this.mainActivity = mainActivity;
    }

    @Override
    public int getItemCount() {
        return packingLists.size();
    }

    @Override
    public void onBindViewHolder(final PackingListViewHolder packingListViewHolder, int i) {
        final PackingList pl = packingLists.get(i);
        packingListViewHolder.viewName.setText(pl.getName());

        packingListViewHolder.viewMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //creating a popup menu
                PopupMenu popup = new PopupMenu(mainActivity, packingListViewHolder.viewMenu);
                //inflating menu from xml resource
                popup.inflate(R.menu.packing_list_popup_menu);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menuPackingListEdit:
                                //handle menu1 click
                                mainActivity.editPackingList(pl.getName().toLowerCase(), "New name");
                                break;
                            case R.id.menuPackingListDelete:
                                //handle menu2 click
                                mainActivity.deletePackingList(pl.getName().toLowerCase());
                                break;
                        }
                        return false;
                    }
                });
                //displaying the popup
                popup.show();
            }
        });
    }

    @Override
    public PackingListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.packing_list_card, parent, false);

        return new PackingListViewHolder(itemView);
    }

    class PackingListViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        protected TextView viewName;
        protected ImageButton viewMenu;

        public PackingListViewHolder(View view) {
            //make constructor
            super(view);
            viewName = (TextView) view.findViewById(R.id.packingListName);
            viewMenu = view.findViewById(R.id.packingListMenu);
//            v.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {

        }
    }
}
