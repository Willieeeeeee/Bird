package edu.ucsd.cse110.team13.bof;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.ucsd.cse110.team13.bof.model.ClassSize;
import edu.ucsd.cse110.team13.bof.model.IClass;
import edu.ucsd.cse110.team13.bof.model.Quarter;
import edu.ucsd.cse110.team13.bof.model.mProfile;
import edu.ucsd.cse110.team13.bof.model.mClass;

public class mProfileTest {
    @Test
    public void mProfile_constructorsAndGetters() {
        String p1HeadshotUrl = "https://kurzgesagt.org/wp-content/themes/kurzgesagt/library/images/logo.gif";
        mProfile p1 = new mProfile("Kurzgesagt", p1HeadshotUrl);

        assertEquals("Kurzgesagt", p1.getFirstName());
        assertEquals(p1HeadshotUrl, p1.getHeadshotUrl());
        assertEquals(0, p1.getClasses().size());
        assertFalse(p1.isFavorite());

        IClass[] p2Classes = {
                new mClass(2020, Quarter.FA, ClassSize.SMALL, "CSE", "130"),
                new mClass(2020, Quarter.SP, ClassSize.LARGE, "ECE", "109"),
                new mClass(2020, Quarter.SP, ClassSize.TINY , "ECE", "109")
        };
        mProfile p2 = new mProfile("", "", p2Classes);
        p2.favorite = true;

        assertEquals("", p2.getFirstName());
        assertEquals("", p2.getHeadshotUrl());
        assertEquals(2, p2.getClasses().size());
        assertEquals(p2Classes[0], p2.getClasses().get(0));
        assertEquals(p2Classes[1], p2.getClasses().get(1));
        assertTrue(p2.isFavorite());
    }

    @Test
    public void mProfile_commonClassesTest() {
        mProfile p1 = new mProfile("","", new mClass[]{
                new mClass(2020, Quarter.FA, ClassSize.SMALL, "CSE", "130"),
                new mClass(2020, Quarter.FA, ClassSize.SMALL, "MGT", "164"),
                new mClass(2020, Quarter.SP, ClassSize.SMALL, "ECE", "109"),
                new mClass(2019, Quarter.FA, ClassSize.SMALL, "CSE", "15L"),
                new mClass(2019, Quarter.FA, ClassSize.SMALL, "CSE", "30")

        });
        mProfile p2 = new mProfile("diffName","diffUrl", new mClass[]{});
        mProfile p3 = new mProfile("diffName","diffUrl", new mClass[]{
                new mClass(0, Quarter.FA, ClassSize.SMALL, "CSE", "130"),
                new mClass(2, Quarter.FA, ClassSize.SMALL, "MGT", "164")
        });
        mProfile p4 = new mProfile("","", new mClass[]{
                new mClass(2020, Quarter.FA , ClassSize.TINY, "CSE", "130"),
                new mClass(2020, Quarter.FA , ClassSize.TINY, "MGT", "164"),
                new mClass(2020, Quarter.SSS, ClassSize.TINY, "ECE", "109"),
                new mClass(2019, Quarter.FA , ClassSize.TINY, "DCSE", "15L"),
                new mClass(2019, Quarter.FA , ClassSize.TINY, "CSE", "30+")
        });

        assertTrue(p1.hasCommonClasses(p1));
        assertEquals(5, p1.getCommonClasses(p1).size());

        assertFalse(p1.hasCommonClasses(p2));
        assertEquals(0, p1.getCommonClasses(p2).size());

        assertFalse(p1.hasCommonClasses(p3));
        assertEquals(0, p1.getCommonClasses(p3).size());

        assertTrue(p1.hasCommonClasses(p4));
        assertEquals(2, p1.getCommonClasses(p4).size());

        // class size will not be checked,
        //   but it should return the class size from profile whose method gets called
        assertTrue(p1.getCommonClasses(p4).contains(
                new mClass(2020, Quarter.FA, ClassSize.SMALL, "CSE", "130")));
        assertTrue(p1.getCommonClasses(p4).contains(
                new mClass(2020, Quarter.FA, ClassSize.SMALL, "MGT", "164")));
    }
}
