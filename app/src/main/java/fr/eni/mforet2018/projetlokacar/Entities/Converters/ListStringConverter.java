package fr.eni.mforet2018.projetlokacar.Entities.Converters;

import android.arch.persistence.room.TypeConverter;

import java.util.ArrayList;
import java.util.List;

public class ListStringConverter {

    @TypeConverter
    public static String[] toStringTable(List<String> value) {
        return (String[]) value.toArray();
    }

    @TypeConverter
    public static List<String> toList(String[] value) {
        List<String> ls = new ArrayList<>();
        for (String string : value) {
            ls.add(string);
        }
        return ls;
    }
}
