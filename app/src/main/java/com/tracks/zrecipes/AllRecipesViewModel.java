// java/com/tracks/zrecipes/AllRecipesViewModel.java
package com.tracks.zrecipes;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;


import com.tracks.zrecipes.db.AppDataBase;
import com.tracks.zrecipes.db.Recipe;

import java.util.List;
//querries database to populate a LiveData<List
public class AllRecipesViewModel extends ViewModel {
    // LiveData is used to handle data from a database, keeps it from the main thread
    private LiveData<List<Recipe>> recipeList;

    public  LiveData<List<Recipe>> getAllRecipes(Context context){


        AppDataBase db = AppDataBase.getInstance(context);

        recipeList = db.recipeDAO().getAllRecipes();

        return recipeList;
    }

    public static class GetCanvasClass {
    }
}
