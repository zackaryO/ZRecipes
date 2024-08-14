// java/com/tracks/zrecipes/GetSingleRecipe.java
package com.tracks.zrecipes;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetSingleRecipe extends AsyncTask<String, Void, RecipeCard> {

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

        // Build the HTTP request
        Request request = new Request.Builder()
                .url("https://api.spoonacular.com/recipes/" + strings[0] + "/card?apiKey=0c1e5e126eb64e61835075b300a8f5a7")
                .get()
                .build();

        try {
            // Send the HTTP request and get the response
            Response response = client.newCall(request).execute();

            // Check the response code
            int responseCode = response.code();
            if (responseCode == 200) {
                // Parse the JSON response to extract the image URL
                String responseBody = response.body().string();
                JSONObject jsonObject = new JSONObject(responseBody);
                String imageUrl = jsonObject.getString("url");

                // Create a new RecipeCard object and set the image URL
                RecipeCard recipeCard = new RecipeCard();
                recipeCard.setImageUrl(imageUrl);

                return recipeCard;
            } else {
                Log.e("GetSingleRecipe", "Error response code: " + responseCode);
                return null;
            }
        } catch (IOException | JSONException e) {
            Log.e("GetSingleRecipe", "Exception: " + e.getMessage());
            return null;
        }
    }

    @Override
    protected void onPostExecute(RecipeCard recipeCard) {
        if (listener != null) {
            listener.onRecipeLoaded(recipeCard);
        }
    }
}
