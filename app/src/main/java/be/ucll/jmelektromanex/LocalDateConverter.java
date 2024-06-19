package be.ucll.jmelektromanex;

import androidx.room.TypeConverter;

import java.time.LocalDate;

public class LocalDateConverter {
    @TypeConverter
    public static LocalDate toLocalDate(String dateString){
        return dateString == null ? null : LocalDate.parse(dateString);
    }

    @TypeConverter
    public static String fromLocalDate (LocalDate date){
        return date == null ? null : date.toString();
    }
}
