package com.github.beetrox.packapp;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
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
    String currentPackingListName;
    String currentTab;
    Intent intent;

    FirebaseAuth auth = FirebaseAuth.getInstance();

    String userId = auth.getUid();
//    CardView listItemCardView = findViewById(R.id.listItemCardView);
//    RecyclerView listItemRecyclerView = (RecyclerView) findViewById(R.id.listItemRecyclerView);
//    registerForContextMenu(listItemRecyclerView);
//    listItemRecyclerView.setHasFixedSize(true);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_list_items);

        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        RecyclerView listItemRecyclerView = (RecyclerView) findViewById(R.id.listItemRecyclerView);

        listItemRecyclerView.setLayoutManager(linearLayoutManager);

        listItems = new ArrayList<ListItem>();
        listItemRecyclerAdapter = new ListItemRecyclerAdapter(listItems, this);
        listItemRecyclerView.setAdapter(listItemRecyclerAdapter);
//        ViewPager myViewPager = findViewById(R.id.viewpager);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            //get the value based on the key
            currentPackingListName = extras.getString("packingListName");
//            Log.d(TAG, value);
        }

//        setUpDatabase("electronics");

        final ActionBar actionBar = getActionBar();

        // Specify that tabs should be displayed in the action bar.
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create a tab listener that is called when the user changes tabs.
        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                // show the given tab
                currentTab = tab.getText().toString().toLowerCase();

                setUpDatabase(currentTab, currentPackingListName);
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

        intent.putExtra("packingListName", currentPackingListName);

        startActivity(intent);
    }

    private void setUpDatabase(final String category, String packingListName) {

        itemRef = database.getReference().child(userId).child("packingLists").child(packingListName).child("categories");

//        if (category.equals("all") || category.equals("alla")) {
//            itemRef = database.getReference().child(userId).child("packingLists").child(packingListName).child("categories");
//        } else {
//            itemRef = database.getReference().child(userId).child("packingLists").child(packingListName).child("categories").child(category);
//        }

        itemRef.keepSynced(true);

        itemRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                listItems.clear();

                if (category.equals("all") || category.equals("alla")) {
                    for (DataSnapshot category : dataSnapshot.getChildren()) {
                        for (DataSnapshot dataSnapshot1 : category.getChildren()) {

                            ListItem listItem = dataSnapshot1.getValue(ListItem.class);
                            //PackingList packingList = new PackingList();
                            //String name1 = value.getName();
                            //packingList.setName(name1);
                            listItems.add(listItem);
                        }
                    }

                } else {
                    //packingLists = new ArrayList<PackingList>();
                    DataSnapshot categorySnapshot = dataSnapshot.child(category);

                    for (DataSnapshot categoryItem : categorySnapshot.getChildren()) {

                        ListItem listItem = categoryItem.getValue(ListItem.class);
                        //PackingList packingList = new PackingList();
                        //String name1 = value.getName();
                        //packingList.setName(name1);
//                        if (listItem != null) {
                            listItems.add(listItem);
//                        } else {
//                            listItems.clear();
//                        }
                    }
                }
                listItemRecyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "Failed to read value.", databaseError.toException());
            }
        });
    }

    public void listItemPressed(View view) {
        Log.d(TAG, "Pressed");

        TextView textViewName = (TextView) view.findViewById(R.id.listItemName);
        TextView textViewCategory = view.findViewById(R.id.listItemCategory);
        TextView textViewStatus = view.findViewById(R.id.listItemStatus);
        String name = textViewName.getText().toString().toLowerCase();
        //find listItem category and status from listItems to use later
        String category = textViewCategory.getText().toString().toLowerCase();
        String status = textViewStatus.getText().toString().toLowerCase();
//        itemRef = database.getReference().child(userId).child("packingLists").child(currentPackingListName).child("categories").child(category);
//        String status = itemRef.child("göteborg").child(category).child(name).child("status").toString();
//        Resources resources = view.getResources();
        Log.d(TAG, status);

        if (status.equals("red")) {
            itemRef.child(category).child(name).child("status").setValue("yellow");
        } else if (status.equals("yellow")) {
            itemRef.child(category).child(name).child("status").setValue("green");
        }
    }


//    public void changeAmountButtonPressed() {
//
//        intent = new Intent(this, ChangeAmount.class);
//
//        startActivity(intent);
//    }

    public void resetListItem(String name, String category) {

        itemRef.child(category).child(name).child("status").setValue("red");

        name = name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase();
        Toast.makeText(this, name + " " + getText(R.string.status_reset), Toast.LENGTH_SHORT).show();

        listItemRecyclerAdapter.notifyDataSetChanged();
    }

    public void editListItem(String name, String category) {

        intent = new Intent(this, ChangeAmount.class);

        intent.putExtra("currentListItemName", name);
        intent.putExtra("currentListItemCategory", category);
        intent.putExtra("packingListName", currentPackingListName);

        startActivity(intent);
    }

    public void deleteListItem(String name, String category) {

        itemRef.child(category).child(name).removeValue();

        name = name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase();
        Toast.makeText(this, name + " " + getText(R.string.deleted), Toast.LENGTH_SHORT).show();

        listItemRecyclerAdapter.notifyDataSetChanged();
    }

//    @Override
//    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {
//        super.onCreateContextMenu(menu, view, menuInfo);
//
//        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
//
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.list_item_context_menu, menu);
//    }
//
//    @Override
//    public boolean onContextItemSelected(MenuItem item) {
//
//        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
//
//        switch (item.getItemId()) {
//            case R.id.delete_list_item:
//                int position = info.position;
//                ListItem listItem = listItems.get(position);
//                String listItemName = listItem.getName();
////                String itemName = item.toString();
//                itemRef.child(listItemName.toLowerCase()).removeValue();
//                listItemRecyclerAdapter.notifyDataSetChanged();
//                return true;
//            default:
//                return super.onContextItemSelected(item);
//        }
//    }

    @Override
    public boolean onNavigateUp(){

        intent = new Intent(this, MainActivity.class);

        startActivity(intent);

        return true;
    }
}
