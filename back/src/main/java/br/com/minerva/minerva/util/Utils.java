package br.com.minerva.minerva.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class Utils {

    public static Long DateTimeToMoodleDate(LocalDateTime localDate){
        return localDate.toInstant(ZoneOffset.ofHours(-3)).toEpochMilli()/1000;
    }

    public static Long DateToMoodleDate(LocalDate localDate){
        return Utils.DateTimeToMoodleDate(localDate.atTime(0,0));
    }

}
