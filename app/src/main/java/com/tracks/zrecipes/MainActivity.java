package com.tracks.zrecipes;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;


import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
//import com.google.firebase.FirebaseApp;
//import com.google.firebase.analytics.FirebaseAnalytics;
//import com.google.firebase.appcheck.FirebaseAppCheck;
//import com.google.firebase.appcheck.playintegrity.PlayIntegrityAppCheckProviderFactory;
//import com.google.firebase.auth.AuthCredential;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.auth.GoogleAuthProvider;
//import com.google.firebase.firestore.DocumentReference;
//import com.google.firebase.firestore.FirebaseFirestore;
import com.tracks.zrecipes.db.Recipe;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements RecipeListFragment.RecipeListListener,
        SelectIngredientsFragment.SelectIngredientsListener, RecyclerViewAdapter.OnItemClickListener,
        RecipeCardFragment.RecipeCardListener {
    private FragmentManager fm = getSupportFragmentManager();
//    private FirebaseAuth mAuth;
//    private FirebaseAnalytics mFirebaseAnalytics;
//    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private SelectIngredientsFragment ingredientsFragment;
    private static final String TAG = "MainActivity"; // You can choose any string value you prefer
    public static final String KEY_TITLE = "recipe_card";
    private static final int RC_SIGN_IN = 9001;

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
//        FirebaseApp.initializeApp(/*context=*/ this);
//        FirebaseAppCheck firebaseAppCheck = FirebaseAppCheck.getInstance();
//        firebaseAppCheck.installAppCheckProviderFactory(
//                PlayIntegrityAppCheckProviderFactory.getInstance());
//
//        FirebaseApp.initializeApp(this);
//        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        setContentView(R.layout.activity_main);
//        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void ShowViewFrag(int primaryKey) {
        Log.d("test", "calling information for recipe: " + primaryKey);
        RecipeCardFragment viewFragment = new RecipeCardFragment();
        Bundle bundle = new Bundle();
        String recipeID = String.valueOf(primaryKey);
        bundle.putString("recipeID", recipeID);
        Log.d("this", "calling information FOR recipe: " + primaryKey);
        viewFragment.setArguments(bundle);
        fm.beginTransaction()
                .replace(R.id.fragContainer, viewFragment)
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
        RecipeCardFragment viewFragment = new RecipeCardFragment();
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
//    private void showLoginDialog() {
//        // Create a GoogleSignInOptions object
//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken(getString(R.string.default_web_client_id))
//                .requestEmail()
//                .build();
//
//        // Create a GoogleSignInClient object
//        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
//
//        // Show the login popup dialog
//        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
//        startActivityForResult(signInIntent, RC_SIGN_IN);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == RC_SIGN_IN) {
//            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
//            try {
//                // Google Sign In was successful, authenticate with Firebase
//                GoogleSignInAccount account = task.getResult(ApiException.class);
//                firebaseAuthWithGoogle(account);
//            } catch (ApiException e) {
//                // Google Sign In failed, handle the error
//                Log.e(TAG, "Google Sign In failed", e);
//            }
//        }
//    }
//
//    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
//        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
//
//        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
//        mAuth.signInWithCredential(credential)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG, "signInWithCredential:success");
//                            FirebaseUser user = mAuth.getCurrentUser();
//                            // Update UI with the user's information
//                            // ...
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            Log.w(TAG, "signInWithCredential:failure", task.getException());
//                            Toast.makeText(MainActivity.this, "Authentication failed.",
//                                    Toast.LENGTH_SHORT).show();
//                            // Update UI with the error message
//                            // ...
//                        }
//                    }
//                });
//    }
//
//    @Override
//    public void Login() {
//        showLoginDialog();
//    }

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

    // save the URL to firebase
//    @Override
//    public void save(String recipeCardURL) {
//
//        Map<String, Object> data = new HashMap<>();
//        data.put("KEY_TITLE", recipeCardURL);
//
//        db.collection("Recipes")
//                .add(data)
////                .document("Saved Recipes")
////                .set(data)
//                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                    @Override
//                    public void onSuccess(DocumentReference documentReference) {
//                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.w(TAG, "Error adding document", e);
//                    }
//                });
//    }

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

    // Add a logout function to sign out the current user
//    private void logout() {
//        FirebaseAuth.getInstance().signOut();
//        // Update UI after logout, such as redirecting to login page or clearing user data
//        // ...
//    }




}

