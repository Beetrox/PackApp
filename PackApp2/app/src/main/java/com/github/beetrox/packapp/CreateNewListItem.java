package com.github.beetrox.packapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;

public class CreateNewListItem extends AppCompatActivity {


    EditText editListItemName;
    DatabaseReference itemRef;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_new_list_item);

        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        FirebaseDatabase database = FirebaseDatabase.getInstance();


        editListItemName = findViewById(R.id.editListItemName);
        itemRef = database.getReference("packingListItems");
    }

    public void addListItemButtonPressed(View view) {
        ListItem listItem = new ListItem(editListItemName.getText().toString(), "Poop");
        itemRef.child(listItem.getName().toLowerCase()).setValue(listItem);

        intent = new Intent(this, ShowListItems.class);

        startActivity(intent);
    }
}
