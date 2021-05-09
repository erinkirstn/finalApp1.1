package com.example.finalapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class calendarActivity extends AppCompatActivity {
    private static final String KEY_DATE = "date";
    private TextView editDate;



    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference noteRef;
    public String currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        String docPath = intent.getStringExtra("MyLog");
        Log.d("docPath", docPath);

        noteRef = db.collection("Notebook").document(docPath);
        currentUser =(String) getIntent().getStringExtra("currentUser");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        CalendarView calendarView = findViewById(R.id.eventCalendarDate);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String date = (month + 1) +"/" + dayOfMonth + "/" + year;
                noteRef.update(KEY_DATE, date)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText( calendarActivity.this, "Date Added", Toast.LENGTH_SHORT).toString();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText( calendarActivity.this, "Error", Toast.LENGTH_SHORT).toString();
                            }
                        });

                Intent intent = new Intent(calendarActivity.this, addAssignmentActivity.class );

                intent.putExtra("dateString", date);
                intent.putExtra("currentUser", currentUser);

                startActivity(intent);

            }
        });

    }
}