package com.westgoten.dreambuilder.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.westgoten.dreambuilder.R;
import com.westgoten.dreambuilder.WallsCalculationData;

public class BrickSelectionFragment extends Fragment {
    private FragmentManager fragmentManager;
    private CalculationResultFragment calculationResultFragment;
    private Bundle arguments;
    private WallsCalculationData wallsCalculationData;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        arguments = getArguments();
        if (arguments != null)
            wallsCalculationData = (WallsCalculationData) arguments.getSerializable(CalculationTypeSelectionFragment.USER_INPUT);
        else
            wallsCalculationData = new WallsCalculationData();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentManager = getFragmentManager();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.walls);

        calculationResultFragment = new CalculationResultFragment();

        ScrollView rootView = (ScrollView) inflater.inflate(R.layout.fragment_brick_selection, container, false);
        LinearLayout viewContainer = (LinearLayout) rootView.getChildAt(0);

        CardView brickOne = (CardView) viewContainer.getChildAt(2);
        brickOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wallsCalculationData.bricks = (int) Math.ceil((37.59 * ((wallsCalculationData.totalWallsLength *
                        wallsCalculationData.height) - wallsCalculationData.totalGapArea) * 1.1));

                arguments.putSerializable(CalculationTypeSelectionFragment.USER_INPUT, wallsCalculationData);
                calculationResultFragment.setArguments(arguments);

                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, calculationResultFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        CardView brickTwo = (CardView) viewContainer.getChildAt(3);
        brickTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wallsCalculationData.bricks = (int) Math.ceil((27.7 * ((wallsCalculationData.totalWallsLength *
                        wallsCalculationData.height) - wallsCalculationData.totalGapArea) * 1.1));

                arguments.putSerializable(CalculationTypeSelectionFragment.USER_INPUT, wallsCalculationData);
                calculationResultFragment.setArguments(arguments);

                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, calculationResultFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        CardView brickThree = (CardView) viewContainer.getChildAt(4);
        brickThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wallsCalculationData.bricks = (int) Math.ceil((29.76 * ((wallsCalculationData.totalWallsLength *
                        wallsCalculationData.height) - wallsCalculationData.totalGapArea) * 1.1));

                arguments.putSerializable(CalculationTypeSelectionFragment.USER_INPUT, wallsCalculationData);
                calculationResultFragment.setArguments(arguments);

                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, calculationResultFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        return rootView;
    }
}
