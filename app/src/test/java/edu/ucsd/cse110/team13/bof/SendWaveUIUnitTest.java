package edu.ucsd.cse110.team13.bof;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import static edu.ucsd.cse110.team13.bof.util.NearbyUtil.encodeProfile;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import edu.ucsd.cse110.team13.bof.model.IClass;
import edu.ucsd.cse110.team13.bof.model.IProfile;
import edu.ucsd.cse110.team13.bof.model.Quarter;
import edu.ucsd.cse110.team13.bof.model.database.AppDatabase;
import edu.ucsd.cse110.team13.bof.model.mClass;
import edu.ucsd.cse110.team13.bof.model.mProfile;
import edu.ucsd.cse110.team13.bof.util.AppDatabaseUtil;
import edu.ucsd.cse110.team13.bof.util.UserProfileUtil;

/* Test for User Story #9 */
@RunWith(RobolectricTestRunner.class)
public class SendWaveUIUnitTest {
        private AppDatabase db;
        private AppDatabaseUtil dbUtil;
        private UserProfileUtil upUtil;

        /* create the profile to be sent in the intent */
        @Before
        public void setup() {
            Context context = ApplicationProvider.getApplicationContext();
            AppDatabase.useTestSingleton(context);
            db = AppDatabase.singleton(context);
            dbUtil = new AppDatabaseUtil(context);
            upUtil = new UserProfileUtil(context);

            String phil_headshot = "https://i.kym-cdn.com/entries/icons/mobile/000/024/719/Screen_Shot_2017-11-21_at_4.07.30_PM.jpg";
            IClass[] phil_classes = {
                    new mClass(2020, Quarter.FA,"CSE","21"),
                    new mClass(2022, Quarter.SP,"CSE","210"),
                    new mClass(2010, Quarter.WI,"CSE","15L")
            };

            upUtil.setUid();
            upUtil.setFirstName("phil");
            upUtil.setHeadshotUrl(phil_headshot);
            for(IClass c : phil_classes) {
                upUtil.addClass(c);
            }
            upUtil.setClassesAsFinished();
        }

        @After
        public void closeDb() { db.close(); }


       @Test
       public void clickingButton_shouldChangeMessage() {
           /* Robolectric setup to pass intent */
           Context context = ApplicationProvider.getApplicationContext();
           Intent  intent  = new Intent(context, StudentDetailActivity.class);
           intent.putExtra(StudentDetailActivity.UID_CODE, upUtil.getUid());
           intent.putExtra(StudentDetailActivity.IS_ACTIVE_CODE,true);

            try(ActivityScenario<StudentDetailActivity> scenario = ActivityScenario.launch(intent)) {
                scenario.onActivity(activity -> {
                    ImageView hollow_hand = activity.findViewById(R.id.waving_hand_hollow);
                    ImageView filled_hand = activity.findViewById(R.id.waving_hand_filled);

                    /* assert that correct hand is showing before button click */
                    assertEquals(View.VISIBLE, hollow_hand.getVisibility());
                    assertEquals(View.INVISIBLE, filled_hand.getVisibility());

                    /* click the hollow hand */
                    assertTrue(hollow_hand.isClickable());
                    hollow_hand.performClick();

                    /* assert that correct hand is showing AFTER button click */
                    assertEquals(View.INVISIBLE, hollow_hand.getVisibility());
                    assertEquals(View.VISIBLE, filled_hand.getVisibility());

                });
        }
    }
}
