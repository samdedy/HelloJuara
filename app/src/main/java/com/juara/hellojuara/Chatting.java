package com.juara.hellojuara;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.juara.hellojuara.adapter.AdapterListBasic;
import com.juara.hellojuara.adapter.AdapterListChat;
import com.juara.hellojuara.model.ChatModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Chatting extends AppCompatActivity {

    private DatabaseReference mDbFirebase;
    private AdapterListChat adapter;
    List<ChatModel> lstChat = new ArrayList<ChatModel>();
    RecyclerView rvChat;
    Button btnSendChat;
    EditText txtChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);
        mDbFirebase = FirebaseDatabase.getInstance().getReference();
        rvChat = findViewById(R.id.lstChat);
        btnSendChat = findViewById(R.id.btnSendChat);
        txtChat = findViewById(R.id.txtChat);

        loadChat();

        btnSendChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                E dd/MM/yyyy hh:mm:ss a
                Date dNow = new Date( );
                SimpleDateFormat ft = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
                sendChatToFirebase("sam", txtChat.getText().toString(), ft.format(dNow));
            }
        });
    }

    private void sendChatToFirebase(String username, String pesan, String timestamp){
        ChatModel chatModel = new ChatModel();
        chatModel.setNama(username);
        chatModel.setPesan(pesan);
        chatModel.setTimestamp(timestamp);

        mDbFirebase.child("chat").child(timestamp).setValue(chatModel);
        loadChat();
    }

    private void loadChat(){
        mDbFirebase.child("chat").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lstChat.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    ChatModel chat = postSnapshot.getValue(ChatModel.class);
                    lstChat.add(chat);
                }
                adapter = new AdapterListChat(Chatting.this, lstChat);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        rvChat.setLayoutManager(new LinearLayoutManager(Chatting.this));
                        rvChat.setItemAnimator(new DefaultItemAnimator());
                        rvChat.setAdapter(adapter);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}