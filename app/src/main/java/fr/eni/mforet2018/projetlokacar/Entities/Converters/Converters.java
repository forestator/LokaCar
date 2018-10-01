package fr.eni.mforet2018.projetlokacar.Entities.Converters;

import android.arch.persistence.room.TypeConverter;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class Converters {

    @TypeConverter
    public Date toDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    @TypeConverter
    public LocalDate ToLocalDate(Date date) {
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
}
