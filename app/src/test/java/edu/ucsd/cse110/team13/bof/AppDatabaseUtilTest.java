package edu.ucsd.cse110.team13.bof;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Calendar;

import edu.ucsd.cse110.team13.bof.model.ClassSize;
import edu.ucsd.cse110.team13.bof.model.Quarter;
import edu.ucsd.cse110.team13.bof.model.database.AppDatabase;
import edu.ucsd.cse110.team13.bof.model.database.ProfileWithCourses;
import edu.ucsd.cse110.team13.bof.model.database.RoomCourse;
import edu.ucsd.cse110.team13.bof.model.database.RoomProfile;
import edu.ucsd.cse110.team13.bof.util.AppDatabaseUtil;

@RunWith(AndroidJUnit4.class)
public class AppDatabaseUtilTest {
    private AppDatabase db;
    private AppDatabaseUtil dbUtil;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        AppDatabase.useTestSingleton(context);
        db = AppDatabase.singleton(context);
        dbUtil = new AppDatabaseUtil(context);
    }

    @After
    public void closeDb() { db.close(); }

    @Test
    public void defaultBehavior() {
        Assert.assertFalse (dbUtil.hasProfile("0"));
        Assert.assertFalse (dbUtil.hasSession(1));
        Assert.assertNull  (dbUtil.getProfile("0"));
        Assert.assertNull  (dbUtil.getProfilesInSession(1));
        Assert.assertEquals(0,dbUtil.getFavorites().size());
        Assert.assertEquals(0,dbUtil.getAllSessions().size());
        Assert.assertEquals(-1,dbUtil.getSessionId("whatever"));

        Assert.assertFalse(dbUtil.setFavorite("0",true));
        Assert.assertFalse(dbUtil.addProfileToSession(1,
                new ProfileWithCourses(
                        new RoomProfile("0","",""),
                        new ArrayList<>())));
        Assert.assertFalse(dbUtil.renameSession(1,""));
    }

    @Test
    public void createSession() {
        Assert.assertEquals(1,dbUtil.createSession("1/16/22 5:10PM"));
        Assert.assertTrue(dbUtil.hasSession(1));
        Assert.assertEquals(1,dbUtil.getAllSessions().size());
        Assert.assertEquals("1/16/22 5:10PM",dbUtil.getAllSessions().get(0).sessionName);

        Assert.assertEquals(-1,dbUtil.createSession("1/16/22 5:10PM"));
        Assert.assertEquals(1,dbUtil.getAllSessions().size());

        Assert.assertEquals(2,dbUtil.createSession("3/1/22 6:12AM"));
        Assert.assertTrue(dbUtil.hasSession(2));
        Assert.assertEquals(2,dbUtil.getAllSessions().size());
        Assert.assertEquals("3/1/22 6:12AM",dbUtil.getAllSessions().get(1).sessionName);
    }

    @Test
    public void sessionsAndProfiles() {
        int sessionId1 = dbUtil.createSession("1/16/22 5:10PM"),
            sessionId2 = dbUtil.createSession("3/1/22 6:12AM" );
        RoomProfile p1 = new RoomProfile("2202282214069101L","Eddie","abc.xyz"),
                    p2 = new RoomProfile("2202282228069097L","Eda"  ,"qwe.ewq"),
                    p3 = new RoomProfile("2202282214069101L","Eve"  ,"kur.piv");
        RoomCourse rc11 = new RoomCourse(p1.uid,2022,Quarter.FA,ClassSize.LARGE,"CSE","110");
        RoomCourse rc21 = new RoomCourse(p1.uid,2021,Quarter.SP,ClassSize.SMALL,"MGT","167");
        RoomCourse rc22 = new RoomCourse(p2.uid,2021,Quarter.SP,ClassSize.SMALL,"MGT","167");
        RoomCourse rc31 = new RoomCourse(p1.uid,2020,Quarter.FA,ClassSize.LARGE,"CSE","30" );
        ProfileWithCourses p11 = new ProfileWithCourses(p1, new ArrayList<RoomCourse>(){{
            add(rc11); add(rc21); }});
        ProfileWithCourses p12 = new ProfileWithCourses(p3, new ArrayList<RoomCourse>(){{
            add(rc11); add(rc21); add(rc31); }});
        ProfileWithCourses p21 = new ProfileWithCourses(p2, new ArrayList<RoomCourse>(){{
            add(rc21); add(rc22); }});
        // note courses with different uid with the profile's does not matter
        // also existing courses linked to a profile will be ignored

        dbUtil.addProfileToSession(sessionId1,p11);
        Assert.assertTrue(dbUtil.hasProfile(p1.uid));
        Assert.assertEquals(p1.firstName,dbUtil.getProfile(p1.uid).getFirstName());
        Assert.assertEquals(p1.url      ,dbUtil.getProfile(p1.uid).getHeadshotUrl());
        Assert.assertEquals(p1.favorite ,dbUtil.getProfile(p1.uid).isFavorite());
        Assert.assertEquals(1,dbUtil.getProfilesInSession(sessionId1).size());
        Assert.assertEquals(p1.firstName,dbUtil.getProfilesInSession(sessionId1).get(0).getFirstName());
        Assert.assertEquals(p1.url      ,dbUtil.getProfilesInSession(sessionId1).get(0).getHeadshotUrl());
        Assert.assertEquals(p1.favorite ,dbUtil.getProfilesInSession(sessionId1).get(0).isFavorite());
        Assert.assertEquals(2,dbUtil.getProfilesInSession(sessionId1).get(0).getClasses().size());

        dbUtil.addProfileToSession(sessionId1,p11); // nothing should happen
        dbUtil.renameSession(sessionId1,"CSE160"); // nothing should happen
        Assert.assertEquals(1,dbUtil.getProfilesInSession(sessionId1).size());

        dbUtil.addProfileToSession(sessionId1,p21);
        Assert.assertTrue(dbUtil.hasProfile(p2.uid));
        Assert.assertEquals(2,dbUtil.getProfilesInSession(sessionId1).size());
        Assert.assertEquals(p2.firstName,dbUtil.getProfilesInSession(sessionId1).get(1).getFirstName());
        Assert.assertEquals(p2.url      ,dbUtil.getProfilesInSession(sessionId1).get(1).getHeadshotUrl());
        Assert.assertEquals(p2.favorite ,dbUtil.getProfilesInSession(sessionId1).get(1).isFavorite());
        Assert.assertEquals(1,dbUtil.getProfilesInSession(sessionId1).get(1).getClasses().size());

        dbUtil.addProfileToSession(sessionId2,p12);  // link an update profile to another session
        Assert.assertTrue(dbUtil.hasProfile(p1.uid));
        Assert.assertEquals(p3.firstName,dbUtil.getProfile(p3.uid).getFirstName());
        Assert.assertEquals(p3.url      ,dbUtil.getProfile(p1.uid).getHeadshotUrl());
        Assert.assertEquals(p3.favorite ,dbUtil.getProfile(p1.uid).isFavorite());
        Assert.assertEquals(2,dbUtil.getProfilesInSession(sessionId1).size());
        Assert.assertEquals(1,dbUtil.getProfilesInSession(sessionId2).size());
        Assert.assertEquals(p3.firstName,dbUtil.getProfilesInSession(sessionId2).get(0).getFirstName());
        Assert.assertEquals(p3.url      ,dbUtil.getProfilesInSession(sessionId2).get(0).getHeadshotUrl());
        Assert.assertEquals(p3.favorite ,dbUtil.getProfilesInSession(sessionId2).get(0).isFavorite());
        Assert.assertEquals(3,dbUtil.getProfilesInSession(sessionId2).get(0).getClasses().size());
        Assert.assertEquals(3,dbUtil.getProfilesInSession(sessionId1).get(0).getClasses().size());
    }

    @Test
    public void favorites() {
        int sessionId1 = dbUtil.createSession("1/16/22 5:10PM"),
            sessionId2 = dbUtil.createSession("3/1/22 6:12AM" );
        RoomProfile p1 = new RoomProfile("2202282214069101L","Eddie","abc.xyz"),
                    p2 = new RoomProfile("2202282228069097L","Eda"  ,"qwe.ewq"),
                    p3 = new RoomProfile("2202282228069101L","Eve"  ,"kur.piv");
        RoomCourse rc11 = new RoomCourse(p1.uid,2022,Quarter.FA,ClassSize.LARGE,"CSE","110");
        RoomCourse rc21 = new RoomCourse(p1.uid,2021,Quarter.SP,ClassSize.SMALL,"MGT","167");
        RoomCourse rc22 = new RoomCourse(p2.uid,2021,Quarter.SP,ClassSize.SMALL,"MGT","167");
        RoomCourse rc33 = new RoomCourse(p1.uid,2020,Quarter.FA,ClassSize.LARGE,"CSE","30" );
        ProfileWithCourses pwc1 = new ProfileWithCourses(p1, new ArrayList<RoomCourse>(){{ add(rc11); add(rc21); add(rc33); }});
        ProfileWithCourses pwc2 = new ProfileWithCourses(p2, new ArrayList<RoomCourse>(){{ add(rc21); add(rc22); }});
        ProfileWithCourses pwc3 = new ProfileWithCourses(p3, new ArrayList<RoomCourse>(){{ add(rc33); }});
        dbUtil.addProfileToSession(sessionId1,pwc1);
        dbUtil.addProfileToSession(sessionId1,pwc2);
        dbUtil.addProfileToSession(sessionId1,pwc3);
        dbUtil.addProfileToSession(sessionId2,pwc1);

        dbUtil.setFavorite(p1.uid,true);
        Assert.assertEquals(1,dbUtil.getFavorites().size());
        Assert.assertEquals(3,dbUtil.getFavorites().get(0).getClasses().size());
        Assert.assertEquals(p1.firstName,dbUtil.getFavorites().get(0).getFirstName());
        Assert.assertTrue(dbUtil.getProfile(p1.uid).isFavorite());
        Assert.assertTrue(dbUtil.getProfilesInSession(sessionId1).get(0).isFavorite());
        Assert.assertTrue(dbUtil.getProfilesInSession(sessionId2).get(0).isFavorite());

        pwc1.profile.firstName = "Eve";
        dbUtil.addProfileToSession(sessionId1,pwc1);  // update profile does not affect favorite
        Assert.assertEquals(1,dbUtil.getFavorites().size());
        Assert.assertEquals(3,dbUtil.getFavorites().get(0).getClasses().size());
        Assert.assertEquals("Eve",dbUtil.getFavorites().get(0).getFirstName());
        Assert.assertTrue(dbUtil.getProfile(p1.uid).isFavorite());
        Assert.assertTrue(dbUtil.getProfilesInSession(sessionId1).get(0).isFavorite());
        Assert.assertTrue(dbUtil.getProfilesInSession(sessionId2).get(0).isFavorite());

        dbUtil.setFavorite(p1.uid,true);
        dbUtil.setFavorite(p2.uid,true);
        dbUtil.setFavorite(p3.uid,true);
        Assert.assertEquals(3,dbUtil.getFavorites().size());
        Assert.assertTrue(dbUtil.getProfile(p2.uid).isFavorite());
        Assert.assertTrue(dbUtil.getProfilesInSession(sessionId1).get(1).isFavorite());
        Assert.assertEquals(1,dbUtil.getProfilesInSession(sessionId1).get(1).getClasses().size());
        Assert.assertTrue(dbUtil.getProfile(p3.uid).isFavorite());
        Assert.assertTrue(dbUtil.getProfilesInSession(sessionId1).get(2).isFavorite());
        Assert.assertEquals(1,dbUtil.getProfilesInSession(sessionId1).get(2).getClasses().size());

        dbUtil.setFavorite(p1.uid,false);
        Assert.assertEquals(2,dbUtil.getFavorites().size());
        Assert.assertFalse(dbUtil.getProfile(p1.uid).isFavorite());
        Assert.assertFalse(dbUtil.getProfilesInSession(sessionId1).get(0).isFavorite());
        Assert.assertFalse(dbUtil.getProfilesInSession(sessionId2).get(0).isFavorite());
    }

    @Test
    public void makeSessionName() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, Calendar.JANUARY);
        calendar.set(Calendar.DAY_OF_MONTH, 6);
        calendar.set(Calendar.YEAR, 2002);
        calendar.set(Calendar.AM_PM, Calendar.AM);
        calendar.set(Calendar.HOUR, 6);
        calendar.set(Calendar.MINUTE, 1);

        String expected1 = "1/6/02 6:01AM";
        Assert.assertEquals(expected1, AppDatabaseUtil.makeSessionName(calendar.getTime()));

        calendar.set(Calendar.MONTH, Calendar.NOVEMBER);
        calendar.set(Calendar.DAY_OF_MONTH, 22);
        calendar.set(Calendar.YEAR, 2022);
        calendar.set(Calendar.AM_PM, Calendar.PM);
        calendar.set(Calendar.HOUR, 10);
        calendar.set(Calendar.MINUTE, 10);
        String expected2 = "11/22/22 10:10PM";
        Assert.assertEquals(expected2, AppDatabaseUtil.makeSessionName(calendar.getTime()));
    }
}
