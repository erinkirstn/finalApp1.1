package com.example.finalapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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

    private ImageButton button;

    private String transferData;
    private TextView textViewData;

    public String currentUser;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference notebookRef = db.collection("Notebook");
    private DocumentReference noteRef = db.collection("Notebook").document("My First Note");

    private LinearLayout dateLayout;
    private int buttonCount;
    private Button newbtn;



    public String color1 = "#80BACB";
    public String color2 = "#4D81AE";
    public String color3 = "#f4acb7";
    public String color4 = "#DEB1C8";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cal_view);


        button = (ImageButton) findViewById(R.id.calendarButton2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                openMainActivity();
            }
        });



        Intent intent = getIntent();
        currentUser = (String) getIntent().getStringExtra("currentUser");

        String title = intent.getStringExtra(MainActivity2.TITLE);
        String description = intent.getStringExtra(MainActivity2.DESCRIPTION);
        String date = intent.getStringExtra(MainActivity2.DATE);

        CalendarView calendarView = findViewById(R.id.eventCalendarDate2);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String date = (month + 1) +"/" + dayOfMonth + "/" + year;
                dateLayout = (LinearLayout) findViewById(R.id.dateDisplay);
                dateLayout.removeAllViews();

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
                                    doAddButton(title, description, date);
                                }
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
    public void optionsPage() {
        Intent intent = new Intent(this, AssignmentOptions.class);
        intent.putExtra("currentUser", currentUser);
        startActivity(intent);
    }
    private void doAddButton(String title, String description, String date) {
        newbtn = new Button(this);
        String s = "\nTitle: " + title + "\nDescription:" + description + "\nDate:" + date;
        newbtn.setText(s);
        dateLayout.addView(newbtn);

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
        newbtn.setTypeface(ResourcesCompat.getFont(this, R.font.circerounded));
        newbtn.setTextSize(20);
        buttonCount++;


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

        Intent intent = new Intent(this, AssignmentOptions.class);

        newbtn.setOnClickListener(clicks);


    }
    View.OnClickListener clicks = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            optionsPage();
        }
    };


