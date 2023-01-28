package edu.ucsd.cse110.team13.bof;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import edu.ucsd.cse110.team13.bof.model.ClassSize;
import edu.ucsd.cse110.team13.bof.model.Quarter;
import edu.ucsd.cse110.team13.bof.model.database.AppDatabase;
import edu.ucsd.cse110.team13.bof.model.database.CourseDao;
import edu.ucsd.cse110.team13.bof.model.database.ProfileDao;
import edu.ucsd.cse110.team13.bof.model.database.ProfileWithCourses;
import edu.ucsd.cse110.team13.bof.model.database.RoomCourse;
import edu.ucsd.cse110.team13.bof.model.database.RoomProfile;
import edu.ucsd.cse110.team13.bof.model.database.RoomSession;
import edu.ucsd.cse110.team13.bof.model.database.SessionDao;
import edu.ucsd.cse110.team13.bof.model.database.SessionProfileCrossRef;

@RunWith(AndroidJUnit4.class)
public class AppDatabaseTest {
    private AppDatabase db;
    private SessionDao sessionDao;
    private ProfileDao profileDao;
    private CourseDao  courseDao;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        AppDatabase.useTestSingleton(context);
        db = AppDatabase.singleton(context);
        sessionDao = db.sessionDao();
        profileDao = db.profileDao();
        courseDao  = db.courseDao();
    }

    @After
    public void closeDb() { db.close(); }

    /* CourseDao  */
    @Test
    public void courseDao_defaultOutput() {
        Assert.assertEquals(0,courseDao.size());
        Assert.assertEquals(0,courseDao.getCourses("0").size());
        Assert.assertEquals(0,courseDao.findCount ("0",
                0,Quarter.FA,"",""));
        Assert.assertNull(courseDao.get(0));
    }

    @Test
    public void courseDao_insertCourse() {
        RoomCourse rc11 = new RoomCourse("0",2022,Quarter.FA,ClassSize.LARGE,"CSE","110");
        RoomCourse rc21 = new RoomCourse("0",2021,Quarter.SP,ClassSize.SMALL,"MGT","167");
        RoomCourse rc22 = new RoomCourse("1",2021,Quarter.SP,ClassSize.SMALL,"MGT","167");

        courseDao.insert(rc11);
        Assert.assertEquals(1,courseDao.size());
        Assert.assertEquals("0",courseDao.get(1).uid);
        Assert.assertEquals(rc11.year         ,courseDao.get(1).year);
        Assert.assertEquals(rc11.quarter      ,courseDao.get(1).quarter);
        Assert.assertEquals(rc11.size         ,courseDao.get(1).size);
        Assert.assertEquals(rc11.subject      ,courseDao.get(1).subject);
        Assert.assertEquals(rc11.course_number,courseDao.get(1).course_number);

        courseDao.insert(rc21);
        Assert.assertEquals(2,courseDao.size());
        Assert.assertEquals("0",courseDao.get(2).uid);
        Assert.assertEquals(rc21.year         ,courseDao.get(2).year);
        Assert.assertEquals(rc21.quarter      ,courseDao.get(2).quarter);
        Assert.assertEquals(rc21.size         ,courseDao.get(2).size);
        Assert.assertEquals(rc21.subject      ,courseDao.get(2).subject);
        Assert.assertEquals(rc21.course_number,courseDao.get(2).course_number);

        courseDao.insert(rc22);
        Assert.assertEquals(3,courseDao.size());
        Assert.assertEquals("1",courseDao.get(3).uid);
        Assert.assertEquals(rc22.year         ,courseDao.get(3).year);
        Assert.assertEquals(rc22.quarter      ,courseDao.get(3).quarter);
        Assert.assertEquals(rc22.size         ,courseDao.get(3).size);
        Assert.assertEquals(rc22.subject      ,courseDao.get(3).subject);
        Assert.assertEquals(rc22.course_number,courseDao.get(3).course_number);
    }

    @Test
    public void courseDao_findCount() {
        RoomCourse rc11 = new RoomCourse("0",2022,Quarter.FA,ClassSize.LARGE,"CSE","110");
        RoomCourse rc21 = new RoomCourse("0",2021,Quarter.SP,ClassSize.SMALL,"MGT","167");
        RoomCourse rc22 = new RoomCourse("1",2021,Quarter.SP,ClassSize.SMALL,"MGT","167");
        courseDao.insert(rc11);
        courseDao.insert(rc21);
        courseDao.insert(rc22);

        Assert.assertEquals(1,courseDao.findCount(rc11.uid,rc11.year,rc11.quarter,rc11.subject,rc11.course_number));
        Assert.assertEquals(1,courseDao.findCount(rc21.uid,rc21.year,rc21.quarter,rc21.subject,rc21.course_number));
    }

    @Test
    public void courseDao_deleteByUid() {
        RoomCourse rc11 = new RoomCourse("0",2022,Quarter.FA,ClassSize.LARGE,"CSE","110");
        RoomCourse rc21 = new RoomCourse("0",2021,Quarter.SP,ClassSize.SMALL,"MGT","167");
        RoomCourse rc22 = new RoomCourse("1",2021,Quarter.SP,ClassSize.SMALL,"MGT","167");
        courseDao.insert(rc11);
        courseDao.insert(rc21);
        courseDao.insert(rc22);

        courseDao.deleteByUid("2");
        Assert.assertEquals(3,courseDao.size());
        Assert.assertEquals(2,courseDao.getCourses("0").size());
        Assert.assertEquals(1,courseDao.getCourses("1").size());

        courseDao.deleteByUid("0");
        Assert.assertEquals(1,courseDao.size());
        Assert.assertEquals(0,courseDao.getCourses("0").size());
        Assert.assertEquals(1,courseDao.getCourses("1").size());
        Assert.assertEquals("1",courseDao.get(3).uid);
        Assert.assertEquals(rc22.year         ,courseDao.get(3).year);
        Assert.assertEquals(rc22.quarter      ,courseDao.get(3).quarter);
        Assert.assertEquals(rc22.size         ,courseDao.get(3).size);
        Assert.assertEquals(rc22.subject      ,courseDao.get(3).subject);
        Assert.assertEquals(rc22.course_number,courseDao.get(3).course_number);
    }

    /* ProfileDao */
    @Test
    public void profileDao_defaultOutput() {
        Assert.assertEquals(0,profileDao.size());
        Assert.assertEquals(0,profileDao.getAll().size());
        Assert.assertEquals(0,profileDao.getFavoritesWithCourses().size());
        Assert.assertEquals(0,profileDao.getAllProfilesWithCourses().size());
        Assert.assertNull(profileDao.get("1"));
        Assert.assertNull(profileDao.getProfileWithCourses("1"));
    }

    @Test
    public void profileDao_insertProfile() {
        profileDao.insert(new RoomProfile("2202282214069101L","Eddie","abc.xyz"));
        Assert.assertEquals(1,profileDao.size());
        profileDao.insert(new RoomProfile("2202282228069097L","Eda"  ,"qwe.ewq"));
        Assert.assertEquals(2,profileDao.size());

        List<RoomProfile> profiles = profileDao.getAll();
        Assert.assertEquals("2202282214069101L",profiles.get(0).uid);
        Assert.assertEquals("Eddie",            profiles.get(0).firstName);
        Assert.assertEquals("abc.xyz",          profiles.get(0).url);
        Assert.assertEquals("2202282228069097L",profiles.get(1).uid);
        Assert.assertEquals("Eda",              profiles.get(1).firstName);
        Assert.assertEquals("qwe.ewq",          profiles.get(1).url);

        Assert.assertEquals("2202282214069101L",profileDao.get("2202282214069101L").uid);
        Assert.assertEquals("Eddie",            profileDao.get("2202282214069101L").firstName);
        Assert.assertEquals("abc.xyz",          profileDao.get("2202282214069101L").url);
        Assert.assertEquals("2202282228069097L",profileDao.get("2202282228069097L").uid);
        Assert.assertEquals("Eda",              profileDao.get("2202282228069097L").firstName);
        Assert.assertEquals("qwe.ewq",          profileDao.get("2202282228069097L").url);
    }

    @Test
    public void profileDao_profileWithCourses() {
        String uid1 = "2202282214069101L",
               uid2 = "2202282228069097L";
        profileDao.insert(new RoomProfile(uid1,"Eddie","abc.xyz"));
        profileDao.insert(new RoomProfile(uid2,"Eda"  ,"qwe.ewq"));

        RoomCourse rc11 = new RoomCourse(uid1,2022,Quarter.FA,ClassSize.LARGE,"CSE","110");
        RoomCourse rc21 = new RoomCourse(uid1,2021,Quarter.SP,ClassSize.SMALL,"MGT","167");
        RoomCourse rc22 = new RoomCourse(uid2,2021,Quarter.SP,ClassSize.SMALL,"MGT","167");
        courseDao.insert(rc11);
        courseDao.insert(rc21);
        courseDao.insert(rc22);

        ProfileWithCourses pwc1 = profileDao.getProfileWithCourses(uid1),
                           pwc2 = profileDao.getProfileWithCourses(uid2);
        Assert.assertEquals(uid1,pwc1.profile.uid);
        Assert.assertEquals("Eddie"  ,pwc1.getFirstName());
        Assert.assertEquals("abc.xyz",pwc1.getHeadshotUrl());
        Assert.assertEquals(2        ,pwc1.getClasses().size());
        Assert.assertTrue(pwc1.getClasses().contains(rc11));
        Assert.assertTrue(pwc1.getClasses().contains(rc21));
        Assert.assertEquals(uid2,pwc2.profile.uid);
        Assert.assertEquals("Eda"    ,pwc2.getFirstName());
        Assert.assertEquals("qwe.ewq",pwc2.getHeadshotUrl());
        Assert.assertEquals(1        ,pwc2.getClasses().size());
        Assert.assertTrue(pwc2.getClasses().contains(rc22));

        Assert.assertEquals(2, profileDao.getAllProfilesWithCourses().size());
        Assert.assertEquals(3, courseDao.size());
    }

    @Test
    public void profileDao_favorites() {
        String uid1 = "2202282214069101L",
               uid2 = "2202282228069097L";
        profileDao.insert(new RoomProfile(uid1,"Eddie","abc.xyz"));
        profileDao.insert(new RoomProfile(uid2,"Eda"  ,"qwe.ewq"));

        RoomCourse rc11 = new RoomCourse(uid1,2022,Quarter.FA,ClassSize.LARGE,"CSE","110");
        RoomCourse rc21 = new RoomCourse(uid1,2021,Quarter.SP,ClassSize.SMALL,"MGT","167");
        RoomCourse rc22 = new RoomCourse(uid2,2021,Quarter.SP,ClassSize.SMALL,"MGT","167");
        courseDao.insert(rc11);
        courseDao.insert(rc21);
        courseDao.insert(rc22);

        Assert.assertEquals(0,profileDao.getFavoritesWithCourses().size());

        profileDao.updateFavorite(uid1,true);
        profileDao.updateFavorite(uid2,false);    // no actual change should be made
        profileDao.updateFavorite("414",true);  // nothing should happen
        Assert.assertEquals(1,profileDao.getFavoritesWithCourses().size());
        Assert.assertEquals(uid1,profileDao.getFavoritesWithCourses().get(0).profile.uid);
        Assert.assertEquals(2,profileDao.getFavoritesWithCourses().get(0).getClasses().size());

        profileDao.updateFavorite(uid2,true);
        Assert.assertEquals(2,profileDao.getFavoritesWithCourses().size());
        Assert.assertEquals(uid1,profileDao.getFavoritesWithCourses().get(0).profile.uid);
        Assert.assertEquals(uid2,profileDao.getFavoritesWithCourses().get(1).profile.uid);
        Assert.assertEquals(1,profileDao.getFavoritesWithCourses().get(1).getClasses().size());

        profileDao.updateFavorite(uid1,false);
        Assert.assertEquals(1,profileDao.getFavoritesWithCourses().size());
        Assert.assertEquals(uid2,profileDao.getFavoritesWithCourses().get(0).profile.uid);
        Assert.assertEquals(1,profileDao.getFavoritesWithCourses().get(0).getClasses().size());
    }

    @Test
    public void profileDao_updateProfile() {
        String uid1 = "2202282214069101L",
               uid2 = "2202282228069097L";
        profileDao.insert(new RoomProfile(uid1,"Eddie","abc.xyz"));
        profileDao.insert(new RoomProfile(uid2,"Eda"  ,"qwe.ewq"));

        RoomCourse rc11 = new RoomCourse(uid1,2022,Quarter.FA,ClassSize.LARGE,"CSE","110");
        RoomCourse rc21 = new RoomCourse(uid1,2021,Quarter.SP,ClassSize.SMALL,"MGT","167");
        RoomCourse rc22 = new RoomCourse(uid2,2021,Quarter.SP,ClassSize.SMALL,"MGT","167");
        courseDao.insert(rc11);
        courseDao.insert(rc21);
        courseDao.insert(rc22);

        profileDao.insert(new RoomProfile(uid1,"Eve","pix.ty"));
        Assert.assertEquals(2,profileDao.size());

        List<RoomProfile> profiles = profileDao.getAll();
        Assert.assertEquals(uid1,profiles.get(1).uid);
        Assert.assertEquals("Eve",    profiles.get(1).firstName);
        Assert.assertEquals("pix.ty", profiles.get(1).url);
        Assert.assertEquals(uid2,profiles.get(0).uid);
        Assert.assertEquals("Eda",    profiles.get(0).firstName);
        Assert.assertEquals("qwe.ewq",profiles.get(0).url);
    }

    /* SessionDao */
    @Test
    public void sessionDao_defaultOutput() {
        Assert.assertEquals(0,sessionDao.size());
        Assert.assertEquals(0,sessionDao.getAll().size());
        Assert.assertEquals(0,sessionDao.getAllSessionsWithProfiles().size());
        Assert.assertNull(sessionDao.get(1));
        Assert.assertNull(sessionDao.get("k"));
        Assert.assertNull(sessionDao.getSessionWithProfiles(1));
    }

    @Test
    public void sessionDao_insertSession() {
        sessionDao.insert(new RoomSession("CSE140L"));
        Assert.assertEquals(1,sessionDao.size());
        sessionDao.insert(new RoomSession("CSE110"));
        Assert.assertEquals(2,sessionDao.size());

        List<RoomSession> sessions = sessionDao.getAll();
        Assert.assertEquals(1,sessions.get(0).sessionId);
        Assert.assertEquals("CSE140L",sessions.get(0).sessionName);
        Assert.assertEquals(2,sessions.get(1).sessionId);
        Assert.assertEquals("CSE110" ,sessions.get(1).sessionName);

        Assert.assertEquals(1,sessionDao.get("CSE140L").sessionId);
        Assert.assertEquals(2,sessionDao.get("CSE110" ).sessionId);
        Assert.assertEquals("CSE140L",sessionDao.get(1).sessionName);
        Assert.assertEquals("CSE110" ,sessionDao.get(2).sessionName);
    }

    @Test
    public void sessionDao_sessionsWithProfiles() {
        String uid1 = "2202282214069101L",
               uid2 = "2202282228069097L";
        profileDao.insert(new RoomProfile(uid1,"Eddie","abc.xyz"));
        profileDao.insert(new RoomProfile(uid2,"Eda"  ,"qwe.ewq"));

        RoomCourse rc11 = new RoomCourse(uid1,2022,Quarter.FA,ClassSize.LARGE,"CSE","110");
        RoomCourse rc21 = new RoomCourse(uid1,2021,Quarter.SP,ClassSize.SMALL,"MGT","167");
        RoomCourse rc22 = new RoomCourse(uid2,2021,Quarter.SP,ClassSize.SMALL,"MGT","167");
        courseDao.insert(rc11);
        courseDao.insert(rc21);
        courseDao.insert(rc22);

        sessionDao.insert(new RoomSession("CSE140L"));
        sessionDao.insert(new RoomSession("CSE110" ));

        sessionDao.insert(new SessionProfileCrossRef(sessionDao.get("CSE140L").sessionId,uid1));
        sessionDao.insert(new SessionProfileCrossRef(sessionDao.get("CSE140L").sessionId,uid1));
        Assert.assertEquals(1,
                sessionDao.getSessionWithProfiles(sessionDao.get("CSE140L").sessionId)
                        .profiles.size());

        sessionDao.insert(new SessionProfileCrossRef(sessionDao.get("CSE140L").sessionId,uid2));
        sessionDao.insert(new SessionProfileCrossRef(sessionDao.get("CSE110" ).sessionId,uid1));
        Assert.assertEquals(2,
                sessionDao.getSessionWithProfiles(sessionDao.get("CSE140L").sessionId)
                        .profiles.size());
        Assert.assertEquals(uid1,
                sessionDao.getSessionWithProfiles(sessionDao.get("CSE140L").sessionId)
                        .profiles.get(0).uid);
        Assert.assertEquals(uid2,
                sessionDao.getSessionWithProfiles(sessionDao.get("CSE140L").sessionId)
                        .profiles.get(1).uid);
        Assert.assertEquals(1,
                sessionDao.getSessionWithProfiles(sessionDao.get("CSE110" ).sessionId)
                        .profiles.size());
        Assert.assertEquals(uid1,
                sessionDao.getSessionWithProfiles(sessionDao.get("CSE110").sessionId)
                        .profiles.get(0).uid);

        Assert.assertEquals(2,sessionDao.getAllSessionsWithProfiles().size());
        Assert.assertEquals(2,sessionDao.getAllSessionsWithProfiles().get(0).profiles.size());
        Assert.assertEquals(1,sessionDao.getAllSessionsWithProfiles().get(1).profiles.size());
    }

    @Test
    public void sessionDao_renameSession() {
        sessionDao.insert(new RoomSession("CSE140L"));
        sessionDao.insert(new RoomSession("CSE110"));

        RoomSession session = sessionDao.get("CSE110");
        sessionDao.renameSession(session.sessionId,"CSE101");
        Assert.assertEquals(2,sessionDao.size());
        Assert.assertEquals(session.sessionId,sessionDao.get("CSE101").sessionId);
        Assert.assertNull   (sessionDao.get("CSE110" ));
        Assert.assertNotNull(sessionDao.get("CSE140L"));
    }

    // TODO
    @Test
    public void appDatabase_readFromEmptyDb() {}
}
