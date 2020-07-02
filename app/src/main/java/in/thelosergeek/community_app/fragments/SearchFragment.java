package in.thelosergeek.community_app.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import in.thelosergeek.community_app.R;
import in.thelosergeek.community_app.Models.UserModel;
import in.thelosergeek.community_app.Adapters.UsersAdapter;
import in.thelosergeek.community_app.ui.LoginActivity;


public class SearchFragment extends Fragment {
    RecyclerView recyclerView;
    UsersAdapter usersAdapter;
    List<UserModel> userList;
    FirebaseAuth firebaseAuth;


    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search,container,false);
        firebaseAuth = FirebaseAuth.getInstance();

        recyclerView = view.findViewById(R.id.search_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        userList =  new ArrayList<>();

        getAllUsers();

        return view;
    }
    private void UpdateUI() {
        Intent startIntent = new Intent(getActivity(), LoginActivity.class);
        startActivity(startIntent);
        getActivity().finish();
    }
    private void searchUsers(final String query) {
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userList.clear();
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    UserModel userModel = ds.getValue(UserModel.class);

                    if(!userModel.getUid().equals(firebaseUser.getUid())){
                        if(userModel.getName().toLowerCase().contains(query.toLowerCase()) ||
                                 userModel.getEmail().toLowerCase().contains(query.toLowerCase())){
                            userList.add(userModel);
                        }

                    }
                    usersAdapter = new UsersAdapter(getActivity(), userList);

                    usersAdapter.notifyDataSetChanged();

                    recyclerView.setAdapter(usersAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void getAllUsers() {

        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userList.clear();
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    UserModel userModel = ds.getValue(UserModel.class);

                    if(!userModel.getUid().equals(firebaseUser.getUid())){
                        userList.add(userModel);
                    }
                    usersAdapter = new UsersAdapter(getActivity(), userList);
                    recyclerView.setAdapter(usersAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.top_app_bar,menu);

        /*MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(!TextUtils.isEmpty(query.trim())){

                    searchUsers(query);
                }
                else
                {
                    getAllUsers();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });*/

        super.onCreateOptionsMenu(menu, inflater);
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.logout)
        {
         firebaseAuth.signOut();
            UpdateUI();
        }

        return super.onOptionsItemSelected(item);
    }
}