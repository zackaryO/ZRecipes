package com.tracks.zrecipes.db;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface RecipeDAO {

    // Insert a Recipe object into the Recipe table
    // If a recipe with the same id already exists, it will be replaced with the new recipe
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRecipe(Recipe recipe);

    @Insert
    void insertAll(Recipe... recipe);

    // Retrieve all Recipe objects from the Recipe table
    // Returns a LiveData object that can be observed for changes
    @Query("SELECT * FROM Recipe")
    LiveData<List<Recipe>> getAllRecipes();

    @Query("Select * from Recipe")
    List<Recipe> getAllc();

    // Retrieve a single Recipe object from the Recipe table by its id
    // Returns a LiveData object that can be observed for changes
    @Query("SELECT * FROM Recipe WHERE id = :id")
    LiveData<Recipe> getRecipeById(int id);

    // Update a Recipe object in the Recipe table
    // The recipe to be updated is identified by its id
    @Update
    void updateRecipe(Recipe recipe);

    // Delete a Recipe object from the Recipe table
    @Delete
    void deleteRecipe(Recipe recipe);
}




