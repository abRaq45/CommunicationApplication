package com.example.pbl;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {

    private EditText editMessage;
    private Button btnSend;
    private ListView chatListView;
    private ArrayList<String> messages;
    private ChatAdapter chatAdapter;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // Initialize UI components
        editMessage = findViewById(R.id.editMessage);
        btnSend = findViewById(R.id.btnSend);
        chatListView = findViewById(R.id.chatListView);

        messages = new ArrayList<>();
        chatAdapter = new ChatAdapter(this, messages);
        chatListView.setAdapter(chatAdapter);

        // Get a reference to the Firebase Realtime Database
        databaseReference = FirebaseDatabase.getInstance().getReference("chats");

        // Fetch existing chat messages from Firebase
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                messages.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String message = snapshot.getValue(String.class);
                    messages.add(message);
                }
                chatAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ChatActivity.this, "Failed to load messages.", Toast.LENGTH_SHORT).show();
            }
        });

        // Send message when the "Send" button is clicked
        btnSend.setOnClickListener(view -> {
            String message = editMessage.getText().toString().trim();
            if (!message.isEmpty()) {
                sendMessage(message);
                editMessage.setText(""); // Clear the input field
            }
        });
    }

    // Send a message to Firebase Realtime Database
    private void sendMessage(String message) {
        String messageId = databaseReference.push().getKey(); // Generate a unique ID for the message
        if (messageId != null) {
            databaseReference.child(messageId).setValue(message);
        }
    }
}
