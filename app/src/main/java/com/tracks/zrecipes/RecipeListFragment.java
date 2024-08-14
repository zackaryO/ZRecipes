// java/com/tracks/zrecipes/RecipeListFragment.java
package com.tracks.zrecipes;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tracks.zrecipes.db.AppDataBase;
import com.tracks.zrecipes.db.Recipe;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecipeListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipeListFragment extends Fragment implements RecyclerViewAdapter.OnItemClickListener, RecyclerViewAdapter.OnItemLongClickListener {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View root;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private String recipes;
    private GetRecipe task;
    ArrayList<Recipe> recipeList = new ArrayList<>();


    private FloatingActionButton createNew;

    private RecipeListListener mCallBack;

    @Override
    public void onItemClick(Recipe recipe) {

    }

    @Override
    public void onItemLongClick(Recipe recipe) {

    }

    public interface RecipeListListener {
        void ShowViewFrag(int primaryKey);
        void clearIngredients();
    }

    public RecipeListFragment() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CourseListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecipeListFragment newInstance(String param1, String param2) {
        RecipeListFragment fragment = new RecipeListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            recipes = bundle.getString("ingredientList");

        }
        deleteCourse();
        GetRecipe(recipes);
        // Inflate the layout for this fragment
        return root = inflater.inflate(R.layout.fragment_recipe_list, container, false);
    }




    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        try {
            mCallBack = (RecipeListListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity + " must implement RecipeListListener");
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        // Get a reference to the "create new" FAB and set an OnClickListener to handle clicks
        Context context = getContext();

        // Set up the RecyclerView and adapter for displaying the list of recipes
        recyclerView = root.findViewById(R.id.userRecyleV);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);
        recyclerView.addItemDecoration(new RecyclerViewItemDecoration(spacingInPixels));
        adapter = new RecyclerViewAdapter(new ArrayList<>());

        // Set the OnItemClickListener and OnItemLongClickListener for the adapter to handle user interaction with each recipe
        adapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Recipe recipe) {
                int primaryKey = recipe.getId();
                mCallBack.ShowViewFrag(primaryKey);
                Log.d("click", " onItemClick ");
            }
        });

        // Set the layout manager and adapter for the RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);

        // Set the RecyclerView to not have a fixed size so that changes to the adapter are not ignored
        recyclerView.setHasFixedSize(false);

        // Set up an observer for changes to the list of recipes stored in the ViewModel
        new ViewModelProvider(this).get(AllRecipesViewModel.class).getAllRecipes(context).observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(@Nullable List<Recipe> recipes) {
                // If the list of recipes changes, update the adapter with the new list
                if (recipes != null) {
                    adapter.setRecipeList(recipes);
                }
            }
        });
    }

    public class RecyclerViewItemDecoration extends RecyclerView.ItemDecoration {
        private int space;

        public RecyclerViewItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.left = space;
            outRect.right = space;
            outRect.bottom = space;
            // Add top margin only for the first item to avoid double space between items
            if (parent.getChildAdapterPosition(view) == 0) {
                outRect.top = space;
            } else {
                outRect.top = 0;
            }
        }
    }

    // This method gets a list of recipes asynchronously and updates the adapter with the new data
    public void GetRecipe(String recipes) {
        // Create a new GetRecipe AsyncTask object
        task = new GetRecipe();

        // Set the OnCourseListComplete listener to handle the list of recipes returned by the AsyncTask
        task.SetOnCourseComplete(new GetRecipe.OnCourseListComplete() {
            @Override
            public void ProcessCourseList(Recipe[] recipes) {
                // If the list of recipes is not null, clear the adapter and add each recipe to the list
                if (recipes != null) {
                    adapter.clear();
                    for (Recipe recipe : recipes) {
                        recipeList.add(recipe);
                        // Save the recipe to a database or another storage mechanism
                        save(recipe);
                        // Log the recipe title for debugging purposes
                        Log.d("test", recipe.getTitle());
                    }
                }
            }
        });

        // Execute the AsyncTask with an empty string parameter
        task.execute(recipes);
    }

    public void save(Recipe recipes) {
        final int id = recipes.getId();
        final String title = recipes.getTitle();
        final int usedIngredientCount = recipes.getUsedIngredientCount();
        final String image = recipes.getImage();
        final int missedIngredientCount = recipes.getMissedIngredientCount();
        final int likes = recipes.getLikes();
//        final String end = recipes.getEnd();
        new Thread(() -> {
            //background
            AppDataBase db = AppDataBase.getInstance(getContext());
            db.recipeDAO().insertAll(new Recipe(id, title, image, usedIngredientCount, missedIngredientCount, likes));
            Log.d("test", id + title + image + usedIngredientCount);
        }).start();
    }

    public void deleteCourse() {
        new Thread(() -> {

            AppDataBase db = AppDataBase.getInstance(getContext());
            List<Recipe> recipes = db.recipeDAO().getAllc();
            for (int i = 0; i < recipes.size(); i++) {
                db.recipeDAO().deleteRecipe(recipes.get(i));
            }
        }).start();
        mCallBack.clearIngredients();
    }

}