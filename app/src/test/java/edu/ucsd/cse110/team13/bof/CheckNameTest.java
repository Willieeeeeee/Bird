package edu.ucsd.cse110.team13.bof;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import edu.ucsd.cse110.team13.bof.model.ClassSize;
import edu.ucsd.cse110.team13.bof.model.IClass;
import edu.ucsd.cse110.team13.bof.model.Quarter;
import edu.ucsd.cse110.team13.bof.model.mClass;
import edu.ucsd.cse110.team13.bof.model.mProfile;

public class CheckNameTest {
    @Test
    public void SimplegetName() {
        IClass[] p2Classes = {
                new mClass(2020, Quarter.FA, ClassSize.SMALL , "CSE", "130"),
                new mClass(2020, Quarter.SP, ClassSize.MEDIUM, "ECE", "109"),
        };
        mProfile profile = new mProfile("skyler", "Picture URL", p2Classes);
        assertEquals("skyler",profile.getFirstName()) ;
    }
}
