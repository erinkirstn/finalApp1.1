package com.example.finalapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MainActivity2 extends AppCompatActivity {

    private Button button;
    private String transferData;
    private TextView textViewData;
    private String user;

    private static final String TAG = "addAssignmentActivity";

    private static final String KEY_TITLE = "title";
    private static final String KEY_DESCRIPTION = "description";

    private static final String KEY_DATE = "thisDate";

    private EditText editTextTitle;
    private EditText editTextDescription;

    /*
    Firestore tutorial I followed: Coding In Flow Firestore Tutorials
     */

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference notebookRef = db.collection("Notebook");
    private DocumentReference noteRef = db.collection("Notebook").document("My First Note");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        button = (Button) findViewById(R.id.addHomeworkButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                openActivity3();
            }
        });


        transferData = (String) getIntent().getStringExtra("TRANSFER_DATA");
        user = (String) getIntent().getStringExtra("currentUser");


        textViewData = findViewById(R.id.text_view_data);
        textViewData.setText(transferData);




            notebookRef.whereEqualTo("user",user)
                    .get()
                    // Query Snapshot = Multiple document snapshots
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            String data = "";

                            // for loop to iterate through the query snapshots
                            for (QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots) {
                                //For each  QueryDocumentSnapshot we will create a note object
                                Note note = documentSnapshot.toObject(Note.class);

                                // TO DO: Add each note to an arraylist?

                                String title = note.getTitle();
                                String description = note.getDescription();
                                String date = note.getDate();
                                String user = note.getUser();

                                data += "\nTitle: " + title + "\nDescription:" + description + "\nDate:" + date;
                            }
                            textViewData.setText(data);
                        }
                    });



    }



    //Override the main method to use the snapshot listener
    @Override
    protected void onStart() {
        super.onStart();
        // SnapshotListener allows for realtime updates as soon as something changes in the document
        /*noteRef.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
                if (e != null) {
                    Toast.makeText(MainActivity2.this, "Error while loading!", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, e.toString());
                    return; //Leaves the method if there is an exception because otherwise the app will crash
                }
                if (documentSnapshot.exists()) {
                    Note note = documentSnapshot.toObject(Note.class);

                    String title = note.getTitle();
                    String description = note.getDescription();
                    String date = note.getDate();

                    textViewData.setText("Title: " + title + "\n" + "Description: " + description +"\n" + "Due Date: " + date);

                }
                else {
                    textViewData.setText("");
                }
            }
        });

         */
    }

    //deletes the note when the delete assignment button is pressed
    public void deleteNote(View v) {
        noteRef.delete();
    }




    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();//logout
        startActivity(new Intent(getApplicationContext(),loginActivity.class));
        finish();
    }

    public void openActivity3() {
        Intent intent = new Intent(this, addAssignmentActivity.class);
        intent.putExtra("currentUser", user);
        startActivity(intent);
    }
}


