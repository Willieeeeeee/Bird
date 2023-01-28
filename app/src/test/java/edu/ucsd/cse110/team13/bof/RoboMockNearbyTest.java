package edu.ucsd.cse110.team13.bof;


import android.app.AlertDialog;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.Shadows;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowAlertDialog;

import java.util.ArrayList;

import edu.ucsd.cse110.team13.bof.mock.MockNearbyActivity;
import edu.ucsd.cse110.team13.bof.model.ClassSize;
import edu.ucsd.cse110.team13.bof.model.Quarter;
import edu.ucsd.cse110.team13.bof.model.database.ProfileWithCourses;
import edu.ucsd.cse110.team13.bof.model.database.RoomCourse;
import edu.ucsd.cse110.team13.bof.model.database.RoomProfile;

@RunWith(AndroidJUnit4.class)
public class RoboMockNearbyTest {
    @Rule
    public ActivityScenarioRule<MockNearbyActivity> scenarioRule = new ActivityScenarioRule<>(MockNearbyActivity.class);

    @Test
    public void mockNearby_emptyInput() {
        ActivityScenario<MockNearbyActivity> scenario = scenarioRule.getScenario();

        scenario.moveToState(Lifecycle.State.CREATED);

        scenario.onActivity(activity -> {
            Button button  = activity.findViewById(R.id.new_msg_btn);

            assertEquals("Go Back", button.getText());
        });
    }

    @Test
    public void mockNearby_validInputResult() {
        String inputStr =
                "a4ca50b6-941b-11ec-b909-0242ac120002,,,,\n" +
                "Bill,,,,\n" +
                "https://abc_def=ghi?jkl.xyz,,,,\n" +
                "2021,FA,CSE,210,Small\n" +
                "2022,WI,CSE,110,Large\n" +
                "4b295157-ba31-4f9f-8401-5d85d9cf659a,wave,,,";
        String headshotUrl = "https://abc_def=ghi?jkl.xyz";
        ProfileWithCourses expected = new ProfileWithCourses(
                new RoomProfile("a4ca50b6-941b-11ec-b909-0242ac120002","Bill", headshotUrl),
                new ArrayList<RoomCourse>(){{
                    add(new RoomCourse("0",2021, Quarter.FA, ClassSize.SMALL, "CSE", "210"));
                    add(new RoomCourse("0",2022, Quarter.WI, ClassSize.LARGE, "CSE", "110")); }});

        ActivityScenario<MockNearbyActivity> scenario = scenarioRule.getScenario();

        scenario.moveToState(Lifecycle.State.CREATED);

        scenario.onActivity(activity -> {
            EditText inputTextView = activity.findViewById(R.id.record_textview);
            Button button  = activity.findViewById(R.id.new_msg_btn);

            inputTextView.setText("");
            assertEquals("Go Back", button.getText());

            inputTextView.setText(inputStr);
            assertEquals("Enter",   button.getText());

            button.performClick();

            assertEquals(MainActivity.CODE_MOCK_REQUEST, scenario.getResult().getResultCode());
//            assertEquals(NearbyUtil.encodeMessage(expected, new ArrayList<String>(){{ add("4b295157-ba31-4f9f-8401-5d85d9cf659a"); }}),
//                    scenario.getResult().getResultData().getStringExtra(String.valueOf(MainActivity.MOCK_REQUEST_CODE)));
        });
    }

    @Test
    public void mockNearby_invalidInputShowDialog() {
        String inputStr = "fjasl;fds,,,,";

        MockNearbyActivity activity = Robolectric.buildActivity(MockNearbyActivity.class).setup().get();
        ShadowActivity shadowActivity = Shadows.shadowOf(activity);

        ((TextView) activity.findViewById(R.id.record_textview)).setText(inputStr);
        activity.findViewById(R.id.new_msg_btn).performClick();
        AlertDialog shadowAlertDialog = ShadowAlertDialog.getLatestAlertDialog();

        assertNotNull(shadowAlertDialog);
        assertTrue(shadowAlertDialog.isShowing());
    }
}
