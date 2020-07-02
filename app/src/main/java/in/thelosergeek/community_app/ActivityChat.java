package in.thelosergeek.community_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import in.thelosergeek.community_app.Adapters.ChatAdapter;
import in.thelosergeek.community_app.Models.ChatModel;
import in.thelosergeek.community_app.ui.ProfileActivity;

public class ActivityChat extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    ImageView profile;
    TextView nametv, last_seen;
    EditText messageet;
    ImageButton sendmessage;
    private FirebaseAuth mAuth;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    List<ChatModel> chatList;
    ChatAdapter chatAdapter;

    String senderUID;
    String receiverUID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("");
        recyclerView = findViewById(R.id.chat_recycleview);
        nametv = findViewById(R.id.nameTv);
        last_seen = findViewById(R.id.last_seen);
        messageet = findViewById(R.id.messageEt);
        sendmessage = findViewById(R.id.sendbtn);
        profile = findViewById(R.id.profileIv);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        Intent intent = getIntent();
        senderUID = intent.getStringExtra("senderUID");

        mAuth = FirebaseAuth.getInstance();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");


        Query query = databaseReference.orderByChild("uid").equalTo(senderUID);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren())
                {
                    String name = ""+ ds.child("name").getValue();
                    String image = "" + ds.child("image").getValue();

                    nametv.setText(name);
                    try {
                        Picasso.get().load(image).placeholder(R.drawable.defaultpic).into(profile);
                    }
                    catch (Exception e){
                        Picasso.get().load(R.drawable.defaultpic).into(profile);



                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        sendmessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = messageet.getText().toString().trim();

                if(TextUtils.isEmpty(message)){
                    Toast.makeText(ActivityChat.this,"Empty message",Toast.LENGTH_SHORT).show();
                }
                else{
                    SendMessage(message);

                }
            }
        });

        readMessages();

    }

    private void readMessages() {
        chatList = new ArrayList<>();
        DatabaseReference db =  FirebaseDatabase.getInstance().getReference("Chats");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                chatList.clear();
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    ChatModel chat = ds.getValue(ChatModel.class);
                    if(chat.getReceiver().equals(senderUID) && chat.getSender().equals(receiverUID)  ||
                         chat.getReceiver().equals(receiverUID) && chat.getSender().equals(senderUID)){
                        chatList.add(chat);
                    }
                    chatAdapter = new ChatAdapter(ActivityChat.this,chatList);
                    chatAdapter.notifyDataSetChanged();
                    recyclerView.setAdapter(chatAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void SendMessage(String message) {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", senderUID);
        hashMap.put("receiver", receiverUID);
        hashMap.put("message", message);
        databaseReference.child("Chats").push().setValue(hashMap);

        messageet.setText("");


    }

    private void checkUserStatus() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null) {

            receiverUID = currentUser.getUid();


        }
        else{
          startActivity(new Intent(this, MainActivity.class));
          finish();
    }}

    @Override
    protected void onStart() {
        checkUserStatus();
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.info,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if(id== R.id.info){
            startActivity(new Intent(ActivityChat.this, ProfileActivity.class));   // direct to user's profile
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}