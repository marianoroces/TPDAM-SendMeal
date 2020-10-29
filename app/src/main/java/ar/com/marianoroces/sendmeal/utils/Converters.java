package ar.com.marianoroces.sendmeal.utils;

import androidx.room.TypeConverter;

import java.util.Date;

import ar.com.marianoroces.sendmeal.enums.TipoEnvio;

public class Converters {

    @TypeConverter
    public static TipoEnvio fromStringToEnum(String string){
        return TipoEnvio.valueOf(string);
    }

    @TypeConverter
    public static String fromEnumToString(TipoEnvio tipoEnvio){
        return tipoEnvio.toString();
    }

    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}
