package com.amressam.bakingapp.adapters;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.amressam.bakingapp.MainActivity;
import com.amressam.bakingapp.R;
import com.amressam.bakingapp.classes.Recipes;
import com.amressam.bakingapp.classes.Steps;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;


public class RecipesRecyclerViewAdapter extends RecyclerView.Adapter<RecipesRecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "RecipesRecyclerViewAdap";

    private List<Recipes> mRecipes;

    private List<Steps> mSteps;

    private Context mContext;

    private final RecipesRecyclerViewAdapter.AdapterOnClickHandler mClickHandler;

    public interface AdapterOnClickHandler {
        void onRecipeClick(int position);
    }


    public RecipesRecyclerViewAdapter(List<Recipes> recipes,List<Steps> steps, Context context,RecipesRecyclerViewAdapter.AdapterOnClickHandler clickHandler) {
        mRecipes=recipes;
        mSteps = steps;
        mContext = context;
        mClickHandler = clickHandler;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //called by the layout manager when it needs a new view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_recipes, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Recipes recipes = mRecipes.get(position);
        ArrayList<Steps> recipeSteps = new ArrayList<Steps>();
        for(int i=0;i<mSteps.size();i++){
            if(mRecipes.get(position).getRecipe_name().equals(mSteps.get(i).getRecipe_name())){
                Steps current_step = mSteps.get(i);
                recipeSteps.add(current_step);
            }
        }
        if(recipeSteps.size()!=0){
            Glide.with(holder.recipe_image)
                    .load(recipeSteps.get(recipeSteps.size()-1).getVideoURL())
                    .centerCrop()
                    .placeholder(R.drawable.placeholder)
                    .into(holder.recipe_image);
        }
        holder.recipe_name.setText(recipes.getRecipe_name());
        holder.recipe_servings.setText(recipes.getServings()+" Serving");

    }

    @Override
    public int getItemCount() {
        if (null == mRecipes) return 0;
        return mRecipes.size();
    }

    public void loadNewRecipesData(List<Recipes> recipes) {
        this.mRecipes = recipes;
        notifyDataSetChanged();
    }
    public void loadNewStepsData(List<Steps> steps) {
        this.mSteps=steps;
        notifyDataSetChanged();
    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView recipe_image;
        public TextView recipe_name;
        public TextView recipe_servings;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.recipe_image = (ImageView) itemView.findViewById(R.id.recipe_image);
            this.recipe_name = (TextView) itemView.findViewById(R.id.recipe_name);
            this.recipe_servings = (TextView) itemView.findViewById(R.id.recipe_servings);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition =getAdapterPosition();
            mClickHandler.onRecipeClick(adapterPosition);
        }
    }
}
