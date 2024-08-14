// java/com/tracks/zrecipes/db/SingleRecipeDAO.java
package com.tracks.zrecipes.db;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

public interface SingleRecipeDAO {
    @Query("Select * from `single_recipe`")
    LiveData<List<Ingredient>> getAll();

    @Query("Select * from `single_recipe`")
    List<Ingredient> getAllc();


    @Query("Select * from single_recipe WHERE name =:name")
    Ingredient getName(String name);

//    @Query("Select * FROM `single_recipe` WHERE course_name LIKE :name LIMIT 1")
//    SingleRecipe findByName(String name);
@Insert
void insertAll(Ingredient... singleRecipes);

    @Delete
    void deleteSingleRecipe(Ingredient singleRecipes);

    @Update
    void updateSingleRecipe(Ingredient singleRecipes);


}
