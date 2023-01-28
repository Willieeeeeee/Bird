package edu.ucsd.cse110.team13.bof;

import static edu.ucsd.cse110.team13.bof.util.NearbyUtil.decodeMessage;
import static edu.ucsd.cse110.team13.bof.util.NearbyUtil.decodeProfile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import edu.ucsd.cse110.team13.bof.model.IProfile;
import edu.ucsd.cse110.team13.bof.model.database.ProfileWithCourses;
import edu.ucsd.cse110.team13.bof.model.database.RoomCourse;
import edu.ucsd.cse110.team13.bof.util.AppDatabaseUtil;
import edu.ucsd.cse110.team13.bof.util.NearbyUtil;
import edu.ucsd.cse110.team13.bof.util.UserProfileUtil;

public class StudentDetailActivity extends AppCompatActivity {
    /* keys for sending and receiving profile data within intent */
    public static final String UID_CODE = "uid",
                               IS_ACTIVE_CODE = "isActive";

    private ProfileWithCourses student;
    private boolean isFavorite, isWaved, isActive;
    private UserProfileUtil userProfileUtil;
    private AppDatabaseUtil appDatabaseUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_detail);

        userProfileUtil = new UserProfileUtil(this);
        appDatabaseUtil = new AppDatabaseUtil(this);

        /* Get profile info */
        Intent intent = getIntent();
        student = appDatabaseUtil.getProfile(intent.getStringExtra(UID_CODE));
        List<RoomCourse> commonClasses = new ArrayList<>();
        for(RoomCourse c : userProfileUtil.getUserProfile().courses) {
            if(student.courses.contains(c)) { commonClasses.add(c); }
        }
        student = new ProfileWithCourses(student.profile, commonClasses);

        /* Set menu bar */
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(student.getFirstName());

        /* receive profile data from intent */
        boolean isFavorite = student.isFavorite();
        boolean isWaved    = NearbyUtil.isWaved(student.getUid());
        boolean isActive   = intent.getBooleanExtra(IS_ACTIVE_CODE,false);

        /* Set waving hand */
        ImageView filled_wave = findViewById(R.id.waving_hand_filled);
        ImageView hollow_wave = findViewById(R.id.waving_hand_hollow);
        if(!isActive) {
            filled_wave.setVisibility(View.INVISIBLE);
            hollow_wave.setVisibility(View.INVISIBLE);
        } else if(isWaved){
            filled_wave.setVisibility(View.VISIBLE);
            hollow_wave.setVisibility(View.INVISIBLE);
        } else{
            filled_wave.setVisibility(View.INVISIBLE);
            hollow_wave.setVisibility(View.VISIBLE);
        }

        /* Set favorites star */
        ImageView filled_star = findViewById(R.id.filled_star_details);
        ImageView hollow_star = findViewById(R.id.hollow_star_details);
        if(isFavorite){
            filled_star.setVisibility(View.VISIBLE);
            hollow_star.setVisibility(View.INVISIBLE);
        }
        else{
            filled_star.setVisibility(View.INVISIBLE);
            hollow_star.setVisibility(View.VISIBLE);
        }

        /* Set headshot image */
        ImageView headshotView = findViewById(R.id.detail_student_headshot_view);
        Glide.with(headshotView.getContext())
                .load(student.getHeadshotUrl())
                .circleCrop()
                .placeholder(R.drawable.ic_default_headshot_50)
                .fallback(R.drawable.ic_default_headshot_50)
                .into(headshotView);

        /* Set common class list */
        RecyclerView classRecyclerView = findViewById(R.id.detail_student_common_classes_view);
        RecyclerView.LayoutManager classLayoutManager = new LinearLayoutManager(this);
        classRecyclerView.setLayoutManager(classLayoutManager);

        ClassViewAdapter classViewAdapter = new ClassViewAdapter(student.getClasses());
        classRecyclerView.setAdapter(classViewAdapter);
    }

    /* returns favorite and waveSent data in an intent */
    private void sendReturnIntent(){
//        Intent returnIntent = new Intent();
//
//        boolean favorite_status = student.isFavorite();
//        boolean sent_wave_status = student.getSentWaveTo();
//        String student_id = student.getFirstName(); // TODO: CHANGE getFirstName() to a method using UUID
//
//        returnIntent.putExtra(FAVORITE_CODE, favorite_status);
//        returnIntent.putExtra(WAVED_CODE, sent_wave_status);
//        returnIntent.putExtra("student_id", student_id);
//        setResult(MainActivity.CODE_FAVORITE_UPDATE, returnIntent);
//
//        Log.d("student_dump", "student in sendReturnIntent() :\n"
//                + "favorite bool: " + student.isFavorite() + "\n"
//                + "waved bool: " + student.getSentWaveTo() + "\n"
//                + "student id: " + student.getFirstName());
    }

    /* sends wave to another student */
    public void sendWave(View view) {
        /* set UI hand to filled */
        ImageView filled_hand = findViewById(R.id.waving_hand_filled);
        ImageView hollow_hand = findViewById(R.id.waving_hand_hollow);
        hollow_hand.setVisibility(View.INVISIBLE);
        filled_hand.setVisibility(View.VISIBLE);

        /* send Toast */
        Context context = getApplicationContext();
        CharSequence text = "wave sent";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

        /* send wave to student */
        NearbyUtil.waveTo(student.getUid());

        /* set and return data */
//        student.setSentWaveTo(true);
        sendReturnIntent();

        Log.d("sendWave()",  "sending wave to: " + student.getFirstName());
    }

    /* onClick listener of clicking the hollow_star
    *  adds a student to favorites */
    public void favorite(View view) {
        /* update UI */
        ImageView filled_star = findViewById(R.id.filled_star_details);
        ImageView hollow_star = findViewById(R.id.hollow_star_details);
        filled_star.setVisibility(View.VISIBLE);
        hollow_star.setVisibility(View.INVISIBLE);

        /* send toast */
        Context context = getApplicationContext();
        CharSequence text = "Saved to Favorites";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

        /* set and return data */
        appDatabaseUtil.setFavorite(student.getUid(),true);
//        student.setFavorite(true);
//        sendReturnIntent();

        Log.d("favorite()",  student.getFirstName() + " added to favorites");
    }

    /* onClick listener of clicking the filled_star
     *  adds a student to favorites */
    public void unfavorite(View view) {
        /* update UI */
        ImageView filled_star = findViewById(R.id.filled_star_details);
        ImageView hollow_star = findViewById(R.id.hollow_star_details);
        filled_star.setVisibility(View.INVISIBLE);
        hollow_star.setVisibility(View.VISIBLE);

        /* set and return data */
        appDatabaseUtil.setFavorite(student.getUid(),false);
//        student.setFavorite(false);
        Log.d("unfavorite()",  student.getFirstName() + " removed from favorites");
//        sendReturnIntent();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        finish(); // sends the intent onActivityResult
    }
}