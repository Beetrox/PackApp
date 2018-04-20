package com.github.beetrox.packapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;

public class CreateNewListItem extends AppCompatActivity {


    EditText editListItemName;
    Spinner editListItemCategory;
    DatabaseReference itemRef;
    Intent intent;
    String currentPackingListName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_new_list_item);

        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        FirebaseDatabase database = FirebaseDatabase.getInstance();


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            //get the value based on the key
            currentPackingListName = extras.getString("packingListName");
//            Log.d(TAG, value);
        }

        editListItemName = findViewById(R.id.editListItemName);
        editListItemCategory = findViewById(R.id.categorySpinner);
        itemRef = database.getReference("packingLists").child(currentPackingListName).child("categories");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, getCategories());

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editListItemCategory = (Spinner) findViewById(R.id.categorySpinner);
        editListItemCategory.setAdapter(adapter);
    }

    public void addListItemButtonPressed(View view) {

        String selected = editListItemCategory.getSelectedItem().toString();

        ListItem listItem = new ListItem(editListItemName.getText().toString(), selected.toLowerCase());
        itemRef.child(selected.toLowerCase()).child(listItem.getName().toLowerCase()).setValue(listItem);

        intent = new Intent(this, ShowListItems.class);

        intent.putExtra("packingListName", currentPackingListName);

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

    @Override
    public boolean onNavigateUp(){
        finish();
        return true;
    }
}
