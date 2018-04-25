package com.github.beetrox.packapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChangeAmount extends AppCompatActivity {

    Intent intent;
    String listItemName;
    String listItemCategory;
    String packingListName;
    DatabaseReference itemRef;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    ListItemRecyclerAdapter listItemRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_amount);

//        getActionBar().setHomeButtonEnabled(true);
//        getActionBar().setDisplayHomeAsUpEnabled(true);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        String userId = auth.getUid();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            //get the value based on the key
            listItemName = extras.getString("currentListItemName");
            listItemCategory = extras.getString("currentListItemCategory");
            packingListName = extras.getString("packingListName");
//            Log.d(TAG, value);

            itemRef = database.getReference().child(userId).child("packingLists").child(packingListName).child("categories").child(listItemCategory);
        }
    }

    public void confirmChangeAmountButtonPressed(View view) {

        EditText changeListItemAmount = findViewById(R.id.changeListItemAmount);
        String newAmount = changeListItemAmount.getText().toString();

        if(newAmount.matches("")) {
            // editText is empty
            Toast.makeText(this, "Enter a number", Toast.LENGTH_SHORT).show();
            return;
        } else {
            // editText is not empty
            int newAmountInt = Integer.parseInt(newAmount);

            itemRef.child(listItemName).child("amount").setValue(newAmountInt);

//        listItemRecyclerAdapter.notifyDataSetChanged();

            intent = new Intent(this, ShowListItems.class);

            intent.putExtra("packingListName", packingListName);

            startActivity(intent);
        }
    }

    @Override
    public boolean onNavigateUp(){

        intent = new Intent(this, ShowListItems.class);

        intent.putExtra("packingListName", packingListName);

        startActivity(intent);

        return true;
    }
}