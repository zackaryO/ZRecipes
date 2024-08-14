// java/com/tracks/zrecipes/GetSingleRecipe.java
package com.tracks.zrecipes;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;



import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetSingleRecipe extends AsyncTask<String, Void, RecipeCard> {

    // Define an interface for callback
    public interface OnRecipeLoadedListener {
        void onRecipeLoaded(RecipeCard recipeCard);
    }

    private OnRecipeLoadedListener listener;

    public GetSingleRecipe(OnRecipeLoadedListener listener) {
        this.listener = listener;
    }

    @Override
    protected RecipeCard doInBackground(String... strings) {
        OkHttpClient client = new OkHttpClient();

        // Build the HTTP request with headers and query parameters
        Request request = new Request.Builder()
                .url("https://api.spoonacular.com/recipes/" + strings[0] + "/card.png?apiKey=0c1e5e126eb64e61835075b300a8f5a7")
                .get()
                .build();

        try {
            // Send the HTTP request and get the response
            Response response = client.newCall(request).execute();

            // Check the response code
            int responseCode = response.code();
            if (responseCode == 200) {
                // If response code is 200 (OK), retrieve the image data
                byte[] imageData = response.body().bytes();

                // Create a new RecipeCard object and set the image data
                RecipeCard recipeCard = new RecipeCard();
                recipeCard.setImageData(imageData);

                // Return the RecipeCard object
                return recipeCard;
            } else {
                Log.d("test", String.valueOf(responseCode));
                // If response code indicates an error, throw an exception
                throw new IOException("Error response code: " + responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // If an exception is thrown or response code indicates an error, return null
        return null;
    }

    @Override
    protected void onPostExecute(RecipeCard recipeCard) {
        super.onPostExecute(recipeCard);
        // Call the callback method with the loaded recipeCard data
        if (listener != null) {
            listener.onRecipeLoaded(recipeCard);
        }
    }
}




//public class GetSingleRecipe extends AsyncTask<String, Void, RecipeCard> {
//
//    // Define an interface for callback
//    public interface OnRecipeLoadedListener {
//        void onRecipeLoaded(RecipeCard recipeCard);
//    }
//
//    private OnRecipeLoadedListener listener;
//
//    public GetSingleRecipe(OnRecipeLoadedListener listener) {
//        this.listener = listener;
//    }
//
//    @Override
//    protected RecipeCard doInBackground(String... strings) {
//        OkHttpClient client = new OkHttpClient();
//
//        // Build the HTTP request with headers and query parameters
//        Request request = new Request.Builder()
//                .url("https://api.spoonacular.com/recipes/" + strings[0] + "/card.png?apiKey=0c1e5e126eb64e61835075b300a8f5a7")
//                .get()
//                //.addHeader("limitLicense", "true")
//                .build();
//
//        try {
//            // Send the HTTP request and get the response
//            Response response = client.newCall(request).execute();
//
//            // Check the response code
//            int responseCode = response.code();
//            if (responseCode == 200) {
//                // If response code is 200 (OK), continue with parsing JSON
//                // Get the JSON string from the response body
//                String jsonString = response.body().string();
//
//                // Create a new RecipeCard object and set the card string to the JSON string
//                RecipeCard recipeCard = new RecipeCard();
//                recipeCard.setCard(jsonString);
//
//                // Return the RecipeCard object
//                return recipeCard;
//            } else {
//                Log.d("test", String.valueOf(responseCode));
//                // If response code indicates an error, throw an exception
//                throw new IOException("Error response code: " + responseCode);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        // If an exception is thrown or response code indicates an error, return null
//        return null;
//    }
//
//    @Override
//    protected void onPostExecute(RecipeCard recipeCard) {
//        super.onPostExecute(recipeCard);
//        // Call the callback method with the loaded recipeCard data
//        if (listener != null) {
//            listener.onRecipeLoaded(recipeCard);
//        }
//    }
//}



//package com.example.myapplication;
//
//import android.os.AsyncTask;
//import android.util.Log;
//
//import com.example.myapplication.db.Recipe;
//import com.google.gson.Gson;
//
//import java.io.IOException;
//
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.Response;
//
//public class GetSingleRecipe extends AsyncTask<String, Void, RecipeCard> {
//
//    public GetSingleRecipe() {
//    }
//
//
//    @Override
//    protected RecipeCard doInBackground(String... strings) {
//        OkHttpClient client = new OkHttpClient();
//
//        // Build the HTTP request with headers and query parameters
//        Request request = new Request.Builder()
//                .url("https://api.spoonacular.com/recipes/"+strings[0]+"/card?apiKey=0c1e5e126eb64e61835075b300a8f5a7")
//                .get()
//                //.addHeader("limitLicense", "true")
//                .build();
//
//        try {
//            // Send the HTTP request and get the response
//            Response response = client.newCall(request).execute();
//
//            // Check the response code
//            int responseCode = response.code();
//            if (responseCode == 200) {
//                // If response code is 200 (OK), continue with parsing JSON
//                // Get the JSON string from the response body
//                String jsonString = response.body().string();
//
//                // Create a new RecipeCard object and set the card string to the JSON string
//                RecipeCard recipeCard = new RecipeCard();
//                recipeCard.setCard(jsonString);
//
//                // Return the RecipeCard object
//                return recipeCard;
//            } else {
//                Log.d("test", String.valueOf(responseCode));
//                // If response code indicates an error, throw an exception
//                throw new IOException("Error response code: " + responseCode);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        // If an exception is thrown or response code indicates an error, return null
//        return null;
//    }
//
//}