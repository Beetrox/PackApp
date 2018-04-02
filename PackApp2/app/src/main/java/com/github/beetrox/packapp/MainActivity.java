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
import android.widget.ExpandableListView;

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
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            // already signed in
        } else {
            // not signed in
            AuthUI.SignInIntentBuilder builder = AuthUI.getInstance().createSignInIntentBuilder();
            builder.setAvailableProviders(providers);
            startActivityForResult(builder.build(), RC_SIGN_IN);
        }

        RecyclerView packingListRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        CardView packingListCardView = findViewById(R.id.packingListCard);
        registerForContextMenu(packingListRecyclerView);
        packingListRecyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        packingListRecyclerView.setLayoutManager(linearLayoutManager);

        packingLists = createFakePackingLists();
        packingListRecyclerAdapter = new PackingListRecyclerAdapter(packingLists);
        packingListRecyclerView.setAdapter(packingListRecyclerAdapter);

        itemRef = database.getReference().child("packingLists");
        itemRef.keepSynced(true);

        itemRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("Main", "onDataChange: ");

                packingLists.clear();

                //packingLists = new ArrayList<PackingList>();
                for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()) {

                    PackingList packingList = dataSnapshot1.getValue(PackingList.class);
                    //PackingList packingList = new PackingList();
                    //String name1 = value.getName();
                    //packingList.setName(name1);
                    packingLists.add(packingList);
                    packingListRecyclerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "Failed to read value.", databaseError.toException());
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.packing_list_context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
//        int index = info.position;
//        View view = info.targetView;

        switch (item.getItemId()) {
            case R.id.delete_packing_list:
                Log.d(TAG, item.toString());
                int position = info.position;
                PackingList packingList = packingLists.get(position);
                String packingListName = packingList.getName();
//                String itemName = item.toString();
                itemRef.child(packingListName.toLowerCase()).removeValue();
                packingListRecyclerAdapter.notifyDataSetChanged();
                return true;
            default:
                return super.onContextItemSelected(item);
        }

//        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
//
//        switch (item.getItemId()) {
//            case R.id.delete_packing_list:
//                Log.d(TAG, item.toString());
////                itemRef.child(info).removeValue();
//                packingListRecyclerAdapter.notifyDataSetChanged();
//                return true;
//            default:
//                return super.onContextItemSelected(item);
//        }

//        return super.onContextItemSelected(item);
    }

    public void floatingActionButtonPressed(View view) {

        intent = new Intent(this, CreateNewPackingList.class);

        startActivity(intent);
    }

    public void packingListPressed(View view) {
        Log.d("Packing List", "Pressed");
    }

    public List<PackingList> createFakePackingLists() {

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
                // ...
            } else {
                // Sign in failed, check response for error code
                // ...
            }
        }
    }

    public void signOutUser(View view) {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        startMainActivity();
                    }
                });
    }

    private void startMainActivity() {

        Intent intent;

        intent = new Intent(this, MainActivity.class);

        startActivity(intent);
    }
}
