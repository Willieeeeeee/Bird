package edu.ucsd.cse110.team13.bof;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import edu.ucsd.cse110.team13.bof.model.ClassSize;
import edu.ucsd.cse110.team13.bof.model.IClass;
import edu.ucsd.cse110.team13.bof.model.Quarter;
import edu.ucsd.cse110.team13.bof.model.mClass;
import edu.ucsd.cse110.team13.bof.model.mProfile;

public class CheckURLTest {
        @Test
        public void SimplegetURL() {
            IClass[] p2Classes = {
                    new mClass(2020, Quarter.FA, ClassSize.SMALL, "CSE", "130"),
            };
            mProfile profile = new mProfile("skyler", "https://upload.wikimedia.org/wikipedia/commons/thumb/3/3c/Los_Angeles_Lakers_logo.svg/800px-Los_Angeles_Lakers_logo.svg.png", p2Classes);
            assertEquals("https://upload.wikimedia.org/wikipedia/commons/thumb/3/3c/Los_Angeles_Lakers_logo.svg/800px-Los_Angeles_Lakers_logo.svg.png",profile.getHeadshotUrl());
        }
    }
