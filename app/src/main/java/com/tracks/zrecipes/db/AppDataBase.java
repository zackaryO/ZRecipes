package com.tracks.zrecipes.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

// exportSchema should be true except for when app is launched, it should not be
@Database(entities = {Recipe.class}, version = 2, exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {

    private static AppDataBase instance;

    public static AppDataBase getInstance(Context context) {
        if(instance != null) {
            return instance;
        }
        instance = Room.databaseBuilder(context, AppDataBase.class, "recipe-database")
                .fallbackToDestructiveMigration()
                .build();
        return instance;
    }

    public abstract RecipeDAO recipeDAO();

}


//package com.example.myapplication.db;
//
//
//import android.content.Context;
//
//import androidx.room.Database;
//import androidx.room.Room;
//import androidx.room.RoomDatabase;
//
//// exportSchema should be true except for when app is launched, it should not be
//@Database(entities = {Recipe.class}, version = 1, exportSchema = false)
//public abstract class AppDataBase extends RoomDatabase {
//
//    private static AppDataBase instance;
//
//    public static AppDataBase getInstance(Context context){
//        if(instance != null){
//            return instance;
//        }
//        instance = Room.databaseBuilder(context, AppDataBase.class, "recipe-database").build();
//        return instance;
//    }
//
//    public abstract RecipeDAO recipeDAO();
//
//}
