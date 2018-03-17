package com.github.beetrox.packapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView packingListRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        packingListRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        packingListRecyclerView.setLayoutManager(linearLayoutManager);

        PackingListRecyclerAdapter packingListRecyclerAdapter = new PackingListRecyclerAdapter(createFakePackingLists());
        packingListRecyclerView.setAdapter(packingListRecyclerAdapter);


        //ListItemRecyclerAdapter listItemRecyclerAdapter = new ListItemRecyclerAdapter(createFakeListItems());
        //packingListRecyclerView.setAdapter(listItemRecyclerAdapter);
    }

    public void floatingActionButtonPressed(View view) {
        Log.d("FAB", "Pressed");
    }

    public void packingListPressed(View view) {
        Log.d("Packing List", "Pressed");
    }

    public List<PackingList> createFakePackingLists() {

        List<PackingList> packingLists = new ArrayList<PackingList>();

        PackingList packingList1 = new PackingList("packing list 1");
        PackingList packingList2 = new PackingList("packing list 2");
        PackingList packingList3 = new PackingList("packing list 3");

        packingLists.add(packingList1);
        packingLists.add(packingList2);
        packingLists.add(packingList3);

        return packingLists;
    }

    /*
    public List<ListItem> createFakeListItems() {

        List<ListItem> listItems = new ArrayList<ListItem>();

        ListItem listItem1 = new ListItem("list item 1", 1);
        ListItem listItem2 = new ListItem("list item 2", 3);
        ListItem listItem3 = new ListItem("list item 3", 7);

        listItems.add(listItem1);
        listItems.add(listItem2);
        listItems.add(listItem3);

        return listItems;
    }
    */
}
