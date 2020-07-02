package in.thelosergeek.community_app.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import in.thelosergeek.community_app.Adapters.HomeAdapter;
import in.thelosergeek.community_app.HomeActivity;
import in.thelosergeek.community_app.MainActivity;
import in.thelosergeek.community_app.Models.ModelHome;
import in.thelosergeek.community_app.R;
import in.thelosergeek.community_app.ui.LoginActivity;
import in.thelosergeek.community_app.ui.ProfileActivity;

public class PostFragment extends Fragment {

    FirebaseAuth firebaseAuth;
    RecyclerView recyclerView;
    List<ModelHome> postlist;
    HomeAdapter homeAdapter;


    public PostFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post, container, false);
        firebaseAuth = FirebaseAuth.getInstance();
        recyclerView = view.findViewById(R.id.posts_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);

        recyclerView.setLayoutManager(linearLayoutManager);

        postlist = new ArrayList<>();

        loadPosts();
        FloatingActionButton newpost = view.findViewById(R.id.addpost);
        newpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HomeActivity.class);
                startActivity(intent);
            }
        });
        return view;

    }

    private void loadPosts() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Posts");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                postlist.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    ModelHome modelHome = ds.getValue(ModelHome.class);
                    postlist.add(modelHome);

                    homeAdapter = new HomeAdapter(getActivity(), postlist);
                    recyclerView.setAdapter(homeAdapter);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void searchPosts(final String searchQuery) {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Posts");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                postlist.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    ModelHome modelHome = ds.getValue(ModelHome.class);
                    if (modelHome.getpTitle().toLowerCase().contains(searchQuery.toLowerCase()) || modelHome.getpDescription().toLowerCase().contains(searchQuery.toLowerCase())) {
                        postlist.add(modelHome);
                    }

                    homeAdapter = new HomeAdapter(getActivity(), postlist);
                    recyclerView.setAdapter(homeAdapter);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //inflater.inflate(R.menu.menu_main, menu);


        //MenuItem item = menu.findItem(R.id.action_search);
       // SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);

      /* searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!TextUtils.isEmpty(query)) {
                    searchPosts(query);
                } else {
                    loadPosts();
                }
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                if (!TextUtils.isEmpty(newText)) {
                    searchPosts(newText);
                } else {
                    loadPosts();
                }
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);*/
    }

   /* @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

    }*/

    private void checkUserStatus() {
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();


        if(currentUser == null)
        {
            UpdateUI();

        }
    }

    private void UpdateUI() {
        Intent startIntent = new Intent(getActivity(), LoginActivity.class);
        startActivity(startIntent);
    }


}

