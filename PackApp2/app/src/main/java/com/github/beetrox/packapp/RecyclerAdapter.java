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

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    protected TextView vName;
    ViewGroup viewGroup;

    private List<PackingList> packingListList;


    public RecyclerAdapter(List<PackingList> packingList) {
        this.packingListList = packingList;
    }

    @Override
    public int getItemCount() {
        return packingListList.size();
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        PackingList pl = packingListList.get(i);
        myViewHolder.vName.setText(pl.name);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.packing_list_card, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(View v) {
            //make constructor
            super(v);
            vName = (TextView) v.findViewById(R.id.itemName);
        }
    }

}
