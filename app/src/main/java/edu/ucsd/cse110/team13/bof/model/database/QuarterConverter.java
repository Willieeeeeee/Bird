package edu.ucsd.cse110.team13.bof.model.database;

import androidx.room.ProvidedTypeConverter;
import androidx.room.TypeConverter;

import edu.ucsd.cse110.team13.bof.model.Quarter;

@ProvidedTypeConverter
public class QuarterConverter {
    @TypeConverter
    public static Quarter stringToQuarter(String code) {
        if(code == null) { return null; }
        for(Quarter qtr : Quarter.values()) {
            if(qtr.getQuarterCode().equals(code)) { return qtr; }
        }
        return null;
    }

    @TypeConverter
    public static String quarterToString(Quarter qtr) {
        return qtr.getQuarterCode();
    }
}