package com.tracks.zrecipes;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
//import com.google.firebase.firestore.DocumentReference;
//import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecipeCardAdapter extends ArrayAdapter<RecipeCard> {
    private ImageView imageView;
    private View root;
    private Context mContext;
    private ArrayList<RecipeCard> mRecipeCards;
//    private FirebaseFirestore db;

    public RecipeCardAdapter(Context context, ArrayList<RecipeCard> recipeCards) {
        super(context, 0, recipeCards);
        mContext = context;
        mRecipeCards = recipeCards;
//        db = FirebaseFirestore.getInstance();
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.list_item_recipe, parent, false);
        }

        final RecipeCard recipeCard = getItem(position);
        imageView = convertView.findViewById(R.id.imageView); // Fix: change `root` to `convertView`
        TextView textView = convertView.findViewById(R.id.textView);
        Button btnDelete = convertView.findViewById(R.id.btnDelete);
        // if (recipeCard.getCard2() != null && !recipeCard.getCard2().isEmpty()) {
        //      Picasso.get().load(recipeCard.getCard2()).into(imageView);
        // } else {
        //     imageView.setImageResource(R.drawable.baseline_broken_image_24);
        // }
        // Set click listener for delete button
// Set click listener for delete button
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Delete Recipe")
                        .setMessage("Are you sure you want to delete this recipe?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Delete the recipe from Firestore
                                String collectionName = "recipes";
                                //String documentId = recipeCard.getCard(); // Use the appropriate document ID or name from your RecipeCard object
//                                DocumentReference docRef = FirebaseFirestore.getInstance().collection(collectionName).document(documentId);
//                                docRef.delete()
//                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                            @Override
//                                            public void onComplete(@NonNull Task<Void> task) {
//                                                if (task.isSuccessful()) {
//                                                     Remove the recipe from the recipeCards ArrayList
//                                                    mRecipeCards.remove(position);
//                                                    notifyDataSetChanged();
//                                                    Log.d("test", "Recipe deleted successfully");
//                                                } else {
//                                                    Log.w("test", "Error deleting recipe", task.getException());
//                                                }
//                                            }
//                                        });
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Do nothing
                            }
                        })
                        .show();
            }
        });


        return convertView;
    }
}
