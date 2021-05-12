package com.example.finalapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class calView extends AppCompatActivity {
    private static final String KEY_DATE = "date";
    private TextView editDate;

    private Button button;

    private String transferData;
    private TextView textViewData;

    public String currentUser;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference notebookRef = db.collection("Notebook");
    private DocumentReference noteRef = db.collection("Notebook").document("My First Note");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cal_view);


        button = (Button) findViewById(R.id.calendarButton2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                openMainActivity();
            }
        });

        Intent intent = getIntent();
        currentUser = (String) getIntent().getStringExtra("currentUser");

        textViewData = findViewById(R.id.text_view_data);

        CalendarView calendarView = findViewById(R.id.eventCalendarDate2);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String date = (month + 1) +"/" + dayOfMonth + "/" + year;

                notebookRef.whereEqualTo("user",currentUser).whereEqualTo("date", date)
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
        });
    }

    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity2.class);
        intent.putExtra("currentUser", currentUser);
        startActivity(intent);
    }


}