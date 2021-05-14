package com.example.finalapp;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

public class AssignmentOptions extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference notebookRef = db.collection("Notebook");
    private DocumentReference noteRef = db.collection("Notebook").document("My First Note");
    private Button deleteButton;
    private Button startButton;
    private String transferData;
   private String currentUser;
    private String ddescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.option);
        Intent intent = getIntent();
        String title = intent.getStringExtra(MainActivity2.TITLE);
        String description = intent.getStringExtra(MainActivity2.DESCRIPTION);
        String date = intent.getStringExtra(MainActivity2.DATE);


        startButton = (Button) findViewById(R.id.startButton);
        deleteButton = (Button) findViewById(R.id.deleteButton);
        transferData = (String) getIntent().getStringExtra("TRANSFER_DATA");
        currentUser = (String) getIntent().getStringExtra("currentUser");
        ddescription = intent.getStringExtra(MainActivity2.DESCRIPTION);


        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                openTimer();
            }

        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                notebookRef.whereEqualTo("user",currentUser)
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

                                    if(description==MainActivity2.DESCRIPTION){
                                        documentSnapshot.getReference().delete();
                                    }
                                }


                            }
                        });
            }
        });
    }

    public void openTimer(){
        Intent intent = new Intent(this, timerActivity.class);
        intent.putExtra("currentUser", currentUser);
        startActivity(intent);
    }
}
