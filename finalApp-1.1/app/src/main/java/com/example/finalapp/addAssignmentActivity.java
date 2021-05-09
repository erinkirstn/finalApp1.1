package com.example.finalapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class addAssignmentActivity extends AppCompatActivity {
    private static final String TAG = "addAssignmentActivity";

    private static final String KEY_TITLE = "title";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_DATE = "thisDate";

    private EditText editTextTitle;
    private EditText editTextDescription;
    private TextView editDate;

    private ListenerRegistration noteListener;


    /*
    Firestore tutorial I followed: Coding In Flow Firestore Tutorials
     */
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference notebookRef = db.collection("Notebook");
    private DocumentReference noteRef;


    private String dateString;
    private String currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assignment);

        Intent intent = getIntent();
        dateString = intent.getStringExtra("dateString");


        currentUser =(String) getIntent().getStringExtra("currentUser");


        editTextTitle = findViewById(R.id.edit_text_title);
        editTextDescription = findViewById(R.id.edit_text_description);
        editDate = findViewById(R.id.date);



        Button button = findViewById(R.id.addDueDateButton);
        Button button2 = findViewById(R.id.addAssignmentButton);

        // must use .toString() because exitTextTitle is an editable, not a string
        String title = "";
        String description = "";
        String date = "";


        //Constructs a new Note object from the Note java class
        final Note note = new Note(title, description, date, currentUser);


        final String id = notebookRef.document().getId();

            // Set the text box equal to the date selected in the calendar activity
            editDate.setText(dateString);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(addAssignmentActivity.this, calendarActivity.class);

                intent.putExtra("MyLog",id);
                intent.putExtra("currentUser", currentUser);
                Log.d("MyLog",id);
                startActivity(intent);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                note.setTitle(editTextTitle.getText().toString());
                note.setDate(editDate.getText().toString());
                note.setDescription(editTextDescription.getText().toString());
                note.setUser(currentUser);

                notebookRef.document(id).set(note);
                Log.d("addAssignment", "New doc added to firebase");

                Intent intent = new Intent(addAssignmentActivity.this, MainActivity2.class);
                intent.putExtra("currentUser", currentUser);
                startActivity(intent);
            }
        });


    }

    //Override the main method to use the snapshot listener
    @Override
    protected void onStart() {

        super.onStart();

        /*
        // SnapshotListener allows for realtime updates as soon as something changes in the document
        noteListener = noteRef.addSnapshotListener( new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
                if (e != null) {
                    Toast.makeText(addAssignmentActivity.this, "Error while loading!", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, e.toString());
                    return; //Leaves the method if there is an exception because otherwise the app will crash
                }
                if (documentSnapshot.exists()) {

                    Note note = documentSnapshot.toObject(Note.class);

                    String title = note.getTitle();
                    String description = note.getDescription();
                    String date = note.getDate();

                    editTextTitle.setText(title);
                    editTextDescription.setText(description);
                    editDate.setText(date);

                }
            }
        });
        */
    }






    public void addNote(View v) {

        // must use .toString() because exitTextTitle is an editable, not a string
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();
        String date = editDate.getText().toString();








    }







    }

