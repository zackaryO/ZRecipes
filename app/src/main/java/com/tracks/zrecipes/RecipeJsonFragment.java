package com.tracks.zrecipes;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import org.json.JSONException;
import org.json.JSONObject;

public class RecipeJsonFragment extends Fragment {

    private static final String ARG_RECIPE_ID = "recipeId";
    private String recipeId;
    private TextView recipeTitleTextView;
    private TextView recipeSummaryTextView;
    private TextView recipeIngredientsTextView;

    public RecipeJsonFragment() {
        // Required empty public constructor
    }

    // Method to create a new instance of the fragment with the recipe ID as an argument
    public static RecipeJsonFragment newInstance(String recipeId) {
        RecipeJsonFragment fragment = new RecipeJsonFragment();
        Bundle args = new Bundle();
        args.putString(ARG_RECIPE_ID, recipeId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            recipeId = getArguments().getString(ARG_RECIPE_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_recipe_json, container, false);
        Log.e("RecipeJsonFragment", "onCreateView");

        // Find the TextViews by their IDs
        recipeTitleTextView = root.findViewById(R.id.recipeTitleTextView);
        recipeSummaryTextView = root.findViewById(R.id.recipeSummaryTextView);
        recipeIngredientsTextView = root.findViewById(R.id.recipeIngredientsTextView);
        Button downloadCardButton = root.findViewById(R.id.downloadCardButton);

        // Fetch and display the recipe information using the recipeId
        if (recipeId != null) {
            Log.e("RecipeJsonFragment", "recipeId is not null");
            fetchRecipeJson(recipeId);
        }

        downloadCardButton.setOnClickListener(v -> downloadRecipeCard(recipeId));

        return root;
    }

    private void fetchRecipeJson(String recipeId) {
        new GetRecipeJson(json -> {
            if (json != null) {
                // Log the JSON data
                Log.d("RecipeJsonFragment", "JSON Data: " + json.toString());

                // Extract and display the relevant recipe information
                displayRecipeInfo(json);
            } else {
                Log.e("RecipeJsonFragment", "Failed to load JSON data.");
                recipeTitleTextView.setText("Failed to load recipe data.");
            }
        }).execute(recipeId);
    }

    private void displayRecipeInfo(String jsonString) {
        try {
            JSONObject jsonObject = new JSONObject(jsonString);

            // Extract relevant fields from the JSON
            String title = jsonObject.getString("title");
            String summary = jsonObject.getString("summary").replaceAll("<[^>]*>", ""); // Removing HTML tags
            StringBuilder ingredients = new StringBuilder();

            for (int i = 0; i < jsonObject.getJSONArray("extendedIngredients").length(); i++) {
                ingredients.append("- ")
                        .append(jsonObject.getJSONArray("extendedIngredients").getJSONObject(i).getString("original"))
                        .append("\n");
            }

            // Display the extracted information in the TextViews
            recipeTitleTextView.setText(title);
            recipeSummaryTextView.setText(summary);
            recipeIngredientsTextView.setText(ingredients.toString());

        } catch (JSONException e) {
            Log.e("RecipeJsonFragment", "Failed to parse JSON", e);
            recipeTitleTextView.setText("Error parsing recipe data.");
        }
    }

    private void downloadRecipeCard(String recipeId) {
        // Your existing method to download the recipe card
        new GetSingleRecipe(recipeCard -> {
            if (recipeCard != null && recipeCard.getImageUrl() != null) {
                // Implement logic to download the card or display a success message
                String imageUrl = recipeCard.getImageUrl();
                // Here you can download the image using the imageUrl or display it
            } else {
                // Handle the error case
                // For example, show a toast or log the error
            }
        }).execute(recipeId);
    }
}
