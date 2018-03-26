package com.github.beetrox.packapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Intent intent;
    DatabaseReference itemRef;
    List<PackingList> packingLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView packingListRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        packingListRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        packingListRecyclerView.setLayoutManager(linearLayoutManager);

//        PackingListRecyclerAdapter packingListRecyclerAdapter = new PackingListRecyclerAdapter(createFakePackingLists());
//        packingListRecyclerView.setAdapter(packingListRecyclerAdapter);

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        itemRef = database.getReference().child("packimgLists");
        itemRef.keepSynced(true);

        itemRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                packingLists = new ArrayList<PackingList>();
                for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()) {

                    PackingList value = dataSnapshot1.getValue(PackingList.class);
                    PackingList packingList = new PackingList();
                    String name1 = value.getName();
                    packingList.setName(name1);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        //ListItemRecyclerAdapter listItemRecyclerAdapter = new ListItemRecyclerAdapter(createFakeListItems());
        //packingListRecyclerView.setAdapter(listItemRecyclerAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        PackingListRecyclerAdapter<PackingList, PackingListRecyclerAdapter.PackingListViewHolder> packingListRecyclerAdapter = new packingListRecyclerAdapter<PackingList, PackingListRecyclerAdapter.PackingListViewHolder>();
        @Override
                protected void populateViewHolder(packingListRecyclerAdapter.PackingListViewHolder, PackingList model, int position) {

        }
    }

    public void floatingActionButtonPressed(View view) {

        intent = new Intent(this, CreateNewPackingList.class);

        startActivity(intent);
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

    public List<PackingList> createFireBasePackingLists() {

        List<PackingList> packingLists = new ArrayList<PackingList>();



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
