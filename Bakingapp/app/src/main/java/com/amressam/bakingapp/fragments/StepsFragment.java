package com.amressam.bakingapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.amressam.bakingapp.R;
import com.amressam.bakingapp.adapters.StepsRecyclerViewAdapter;
import com.amressam.bakingapp.classes.Steps;
import com.amressam.bakingapp.databinding.StepsFragmentBinding;

import java.io.Serializable;
import java.util.ArrayList;


public class StepsFragment extends Fragment implements StepsRecyclerViewAdapter.AdapterOnClickHandler {

    private ArrayList<Steps> mStepsArrayList;
    private StepsFragmentBinding mStepsFragmentBinding;
    private StepsRecyclerViewAdapter mStepsRecyclerViewAdapter;

    OnStepClickListener mCallback;

    public interface OnStepClickListener {
        void onStepSelected(int position);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallback = (OnStepClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnImageClickListener");
        }
    }

    public StepsFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mStepsFragmentBinding = DataBindingUtil.inflate(inflater,R.layout.steps_fragment,container,false);
        View view = mStepsFragmentBinding.getRoot();
        mStepsRecyclerViewAdapter = new StepsRecyclerViewAdapter(new ArrayList<Steps>(),getContext(),this);
        mStepsFragmentBinding.stepsRecyclerView.setAdapter(mStepsRecyclerViewAdapter);
        if(savedInstanceState!=null){
            mStepsArrayList = (ArrayList<Steps>) savedInstanceState.getSerializable("MORA");
        }
        mStepsRecyclerViewAdapter.loadNewData(mStepsArrayList);

        return view;
    }

    public void setStepsArrayList(ArrayList<Steps> stepsArrayList) {
        mStepsArrayList = stepsArrayList;

    }

    @Override
    public void onStepClick(int position) {
        mCallback.onStepSelected(position);
    }

    @Override
    public void onSaveInstanceState(Bundle currentState) {
        currentState.putSerializable("MORA",(Serializable) mStepsArrayList);
    }

}
