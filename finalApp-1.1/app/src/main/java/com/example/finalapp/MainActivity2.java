package com.example.finalapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout;
import android.view.Gravity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;


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
    private Button calButton;
    private String transferData;
    private TextView textViewData;
    private String user;

    private static final String TAG = "addAssignmentActivity";

    private static final String KEY_TITLE = "title";
    private static final String KEY_DESCRIPTION = "description";

    private static final String KEY_DATE = "thisDate";

    private EditText editTextTitle;
    private EditText editTextDescription;
    private String color1 = "#80BACB";
    private String color2 = "#4D81AE";
    private String color3 = "#f4acb7";
    private String color4 = "#DEB1C8";

    /*
    Firestore tutorial I followed: Coding In Flow Firestore Tutorials
     */

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference notebookRef = db.collection("Notebook");
    private DocumentReference noteRef = db.collection("Notebook").document("My First Note");
    
    private LinearLayout assignmentLayout;
    private int buttonCount;
    Button newbtn;



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

        calButton = (Button) findViewById(R.id.calendarButton);
        calButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                openCalActivity();
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

                                data = "\nTitle: " + title + "\nDescription:" + description + "\nDate:" + date;
                                doAddButton(data);
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
    private void doAddButton(String s) {
        assignmentLayout = (LinearLayout) findViewById(R.id.assignmentLayout);

        newbtn = new Button(this);
        newbtn.setText(s);
        assignmentLayout.addView(newbtn);

        newbtn.setBackgroundResource(R.drawable.buttons);
        Drawable buttonDrawable = newbtn.getBackground();
        buttonDrawable = DrawableCompat.wrap(buttonDrawable);
        newbtn.setBackground(buttonDrawable);
        LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        buttonLayoutParams.setMargins(0, 0, 0, 40);
        newbtn.setLayoutParams(buttonLayoutParams);
        newbtn.setWidth(930);
        newbtn.setTransformationMethod(null);
        newbtn.setTextColor(Color.parseColor("#FFFFFF"));
        newbtn.setPadding(40,20,20,20);
        newbtn.setTextSize(20);
        buttonCount++;
        newbtn.setTypeface(ResourcesCompat.getFont(this, R.font.circerounded));


        DrawableCompat.setTint(buttonDrawable, Color.parseColor(color1));
        if((buttonCount % 4) == 0){
            DrawableCompat.setTint(buttonDrawable, Color.parseColor(color2));
        }
        else if((buttonCount % 2) == 0){
            DrawableCompat.setTint(buttonDrawable, Color.parseColor(color3));
        }
        else if((buttonCount % 3) == 0){
            DrawableCompat.setTint(buttonDrawable, Color.parseColor(color4));
        }
        newbtn.setGravity(Gravity.LEFT);

       //android:gravity="left"
    }


    public void openCalActivity() {
        Intent intent = new Intent(this, calView.class);
        intent.putExtra("currentUser", user);
        startActivity(intent);
    }
}


