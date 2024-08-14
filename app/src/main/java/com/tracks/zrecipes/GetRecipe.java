// java/com/tracks/zrecipes/GetRecipe.java
package com.tracks.zrecipes;

import android.os.AsyncTask;
import android.util.Log;


import com.google.gson.Gson;
import com.tracks.zrecipes.db.Recipe;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetRecipe extends AsyncTask<String, Void, Recipe[]> {
    public GetRecipe() {
    }

    public interface OnCourseListComplete {
        void ProcessCourseList(Recipe[] recipes);
    }

    private OnCourseListComplete mListener;

    public void SetOnCourseComplete(OnCourseListComplete listener) {
        mListener = listener;
    }
    @Override
    protected Recipe[] doInBackground(String... strings) {
        OkHttpClient client = new OkHttpClient();

        // Build the HTTP request with headers and query parameters
        Request request = new Request.Builder()
                .url("https://api.spoonacular.com/recipes/findByIngredients?apiKey=0c1e5e126eb64e61835075b300a8f5a7&ingredients=" + strings[0] + "&number=75")
                .get()
                .addHeader("limitLicense", "true")
                .build();

        Log.d("cuisine1", strings[0]);
        try {
            // Send the HTTP request and get the response
            Response response = client.newCall(request).execute();

            // Check the response code
            int responseCode = response.code();
            if (responseCode == 200) {
                // If response code is 200 (OK), continue with parsing JSON
                // Get the JSON string from the response body
                String jsonString = response.body().string();

                // Parse the JSON string into an array of Recipe objects using Gson
                Gson gson = new Gson();
                Recipe[] recipes = gson.fromJson(jsonString, Recipe[].class);

                // Return the Recipe array
                return recipes;
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
    protected void onPostExecute(Recipe[] recipes) {
        super.onPostExecute(recipes);
        if (mListener != null) {
            mListener.ProcessCourseList(recipes);
        }
    }
}
