package com.cleverson.gerenciadorleitura.utils;

import androidx.room.TypeConverter;

import java.util.Date;

public class ConversorDatas {
        @TypeConverter
        public static Date toDate(Long timestamp) {
            return timestamp == null ? null : new Date(timestamp);
        }

        @TypeConverter
        public static Long toTimestamp(Date date) {
            return date == null ? null : date.getTime();
        }

}
