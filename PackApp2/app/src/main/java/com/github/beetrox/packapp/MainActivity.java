package com.github.beetrox.packapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "Firebase";

    Intent intent;
    DatabaseReference itemRef;
    List<PackingList> packingLists;
    PackingListRecyclerAdapter packingListRecyclerAdapter;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static final int RC_SIGN_IN = 123;

    List<AuthUI.IdpConfig> providers = Arrays.asList(
            new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
            new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseAuth auth = FirebaseAuth.getInstance();

        String userId = auth.getUid();

        // Show firebase packing lists
        RecyclerView packingListRecyclerView = (RecyclerView) findViewById(R.id.packingListRecyclerView);
        CardView packingListCardView = findViewById(R.id.listItemCardView);
//        registerForContextMenu(packingListRecyclerView);
        packingListRecyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        packingListRecyclerView.setLayoutManager(linearLayoutManager);

        packingLists = createFakePackingLists();
        packingListRecyclerAdapter = new PackingListRecyclerAdapter(packingLists, this);
        packingListRecyclerView.setAdapter(packingListRecyclerAdapter);

        // Log in
        FirebaseUser user = auth.getCurrentUser();

        if (user != null) {
            setUpDataBase(userId);
        } else {
            // not signed in
            AuthUI.SignInIntentBuilder builder = AuthUI.getInstance().createSignInIntentBuilder();
            builder.setAvailableProviders(providers);
            startActivityForResult(builder.build(), RC_SIGN_IN);
        }
    }

    public void setUpDataBase(String userId) {
        // already signed in
        itemRef = database.getReference().child(userId).child("packingLists");
        itemRef.keepSynced(true);

        itemRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("Main", "onDataChange: ");

                packingLists.clear();

                //packingLists = new ArrayList<PackingList>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    PackingList packingList = dataSnapshot1.getValue(PackingList.class);
                    //PackingList packingList = new PackingList();
                    //String name1 = value.getName();
                    //packingList.setName(name1);
                    packingLists.add(packingList);
                }
                packingListRecyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "Failed to read value.", databaseError.toException());
            }
        });
    }

//    @Override
//    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {
//        super.onCreateContextMenu(menu, view, menuInfo);
//
//        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
//
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.packing_list_context_menu, menu);
//    }
//
//    @Override
//    public boolean onContextItemSelected(MenuItem item) {
//
//
//        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
//        View v = null;
//        if (info != null)
//            v = info.targetView;
//        else
//            Log.d("David", "onContextItemSelected: info null");
//
//       // TextView t = v.findViewById(R.id.listItemName);
//        if (v != null)
//            Log.d("David", "onContextItemSelected: " + v.getId() );
//        else
//            Log.d("David", "onContextItemSelected: null");
//
//
//
//
//        switch (item.getItemId()) {
//            case R.id.delete_packing_list:
//                int position = info.position;
//                PackingList packingList = packingLists.get(position);
//                String packingListName = packingList.getName();
////                String itemName = item.toString();
//                itemRef.child(packingListName.toLowerCase()).removeValue();
//                packingListRecyclerAdapter.notifyDataSetChanged();
//                return true;
//            default:
//                return super.onContextItemSelected(item);
//        }
//
////        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
////
////        switch (item.getItemId()) {
////            case R.id.delete_packing_list:
////                Log.d(TAG, item.toString());
////                int position = info.position;
////                PackingList packingList = packingLists.get(position);
////                String packingListName = packingList.getName();
//////                String itemName = item.toString();
////                itemRef.child(packingListName.toLowerCase()).removeValue();
////                packingListRecyclerAdapter.notifyDataSetChanged();
////                return true;
////            default:
////                return super.onContextItemSelected(item);
////        }
//
////        return super.onContextItemSelected(item);
//    }

    public void floatingActionButtonAddPackingList(View view) {

        intent = new Intent(this, CreateNewPackingList.class);

        startActivity(intent);
    }

    public void mainMenuPressed(View view) {
        //creating a popup menu
        PopupMenu popup = new PopupMenu(this, findViewById(R.id.mainMenuMainActivity));
        //inflating menu from xml resource
        popup.inflate(R.menu.main_menu);
        //adding click listener
        final MainActivity mainActivity = this;
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.signOutMenuOption:
                        //handle menu2 click
                        AuthUI.getInstance()
                                .signOut(mainActivity)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    public void onComplete(@NonNull Task<Void> task) {
                                        startMainActivity();
                                    }
                                });
                        break;
                }
                return false;
            }
        });
        //displaying the popup
        popup.show();
    }

    public void packingListPressed(View view) {
//        Log.d("Packing List", "Pressed");

        intent = new Intent(this, ShowListItems.class);

        TextView textViewName = (TextView) view.findViewById(R.id.packingListName);
        String packingListName = textViewName.getText().toString().toLowerCase();
        intent.putExtra("packingListName", packingListName);

        startActivity(intent);
    }

//    public void editPackingList(String name, String newName) {
//
//        intent = new Intent(this, EditPackingList.class);
//
//        intent.putExtra("packingListName", name);
//
//        startActivity(intent);
//    }

    public void deletePackingList(String name) {

        itemRef.child(name).removeValue();
        packingListRecyclerAdapter.notifyDataSetChanged();

        name = name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase();
        Toast.makeText(this, name + " " + getText(R.string.deleted), Toast.LENGTH_SHORT).show();
    }

    public static List<PackingList> createFakePackingLists() {

        List<PackingList> packingLists = new ArrayList<PackingList>();

        return packingLists;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                setUpDataBase(user.getUid());
                // ...
            } else {
                // Sign in failed, check response for error code
                // ...
            }
        }
    }

//    public void signOutUser(View view) {
//        AuthUI.getInstance()
//                .signOut(this)
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    public void onComplete(@NonNull Task<Void> task) {
//                        startMainActivity();
//                    }
//                });
//    }

    public void startMainActivity() {

        Intent intent;

        intent = new Intent(this, MainActivity.class);

        startActivity(intent);
    }
}
