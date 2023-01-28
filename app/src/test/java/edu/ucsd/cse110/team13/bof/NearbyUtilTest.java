package edu.ucsd.cse110.team13.bof;

import static edu.ucsd.cse110.team13.bof.util.NearbyUtil.decodeMessage;
import static edu.ucsd.cse110.team13.bof.util.NearbyUtil.decodeProfile;
import static edu.ucsd.cse110.team13.bof.util.NearbyUtil.encodeMessage;
import static edu.ucsd.cse110.team13.bof.util.NearbyUtil.encodeProfile;

import android.util.Pair;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import edu.ucsd.cse110.team13.bof.model.ClassSize;
import edu.ucsd.cse110.team13.bof.model.IClass;
import edu.ucsd.cse110.team13.bof.model.IProfile;
import edu.ucsd.cse110.team13.bof.model.Quarter;
import edu.ucsd.cse110.team13.bof.model.database.ProfileWithCourses;
import edu.ucsd.cse110.team13.bof.model.database.RoomCourse;
import edu.ucsd.cse110.team13.bof.model.database.RoomProfile;
import edu.ucsd.cse110.team13.bof.model.mClass;
import edu.ucsd.cse110.team13.bof.model.mProfile;

@RunWith(AndroidJUnit4.class)
public class NearbyUtilTest {
    @Test
    public void encodedProfile_canBeDecoded() {
        IProfile profile = new mProfile("Eddie","abc.xyz", new IClass[]{
                new mClass(2022,Quarter.FA,ClassSize.LARGE,"CSE","110"),
                new mClass(2021,Quarter.SP,ClassSize.SMALL,"MGT","167")
        });
        String msg = encodeProfile(profile);
        Optional<IProfile> profileOp = decodeProfile(msg);
        Assert.assertTrue(profileOp.isPresent());

        Assert.assertEquals(profile.getFirstName(), profileOp.get().getFirstName());
        Assert.assertEquals(profile.getHeadshotUrl(), profileOp.get().getHeadshotUrl());
        Assert.assertEquals(profile.getClasses().size(), profileOp.get().getClasses().size());
    }

    @Test
    public void decodeMessage_valid() {
        String msg1 =
                "a4ca50b6-941b-11ec-b909-0242ac120002,,,,\n" +
                "Bill,,,,\n" +
                "https://abc_def=ghi?jkl.xyz,,,,\n" +
                "2021,FA,CSE,210,Small\n" +
                "2022,WI,CSE,110,Large";

        Optional<Pair<ProfileWithCourses, List<String>>> messageOp = decodeMessage(msg1);
        Assert.assertTrue(messageOp.isPresent());

        ProfileWithCourses profileRead = messageOp.get().first;
        Assert.assertEquals("a4ca50b6-941b-11ec-b909-0242ac120002",profileRead.getUid());
        Assert.assertEquals("Bill",                       profileRead.getFirstName());
        Assert.assertEquals("https://abc_def=ghi?jkl.xyz",profileRead.getHeadshotUrl());
        Assert.assertEquals(2,                            profileRead.getClasses().size());

        List<IClass> classes = profileRead.getClasses();
        Assert.assertEquals(classes.get(0).getYear(), 2021);
        Assert.assertEquals(classes.get(0).getQuarter(), Quarter.FA);
        Assert.assertEquals(classes.get(0).getSize(), ClassSize.SMALL);
        Assert.assertEquals(classes.get(0).getSubject(), "CSE");
        Assert.assertEquals(classes.get(0).getCourseNumber(), "210");
        Assert.assertEquals(classes.get(1).getYear(), 2022);
        Assert.assertEquals(classes.get(1).getQuarter(), Quarter.WI);
        Assert.assertEquals(classes.get(1).getSize(), ClassSize.LARGE);
        Assert.assertEquals(classes.get(1).getSubject(), "CSE");
        Assert.assertEquals(classes.get(1).getCourseNumber(), "110");

        List<String> uids = messageOp.get().second;
        Assert.assertEquals(0,uids.size());

        String msg2 =
                "a4ca50b6-941b-11ec-b909-0242ac120002,,,,\n" +
                "Bill,,,,\n" +
                "https://abc_def=ghi?jkl.xyz,,,,\n" +
                "2021,FA,CSE,210,Small\n" +
                "2022,WI,CSE,110,Large\n" +
                "4b295157-ba31-4f9f-8401-5d85d9cf659a,wave,,,";

        messageOp = decodeMessage(msg2);
        Assert.assertTrue(messageOp.isPresent());

        profileRead = messageOp.get().first;
        Assert.assertEquals("a4ca50b6-941b-11ec-b909-0242ac120002",profileRead.getUid());
        Assert.assertEquals("Bill",                       profileRead.getFirstName());
        Assert.assertEquals("https://abc_def=ghi?jkl.xyz",profileRead.getHeadshotUrl());
        Assert.assertEquals(2,                            profileRead.getClasses().size());

        classes = profileRead.getClasses();
        Assert.assertEquals(classes.get(0).getYear(), 2021);
        Assert.assertEquals(classes.get(0).getQuarter(), Quarter.FA);
        Assert.assertEquals(classes.get(0).getSize(), ClassSize.SMALL);
        Assert.assertEquals(classes.get(0).getSubject(), "CSE");
        Assert.assertEquals(classes.get(0).getCourseNumber(), "210");
        Assert.assertEquals(classes.get(1).getYear(), 2022);
        Assert.assertEquals(classes.get(1).getQuarter(), Quarter.WI);
        Assert.assertEquals(classes.get(1).getSize(), ClassSize.LARGE);
        Assert.assertEquals(classes.get(1).getSubject(), "CSE");
        Assert.assertEquals(classes.get(1).getCourseNumber(), "110");

        uids = messageOp.get().second;
        Assert.assertEquals(1,uids.size());
        Assert.assertEquals("4b295157-ba31-4f9f-8401-5d85d9cf659a",uids.get(0));
    }

    @Test
    public void encodedMessage_canBeDecoded() {
        RoomProfile p  = new RoomProfile("2202282214069101L","Eddie","abc.xyz");
        RoomCourse rc1 = new RoomCourse(p.uid,2022,Quarter.FA,ClassSize.LARGE,"CSE","110");
        RoomCourse rc2 = new RoomCourse(p.uid,2021,Quarter.SP,ClassSize.SMALL,"MGT","167");
        RoomCourse rc3 = new RoomCourse(p.uid,2020,Quarter.FA,ClassSize.LARGE,"CSE","30" );
        ProfileWithCourses pwc = new ProfileWithCourses(p, new ArrayList<RoomCourse>(){{
            add(rc1); add(rc2); add(rc3); }});
        List<String> waveUids = new ArrayList<String>(){{
            add("2202282228069097L"); add("2202282228069101L"); }};
        String msg = encodeMessage(pwc,waveUids);

        Optional<Pair<ProfileWithCourses, List<String>>> messageOp = decodeMessage(msg);
        Assert.assertTrue(messageOp.isPresent());

        IProfile profileRead = messageOp.get().first;
        Assert.assertEquals(profileRead.getFirstName(), "Eddie");
        Assert.assertEquals(profileRead.getHeadshotUrl(), "abc.xyz");
        Assert.assertEquals(profileRead.getClasses().size(), 3);

        List<IClass> classes = profileRead.getClasses();
        Assert.assertEquals(classes.get(0).getYear(),        rc1.year);
        Assert.assertEquals(classes.get(0).getQuarter(),     rc1.quarter);
        Assert.assertEquals(classes.get(0).getSize(),        rc1.size);
        Assert.assertEquals(classes.get(0).getSubject(),     rc1.subject);
        Assert.assertEquals(classes.get(0).getCourseNumber(),rc1.course_number);
        Assert.assertEquals(classes.get(1).getYear(),        rc2.year);
        Assert.assertEquals(classes.get(1).getQuarter(),     rc2.quarter);
        Assert.assertEquals(classes.get(1).getSize(),        rc2.size);
        Assert.assertEquals(classes.get(1).getSubject(),     rc2.subject);
        Assert.assertEquals(classes.get(1).getCourseNumber(),rc2.course_number);
        Assert.assertEquals(classes.get(2).getYear(),        rc3.year);
        Assert.assertEquals(classes.get(2).getQuarter(),     rc3.quarter);
        Assert.assertEquals(classes.get(2).getSize(),        rc3.size);
        Assert.assertEquals(classes.get(2).getSubject(),     rc3.subject);
        Assert.assertEquals(classes.get(2).getCourseNumber(),rc3.course_number);

        List<String> uids = messageOp.get().second;
        Assert.assertTrue(uids.contains("2202282228069097L"));
        Assert.assertTrue(uids.contains("2202282228069101L"));
    }
}
