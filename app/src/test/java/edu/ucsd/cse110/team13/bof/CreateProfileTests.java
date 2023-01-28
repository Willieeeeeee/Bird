package edu.ucsd.cse110.team13.bof;

import org.junit.Test;

import static org.junit.Assert.*;

import edu.ucsd.cse110.team13.bof.model.ClassSize;
import edu.ucsd.cse110.team13.bof.model.Quarter;
import edu.ucsd.cse110.team13.bof.model.mClass;
import edu.ucsd.cse110.team13.bof.model.mProfile;

public class CreateProfileTests {
    mClass[] classes = { new mClass(2020, Quarter.FA, ClassSize.SMALL, "CSE", "101") };
    mProfile profile = new mProfile("User1", "Picture URL", classes);

    @Test
    public void ProfileNameCheck() {
        assertEquals(profile.getFirstName(), "User1");
    }

    @Test
    public void ProfilePicUrlCheck() {
        assertEquals(profile.getHeadshotUrl(), "Picture URL");
    }

    @Test
    public void ProfileClasses() {
        assertFalse(profile.getClasses().isEmpty() );
    }
}