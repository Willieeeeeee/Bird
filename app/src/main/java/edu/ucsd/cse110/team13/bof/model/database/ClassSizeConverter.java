package edu.ucsd.cse110.team13.bof.model.database;

import androidx.room.ProvidedTypeConverter;
import androidx.room.TypeConverter;

import edu.ucsd.cse110.team13.bof.model.ClassSize;

@ProvidedTypeConverter
public class ClassSizeConverter {
    @TypeConverter
    public static ClassSize stringToSize(String code) {
        if(code == null) { return null; }
        for(ClassSize size : ClassSize.values()) {
            if(size.getSizeName().equals(code)) { return size; }
        }
        return null;
    }

    @TypeConverter
    public static String sizeToString(ClassSize size) {
        return size.getSizeName();
    }
}
