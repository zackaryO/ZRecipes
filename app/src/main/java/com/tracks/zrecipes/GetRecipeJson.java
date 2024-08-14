package com.tracks.zrecipes;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetRecipeJson extends AsyncTask<String, Void, String> {

    public interface OnRecipeJsonLoadedListener {
        void onRecipeJsonLoaded(String recipeJson);
    }

    private final OnRecipeJsonLoadedListener listener;

    public GetRecipeJson(OnRecipeJsonLoadedListener listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String... strings) {
        OkHttpClient client = new OkHttpClient();

        // Build the HTTP request
        Request request = new Request.Builder()
                .url("https://api.spoonacular.com/recipes/" + strings[0] + "/information?apiKey=0c1e5e126eb64e61835075b300a8f5a7")
                .get()
                .build();

        try {
            // Send the HTTP request and get the response
            Response response = client.newCall(request).execute();

            // Check the response code
            int responseCode = response.code();
            if (responseCode == 200 && response.body() != null) {
                // Return the JSON response as a string
                String jsonResponse = response.body().string();
                Log.d("GetRecipeJson", "Received JSON: " + jsonResponse);
                return jsonResponse;
            } else {
                Log.e("GetRecipeJson", "Error response code: " + responseCode);
                return null;
            }
        } catch (IOException e) {
            Log.e("GetRecipeJson", "Exception: " + e.getMessage(), e);
            return null;
        }
    }

    @Override
    protected void onPostExecute(String recipeJson) {
        if (listener != null) {
            listener.onRecipeJsonLoaded(recipeJson);
        }
    }
}
