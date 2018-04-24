package com.github.beetrox.packapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CreateNewPackingList extends AppCompatActivity {

    EditText editPackingListName;
    DatabaseReference itemRef;
    Intent intent;

    FirebaseAuth auth = FirebaseAuth.getInstance();

    String userId = auth.getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_new_packing_list);

        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        editPackingListName = findViewById(R.id.editPackingListName);
        itemRef = database.getReference().child(userId).child("packingLists");


    }

    public void addPackingListButtonPressed(View view) {
//        save to database then subscribe to that from main activity
        PackingList packingList = new PackingList(editPackingListName.getText().toString());
        itemRef.child(packingList.getName().toLowerCase()).setValue(packingList);

        intent = new Intent(this, MainActivity.class);

        startActivity(intent);
    }

    @Override
    public boolean onNavigateUp(){


        intent = new Intent(this, MainActivity.class);

        startActivity(intent);

        return true;
    }
}
