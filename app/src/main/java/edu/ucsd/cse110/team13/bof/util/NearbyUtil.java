package edu.ucsd.cse110.team13.bof.util;

import static edu.ucsd.cse110.team13.bof.util.CSVUtil.parse;
import static edu.ucsd.cse110.team13.bof.util.CSVUtil.toCSVSimple;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Pair;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import edu.ucsd.cse110.team13.bof.MainActivity;
import edu.ucsd.cse110.team13.bof.model.IClass;
import edu.ucsd.cse110.team13.bof.model.IProfile;
import edu.ucsd.cse110.team13.bof.model.database.ClassSizeConverter;
import edu.ucsd.cse110.team13.bof.model.database.ProfileWithCourses;
import edu.ucsd.cse110.team13.bof.model.database.QuarterConverter;
import edu.ucsd.cse110.team13.bof.model.database.RoomCourse;
import edu.ucsd.cse110.team13.bof.model.database.RoomProfile;
import edu.ucsd.cse110.team13.bof.model.mClass;
import edu.ucsd.cse110.team13.bof.model.mProfile;

public class NearbyUtil {
    public static final int    ENCODE_COLUMN  =  5 ;
    public static final String WAVE_PREFIX    = "wave";

    /* Sync sending wave request across the app - Variables */
    private static MainActivity mainActivity   = null;
    private static List<String> wavedUids      = new ArrayList<>();

    /* Sync sending wave request across the app - Methods */
    public static void setMainActivity(MainActivity mainActivity) { NearbyUtil.mainActivity = mainActivity; }
    public static void clearWavedUids() { wavedUids.clear(); }
    public static boolean isWaved(String uid) { return wavedUids.contains(uid); }
    public static List<String> getWavedUids() { return wavedUids; }
    public static void waveTo(String uid) {
        if(!isWaved(uid)) { wavedUids.add(uid); }
    }

    // You can call this method in an activity with YourActivity.this
    public static boolean hasNearbyPermission(Context context) {
        boolean locPermission = ContextCompat.checkSelfPermission(
                context, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED;
        boolean blePermission = ContextCompat.checkSelfPermission(
                context, Manifest.permission.BLUETOOTH)
                == PackageManager.PERMISSION_GRANTED;

        return locPermission && blePermission;
    }

    public static String encodeProfile(IProfile profile) {
        ArrayList<ArrayList<String>> profileTable = new ArrayList<>();

        // 1. Prepare table row for name
        ArrayList<String> nameRow = new ArrayList<>();
        nameRow.add(profile.getFirstName());
        nameRow.add("");
        nameRow.add("");
        nameRow.add("");
        nameRow.add("");
        profileTable.add(nameRow);

        // 2. Prepare table row for headshot url
        ArrayList<String> urlRow = new ArrayList<>();
        urlRow.add(profile.getHeadshotUrl());
        urlRow.add("");
        urlRow.add("");
        urlRow.add("");
        urlRow.add("");
        profileTable.add(urlRow);

        // 3+. Prepare table rows for previous classes
        for(IClass c : profile.getClasses()) {
            ArrayList<String> cRow = new ArrayList<>();
            cRow.add(String.valueOf(c.getYear()));
            cRow.add(c.getQuarter().getQuarterCode());
            cRow.add(c.getSize().getSizeCode());
            cRow.add(c.getSubject());
            cRow.add(c.getCourseNumber());
            profileTable.add(cRow);
        }

        return toCSVSimple(profileTable);
    }

    public static Optional<IProfile> decodeProfile(String str) {
        Optional<ArrayList<ArrayList<String>>> profileOp = parse(str,ENCODE_COLUMN);
        if(!profileOp.isPresent() || profileOp.get().size() <= 2) { return Optional.empty(); }
        ArrayList<ArrayList<String>> profileArr = profileOp.get();

        mProfile result = new mProfile(profileArr.get(0).get(0), profileArr.get(1).get(0));
        for(int i = 2; i < profileArr.size(); i++) {
            mClass c = new mClass(Integer.parseInt(profileArr.get(i).get(0)),
                    QuarterConverter.stringToQuarter(profileArr.get(i).get(1)),
                    ClassSizeConverter.stringToSize(profileArr.get(i).get(2)),
                    profileArr.get(i).get(3),
                    profileArr.get(i).get(4));
            result.addClass(c);
        }

        return Optional.of(result);
    }

    public static String encodeMessage(ProfileWithCourses pwc, List<String> uids) {
        ArrayList<ArrayList<String>> messageTable = new ArrayList<>();

        // 1 - uid
        ArrayList<String> uidRow = new ArrayList<>();
        uidRow.add(pwc.getUid());
        uidRow.add("");
        uidRow.add("");
        uidRow.add("");
        uidRow.add("");
        messageTable.add(uidRow);

        // 2 - name
        ArrayList<String> nameRow = new ArrayList<>();
        nameRow.add(pwc.getFirstName());
        nameRow.add("");
        nameRow.add("");
        nameRow.add("");
        nameRow.add("");
        messageTable.add(nameRow);

        // 3 - url
        ArrayList<String> urlRow = new ArrayList<>();
        urlRow.add(pwc.getHeadshotUrl());
        urlRow.add("");
        urlRow.add("");
        urlRow.add("");
        urlRow.add("");
        messageTable.add(urlRow);

        // 4 - past classes
        for(IClass c : pwc.getClasses()) {
            ArrayList<String> classRow = new ArrayList<>();
            classRow.add(String.valueOf(c.getYear()));
            classRow.add(c.getQuarter().getQuarterCode());
            classRow.add(c.getSubject());
            classRow.add(c.getCourseNumber());
            classRow.add(c.getSize().getSizeName());
            messageTable.add(classRow);
        }

        // 5 - wave uids
        for(String uid : uids) {
            ArrayList<String> waveRow = new ArrayList<>();
            waveRow.add(uid);
            waveRow.add(WAVE_PREFIX);
            waveRow.add("");
            waveRow.add("");
            waveRow.add("");
            messageTable.add(waveRow);
        }

        return toCSVSimple(messageTable);
    }

    public static Optional<Pair<ProfileWithCourses, List<String>>> decodeMessage(String msg) {
        Optional<ArrayList<ArrayList<String>>> messageOp = parse(msg,ENCODE_COLUMN);
        if(!messageOp.isPresent() || messageOp.get().size() <= 3) { return Optional.empty(); }
        ArrayList<ArrayList<String>> messageTable = messageOp.get();

        RoomProfile profile = new RoomProfile(
                messageTable.get(0).get(0),
                messageTable.get(1).get(0),
                messageTable.get(2).get(0));

        ArrayList<RoomCourse> courses = new ArrayList<>();
        ArrayList<String>     uids    = new ArrayList<>();
        for(int i = 3; i < messageTable.size(); i++) {
            if(messageTable.get(i).get(1).equals(WAVE_PREFIX)) {
                uids.add(messageTable.get(i).get(0));
            } else {
                RoomCourse c = new RoomCourse(
                        profile.uid,
                        Integer.parseInt(messageTable.get(i).get(0)),
                        QuarterConverter.stringToQuarter(messageTable.get(i).get(1)),
                        ClassSizeConverter.stringToSize(messageTable.get(i).get(4)),
                        messageTable.get(i).get(2),
                        messageTable.get(i).get(3));
                courses.add(c);
            }
        }

        return Optional.of(Pair.create(new ProfileWithCourses(profile, courses),uids));
    }
}
