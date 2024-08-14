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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tracks.zrecipes.db.AppDataBase;
import com.tracks.zrecipes.db.Recipe;

import java.util.ArrayList;
import java.util.List;

public class RecipeListFragment extends Fragment implements RecyclerViewAdapter.OnItemClickListener, RecyclerViewAdapter.OnItemLongClickListener {

    // Define ARG_PARAM1 and ARG_PARAM2
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

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
        if (recipe != null) {
            String recipeId = String.valueOf(recipe.getId());
            RecipeJsonFragment fragment = RecipeJsonFragment.newInstance(recipeId);

            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragContainer, fragment)
                    .addToBackStack(null)
                    .commit();

            Log.d("click", "Navigated to RecipeJsonFragment for recipe ID: " + recipeId);
        }
    }

    @Override
    public void onItemLongClick(Recipe recipe) {
        // Handle long click if needed
    }

    public interface RecipeListListener {
        void ShowViewFrag(int primaryKey);
        void clearIngredients();
    }

    public RecipeListFragment() {
        // Required empty public constructor
    }

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

        Context context = getContext();

        recyclerView = root.findViewById(R.id.userRecyleV);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);
        recyclerView.addItemDecoration(new RecyclerViewItemDecoration(spacingInPixels));
        adapter = new RecyclerViewAdapter(new ArrayList<>());

        adapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Recipe recipe) {
                if (recipe != null) {
                    String recipeId = String.valueOf(recipe.getId());
                    RecipeJsonFragment fragment = RecipeJsonFragment.newInstance(recipeId);

                    getParentFragmentManager().beginTransaction()
                            .replace(R.id.fragContainer, fragment)
                            .addToBackStack(null)
                            .commit();

                    Log.d("click", "Navigated to RecipeJsonFragment for recipe ID: " + recipeId);
                }
            }
        });

        int spanCount = getResources().getConfiguration().screenWidthDp / 180; // 180dp per item
        if (spanCount < 2) spanCount = 2;

        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, spanCount);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        new ViewModelProvider(this).get(AllRecipesViewModel.class).getAllRecipes(context).observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(@Nullable List<Recipe> recipes) {
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
            if (parent.getChildAdapterPosition(view) == 0) {
                outRect.top = space;
            } else {
                outRect.top = 0;
            }
        }
    }

    public void GetRecipe(String recipes) {
        task = new GetRecipe();
        task.SetOnCourseComplete(new GetRecipe.OnCourseListComplete() {
            @Override
            public void ProcessCourseList(Recipe[] recipes) {
                if (recipes != null) {
                    adapter.clear();
                    for (Recipe recipe : recipes) {
                        recipeList.add(recipe);
                        save(recipe);
                        Log.d("test", recipe.getTitle());
                    }
                }
            }
        });
        task.execute(recipes);
    }

    public void save(Recipe recipes) {
        final int id = recipes.getId();
        final String title = recipes.getTitle();
        final int usedIngredientCount = recipes.getUsedIngredientCount();
        final String image = recipes.getImage();
        final int missedIngredientCount = recipes.getMissedIngredientCount();
        final int likes = recipes.getLikes();
        new Thread(() -> {
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
