package com.github.beetrox.packapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class EditListItem extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_list_item);

        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onNavigateUp(){
        finish();
        return true;
    }
}
