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

import com.amressam.bakingapp.R;
import com.amressam.bakingapp.classes.Recipes;
import com.amressam.bakingapp.classes.Steps;

import java.util.ArrayList;
import java.util.List;


public class StepsRecyclerViewAdapter extends RecyclerView.Adapter<StepsRecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "StepsRecyclerViewAdapte";

    private ArrayList<Steps> mSteps;

    private Context mContext;

    private final StepsRecyclerViewAdapter.AdapterOnClickHandler mClickHandler;

    public interface AdapterOnClickHandler {
        void onStepClick(int position);
    }


    public StepsRecyclerViewAdapter(ArrayList<Steps> steps, Context context, StepsRecyclerViewAdapter.AdapterOnClickHandler clickHandler) {
        mSteps = steps;
        mContext = context;
        mClickHandler = clickHandler;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //called by the layout manager when it needs a new view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_steps, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Steps steps = mSteps.get(position);
        holder.step.setText("Step "
                + position
                + ": "
                + steps.getShortDescription());

    }

    @Override
    public int getItemCount() {
        if (null == mSteps) return 0;
        return mSteps.size();
    }

    public void loadNewData(ArrayList<Steps> steps) {
        this.mSteps = steps;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView thumbnail;
        public TextView step;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.thumbnail = (ImageView) itemView.findViewById(R.id.video_image);
            this.step = (TextView) itemView.findViewById(R.id.step_number);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            mClickHandler.onStepClick(adapterPosition);
        }
    }
}
