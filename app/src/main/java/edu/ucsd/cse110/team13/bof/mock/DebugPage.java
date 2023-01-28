package edu.ucsd.cse110.team13.bof.mock;

import static edu.ucsd.cse110.team13.bof.util.NearbyUtil.encodeProfile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import edu.ucsd.cse110.team13.bof.MainActivity;
import edu.ucsd.cse110.team13.bof.R;
import edu.ucsd.cse110.team13.bof.model.ClassSize;
import edu.ucsd.cse110.team13.bof.StudentsViewAdapter;
import edu.ucsd.cse110.team13.bof.model.IClass;
import edu.ucsd.cse110.team13.bof.model.IProfile;
import edu.ucsd.cse110.team13.bof.model.Quarter;
import edu.ucsd.cse110.team13.bof.model.mClass;
import edu.ucsd.cse110.team13.bof.util.UserProfileUtil;

public class DebugPage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debug_page);

        receiveProfile();
    }

    // load the data to the UI
    private void receiveProfile(){
        UserProfileUtil userProfileUtil = new UserProfileUtil(this);
        String first_name;
        String headshot_url;
        List<IClass> class_list;
        ListView classesListView = findViewById(R.id.debug_classes);
        IProfile user_profile = userProfileUtil.getUserProfile();
        class_list = user_profile.getClasses();

        TextView nameView = findViewById(R.id.debug_name);
        TextView uidView = findViewById(R.id.debug_uid);
        TextView urlView = findViewById(R.id.debug_url);
        if(userProfileUtil.needFirstName()) { nameView.setText("No Name Found"); }
        else { nameView.setText(user_profile.getFirstName()); }
        if(userProfileUtil.needUid()) { uidView.setText("No UID Found"); }
        else uidView.setText(userProfileUtil.getUid());
        if(userProfileUtil.needHeadshotUrl()) { urlView.setText("No URL Found"); }
        else { urlView.setText(user_profile.getHeadshotUrl()); }

        List<String> string_class_list = new ArrayList<>();
        if(!userProfileUtil.needClasses()) {
            String class_string;
            for(IClass c : class_list) {
                class_string = String.format(Locale.getDefault(), "%s %s %s %d",
                        c.getSubject(),
                        c.getCourseNumber(),
                        c.getQuarter().getQuarterName(),
                        c.getYear());
                string_class_list.add(class_string);

            }
        }

        ArrayAdapter adapter = new ArrayAdapter<>(this,
        R.layout.class_row,
                R.id.class_row_text,
                string_class_list);
        classesListView.setAdapter(adapter);

        Log.i("BOD_Debug_Activity", "UID: " + userProfileUtil.getUid());
    }

    /*
     * Set user record to a hard-coded version (same as the one from MS2 delivery canvas page)
     * It only modifies user-related records and does not affect saved sessions
     */
    private void setProfile(){
        UserProfileUtil userProfileUtil = new UserProfileUtil(this);
        IClass c1 = new mClass(2021, Quarter.FA, ClassSize.SMALL, "CSE", "210");
        IClass c2 = new mClass(2022, Quarter.WI, ClassSize.LARGE, "CSE", "110");
        userProfileUtil.resetProfile();
        userProfileUtil.setFirstName("Bill");
        userProfileUtil.setHeadshotUrl("https://lh3.googleusercontent.com/pw/AM-JKLXQ2ix4dg-PzLrPOSMOOy6M3PSUrijov9jCLXs4IGSTwN73B4kr-F6Nti_4KsiUU8LzDSGPSWNKnFdKIPqCQ2dFTRbARsW76pevHPBzc51nceZDZrMPmDfAYyI4XNOnPrZarGlLLUZW9wal6j-z9uA6WQ=w854-h924-no?authuser=0");
        userProfileUtil.addClass(c1);
        userProfileUtil.addClass(c2);
        userProfileUtil.setClassesAsFinished();
        receiveProfile();
    }


    public void exitDebug(View view) {
        finish();
    }

    public void clearDatabase(View view) {
        UserProfileUtil userProfileUtil;
        userProfileUtil = new UserProfileUtil(this);
        userProfileUtil.resetDatabase();
        receiveProfile();
    }

    public void setProfileBtnOnClicked(View view) {
        setProfile();
    }

    public void receiveMockWave(View view) {
        TextView wavingNameView = findViewById(R.id.waverName);
        String name = String.valueOf(wavingNameView.getText());
        Intent intent = new Intent();
        intent.putExtra("student_id", name);
        setResult(MainActivity.MOCK_WAVE_REQUEST_CODE, intent);
        finish();

    }

}