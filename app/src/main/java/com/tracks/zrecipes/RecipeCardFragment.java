// java/com/tracks/zrecipes/RecipeCardFragment.java
package com.tracks.zrecipes;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.FileOutputStream;
import java.io.IOException;

public class RecipeCardFragment extends Fragment implements GetSingleRecipe.OnRecipeLoadedListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final int NONE = 0;
    private static final int DRAG = 1;
    private static final int ZOOM = 2;

    private String mParam1;
    private String mParam2;
    private View root;
    private String recipeID;
    private ImageView recipe_card_image;
    private RecipeCardListener mCallBack;
    private String recipeCardURL;
    private FloatingActionButton createNew;

    private ScaleGestureDetector scaleGestureDetector;
    private Matrix matrix;
    private int mode;
    private float lastTouchX;
    private float lastTouchY;
    private float startTouchX;
    private float startTouchY;
    private float startMatrixX;
    private float startMatrixY;
    private float minScale = 1.0f;
    private float maxScale = 3.0f;

    public RecipeCardFragment() {
        // Required empty public constructor
    }

    public interface RecipeCardListener {
        void save(String recipeCardURL);
        void home();
    }

    public static RecipeCardFragment newInstance(String param1, String param2) {
        RecipeCardFragment fragment = new RecipeCardFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        try {
            mCallBack = (RecipeCardListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() +
                    " must implement RecipeCardListener");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getSingleRecipe(recipeID);
        recipe_card_image = root.findViewById(R.id.imgProtien);
        createNew = root.findViewById(R.id.floatingActionButton);
        scaleGestureDetector = new ScaleGestureDetector(getActivity(), new MyScaleGestureListener());
        matrix = new Matrix();
        mode = NONE;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            recipeID = bundle.getString("recipeID");
        }
        return root = inflater.inflate(R.layout.fragment_recipe_card, container, false);
    }

    public void getSingleRecipe(String recipeID) {
        if (recipeID != null) {
            GetSingleRecipe task = new GetSingleRecipe(this);
            task.execute(recipeID);
        }
    }

    @Override
    public void onRecipeLoaded(RecipeCard recipeCard) {
        if (recipeCard != null && recipeCard.getImageUrl() != null) {
            // Use Picasso or Glide to load the image
            Picasso.get()
                    .load(recipeCard.getImageUrl())
                    .into(recipe_card_image);

            // Set click listener for the floating action button to save the image
            createNew.setOnClickListener(v -> saveImageToGalleryFromUrl(recipeCard.getImageUrl()));

            recipe_card_image.setOnTouchListener((v, event) -> RecipeCardFragment.this.onTouchEvent(event));
        } else {
            Log.e("RecipeCardFragment", "RecipeCard is null or has no image URL.");
            Toast.makeText(getActivity(), "Failed to load recipe. Please try again later.", Toast.LENGTH_LONG).show();
        }
    }


    private void saveImageToGalleryFromUrl(String imageUrl) {
        Picasso.get().load(imageUrl).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                saveImageToGallery(bitmap);
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                Toast.makeText(getActivity(), "Failed to save image", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                // Do nothing
            }
        });
    }


    // Method to save the image to the gallery
    private void saveImageToGallery(Bitmap bitmap) {
        // Create a file name with a timestamp
        String fileName = "recipe_image_" + System.currentTimeMillis() + ".jpg";
        String filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/" + fileName;
        try {
            // Save the bitmap as a JPEG file
            FileOutputStream fileOutputStream = new FileOutputStream(filePath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();

            // Insert the image into the gallery
            MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), filePath, fileName, null);

            // Display a toast message to indicate successful image saving
            Toast.makeText(getActivity(), "Image saved to gallery", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            // Display a toast message to indicate failed image saving
            Toast.makeText(getActivity(), "Failed to save image", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.save_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.redirHome:
                // Call the home() method of the callback interface when the home menu item is selected
                mCallBack.home();
                return true;
            default:
                break;
        }
        return false;
    }

    public boolean onTouchEvent(MotionEvent event) {
        // Handle touch events for zooming and dragging the image
        scaleGestureDetector.onTouchEvent(event);

        float currentTouchX = event.getX();
        float currentTouchY = event.getY();

        int action = event.getActionMasked();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                // Save the initial touch position and image matrix for drag gesture
                lastTouchX = currentTouchX;
                lastTouchY = currentTouchY;
                startTouchX = currentTouchX;
                startTouchY = currentTouchY;
                startMatrixX = matrixValues[Matrix.MTRANS_X];
                startMatrixY = matrixValues[Matrix.MTRANS_Y];
                mode = DRAG;
                break;
            case MotionEvent.ACTION_MOVE:
                if (mode == DRAG) {
                    // Calculate the change in touch position
                    float deltaX = currentTouchX - lastTouchX;
                    float deltaY = currentTouchY - lastTouchY;

                    // Apply the translation to the image matrix
                    matrix.postTranslate(deltaX, deltaY);
                    fixTrans();
                    recipe_card_image.setImageMatrix(matrix);
                } else if (mode == ZOOM && event.getPointerCount() == 2) {
                    // Handle zooming with two fingers
                    float newDistance = getDistance(event);
                    float scale = newDistance / oldDistance;
                    oldDistance = newDistance;

                    // Limit the scaling within the defined minScale and maxScale values
                    float currentScale = getCurrentScale();
                    float newScale = currentScale * scale;
                    if (newScale < minScale) {
                        scale = minScale / currentScale;
                    } else if (newScale > maxScale) {
                        scale = maxScale / currentScale;
                    }

                    // Apply the scaling to the image matrix
                    matrix.postScale(scale, scale, midPointX, midPointY);
                    fixTrans();
                    recipe_card_image.setImageMatrix(matrix);
                }

                lastTouchX = currentTouchX;
                lastTouchY = currentTouchY;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                // Save the old distance between two fingers for zooming
                oldDistance = getDistance(event);
                mode = ZOOM;
                break;
            case MotionEvent.ACTION_POINTER_UP:
                mode = NONE;
                break;
            case MotionEvent.ACTION_UP:
                // Check for a click event (ACTION_UP) and call performClick()
                if (isAClick()) {
                    recipe_card_image.performClick();
                }
                break;
        }

        return true;
    }

    private void fixTrans() {
        float[] matrixValues = new float[9];
        matrix.getValues(matrixValues);
        float transX = matrixValues[Matrix.MTRANS_X];
        float transY = matrixValues[Matrix.MTRANS_Y];

        float imageWidth = recipe_card_image.getDrawable().getIntrinsicWidth();
        float imageHeight = recipe_card_image.getDrawable().getIntrinsicHeight();
        float screenWidth = recipe_card_image.getWidth();
        float screenHeight = recipe_card_image.getHeight();

        float minX = screenWidth - imageWidth * matrixValues[Matrix.MSCALE_X];
        float minY = screenHeight - imageHeight * matrixValues[Matrix.MSCALE_Y];
        float maxX = 0;
        float maxY = 0;

        // Prevent the right edge of the image from moving past the left edge of the screen
        if (transX > maxX) {
            transX = maxX;
        }
        // Prevent the left edge of the image from moving past the right edge of the screen
        else if (transX < minX) {
            transX = minX;
        }

        // Prevent the bottom edge of the image from moving past the top edge of the screen
        if (transY > maxY) {
            transY = maxY;
        }
        // Prevent the top edge of the image from moving past the bottom edge of the screen
        else if (transY < minY) {
            transY = minY;
        }

        matrixValues[Matrix.MTRANS_X] = transX;
        matrixValues[Matrix.MTRANS_Y] = transY;
        matrix.setValues(matrixValues);
    }


    private class MyScaleGestureListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        // Implement this method as before
        // ... (existing code)
    }

    // Helper method to calculate the distance between two fingers
    private float getDistance(MotionEvent event) {
        float deltaX = event.getX(0) - event.getX(1);
        float deltaY = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }

    // Helper method to calculate the midpoint between two fingers
    private float midPointX;
    private float midPointY;

    private void midPoint(MotionEvent event) {
        midPointX = (event.getX(0) + event.getX(1)) / 2;
        midPointY = (event.getY(0) + event.getY(1)) / 2;
    }

    // Helper method to calculate the current scale
    private float[] matrixValues = new float[9];

    private float getCurrentScale() {
        matrix.getValues(matrixValues);
        return matrixValues[Matrix.MSCALE_X];
    }

    // Variables to handle click detection
    private boolean isClickDetected;
    private float oldDistance;

    // Helper method to check if it's a click event
    private boolean isAClick() {
        // Calculate the movement distance
        float deltaX = Math.abs(lastTouchX - startTouchX);
        float deltaY = Math.abs(lastTouchY - startTouchY);

        // Check if it's a click (below a certain threshold)
        isClickDetected = deltaX < 5 && deltaY < 5;
        return isClickDetected;
    }
}