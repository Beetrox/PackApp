package com.github.beetrox.packapp;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ShowListItems extends FragmentActivity {

    public static final String TAG = "ListItem";
    DatabaseReference itemRef;
    List<ListItem> listItems;
    ListItemRecyclerAdapter listItemRecyclerAdapter;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_list_items);

        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        RecyclerView packingListRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewShowListItems);

        packingListRecyclerView.setLayoutManager(linearLayoutManager);

        listItems = new ArrayList<ListItem>();
        listItemRecyclerAdapter = new ListItemRecyclerAdapter(listItems);
        packingListRecyclerView.setAdapter(listItemRecyclerAdapter);
//        ViewPager myViewPager = findViewById(R.id.viewpager);

        setUpDatabase("electronics");

        final ActionBar actionBar = getActionBar();

        // Specify that tabs should be displayed in the action bar.
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create a tab listener that is called when the user changes tabs.
        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                // show the given tab
                String currentCategory = tab.getText().toString().toLowerCase();

                setUpDatabase(currentCategory);
            }

            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
                // hide the given tab
            }

            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
                // probably ignore this event
            }
        };

        // Add 3 tabs, specifying the tab's text and TabListener
        String[] categories = getCategories();

        for (int i = 0; i < categories.length; i++) {
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(categories[i])
                            .setTabListener(tabListener));
        }

//        myViewPager.setOnPageChangeListener(
//                new ViewPager.SimpleOnPageChangeListener() {
//                    @Override
//                    public void onPageSelected(int position) {
//                        // When swiping between pages, select the
//                        // corresponding tab.
//                        getActionBar().setSelectedNavigationItem(position);
//                    }
//                });
    }

    private String[] getCategories() {
        String[] categories;
        categories = new String[]{
                getText(R.string.categoryAll).toString(),
                getText(R.string.categoryClothes).toString(),
                getText(R.string.categoryToiletries).toString(),
                getText(R.string.categoryElectronics).toString(),
                getText(R.string.categoryMiscellaneous).toString()};

        return categories;
    }

    public void floatingActionButtonAddListItem(View view) {

        Intent intent;

        intent = new Intent(this, CreateNewListItem.class);

        startActivity(intent);
    }

    private void setUpDatabase(final String category) {

        if (category.equals("all")) {
            itemRef = database.getReference().child("packingLists").child("göteborg").child("categories");
        } else {
            itemRef = database.getReference().child("packingLists").child("göteborg").child("categories").child(category);
        }

        itemRef.keepSynced(true);

        itemRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int noll = 0;
                listItems.clear();

                if (category.equals("all")) {

                    for (DataSnapshot category : dataSnapshot.getChildren()) {

                        for (DataSnapshot dataSnapshot1 : category.getChildren()) {

                            ListItem listItem = dataSnapshot1.getValue(ListItem.class);
                            //PackingList packingList = new PackingList();
                            //String name1 = value.getName();
                            //packingList.setName(name1);
                            listItems.add(listItem);
                            listItemRecyclerAdapter.notifyDataSetChanged();

                        }
                    }

                } else {

                    //packingLists = new ArrayList<PackingList>();
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                        ListItem listItem = dataSnapshot1.getValue(ListItem.class);
                        //PackingList packingList = new PackingList();
                        //String name1 = value.getName();
                        //packingList.setName(name1);
                        listItems.add(listItem);
                        listItemRecyclerAdapter.notifyDataSetChanged();

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "Failed to read value.", databaseError.toException());
            }
        });
    }

    public void listItemPressed(View view) {
        Log.d(TAG, "Pressed");

        TextView textView = (TextView) view.findViewById(R.id.listItemName);
        String name = textView.getText().toString().toLowerCase();

        itemRef.child(name).child("status").setValue("yellow");



//        int newColor;
//        int oldColor = Color.TRANSPARENT;
//        Drawable background = view.getBackground();
//        if (background instanceof ColorDrawable)
//            oldColor = ((ColorDrawable) background).getColor();
//
//        if (oldColor == getResources().getColor(R.color.redStatus)) {
//            newColor = getResources().getColor(R.color.yellowStatus);
//        } else {
//            newColor = getResources().getColor(R.color.greenStatus);
//        }
//
//        view.setBackgroundColor(newColor);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, view, menuInfo);

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.list_item_context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()) {
            case R.id.delete_list_item:
                int position = info.position;
                ListItem listItem = listItems.get(position);
                String listItemName = listItem.getName();
//                String itemName = item.toString();
                itemRef.child(listItemName.toLowerCase()).removeValue();
                listItemRecyclerAdapter.notifyDataSetChanged();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}
