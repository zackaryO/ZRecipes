package com.tracks.zrecipes;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.tracks.zrecipes.db.Recipe;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private List<Recipe> recipeList;
    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(Recipe recipe);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(Recipe recipe);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void clear() {
        this.recipeList.clear();
        notifyDataSetChanged();
    }

    public RecyclerViewAdapter(List<Recipe> recipeList) {
        this.recipeList = recipeList;
    }

    public void setRecipeList(List<Recipe> list) {
        recipeList.clear();
        recipeList.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        Recipe recipe = recipeList.get(position);
        if (recipe != null) {
            holder.item = recipe;
            holder._id = String.valueOf(recipe.getId());
            holder.txtRecipe.setText(recipe.getTitle());

            if (recipe.getImage() != null) {
                Picasso.get().load(recipe.getImage()).into(holder.imgRecipe);
            }

            holder.itemRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(recipe);
                        Log.d("click", " onItemClick RV");
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (recipeList == null) {
            return 0;
        }
        return recipeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public String _id;
        public View itemRoot;
        public TextView txtRecipe;
        public Recipe item;
        public ImageView imgRecipe;

        public ViewHolder(View view) {
            super(view);
            itemRoot = view;
            txtRecipe = itemRoot.findViewById(R.id.txtTitle);
            imgRecipe = itemView.findViewById(R.id.recipe_image);

            itemRoot.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (onItemLongClickListener != null) {
                        onItemLongClickListener.onItemLongClick(item);
                        Log.d("click", "onItemLongClick RV");
                    }
                    return true;
                }
            });
        }
    }
}
