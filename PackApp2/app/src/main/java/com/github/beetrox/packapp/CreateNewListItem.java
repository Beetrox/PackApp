package com.github.beetrox.packapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;

public class CreateNewListItem extends AppCompatActivity {


    EditText editListItemName;
    Spinner spinner;
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
        itemRef = database.getReference("packingLists").child("göteborg").child("categories");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, getCategories());

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner = (Spinner) findViewById(R.id.categorySpinner);
        spinner.setAdapter(adapter);
    }

    public void addListItemButtonPressed(View view) {

        String selected = spinner.getSelectedItem().toString();

        ListItem listItem = new ListItem(editListItemName.getText().toString());
        itemRef.child(selected.toLowerCase()).child(listItem.getName().toLowerCase()).setValue(listItem);

        intent = new Intent(this, ShowListItems.class);

        startActivity(intent);
    }

    private String[] getCategories() {
        String[] categories;
        categories = new String[] {
                getText(R.string.categoryMiscellaneous).toString(),
                getText(R.string.categoryClothes).toString(),
                getText(R.string.categoryToiletries).toString(),
                getText(R.string.categoryElectronics).toString()};

        return categories;
    }
}
