package com.capa.infrafix.localdatabase;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Array;
import java.util.List;

public class Converters {

    private static Gson gson = new Gson();

    @TypeConverter
    public static String fromStringList(List<String> source) {
        return gson.toJson(source);
    }

    @TypeConverter
    public static List<String> fromString(String source) {
        return gson.fromJson(source, new TypeToken<List<String>>() {}.getType());
    }
}
