package com.github.beetrox.packapp;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ShowListItems extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_list_items);

        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        final ActionBar actionBar = getActionBar();

        // Specify that tabs should be displayed in the action bar.
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create a tab listener that is called when the user changes tabs.
        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                // show the given tab
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

        FirebaseDatabase database = FirebaseDatabase.getInstance();
    }

    private String[] getCategories() {
        String[] categories;
        categories = new String[] {
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
}
