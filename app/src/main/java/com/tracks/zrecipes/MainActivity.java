package com.tracks.zrecipes;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.snackbar.Snackbar;

// Google Play Core imports
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.tasks.Task;

import com.tracks.zrecipes.db.Recipe;

public class MainActivity extends AppCompatActivity implements RecipeListFragment.RecipeListListener,
        SelectIngredientsFragment.SelectIngredientsListener, RecyclerViewAdapter.OnItemClickListener,
        RecipeCardFragment.RecipeCardListener {

    private FragmentManager fm = getSupportFragmentManager();
    private SelectIngredientsFragment ingredientsFragment;
    private static final String TAG = "MainActivity"; // You can choose any string value you prefer
    public static final String KEY_TITLE = "recipe_card";
    private static final int RC_SIGN_IN = 9001;

    // Fields for flexible update
    private AppUpdateManager appUpdateManager;
    private static final int REQUEST_CODE_FLEXIBLE_UPDATE = 123;
    private InstallStateUpdatedListener installStateUpdatedListener;

    @Override
    protected void onResume() {
        super.onResume();

        fm.beginTransaction()
                .replace(R.id.fragContainer, new SelectIngredientsFragment(), "selectIg")
                .addToBackStack(null)
                .commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize appUpdateManager for Flexible Updates
        appUpdateManager = AppUpdateManagerFactory.create(this);

        // Listener for the install state
        installStateUpdatedListener = installState -> {
            if (installState.installStatus() == InstallStatus.DOWNLOADED) {
                // Prompt the user to complete the update.
                popupSnackbarForCompleteUpdate();
            }
        };

        appUpdateManager.registerListener(installStateUpdatedListener);

        // Check for updates
        checkForUpdates();
    }

    private void checkForUpdates() {
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();

        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)) {
                // Request the update.
                try {
                    appUpdateManager.startUpdateFlowForResult(
                            appUpdateInfo,
                            AppUpdateType.FLEXIBLE,
                            this,
                            REQUEST_CODE_FLEXIBLE_UPDATE);
                } catch (IntentSender.SendIntentException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void popupSnackbarForCompleteUpdate() {
        Snackbar snackbar = Snackbar.make(
                findViewById(android.R.id.content),
                "An update has just been downloaded.",
                Snackbar.LENGTH_INDEFINITE);

        snackbar.setAction("RESTART", view -> appUpdateManager.completeUpdate());
        snackbar.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_FLEXIBLE_UPDATE) {
            if (resultCode != RESULT_OK) {
                // Handle cancellation or failure...
            }
        }
    }

    @Override
    protected void onStop() {
        if (appUpdateManager != null) {
            appUpdateManager.unregisterListener(installStateUpdatedListener);
        }
        super.onStop();
    }

    @Override
    public void ShowViewFrag(int primaryKey) {
        Log.d("MainActivity", "calling information for recipe: " + primaryKey);
        String recipeID = String.valueOf(primaryKey);
        RecipeJsonFragment fragment = RecipeJsonFragment.newInstance(recipeID);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragContainer, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void clearIngredients() {
        if (ingredientsFragment == null) {
            ingredientsFragment = (SelectIngredientsFragment) getSupportFragmentManager().findFragmentByTag("selectIg");
        }
        if (ingredientsFragment != null) {
            ingredientsFragment.clearIngredients();
        }
    }

    @Override
    public void GetIngredients(String ingredients) {
        RecipeListFragment viewFragment = new RecipeListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("ingredientList", ingredients);
        viewFragment.setArguments(bundle);
        fm.beginTransaction()
                .replace(R.id.fragContainer, viewFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void Login() {

    }

    @Override
    public void onItemClick(Recipe recipe) {
        Log.d("test", "calling information for recipe: " + recipe);
        // Change this to load RecipeJsonFragment instead of RecipeCardFragment
        RecipeJsonFragment viewFragment = new RecipeJsonFragment();
        Bundle bundle = new Bundle();
        String recipeID = String.valueOf(recipe.getId());
        bundle.putString("recipeID", recipeID);
        Log.d("this", "calling information FOR recipe: " + recipe);
        viewFragment.setArguments(bundle);
        fm.beginTransaction()
                .replace(R.id.fragContainer, viewFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void SavedFragment() {
        SavedFragment viewFragment = new SavedFragment();
        fm.beginTransaction()
                .replace(R.id.fragContainer, viewFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void Logout() {
//        logout();
    }

    @Override
    public void save(String recipeCardURL) {

    }

    @Override
    public void home() {
        SelectIngredientsFragment viewFragment = new SelectIngredientsFragment();
        fm.beginTransaction()
                .replace(R.id.fragContainer, viewFragment)
                .addToBackStack(null)
                .commit();
    }
}
