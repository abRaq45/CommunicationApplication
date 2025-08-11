package com.example.pbl;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editRecipient, editMessage;
    private TextView txtFileName;
    private Button btnAttach, btnSend, btnCall,btnChat;
    private static final int PICK_FILE_REQUEST_CODE = 100;
    private Uri selectedFileUri;  // For storing the selected file

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        editRecipient = findViewById(R.id.editRecipient);
        editMessage = findViewById(R.id.editMessage);
        txtFileName = findViewById(R.id.txtFileName);
        btnAttach = findViewById(R.id.btnAttach);
        btnSend = findViewById(R.id.btnSend);
        btnCall = findViewById(R.id.btnCall);

        // Handle Attach button
        btnAttach.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*");
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            startActivityForResult(Intent.createChooser(intent, "Select File"), PICK_FILE_REQUEST_CODE);
        });

        btnChat = findViewById(R.id.btnChat);

        btnChat.setOnClickListener(view -> {
            String recipient = editRecipient.getText().toString().trim();
            if (!recipient.isEmpty()) {
                Intent intent = new Intent(MainActivity.this, ChatActivity.class);
                intent.putExtra("recipientId", recipient);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Enter recipient to start chat", Toast.LENGTH_SHORT).show();
            }
        });

        // Handle Send button (Email/SMS)
        btnSend.setOnClickListener(view -> {
            String recipient = editRecipient.getText().toString().trim();
            String message = editMessage.getText().toString().trim();

            if (recipient.isEmpty()) {
                Toast.makeText(this, "Please enter a recipient", Toast.LENGTH_SHORT).show();
                return;
            }

            if (recipient.contains("@")) {
                sendEmail(recipient, message);
            } else {
                sendSMS(recipient, message);
            }
        });

        // Handle Call button
        btnCall.setOnClickListener(view -> {
            String recipient = editRecipient.getText().toString().trim();
            if (!recipient.isEmpty()) {
                makePhoneCall(recipient);
            } else {
                Toast.makeText(this, "Please enter a phone number", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Handle result from file picker
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_FILE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            selectedFileUri = data.getData();
            if (selectedFileUri != null) {
                String fileName = selectedFileUri.getLastPathSegment();
                txtFileName.setText(fileName != null ? fileName : "File Selected");
            } else {
                txtFileName.setText("File selection failed");
            }
        }
    }

    private void sendEmail(String email, String message) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
        intent.putExtra(Intent.EXTRA_TEXT, message);

        if (selectedFileUri != null) {
            intent.putExtra(Intent.EXTRA_STREAM, selectedFileUri);
        }

        try {
            startActivity(Intent.createChooser(intent, "Send Email"));
        } catch (Exception e) {
            Toast.makeText(this, "No email app found", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendSMS(String number, String message) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("smsto:" + number));
        intent.putExtra("sms_body", message);
        try {
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(this, "No SMS app found", Toast.LENGTH_SHORT).show();
        }
    }


    private void makePhoneCall(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        try {
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(this, "No phone app found", Toast.LENGTH_SHORT).show();
        }
    }
}
