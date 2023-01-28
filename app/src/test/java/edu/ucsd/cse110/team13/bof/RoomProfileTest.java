package edu.ucsd.cse110.team13.bof;

import org.junit.Assert;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import edu.ucsd.cse110.team13.bof.model.database.RoomProfile;

public class RoomProfileTest {
    @Test
    public void constructorTest() {
        String uuid = "A";
        RoomProfile p = new RoomProfile(uuid, "Eddie", "abc.xyz");
        Assert.assertEquals("A", p.uid);
        Assert.assertEquals("Eddie",   p.firstName);
        Assert.assertEquals("abc.xyz", p.url);
        Assert.assertFalse(p.favorite);
    }
}
